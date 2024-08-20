/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity

abstract class ForegroundService<T : ComponentActivity>(
    private val icon: Int,
    private val title: String,
    private val content: String,
    private val cls: Class<T>,
) : Service() {
    abstract fun onStart()
    abstract fun onStop()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(I3.TAG, "ForegroundService.onStartCommand()")

        startForeground(
            1,
            NotificationHelper.createNotification(this, this.icon, this.title, this.content, this.cls),
            FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE,
        )

        this.onStart()

        return START_STICKY //After an out enough memory, restart the service
    }

    override fun onDestroy() {
        Log.d(I3.TAG, "ForegroundService.onDestroy()")

        this.onStop()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
