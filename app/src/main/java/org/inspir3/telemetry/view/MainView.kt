package org.inspir3.telemetry.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.inspir3.common.DateTime
import org.inspir3.common.compose.Graph
import org.inspir3.telemetry.GraphData
import org.inspir3.telemetry.Settings
import org.inspir3.telemetry.Telemetry
import java.time.LocalDateTime

@Preview(showBackground = false)
@Composable
fun MainView(
    settingRoute: () -> Unit = {},
    loadRoute: () -> Unit = {},
    exitRoute: () -> Unit = {},
    temperature: GraphData = GraphData(),
    pressure: GraphData = GraphData(),
) {
    var debugData by remember { mutableStateOf("") }

    val delay = DateTime.getDelay(Settings.startTime, LocalDateTime.now())
    debugData = "$delay | data count: ${Telemetry.history.size}"

    Column(Modifier.fillMaxWidth()) {
        Graph(
            label = "Temperature",
            color = Color.Yellow.toArgb(),
            ymin = Settings.temperatureYmin.toFloat(),
            ymax = Settings.temperatureYmax.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(0.dp, 0.dp, 0.dp, 8.dp),
            data = temperature.data,
        )
        ChartLegendView(min = temperature.min, actual = temperature.actual, max = temperature.max, legend = "°C", color = Color.Yellow)
        Graph(
            label = "Pressure",
            color = Color.Cyan.toArgb(),
            ymin = Settings.pressureYmin.toFloat(),
            ymax = Settings.pressureYmax.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(0.dp, 0.dp, 0.dp, 8.dp),
            data = pressure.data,
        )
        ChartLegendView(min = pressure.min, actual = pressure.actual, max = pressure.max, legend = " bars", color = Color.Cyan)
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp),
        ) {
            Button(onClick = { settingRoute() }) {
                Text("Settings")
            }
            Button(
                onClick = { loadRoute() },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text("Load")
            }
            Button(
                onClick = { exitRoute() },
            ) {
                Text("Exit")
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
