package org.inspir3.telemetry

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.inspir3.common.I3
import java.time.LocalDateTime

private val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(name = "inspir3")

class Default {
    companion object {
        const val TEMPERATURE_YMIN = -30
        const val TEMPERATURE_YMAX = 150
        const val TEMPERATURE_POINTS = 300 //around 5 minutes
        const val PRESSURE_YMIN = 0
        const val PRESSURE_YMAX = 10
        const val PRESSURE_POINTS = 300 //around 5 minutes
        const val LOG_FILE = true
    }
}

class Settings {
    companion object {
        var temperatureYmin: Int = Default.TEMPERATURE_YMIN
        var temperatureYmax: Int = Default.TEMPERATURE_YMAX
        var temperaturePoints: Int = Default.TEMPERATURE_POINTS
        var pressureYmin: Int = Default.PRESSURE_YMIN
        var pressureYmax: Int = Default.PRESSURE_YMAX
        var pressurePoints: Int = Default.PRESSURE_POINTS
        var logFile: Boolean = Default.LOG_FILE
        var exiting: Boolean = false //Bricolage

        //Divers
        val startTime: LocalDateTime = LocalDateTime.now()

        fun load(context: Context) {
            Log.d(I3.TAG, "Setting.load()")

            runBlocking {
                temperatureYmin = loadInt(context, "temperatureYmin", Default.TEMPERATURE_YMIN)
                temperatureYmax = loadInt(context, "temperatureYmax", Default.TEMPERATURE_YMAX)
                temperaturePoints = loadInt(context, "temperaturePoints", Default.TEMPERATURE_POINTS)
                pressureYmin = loadInt(context, "pressureYmin", Default.PRESSURE_YMIN)
                pressureYmax = loadInt(context, "pressureYmax", Default.PRESSURE_YMAX)
                pressurePoints = loadInt(context, "pressurePoints", Default.PRESSURE_POINTS)
                logFile = loadBoolean(context, "logFile", Default.LOG_FILE)
            }
            log()
        }

        fun save(context: Context) {
            Log.d(I3.TAG, "Setting.save()")

            runBlocking {
                save(context, "temperatureYmin", temperatureYmin)
                save(context, "temperatureYmax", temperatureYmax)
                save(context, "temperaturePoints", temperaturePoints)
                save(context, "pressureYmin", pressureYmin)
                save(context, "pressureYmax", pressureYmax)
                save(context, "pressurePoints", pressurePoints)
                save(context, "logFile", logFile)
            }
            this.log()
        }

        private fun log() {
            Log.d(I3.TAG, "=== Setting.log() ===")
            Log.d(I3.TAG, "temperatureYmin: $temperatureYmin")
            Log.d(I3.TAG, "temperatureYmax: $temperatureYmax")
            Log.d(I3.TAG, "temperaturePoints: $temperaturePoints")
            Log.d(I3.TAG, "pressureYmin: $pressureYmin")
            Log.d(I3.TAG, "pressureYmax: $pressureYmax")
            Log.d(I3.TAG, "pressurePoints: $pressurePoints")
            Log.d(I3.TAG, "logFile: $logFile")
        }

        /**
         * Helper to load a int
         */
        private suspend fun loadInt(context: Context, name: String, default: Int): Int {
            val key = intPreferencesKey(name)
            val flow: Flow<Int> = context.dataStore.data.map {
                it[key] ?: default
            }
            return flow.first()
        }

        /**
         * Helper to load a boolean
         */
        private suspend fun loadBoolean(context: Context, name: String, default: Boolean): Boolean {
            val key = booleanPreferencesKey(name)
            val flow: Flow<Boolean> = context.dataStore.data.map {
                it[key] ?: default
            }
            return flow.first()
        }

        /**
         * Helper to save an int
         */
        private suspend fun save(context: Context, name: String, value: Int) {
            val key = intPreferencesKey(name)
            context.dataStore.edit {
                it[key] = value
            }
        }

        /**
         * Helper to save a boolean
         */
        private suspend fun save(context: Context, name: String, value: Boolean) {
            val key = booleanPreferencesKey(name)
            context.dataStore.edit {
                it[key] = value
            }
        }
    }
}
