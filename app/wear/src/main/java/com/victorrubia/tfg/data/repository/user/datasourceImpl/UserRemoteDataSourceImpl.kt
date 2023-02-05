package com.victorrubia.tfg.data.repository.user.datasourceImpl

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Wearable
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the [UserRemoteDataSource] that communicates with the Wearable DataLayer API.
 * This implementation is based on the Google Android Wearable API.
 *
 * @property context The application context.
 */
class UserRemoteDataSourceImpl(
    private var context: Context
) : UserRemoteDataSource {

    // Best node to send the message to.
    private var bestNodeID: String? = null
    // Boolean var to check if the best node has been found.
    private var _nodesConnected : MutableLiveData<Boolean?> = MutableLiveData<Boolean?>(false)


    override suspend fun requestUser() {
        updateBestNode(context)
        initMessageBroadcaster(context)
        broadcastMessage(context)
    }

    /**
     * Updates the best node ID to send the message to.
     *
     * @param context The application context.
     */
    private fun updateBestNode(context: Context) {
        try {
            val info = Tasks.await(
                Wearable.getCapabilityClient(context)
                    .getCapability("api_key_sender", CapabilityClient.FILTER_REACHABLE)
            )
            for (node in info.nodes) {
                if (node.isNearby) {
                    bestNodeID = node.id
                    _nodesConnected.postValue(true)
                }
            }
        } catch (e: Exception) {
            // Don't call printStackTrace() because that would make an infinite loop
            Log.e("apde", e.toString())
        }
    }

    /**
     * Initializes the message broadcaster.
     *
     * @param context The application context.
     */
    private fun initMessageBroadcaster(context: Context?) {

        if (context != null) {
            Wearable.getCapabilityClient(context).addListener({ updateBestNode(context) }, "apde_receive_logs")
        }

        // Can't do this on the main thread
        CoroutineScope(Dispatchers.IO).launch {
            updateBestNode(context!!)
        }

    }

    /**
     * Broadcasts the api key request to the best node.
     *
     * @param context The application context.
     */
    private fun broadcastMessage(context: Context?) {
        Handler(Looper.getMainLooper()).post {
            try {
                if (context != null) {
                    bestNodeID?.let { Wearable.getMessageClient(context).sendMessage(it, "api_key_sender", ByteArray(0)) }
                }
            } catch (e: java.lang.Exception) {
                // Don't call printStackTrace() because that would make an infinite loop
                Log.e("apde", e.toString())
            }
        }
    }

}