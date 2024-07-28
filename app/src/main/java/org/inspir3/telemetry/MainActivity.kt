package org.inspir3.telemetry

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH_SCAN
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import org.inspir3.common.Dialog
import org.inspir3.common.I3
import org.inspir3.common.NotificationHelper
import org.inspir3.common.file.Dir
import org.inspir3.telemetry.ui.theme.TelemetryTheme
import org.inspir3.telemetry.view.LoadView
import org.inspir3.telemetry.view.MainView
import org.inspir3.telemetry.view.Route
import org.inspir3.telemetry.view.SettingsView

class MainActivity : ComponentActivity() {
    private var init = false

    private lateinit var intentService: Intent

    private val appViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(I3.TAG, "MainActivity.onCreate()")
        super.onCreate(savedInstanceState)

        if (this.permissionsIsMissing()) return
        this.init()

        setContent {
            TelemetryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentRoute by rememberSaveable { mutableStateOf(Route.MAIN) }

                    when (currentRoute) {
                        Route.MAIN -> MainView(
                            settingRoute = { currentRoute = Route.SETTINGS },
                            loadRoute = { currentRoute = Route.LOAD },
                            temperature = Telemetry.temperature,
                            pressure = Telemetry.pressure,
                        )

                        Route.SETTINGS -> SettingsView(
                            mainRoute = { currentRoute = Route.MAIN },
                            context = this,
                        )

                        Route.LOAD -> LoadView(
                            mainRoute = { currentRoute = Route.MAIN },
                            loadFile = {
                                currentRoute = Route.MAIN
                                loadFile(Dir.getDownloadPath(), it)
                            },
                            files = Dir.list(Dir.getDownloadPath()),
                        )
                    }
                }//Surface
            }//TestComposeTheme
        }//setContent
    }

    override fun onDestroy() {
        Log.d(I3.TAG, "MainActivity.onDestroy()")
        super.onDestroy()

        applicationContext.stopService(this.intentService)
    }

    private fun permissionsIsMissing(): Boolean {
        Log.d(I3.TAG, "MainActivity.permissionsIsMissing()")
        val missingPermissions: MutableList<String> = mutableListOf()
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_DENIED) {
            Log.e(I3.TAG, "Permission ACCESS_FINE_LOCATION is missing")
            missingPermissions.add("Position")
        }
        if (ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_DENIED) {
            Log.e(I3.TAG, "Permission ACCESS_COARSE_LOCATION is missing")
            missingPermissions.add("Position")
        }
        if (ContextCompat.checkSelfPermission(this, BLUETOOTH_SCAN) == PERMISSION_DENIED) {
            Log.e(I3.TAG, "Permission BLUETOOTH_SCAN is missing")
            missingPermissions.add("Nearby device (Bluetooth)")
        }
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PERMISSION_DENIED) {
            Log.e(I3.TAG, "Permission POST_NOTIFICATIONS is missing")
            missingPermissions.add("Notifications")
        }
        val missingResume = missingPermissions.distinct()
        if (missingResume.isNotEmpty()) {
            val message = missingResume.joinToString(
                separator = "\n",
            ) { "- $it" }
            Dialog.alert(this, "Some permissions are missing", message, "Exit") {
                Log.i(I3.TAG, "Exiting...")
                this.finishAffinity()
            }
            return true
        }
        return false
    }

    private fun init() {
        Log.d(I3.TAG, "MainActivity.init()")

        if (this.init) return

        Settings.load(this)
        this.startForegroundService()
        this.init = true
    }

    private fun startForegroundService() {
        Log.d(I3.TAG, "MainActivity.startForegroundService()")

        if (this.appViewModel.foregroundServiceRunning) {
            Log.i(I3.TAG, "ForegroundService already running")
            return
        }

        NotificationHelper.createChannel(this)
        this.intentService = Intent(this, MainForegroundService::class.java)
        applicationContext.startForegroundService(this.intentService)
        this.appViewModel.foregroundServiceRunning = true
    }

    private fun loadFile(path: String, filename: String) {
        Log.i(I3.TAG, "MainActivity.loadFile(filename: $filename)")

        applicationContext.stopService(this.intentService)
        Telemetry.loadFile(path, filename)
    }
}
