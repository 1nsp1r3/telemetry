package org.inspir3.telemetry

import android.util.Log
import org.inspir3.common.I3
import org.inspir3.telemetry.ble.BleListener
import org.inspir3.telemetry.ble.Data
import org.json.JSONArray
import java.io.File

class Telemetry {
    companion object {
        val temperature = GraphData()
        val pressure = GraphData()

        val history: MutableList<Data> = mutableListOf()
        var min: Data = Data(temperature = 32767, pressure = 32767)
        var max: Data = Data(temperature = -32768, pressure = -32768)

        private fun getShortAsFloat(value: Short): Float = value.toFloat().div(100)

        private fun clear() {
            Log.d(I3.TAG, "Telemetry.clear()")

            min = Data(temperature = 32767, pressure = 32767)
            max = Data(temperature = -32768, pressure = -32768)
            history.clear()
        }

        fun getLastTemperatures(n: Int): List<Float> {
            val end = history.size
            val start = end - n
            return history
                .subList(if (start < 0) 0 else start, end)
                .map { this.getShortAsFloat(it.temperature) }
        }

        fun getLastPressuresAsBar(n: Int): List<Float> {
            val end = history.size
            val start = end - n
            return history
                .subList(if (start < 0) 0 else start, end)
                .map {
                    this.psiToBar(
                        this.getShortAsFloat(it.pressure)
                    )
                }
        }

        private fun psiToBar(psi: Float): Float = psi / 14.5038f

        fun getTemperatureAsText(value: Short): String = "%.2f".format(
            getShortAsFloat(value)
        )

        fun getPressureAsText(value: Short): String = "%.2f".format(
            psiToBar(
                getShortAsFloat(value)
            )
        )

        fun loadFile(path: String, filename: String) {
            Log.i(I3.TAG, "Telemetry.loadFile(filename: $filename)")

            clear()

            val file = File("$path/$filename")
            var json = file.readText()
            //Log.i(I3.TAG, "json: '$json'")

            if (json.isEmpty()) json = "[]"

            val array = JSONArray(json)
            val dataList: MutableList<Data> = mutableListOf()
            for (i in 0..<array.length()) {
                val obj = array.getJSONObject(i)
                dataList.add(
                    Data(
                        temperature = obj.getInt("temperature").toShort(),
                        pressure = obj.getInt("pressure").toShort(),
                    )
                )
            }
            Log.i(I3.TAG, "Telemetry.loadFile() datacount loaded: ${dataList.size}")

            dataList.forEach {
                BleListener.processData(it)
            }

            if (dataList.isEmpty()) {
                BleListener.processData(Data()) //force screen refresh
            }
        }
    }
}
