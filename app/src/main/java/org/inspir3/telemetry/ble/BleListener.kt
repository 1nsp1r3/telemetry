package org.inspir3.telemetry.ble

import android.util.Log
import io.reactivex.rxjava3.disposables.Disposable
import org.inspir3.common.I3
import org.inspir3.common.file.TextFile
import org.inspir3.telemetry.Telemetry
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


class BleListener {
    companion object {
        fun create(textFile: TextFile): Disposable {
            Log.d(I3.TAG, "BleListener.create()")

            val zone = ZoneId.of("Europe/Paris")
            return BleGapScanCallBack.data.subscribe { data ->
                Telemetry.history.add(data)

                //Calculate minimums
                if (data.temperature < Telemetry.min.temperature) Telemetry.min.temperature = data.temperature
                if (data.pressure < Telemetry.min.pressure) Telemetry.min.pressure = data.pressure

                //Calculate maximums
                if (data.temperature > Telemetry.max.temperature) Telemetry.max.temperature = data.temperature
                if (data.pressure > Telemetry.max.pressure) Telemetry.max.pressure = data.pressure

                //Transform data to human text
                Telemetry.temperature.actual = Telemetry.getTemperatureAsText(data.temperature)
                Telemetry.temperature.min = Telemetry.getTemperatureAsText(Telemetry.min.temperature)
                Telemetry.temperature.max = Telemetry.getTemperatureAsText(Telemetry.max.temperature)

                Telemetry.pressure.actual = Telemetry.getPressureAsText(data.pressure)
                Telemetry.pressure.min = Telemetry.getPressureAsText(Telemetry.min.pressure)
                Telemetry.pressure.max = Telemetry.getPressureAsText(Telemetry.max.pressure)

                //Actualise charts data
                Telemetry.temperature.data = Telemetry.getLastTemperatures(Telemetry.temperaturePoints)
                Telemetry.pressure.data = Telemetry.getLastPressuresAsBar(Telemetry.pressurePoints)

                //Log file
                val now = LocalDateTime.now()
                val zoneOffSet: ZoneOffset = zone.rules.getOffset(now)
                val ts = now.toEpochSecond(zoneOffSet)
                textFile.println("""{"time":${ts},"temperature":${data.temperature},"pressure":${data.pressure}},""")
            }
        }
    }
}