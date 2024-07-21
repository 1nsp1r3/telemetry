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
        const val TEMPERATURE_POINTS = 300 //around 5 minutes
        const val PRESSURE_YMIN = 0
        const val PRESSURE_YMAX = 10
        const val PRESSURE_POINTS = 300 //around 5 minutes
    }
}

class Configuration(
    context: Context,
    var temperatureYmin: Int = Default.TEMPERATURE_YMIN,
    var temperatureYmax: Int = Default.TEMPERATURE_YMAX,
    var temperaturePoints: Int = Default.TEMPERATURE_POINTS,
    var pressureYmin: Int = Default.PRESSURE_YMIN,
    var pressureYmax: Int = Default.PRESSURE_YMAX,
    var pressurePoints: Int = Default.PRESSURE_POINTS,
    //Divers
    val startTime: LocalDateTime = LocalDateTime.now(),
) : Settings(context) {

    override suspend fun _loadSettings() {
        this.temperatureYmin = this.loadInt("temperatureYmin", Default.TEMPERATURE_YMIN)
        this.temperatureYmax = this.loadInt("temperatureYmax", Default.TEMPERATURE_YMAX)
        this.temperaturePoints = this.loadInt("temperaturePoints", Default.TEMPERATURE_POINTS)
        this.pressureYmin = this.loadInt("pressureYmin", Default.PRESSURE_YMIN)
        this.pressureYmax = this.loadInt("pressureYmax", Default.PRESSURE_YMAX)
        this.pressurePoints = this.loadInt("pressurePoints", Default.PRESSURE_POINTS)
    }

    override suspend fun _saveSettings() {
        this.save("temperatureYmin", this.temperatureYmin)
        this.save("temperatureYmax", this.temperatureYmax)
        this.save("temperaturePoints", this.temperaturePoints)
        this.save("pressureYmin", this.pressureYmin)
        this.save("pressureYmax", this.pressureYmax)
        this.save("pressurePoints", this.pressurePoints)
    }

    override fun logSettings() {
        Log.d(I3.TAG, "=== Configuration.logSettings() ===")
        Log.d(I3.TAG, "temperatureYmin: $temperatureYmin")
        Log.d(I3.TAG, "temperatureYmax: $temperatureYmax")
        Log.d(I3.TAG, "temperaturePoints: $temperaturePoints")
        Log.d(I3.TAG, "pressureYmin: $pressureYmin")
        Log.d(I3.TAG, "pressureYmax: $pressureYmax")
        Log.d(I3.TAG, "pressurePoints: $pressurePoints")
    }
}
