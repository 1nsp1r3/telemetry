package org.inspir3.telemetry

import org.inspir3.telemetry.ble.Data

class Telemetry {
    companion object {
        val temperature = GraphData()
        val pressure = GraphData()

        val history: MutableList<Data> = mutableListOf()
        val min: Data = Data(temperature = 32767, pressure = 32767)
        val max: Data = Data(temperature = -32768, pressure = -32768)
        var temperaturePoints = 0
        var pressurePoints = 0

        private fun getShortAsFloat(value: Short): Float = value.toFloat().div(100)

        fun getAllTemperatures(): List<Float> = history.map { this.getShortAsFloat(it.temperature) }

        fun getLastTemperatures(n: Int): List<Float> {
            val end = history.size
            val start = end - n
            return history
                .subList(if (start < 0) 0 else start, end)
                .map { this.getShortAsFloat(it.temperature) }
        }

        fun getAllPressuresAsBar(): List<Float> = history.map {
            this.psiToBar(
                this.getShortAsFloat(it.pressure)
            )
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
    }
}
