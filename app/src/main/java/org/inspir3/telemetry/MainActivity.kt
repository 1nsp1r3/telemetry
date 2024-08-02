package org.inspir3.telemetry

import android.content.Intent
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

        if (Init.permissionsIsMissing(this)) return
        if (Init.requirementsIsMissing(this)) return
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
                                loadFile(Dir.getDownloadPath(), it.name)
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
