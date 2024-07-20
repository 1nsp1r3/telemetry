package org.inspir3.telemetry

import android.content.Context
import android.util.Log
import org.inspir3.common.I3
import org.inspir3.common.Settings
import java.time.LocalDateTime

class Default {
    companion object {
        const val CHART_REFRESH_DELAY = 15
        const val TEMPERATURE_YMIN = -30
        const val TEMPERATURE_YMAX = 150
    }
}

class Configuration(
    context: Context,
    var chartRefreshDelay: Int = Default.CHART_REFRESH_DELAY,
    var temperatureYmin: Int = Default.TEMPERATURE_YMIN,
    var temperatureYmax: Int = Default.TEMPERATURE_YMAX,
    //Divers
    val startTime: LocalDateTime = LocalDateTime.now(),
) : Settings(context) {


    override suspend fun _loadSettings() {
        this.chartRefreshDelay = this.loadInt("chartRefreshDelay", Default.CHART_REFRESH_DELAY)
        this.temperatureYmin = this.loadInt("temperatureYmin", Default.TEMPERATURE_YMIN)
        this.temperatureYmax = this.loadInt("temperatureYmax", Default.TEMPERATURE_YMAX)
    }

    override suspend fun _saveSettings() {
        this.save("chartRefreshDelay", this.chartRefreshDelay)
        this.save("temperatureYmin", this.temperatureYmin)
        this.save("temperatureYmax", this.temperatureYmax)
    }

    override fun logSettings() {
        Log.d(I3.TAG, "=== Configuration.logSettings() ===")
        Log.d(I3.TAG, "chartRefreshDelay: $chartRefreshDelay")
        Log.d(I3.TAG, "temperatureYmin: $temperatureYmin")
        Log.d(I3.TAG, "temperatureYmax: $temperatureYmax")
    }
}
