/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(name = "inspir3")

abstract class Settings(
    private val context: Context,
) {
    /**
     * Helper to load a int
     */
    protected suspend fun loadInt(name: String, default: Int): Int {
        val key = intPreferencesKey(name)
        val flow: Flow<Int> = this.context.dataStore.data.map {
            it[key] ?: default
        }
        return flow.first()
    }

    /**
     * Helper to save an int
     */
    protected suspend fun save(name: String, value: Int) {
        val key = intPreferencesKey(name)
        this.context.dataStore.edit {
            it[key] = value
        }
    }

    fun load() {
        Log.d(I3.TAG, "Settings.load()")

        runBlocking {
            _loadSettings()
        }
        this.logSettings()
    }

    fun save() {
        Log.d(I3.TAG, "Settings.save()")

        runBlocking {
            _saveSettings()
        }
        this.logSettings()
    }

    //To be implemented
    abstract suspend fun _saveSettings()
    abstract suspend fun _loadSettings()
    abstract fun logSettings()
}
