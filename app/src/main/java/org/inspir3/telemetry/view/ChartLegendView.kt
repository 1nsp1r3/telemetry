package org.inspir3.telemetry.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = false)
@Composable
fun ChartLegendView(min: String = "min", actual: String = "ACTUAL", max: String = "max", legend: String = "", color: Color = Color.White) {
    Row {
        Text(
            text = min,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Center,
            color = color,
            fontSize = 16.sp,
        )
        Text(
            text = "${actual}$legend",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Center,
            color = color,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = max,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Center,
            color = color,
            fontSize = 16.sp,
        )
    }//row
}
