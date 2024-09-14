/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class Dialog {
    companion object {
        fun alert(context: Context, title: String, message: String, buttonText: String = "OK", onClick: () -> Unit) {
            AlertDialog
                .Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonText) { dialogInterface: DialogInterface, i: Int -> onClick() }
                .create()
                .show()
        }
    }
}