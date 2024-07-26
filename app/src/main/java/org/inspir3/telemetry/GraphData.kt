package org.inspir3.telemetry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.inspir3.common.compose.Measure

class GraphData(
    var data: List<Measure> = listOf(),
) {
    //These 3 values work together, so only one need a State
    var actual: String by mutableStateOf("") //This lonely state is responsible of all chart refresh, even the data
    var min: String = ""
    var max: String = ""
}
