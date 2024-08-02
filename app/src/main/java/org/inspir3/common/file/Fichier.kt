/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common.file

class Fichier(
    val name: String,
    val size: Long,
) {
    fun getFilesizeForHuman(): String {
        if (this.size < 1024) return "${this.size} o"
        val arrondi = "%.2f".format(this.size.toDouble() / 1024)
        return "$arrondi Ko"
    }
}
