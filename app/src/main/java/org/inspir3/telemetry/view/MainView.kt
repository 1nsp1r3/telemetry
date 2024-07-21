package org.inspir3.telemetry.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.inspir3.common.DateTime
import org.inspir3.common.compose.Graph
import org.inspir3.telemetry.Configuration
import org.inspir3.telemetry.GraphData
import org.inspir3.telemetry.Telemetry
import java.time.LocalDateTime

@Composable
fun MainView(configuration: Configuration, settingRoute: () -> Unit = {}, temperature: GraphData, pressure: GraphData) {
    var debugData by remember { mutableStateOf("") }

    val delay = DateTime.getDelay(configuration.startTime, LocalDateTime.now())
    debugData = "$delay | data count: ${Telemetry.history.size}"

    Column(Modifier.fillMaxWidth()) {
        Graph(
            label = "Temperature",
            color = Color.Yellow.toArgb(),
            ymin = configuration.temperatureYmin.toFloat(),
            ymax = configuration.temperatureYmax.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            data = temperature.data,
        )
        ChartLegendView(min = temperature.min, actual = temperature.actual, max = temperature.max, legend = "°C", color = Color.Yellow)
        Graph(
            label = "Pressure",
            color = Color.Cyan.toArgb(),
            ymin = configuration.pressureYmin.toFloat(),
            ymax = configuration.pressureYmax.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            data = pressure.data,
        )
        ChartLegendView(min = pressure.min, actual = pressure.actual, max = pressure.max, legend = " bars", color = Color.Cyan)
        Row(verticalAlignment = Alignment.Bottom) {
            Button(onClick = { settingRoute() }) {
                Text("Settings")
            }
            Text(
                text = debugData,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textAlign = TextAlign.Right,
                fontSize = 12.sp,
            )
        }
    }
}
