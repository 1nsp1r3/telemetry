/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common

import android.content.Context
import android.content.Intent

class I3 {
    companion object {
        const val TAG = "I3"

        inline fun <reified T : Any> switchToActivity(context: Context, klass: Class<T>) {
            val intent = Intent(context, klass)
            context.startActivity(intent)
        }
    }
}
