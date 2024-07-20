package org.inspir3.telemetry

import io.reactivex.rxjava3.subjects.PublishSubject
import org.inspir3.common.compose.GraphState

class Telemetry {
    companion object {
        val temperatureGraphState = GraphState()
        val pressureGraphState = GraphState()

        val data: PublishSubject<Data> = PublishSubject.create()
        val history: MutableList<Data> = mutableListOf()

        fun getShortAsFloat(value: Short): Float = value.toFloat().div(100)

        fun getAllTemperatures(): List<Float> = history.map { this.getShortAsFloat(it.temperature) }

        fun getAllPressuresAsBar(): List<Float> = history.map {
            this.psiToBar(
                this.getShortAsFloat(it.pressure)
            )
        }

        fun getAllPressuresAsPsi(): List<Float> = history.map { this.getShortAsFloat(it.pressure) }

        fun psiToBar(psi: Float): Float = psi / 14.5038f
    }
}
