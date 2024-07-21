/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun Graph(
    label: String, //isn't displayed
    color: Int,
    ymin: Float,
    ymax: Float,
    modifier: Modifier,
    data: List<Float> = listOf(),
) {
    AndroidView(
        factory = { context ->
            val chart = LineChart(context)

            //axes
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
            chart.setData(
                getLineData(label, color, data)
            )
            chart.invalidate()
        },
        modifier = modifier,
    )
}

/**
 *
 */
private fun getLineData(label: String, color: Int, data: List<Float>): LineData {
    var i = 0f
    val entries = data.map { Entry(i++, it) }
    val lineDataSet = LineDataSet(entries, label)

    lineDataSet.setDrawValues(false) //Hide label of each point
    lineDataSet.setDrawCircles(false) //Hide circle of each point
    lineDataSet.lineWidth = 3f
    lineDataSet.setColor(color)

    return LineData(lineDataSet)
}
