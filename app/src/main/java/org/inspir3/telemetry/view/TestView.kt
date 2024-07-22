/**
 * Only for development
 */
package org.inspir3.telemetry.view

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import org.inspir3.common.compose.Graph
import org.inspir3.telemetry.Telemetry

@Preview(showBackground = false)
@Composable
fun TestView() {
    var checked by remember { mutableStateOf(true) }
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Log file ? (default: yes)",
                modifier = Modifier.fillMaxWidth().weight(1f),
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                modifier = Modifier.weight(1f),
            )
        }

        Button(onClick = { }) {
            Text("Settings")
        }
    }
}