package com.pwr.knif.omatko.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pwr.knif.omatko.R
import java.util.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.HttpURLConnection
import java.net.URL


class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val CHANEL_ID = "OMatKoChannel"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels()
        }

        val notificationId = Random().nextInt(60000)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationTitle = remoteMessage.data["title"]
        val notificationMessage = remoteMessage.data["message"]
        val notificationBitmap = getBitmapfromUrl(remoteMessage.data["image-url"].toString())

        val notificationBuilder = NotificationCompat.Builder(this, CHANEL_ID)
                .setLargeIcon(notificationBitmap)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(notificationTitle)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(notificationMessage))
                .setContentText(notificationMessage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setLights(resources.getColor(R.color.colorBlueOmatko), 500, 500)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val adminChanelName = "OMatKoChannel"
        val adminChanelDescription = "Channel for OMatKo app."
        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(CHANEL_ID, adminChanelName, NotificationManager.IMPORTANCE_LOW)
        adminChannel.description = adminChanelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager.createNotificationChannel(adminChannel)
    }

    private fun getBitmapfromUrl(imageUrl: String): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            null
        }
    }
}