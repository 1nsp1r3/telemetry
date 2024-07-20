/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common.file

import android.os.Environment
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class TextFile {
    private lateinit var textFile: BufferedWriter

    fun open(filename: String) {
        val dirpath = "${Environment.getExternalStorageDirectory()}/Download/"
        val file = File(dirpath, filename)
        val fileWriter = FileWriter(file, true)
        textFile = BufferedWriter(fileWriter)
    }

    fun print(text: String) {
        textFile.write(text)
    }

    fun println(text: String) {
        print("$text\n")
    }

    fun flush() {
        textFile.flush()
    }

    fun close() {
        textFile.close()
    }
}
