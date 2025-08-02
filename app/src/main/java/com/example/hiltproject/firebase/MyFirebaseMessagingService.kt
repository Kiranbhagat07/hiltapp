package com.example.hiltproject.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            notificationHelper.showNotification(
                title = it.title ?: "New Message",
                body = it.body ?: "You have a new notification"
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Save/send token to backend if needed
        Log.d("FCM", "New Token: $token")
    }
}

