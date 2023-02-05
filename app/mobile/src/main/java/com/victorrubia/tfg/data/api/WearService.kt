package com.victorrubia.tfg.data.api

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.presentation.di.Injector
import javax.inject.Inject

/**
 * WearableListenerService to receive messages from the watch even when the app is not running.
 * This is needed to send the user data to the watch.
 */
class WearService : WearableListenerService(), MessageClient.OnMessageReceivedListener {

    @Inject
    lateinit var factory: GetUserUseCase

    override fun onCreate() {
        super.onCreate()
        (application as Injector).createWearServiceSubComponent().inject(this)
    }

    private val API_KEY_CAPABILITY_NAME = "api_key_sender"

    /**
     * When a message is received from the watch, this method is called.
     *
     * @param messageEvent The message received from the watch.
     */
    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == API_KEY_CAPABILITY_NAME) {
            Log.d("Servicio", "Received API Request");
            val receiverID = messageEvent.sourceNodeId

            var responseLiveData = liveData {
                val user = factory.execute("", "")
                emit(user)
            }

            val observer = Observer<User?> {
                if (it != null) {
                    Log.i("Servicio", "Envío $it")
                    Handler(Looper.getMainLooper()).post {
                        try {
                            Wearable.getMessageClient(applicationContext)
                                .sendMessage(receiverID, "api_key", it.apiKey.toByteArray())
                        } catch (e: java.lang.Exception) {
                            // Don't call printStackTrace() because that would make an infinite loop
                            Log.e("Servicio", "Mensaje no se ha podido mandar. Razón: $e")
                        }
                    }
                } else {
                    Handler(Looper.getMainLooper()).post {
                        try {
                            Wearable.getMessageClient(applicationContext)
                                .sendMessage(receiverID, "error", "errorNoUserLoggedIn".toByteArray())
                        } catch (e: java.lang.Exception) {
                            // Don't call printStackTrace() because that would make an infinite loop
                            Log.e("Servicio", "Mensaje no se ha podido mandar. Razón: $e")
                        }
                    }
                    Log.e("Servicio", "ERROR Servicio")
                }
            }

            Handler(Looper.getMainLooper()).post { responseLiveData.observeForever(observer) }
        }
    }

}