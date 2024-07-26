package org.inspir3.telemetry.ble

import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import org.inspir3.common.Binary
import org.inspir3.common.DateTime
import org.inspir3.common.ble.GapScanCallback

class BleGapScanCallBack : GapScanCallback() {
    companion object {
        val data: Subject<Data> = PublishSubject.create()
    }

    override fun onData(services: Map<String, ByteArray>): Unit = data.onNext(
        Data(
            ts = DateTime.getTs(),
            temperature = services["00001809-0000-1000-8000-00805f9b34fb"]?.let { Binary.byteArrayToShort(it) } ?: -32768, //Health Thermometer Service (Signed short)
            pressure = services["00002a6d-0000-1000-8000-00805f9b34fb"]?.let { Binary.byteArrayToShort(it) } ?: -32768,    //Pressure characteristic (Signed short)
        )
    )
}
