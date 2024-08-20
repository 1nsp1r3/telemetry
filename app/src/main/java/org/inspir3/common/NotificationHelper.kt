/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat

class NotificationHelper {
    companion object {
        private const val CHANNEL_ID = "I3"

        /**
         * Create a channel to send notification
         */
        fun createChannel(context: Context) {
            Log.d(I3.TAG, "NotificationHelper.createChannel()")

            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    "I3_CHANNEL",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        /**
         * Create a notification
         */
        fun <T : ComponentActivity> createNotification(context: Context, icon: Int, title: String, content: String, cls: Class<T>): Notification {
            Log.d(I3.TAG, "NotificationHelper.createNotification($title)")

            return NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent( //When user touch the notification
                    createContentIntent(context, cls)
                )
                .build()
        }

        /**
         * Need <activity android:launchMode="singleInstance"> if you doesn't want a new instance of MainActivity (even the ViewModel!)
         */
        private fun <T : ComponentActivity> createContentIntent(context: Context, cls: Class<T>): PendingIntent {
            val intent = Intent(context, cls)
            return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }
    }
}
