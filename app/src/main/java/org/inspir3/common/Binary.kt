/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common

import kotlin.math.absoluteValue

class Binary {
    companion object {
        fun byteArrayToShort(byteArray: ByteArray): Short {
            var ret = 0
            var shift = 0
            for (i in 0..1) { //Only 2 bytes
                ret = ret or (byteArray[i].toInt().absoluteValue shl shift)
                shift += 8
            }
            return ret.toShort()
        }
    }
}