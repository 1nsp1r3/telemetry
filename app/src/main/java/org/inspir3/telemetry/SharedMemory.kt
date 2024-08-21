package org.inspir3.telemetry

/**
 * Handle communications between MainActivity <-> ForegroundService
 * (ViewModel can't)
 */
object SharedMemory {
    /**
     * MainActivity tell MainForegroundService to stop application
     */
    var stopApplication: Boolean = false

    var foregroundServiceRunning: Boolean = false

    val debug = Debug
}
