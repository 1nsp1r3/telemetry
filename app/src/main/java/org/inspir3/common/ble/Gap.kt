/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common.ble

import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.util.Log
import org.inspir3.common.I3

class Gap(
    private val context: Context,
    private val gapScanCallback: GapScanCallback,
) {

    /**
     *
     */
    fun startScan(deviceName: String) {
        Log.d(I3.TAG, "Gap.startScan()")

        this.getBluetoothLeScanner().startScan(
            listOf(
                filterByName(deviceName),
            ),
            getScanSettings(),
            this.gapScanCallback,
        )
    }

    /**
     *
     */
    fun stopScan() {
        Log.d(I3.TAG, "Gap.stopScan()")

        this.getBluetoothLeScanner().stopScan(this.gapScanCallback)
    }

    /**
     *
     */
    private fun getBluetoothLeScanner(): BluetoothLeScanner {
        Log.d(I3.TAG, "Gap.getBluetoothLeScanner()")
        val bluetoothManager = (this.context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?) ?: throw Exception("Unable to retrieve BluetoothManager")
        return bluetoothManager.adapter.bluetoothLeScanner
    }

    private fun filterByName(deviceName: String): ScanFilter = ScanFilter.Builder().setDeviceName(deviceName).build()

    private fun getScanSettings() = ScanSettings.Builder()
        .setScanMode(ScanSettings.CALLBACK_TYPE_FIRST_MATCH)
        .build()
}
