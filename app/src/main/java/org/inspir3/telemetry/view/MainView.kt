package org.inspir3.telemetry.view

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.reactivex.rxjava3.core.Observable
import org.inspir3.common.DateTime
import org.inspir3.common.compose.Graph
import org.inspir3.telemetry.Configuration
import org.inspir3.telemetry.Telemetry
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Composable
fun MainView(configuration: Configuration, settingRoute: () -> Unit = {}) {
    var debugData by remember { mutableStateOf("") }

    //Change debugData every 5 seconds to force label refresh
    val a = Observable
        .interval(5, TimeUnit.SECONDS)
        .subscribe {
            val delay = DateTime.getDelay(configuration.startTime, LocalDateTime.now())
            debugData = "$delay | data count: ${Telemetry.history.size}"
        }

    Column(Modifier.fillMaxWidth()) {
        Text(debugData)
        Graph(
            label = "Temperature",
            color = Color.YELLOW,
            ymin = configuration.temperatureYmin.toFloat(),
            ymax = configuration.temperatureYmax.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            graphState = Telemetry.temperatureGraphState,
        )
        Graph(
            label = "Pressure",
            color = Color.CYAN,
            ymin = configuration.pressureYmin.toFloat(),
            ymax = configuration.pressureYmax.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            graphState = Telemetry.pressureGraphState,
        )
        Button(onClick = { settingRoute() }) {
            Text("Settings")
        }
    }
}
