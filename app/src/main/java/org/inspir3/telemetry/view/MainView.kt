package org.inspir3.telemetry.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay
import org.inspir3.common.DateTime
import org.inspir3.common.compose.Graph
import org.inspir3.telemetry.GraphData
import org.inspir3.telemetry.Settings
import org.inspir3.telemetry.SharedMemory
import org.inspir3.telemetry.Telemetry
import java.time.Instant
import java.time.LocalDateTime
import java.util.Locale

@Preview(showBackground = false)
@Composable
fun MainView(
    settingRoute: () -> Unit = {},
    loadRoute: () -> Unit = {},
    freeRoute: () -> Unit = {},
    exitRoute: () -> Unit = {},
    temperature: GraphData = GraphData(),
    pressure: GraphData = GraphData(),
) {
    var now by remember { mutableStateOf(getNowText()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            now = getNowText()
        }
    }

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
            Button(
                onClick = { settingRoute() },
                modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp),
            ) {
                Text("Settings")
            }
            Button(
                onClick = { loadRoute() },
                modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp),
            ) {
                Text("Load")
            }
            Button(
                onClick = { freeRoute() },
                modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp),
            ) {
                Text("100")
            }
            Button(
                onClick = { exitRoute() },
            ) {
                Text("Exit")
            }
        }
        Row {
            Text(
                text = now,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 12.sp,
            )
        }
        Row {
            Text(
                text = buildDebugData(),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textAlign = TextAlign.Right,
                fontSize = 12.sp,
            )
        }
    }
}

fun buildDebugData(): String {
    val endTs = Instant.now().epochSecond

    val secondsElapsed = endTs - Settings.startTs
    val averageReception: Double = if (secondsElapsed == 0L) 0.0 else SharedMemory.debug.bleCount.toDouble() / secondsElapsed.toDouble()

    val delay = DateTime.getDelay(startTs = Settings.startTs, endTs = endTs)

    var debugData = delay
    debugData += " | Ble: ${DateTime.fromLocalDateTimeToHHmmss(SharedMemory.debug.lastBleReception)}"
    debugData += " count: ${SharedMemory.debug.bleCount}"
    debugData += " avg: ${String.format(Locale.FRANCE, "%.2f", averageReception)}"
    debugData += " | history: ${Telemetry.history.size}"

    return debugData
}

fun getNowText(): String {
    return DateTime.fromLocalDateTimeToHHmmss(LocalDateTime.now())
}
