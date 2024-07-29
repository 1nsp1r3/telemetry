package org.inspir3.telemetry

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH_SCAN
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import org.inspir3.common.Dialog
import org.inspir3.common.I3
import org.inspir3.common.ble.Gap

/**
 * Check if all requirements is authorized/active for running the app
 */
class Init {
    companion object {
        fun permissionsIsMissing(activity: Activity): Boolean {
            Log.d(I3.TAG, "Init.permissionsIsMissing()")

            val missing: MutableList<String> = mutableListOf()

            if (ContextCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) == PERMISSION_DENIED) {
                Log.e(I3.TAG, "Permission ACCESS_FINE_LOCATION is missing")
                missing.add("Position")
            }
            if (ContextCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) == PERMISSION_DENIED) {
                Log.e(I3.TAG, "Permission ACCESS_COARSE_LOCATION is missing")
                missing.add("Position")
            }
            if (ContextCompat.checkSelfPermission(activity, BLUETOOTH_SCAN) == PERMISSION_DENIED) {
                Log.e(I3.TAG, "Permission BLUETOOTH_SCAN is missing")
                missing.add("Nearby device (Bluetooth)")
            }
            if (ContextCompat.checkSelfPermission(activity, POST_NOTIFICATIONS) == PERMISSION_DENIED) {
                Log.e(I3.TAG, "Permission POST_NOTIFICATIONS is missing")
                missing.add("Notifications")
            }
            val missingResume = missing.distinct()
            if (missingResume.isNotEmpty()) {
                val message = missingResume.joinToString(
                    separator = "\n",
                ) { "- $it" }
                Dialog.alert(activity, "Some permissions are missing", message, "Exit") {
                    Log.i(I3.TAG, "Exiting...")
                    activity.finishAffinity()
                }
                return true
            }
            return false
        }

        fun requirementsIsMissing(activity: Activity): Boolean {
            Log.d(I3.TAG, "Init.requirementsIsMissing()")

            val missing: MutableList<String> = mutableListOf()

            if (Gap.isBluetoothDisabled(activity)) {
                Log.e(I3.TAG, "Bluetooth is disabled")
                missing.add("bluetooth")
            }
            if (isLocationDisabled(activity)) {
                Log.e(I3.TAG, "Location (GNSS) is disabled")
                missing.add("location (GNSS)")
            }
            if (missing.isNotEmpty()) {
                val message = missing.joinToString(
                    separator = "\n",
                ) { "- $it" }
                Dialog.alert(activity, "Some requirements are missing", "Please enable:\n$message", "Exit") {
                    Log.i(I3.TAG, "Exiting...")
                    activity.finishAffinity()
                }
                return true
            }
            return false
        }

        private fun isLocationDisabled(context: Context): Boolean {
            Log.d(I3.TAG, "Init.isLocationDisabled()")

            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER).not()
        }
    }
}
