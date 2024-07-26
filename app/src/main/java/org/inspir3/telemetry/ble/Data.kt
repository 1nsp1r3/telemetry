package org.inspir3.telemetry.ble

class Data(
    var ts: Long = 0,
    var temperature: Short = -32768,
    var pressure: Short = -32768,
)
