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
import org.inspir3.telemetry.Telemetry

@Composable
fun SettingsView(configuration: Configuration, mainRoute: () -> Unit = {}) {
    val temperatureYminText = remember { mutableStateOf(configuration.temperatureYmin.toString()) }
    val temperatureYmaxText = remember { mutableStateOf(configuration.temperatureYmax.toString()) }
    val temperaturePointsText = remember { mutableStateOf(configuration.temperaturePoints.toString()) }
    val pressureYminText = remember { mutableStateOf(configuration.pressureYmin.toString()) }
    val pressureYmaxText = remember { mutableStateOf(configuration.pressureYmax.toString()) }
    val pressurePointsText = remember { mutableStateOf(configuration.pressurePoints.toString()) }

    Column(Modifier.fillMaxWidth()) {
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
            label = "Temperature points (default: ${Default.TEMPERATURE_POINTS}, around 5mn)",
            value = temperaturePointsText,
            onUpdate = { configuration.temperaturePoints = it },
            defaultValue = Default.TEMPERATURE_POINTS,
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
        InputWholeNumber(
            label = "Pressure points (default: ${Default.PRESSURE_POINTS}, around 5mn)",
            value = pressurePointsText,
            onUpdate = { configuration.pressurePoints = it },
            defaultValue = Default.PRESSURE_POINTS,
        )
        Button(onClick = {
            configuration.save()
            //Yurk!
            Telemetry.temperaturePoints = configuration.temperaturePoints
            Telemetry.pressurePoints = configuration.pressurePoints

            mainRoute()
        }) {
            Text("Back")
        }
    }
}
