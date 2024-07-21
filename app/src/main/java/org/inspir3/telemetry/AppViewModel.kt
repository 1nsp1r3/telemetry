package org.inspir3.telemetry

import android.util.Log
import androidx.lifecycle.ViewModel
import org.inspir3.common.I3

class AppViewModel : ViewModel() {
    var foregroundServiceRunning = false

    init {
        Log.i(I3.TAG, "First initialization of AppViewModel")
    }
}
