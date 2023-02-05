package com.victorrubia.tfg.data.repository.user.datasourceImpl

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Wearable
import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDatasource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Implementation of the [UserRemoteDataSource] that communicates with the Wearable DataLayer API.
 * This implementation is based on the Google Android Wearable API.
 *
 * @property context The application context.
 */
class UserRemoteDataSourceImpl(
    private val tfgService : TFGService,
    private val context : Context
) : UserRemoteDatasource {

    // Best node to send the message to.
    private var bestNodeID: String? = null
    // Boolean var to check if the best node has been found.
    private var _nodesConnected : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    override suspend fun getUser(email: String, password: String): Response<User> = tfgService.getUserInfo(email,password)

    override suspend fun rememberPassword(email: String) = tfgService.requestPasswordRecovery(email)

    override suspend fun sendApiKeyToWear(apiKey : String) {
        updateBestNode(context)
        broadcastMessage(context, apiKey)
    }

    override suspend fun isWearConnected(): Boolean? {
        val job = CoroutineScope(Dispatchers.IO).launch {
            updateBestNode(context)
        }

        job.join()

        return _nodesConnected.value
    }

    /**
     * Updates the best node ID to send the message to.
     *
     * @param context The application context.
     */
    private fun updateBestNode(context: Context) : Boolean {
        var prueba = false
        try {
            val info = Tasks.await(
                Wearable.getCapabilityClient(context)
                    .getCapability("api_key", CapabilityClient.FILTER_REACHABLE)
            )
            for (node in info.nodes) {
                if (node.isNearby) {
                    bestNodeID = node.id
                    _nodesConnected.postValue(true)
                    prueba = true
                }
            }
        } catch (e: Exception) {
            // Don't call printStackTrace() because that would make an infinite loop
            Log.e("MyTag", e.toString())
        }
        return prueba
    }

    /**
     * Broadcasts the api key request to the best node.
     *
     * @param context The application context.
     */
    fun broadcastMessage(context: Context, apiKey : String) {
        Handler(Looper.getMainLooper()).post {
            try {
                if (bestNodeID != null) {
                    Wearable.getMessageClient(context).sendMessage(bestNodeID!!, "api_key", apiKey.toByteArray())
                    Log.d("MyTag", "API KEY Broadcasted: " + apiKey)
                }
            } catch (e: java.lang.Exception) {
                // Don't call printStackTrace() because that would make an infinite loop
                Log.e("apde", e.toString())
            }
        }
    }
}