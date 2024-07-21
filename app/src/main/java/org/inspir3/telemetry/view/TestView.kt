/**
 * Only for development
 */
package org.inspir3.telemetry.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import org.inspir3.common.compose.Graph
import org.inspir3.telemetry.Telemetry

@Preview(showBackground = false)
@Composable
fun TestView() {
    Column(Modifier.fillMaxWidth()) {
        Text("00h00:05 | data count: 158")
        Graph(
            label = "Temperature",
            color = Color.Yellow.toArgb(),
            ymin = -30f,
            ymax = 150f,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            data = Telemetry.temperature.data,
        )
        ChartLegendView(color = Color.Yellow)
        Graph(
            label = "Pressure",
            color = Color.Cyan.toArgb(),
            ymin = 0f,
            ymax = 10f,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            data = Telemetry.pressure.data,
        )
        ChartLegendView(color = Color.Cyan)
        Button(onClick = { }) {
            Text("Settings")
        }
    }
}