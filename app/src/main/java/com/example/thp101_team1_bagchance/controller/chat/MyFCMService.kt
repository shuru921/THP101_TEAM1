package com.example.thp101_team1_bagchance.controller.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCMService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        var title = ""
        var body = ""
        message.notification?.let {
            title = it.title ?: ""
            body = it.body ?: ""
        }

        val data = message.data["data"]

        val newMessage = "title: $title\nbody: $body\ndata: $data"
        sendLocalBroadcast(newMessage)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("myTag_${javaClass.simpleName}","onNewToken: ${token}")
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        Log.d("myTag_${javaClass.simpleName}","onDeletedMessages")
    }

    private fun sendLocalBroadcast(newMessage: String) {
        val intent = Intent("action_ message")
        val bundle = Bundle()
        bundle.putString("newMessage",newMessage)
        intent.putExtras(bundle)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}