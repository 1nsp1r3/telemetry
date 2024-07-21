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
import io.reactivex.rxjava3.disposables.Disposable
import org.inspir3.common.I3
import org.inspir3.common.NotificationHelper
import org.inspir3.telemetry.ble.BleGapScanCallBack
import org.inspir3.telemetry.ui.theme.TelemetryTheme
import org.inspir3.telemetry.view.MainView
import org.inspir3.telemetry.view.Route
import org.inspir3.telemetry.view.SettingsView

class MainActivity : ComponentActivity() {
    private var init = false

    private lateinit var bleListenerDisposable: Disposable
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

        this.bleListenerDisposable.dispose()
    }

    private fun init() {
        Log.d(I3.TAG, "MainActivity.init()")

        if (this.init) return

        this.configuration = Configuration(this)
        this.configuration.load()
        this.bleListenerDisposable = this.bleListener()

        this.startForegroundService()

        this.init = true
    }

    private fun startForegroundService() {
        Log.d(I3.TAG, "MainActivity.startForegroundService()")

        if (this.appViewModel.foregroundServiceRunning){
            Log.i(I3.TAG, "ForegroundService already running")
            return
        }

        NotificationHelper.createChannel(this)
        this.intentService = Intent(this, MainForegroundService::class.java)
        applicationContext.startForegroundService(this.intentService)
        this.appViewModel.foregroundServiceRunning = true
    }

    private fun bleListener(): Disposable {
        Log.d(I3.TAG, "MainActivity.bleListener()")

        return BleGapScanCallBack.data.subscribe { data ->
            Telemetry.history.add(data)

            val temperatureText = "%.2f".format(
                Telemetry.getShortAsFloat(data.temperature)
            )

            val pressureText = "%.2f".format(
                Telemetry.psiToBar(
                    Telemetry.getShortAsFloat(data.pressure)
                )
            )

            Telemetry.temperatureGraphState.text = "${temperatureText}°C"
            Telemetry.pressureGraphState.text = "$pressureText bars"

            Telemetry.temperatureGraphState.data = Telemetry.getAllTemperatures()
            Telemetry.pressureGraphState.data = Telemetry.getAllPressuresAsBar()
            //Log.i(I3.TAG, "${Telemetry.temperatureGraphState.text} ${Telemetry.pressureGraphState.text}")
        }
    }
}
