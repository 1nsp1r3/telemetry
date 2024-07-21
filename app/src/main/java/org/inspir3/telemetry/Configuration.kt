package org.inspir3.telemetry

import android.content.Context
import android.util.Log
import org.inspir3.common.I3
import org.inspir3.common.Settings
import java.time.LocalDateTime

class Default {
    companion object {
        const val TEMPERATURE_YMIN = -30
        const val TEMPERATURE_YMAX = 150
        const val PRESSURE_YMIN = 0
        const val PRESSURE_YMAX = 10
    }
}

class Configuration(
    context: Context,
    var temperatureYmin: Int = Default.TEMPERATURE_YMIN,
    var temperatureYmax: Int = Default.TEMPERATURE_YMAX,
    var pressureYmin: Int = Default.PRESSURE_YMIN,
    var pressureYmax: Int = Default.PRESSURE_YMAX,
    //Divers
    val startTime: LocalDateTime = LocalDateTime.now(),
) : Settings(context) {


    override suspend fun _loadSettings() {
        this.temperatureYmin = this.loadInt("temperatureYmin", Default.TEMPERATURE_YMIN)
        this.temperatureYmax = this.loadInt("temperatureYmax", Default.TEMPERATURE_YMAX)
        this.pressureYmin = this.loadInt("pressureYmin", Default.PRESSURE_YMIN)
        this.pressureYmax = this.loadInt("pressureYmax", Default.PRESSURE_YMAX)
    }

    override suspend fun _saveSettings() {
        this.save("temperatureYmin", this.temperatureYmin)
        this.save("temperatureYmax", this.temperatureYmax)
        this.save("pressureYmin", this.pressureYmin)
        this.save("pressureYmax", this.pressureYmax)
    }

    override fun logSettings() {
        Log.d(I3.TAG, "=== Configuration.logSettings() ===")
        Log.d(I3.TAG, "temperatureYmin: $temperatureYmin")
        Log.d(I3.TAG, "temperatureYmax: $temperatureYmax")
        Log.d(I3.TAG, "pressureYmin: $pressureYmin")
        Log.d(I3.TAG, "pressureYmax: $pressureYmax")
    }
}
