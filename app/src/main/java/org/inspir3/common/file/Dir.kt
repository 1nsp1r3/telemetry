/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common.file

import android.os.Environment
import android.util.Log
import org.inspir3.common.I3
import java.io.File

class Dir {
    companion object {
        fun list(path: String): List<String> {
            val file = File(path)
            val files = file.list() ?: return listOf()
            files.forEach { Log.d(I3.TAG, it) }
            return files.toList()
        }

        fun getDownloadPath(): String = "${Environment.getExternalStorageDirectory()}/Download/"
    }
}