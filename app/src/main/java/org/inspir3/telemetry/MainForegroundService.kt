package org.inspir3.telemetry

import android.util.Log
import org.inspir3.common.ForegroundService
import org.inspir3.common.I3
import org.inspir3.common.ble.Gap
import org.inspir3.telemetry.ble.BleGapScanCallBack

class MainForegroundService : ForegroundService(
    icon = R.mipmap.ic_launcher,
    title = "Telemetry ON",
    content = "Receiving data...",
) {
    private lateinit var gap: Gap

    override fun onStart() {
        Log.d(I3.TAG, "MainForegroundService.onStart()")
        gap = Gap(this, BleGapScanCallBack())
        gap.startScan("MX5")
    }

    override fun onStop() {
        Log.d(I3.TAG, "MainForegroundService.onStop()")
        gap.stopScan()
    }
}
