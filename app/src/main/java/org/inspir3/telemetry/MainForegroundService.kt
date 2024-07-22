package org.inspir3.telemetry

import android.util.Log
import io.reactivex.rxjava3.disposables.Disposable
import org.inspir3.common.ForegroundService
import org.inspir3.common.I3
import org.inspir3.common.ble.Gap
import org.inspir3.common.file.TextFile
import org.inspir3.telemetry.ble.BleGapScanCallBack
import org.inspir3.telemetry.ble.BleListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainForegroundService : ForegroundService(
    icon = R.mipmap.ic_launcher,
    title = "Telemetry ON",
    content = "Receiving data...",
) {
    private lateinit var gap: Gap
    private lateinit var bleListenerDisposable: Disposable
    private val textFile = TextFile()

    override fun onStart() {
        Log.d(I3.TAG, "MainForegroundService.onStart()")

        this.createLogFile()
        this.bleListenerDisposable = BleListener.create(textFile)
        gap = Gap(this, BleGapScanCallBack())
        gap.startScan("MX5")
    }

    override fun onStop() {
        Log.d(I3.TAG, "MainForegroundService.onStop()")

        this.bleListenerDisposable.dispose()
        textFile.println("]")
        textFile.close()
        gap.stopScan()
    }

    private fun createLogFile() {
        val yyyyMMddHHmm = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyyMMddHHmm")
        )
        textFile.open(filename = "${yyyyMMddHHmm}.json")
        textFile.println("[")
    }
}
