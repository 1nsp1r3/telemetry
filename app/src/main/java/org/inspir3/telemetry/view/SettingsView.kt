package org.inspir3.telemetry.view

import org.inspir3.common.compose.InputWholeNumber
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.inspir3.telemetry.Configuration
import org.inspir3.telemetry.Default

@Composable
fun SettingsView(configuration: Configuration, mainRoute: () -> Unit = {}) {
    val chartRefreshDelayText = remember { mutableStateOf(configuration.chartRefreshDelay.toString()) }
    val temperatureYminText = remember { mutableStateOf(configuration.temperatureYmin.toString()) }
    val temperatureYmaxText = remember { mutableStateOf(configuration.temperatureYmax.toString()) }
    val pressureYminText = remember { mutableStateOf(configuration.pressureYmin.toString()) }
    val pressureYmaxText = remember { mutableStateOf(configuration.pressureYmax.toString()) }

    Column(Modifier.fillMaxWidth()) {
        InputWholeNumber(
            label = "Chart refresh delay in seconds (default: ${Default.CHART_REFRESH_DELAY})",
            value = chartRefreshDelayText,
            onUpdate = { configuration.chartRefreshDelay = it },
            defaultValue = Default.CHART_REFRESH_DELAY,
        )
        InputWholeNumber(
            label = "Temperature Y min (default: ${Default.TEMPERATURE_YMIN})",
            value = temperatureYminText,
            onUpdate = { configuration.temperatureYmin = it },
            defaultValue = Default.TEMPERATURE_YMIN,
        )
        InputWholeNumber(
            label = "Temperature Y max (default: ${Default.TEMPERATURE_YMAX})",
            value = temperatureYmaxText,
            onUpdate = { configuration.temperatureYmax = it },
            defaultValue = Default.TEMPERATURE_YMAX,
        )
        InputWholeNumber(
            label = "Pressure Y min (default: ${Default.PRESSURE_YMIN})",
            value = pressureYminText,
            onUpdate = { configuration.pressureYmin = it },
            defaultValue = Default.PRESSURE_YMIN,
        )
        InputWholeNumber(
            label = "Pressure Y max (default: ${Default.PRESSURE_YMAX})",
            value = pressureYmaxText,
            onUpdate = { configuration.pressureYmax = it },
            defaultValue = Default.PRESSURE_YMAX,
        )
        Button(onClick = {
            configuration.save()
            mainRoute()
        }) {
            Text("Back")
        }
    }
}
