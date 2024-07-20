/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common.ble

import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import org.inspir3.common.I3

abstract class GapScanCallback : ScanCallback() {
    abstract fun onData(services: Map<String, ByteArray>)

    /**
     * @param result
     *        result.device.address (Ex: "3C:84:27:CA:6E:4A")
     *        result.device.name    (Doesn't work, even with BLUETOOTH_CONNECT permission)
     */
    override fun onScanResult(callbackType: Int, result: ScanResult) {
        //Log.d(I3.TAG, "GapScanCallback.onScanResult()")

        //Convert Map<ParcelUuid, byte[]> to Map<String, ByteArray>
        val newMap: MutableMap<String, ByteArray> = mutableMapOf()
        result.scanRecord?.serviceData?.forEach {
            newMap[it.key.toString()] = it.value
        }

        this.onData(services = newMap.toMap())

        //Services list
        //serviceData?.forEach { parcelUuid, bytes ->
        //    Log.d(I3.TAG, "UUID: $parcelUuid")
        //}
    }

    override fun onBatchScanResults(results: MutableList<ScanResult>) {
        Log.d(I3.TAG, "GapScanCallback.onBatchScanResults() NOT IMPLEMENTED")
    }

    override fun onScanFailed(errorCode: Int) {
        Log.d(I3.TAG, "GapScanCallback.onScanFailed(errorCode: $errorCode) NOT IMPLEMENTED")
    }
}
