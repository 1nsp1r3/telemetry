package org.inspir3.telemetry

import android.util.Log
import io.reactivex.rxjava3.disposables.Disposable
import org.inspir3.common.ForegroundService
import org.inspir3.common.I3
import org.inspir3.common.ble.Gap
import org.inspir3.telemetry.ble.BleGapScanCallBack
import org.inspir3.telemetry.ble.BleListener

class MainForegroundService : ForegroundService(
    icon = R.mipmap.ic_launcher,
    title = "Telemetry ON",
    content = "Receiving data...",
) {
    private lateinit var gap: Gap
    private lateinit var bleListenerDisposable: Disposable

    override fun onStart() {
        Log.d(I3.TAG, "MainForegroundService.onStart()")

        this.bleListenerDisposable = BleListener.create()
        gap = Gap(this, BleGapScanCallBack())
        gap.startScan("MX5")
    }

    override fun onStop() {
        Log.d(I3.TAG, "MainForegroundService.onStop()")
        this.bleListenerDisposable.dispose()
        gap.stopScan()
    }
}
