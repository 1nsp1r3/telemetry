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
import org.inspir3.telemetry.ui.theme.TelemetryTheme
import org.inspir3.telemetry.view.MainView
import org.inspir3.telemetry.view.Route
import org.inspir3.telemetry.view.SettingsView

class MainActivity : ComponentActivity() {
    private var init = false

    private lateinit var configuration: Configuration
    private lateinit var intentService: Intent

    private val appViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(I3.TAG, "MainActivity.onCreate()")
        super.onCreate(savedInstanceState)

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
                            configuration = configuration,
                            settingRoute = { currentRoute = Route.SETTINGS },
                            temperature = Telemetry.temperature,
                            pressure = Telemetry.pressure,
                        )

                        Route.SETTINGS -> SettingsView(
                            configuration = configuration,
                            mainRoute = { currentRoute = Route.MAIN },
                        )
                    }
                }//Surface
            }//TestComposeTheme
        }//setContent
    }

    override fun onDestroy() {
        Log.d(I3.TAG, "MainActivity.onDestroy()")
        super.onDestroy()
    }

    private fun init() {
        Log.d(I3.TAG, "MainActivity.init()")

        if (this.init) return

        this.configuration = Configuration(this)
        this.configuration.load()
        //Yurk!
        Telemetry.temperaturePoints = this.configuration.temperaturePoints
        Telemetry.pressurePoints = this.configuration.pressurePoints
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
}
