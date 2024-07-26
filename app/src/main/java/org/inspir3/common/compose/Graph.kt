/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import org.inspir3.common.DateTime

class Measure(
    val ts: Long,
    val value: Float,
)

@Composable
fun Graph(
    label: String, //isn't displayed
    color: Int,
    ymin: Float,
    ymax: Float,
    modifier: Modifier,
    data: List<Measure> = listOf(),
) {
    AndroidView(
        factory = { context ->
            val chart = LineChart(context)

            //X axe
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.xAxis.textColor = color

            //Y axes
            chart.getAxis(YAxis.AxisDependency.LEFT).axisMinimum = ymin
            chart.getAxis(YAxis.AxisDependency.LEFT).axisMaximum = ymax
            chart.getAxis(YAxis.AxisDependency.LEFT).textColor = color
            chart.getAxis(YAxis.AxisDependency.RIGHT).isEnabled = false

            //text
            chart.description.isEnabled = false

            //legend
            chart.legend.isEnabled = false

            chart //return
        },
        //I don't known who/what trigger the call of this update function
        update = { chart ->
            val timeOffset = if (data.isNotEmpty()) data[0].ts else 0 //Because a long can't be stored into a float, we use this trick
            chart.xAxis.valueFormatter = TimeValueFormatter(timeOffset)

            chart.setData(
                getLineData(timeOffset, label, color, data)
            )
            chart.invalidate()
        },
        modifier = modifier,
    )
}

/**
 *
 */
private fun getLineData(timeOffset: Long, label: String, color: Int, data: List<Measure>): LineData {
    val entries = data.map { Entry((it.ts - timeOffset).toFloat(), it.value) }
    val lineDataSet = LineDataSet(entries, label)

    lineDataSet.setDrawValues(false) //Hide label of each point
    lineDataSet.setDrawCircles(false) //Hide circle of each point
    lineDataSet.setDrawFilled(true) //Fill under the line
    lineDataSet.lineWidth = 3f
    lineDataSet.setColor(color)

    return LineData(lineDataSet)
}

private class TimeValueFormatter(private val timeOffset: Long) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val timestamp = timeOffset + value.toLong()
        return DateTime.fromTsToHHmm(timestamp)
    }
}
