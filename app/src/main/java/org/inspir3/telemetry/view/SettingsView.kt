package org.inspir3.telemetry.view

import android.content.Context
import org.inspir3.common.compose.InputWholeNumber
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.inspir3.telemetry.Default
import org.inspir3.telemetry.Settings

@Composable
fun SettingsView(
    mainRoute: () -> Unit = {},
    context: Context,
) {
    val temperatureYminText = remember { mutableStateOf(Settings.temperatureYmin.toString()) }
    val temperatureYmaxText = remember { mutableStateOf(Settings.temperatureYmax.toString()) }
    val temperaturePointsText = remember { mutableStateOf(Settings.temperaturePoints.toString()) }
    val pressureYminText = remember { mutableStateOf(Settings.pressureYmin.toString()) }
    val pressureYmaxText = remember { mutableStateOf(Settings.pressureYmax.toString()) }
    val pressurePointsText = remember { mutableStateOf(Settings.pressurePoints.toString()) }
    var logFile by remember { mutableStateOf(Settings.logFile) }

    Column(Modifier.fillMaxWidth()) {
        InputWholeNumber(
            label = "Temperature Y min (default: ${Default.TEMPERATURE_YMIN})",
            value = temperatureYminText,
            onUpdate = { Settings.temperatureYmin = it },
            defaultValue = Default.TEMPERATURE_YMIN,
        )
        InputWholeNumber(
            label = "Temperature Y max (default: ${Default.TEMPERATURE_YMAX})",
            value = temperatureYmaxText,
            onUpdate = { Settings.temperatureYmax = it },
            defaultValue = Default.TEMPERATURE_YMAX,
        )
        InputWholeNumber(
            label = "Temperature points (default: ${Default.TEMPERATURE_POINTS}, around 5mn)",
            value = temperaturePointsText,
            onUpdate = { Settings.temperaturePoints = it },
            defaultValue = Default.TEMPERATURE_POINTS,
        )
        InputWholeNumber(
            label = "Pressure Y min (default: ${Default.PRESSURE_YMIN})",
            value = pressureYminText,
            onUpdate = { Settings.pressureYmin = it },
            defaultValue = Default.PRESSURE_YMIN,
        )
        InputWholeNumber(
            label = "Pressure Y max (default: ${Default.PRESSURE_YMAX})",
            value = pressureYmaxText,
            onUpdate = { Settings.pressureYmax = it },
            defaultValue = Default.PRESSURE_YMAX,
        )
        InputWholeNumber(
            label = "Pressure points (default: ${Default.PRESSURE_POINTS}, around 5mn)",
            value = pressurePointsText,
            onUpdate = { Settings.pressurePoints = it },
            defaultValue = Default.PRESSURE_POINTS,
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Log file ? (default: yes)",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
            Switch(
                checked = logFile,
                onCheckedChange = {
                    logFile = it
                    Settings.logFile = it
                },
                modifier = Modifier.weight(1f),
            )
        }

        Button(onClick = {
            Settings.save(context)
            mainRoute()
        }) {
            Text("Back")
        }
    }
}
