package org.inspir3.telemetry

import android.util.Log
import org.inspir3.common.Binary
import org.inspir3.common.ForegroundService
import org.inspir3.common.I3
import org.inspir3.common.ble.Gap
import org.inspir3.common.ble.GapScanCallback

class BleGapScanCallBack : GapScanCallback() {
    override fun onData(services: Map<String, ByteArray>) {
        val data = Data()
        data.temperature = services["00001809-0000-1000-8000-00805f9b34fb"]?.let { Binary.byteArrayToShort(it) } ?: -32768 //Health Thermometer Service (Signed short)
        data.pressure = services["00002a6d-0000-1000-8000-00805f9b34fb"]?.let { Binary.byteArrayToShort(it) } ?: -32768    //Pressure characteristic (Signed short)
        Telemetry.data.onNext(data)
    }
}

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
