package com.raflisalam.bfaa_github.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreference private constructor(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) {

    private val key_theme = booleanPreferencesKey("theme_mode")

    companion object {
        @Volatile
        private var instance: SettingsPreference? = null

        fun newInstance(dataStore: DataStore<androidx.datastore.preferences.core.Preferences>): SettingsPreference {
            return instance ?: synchronized(this) {
                val ins = SettingsPreference(dataStore)
                instance = ins
                ins
            }
        }
    }

    fun getThemeMode(): Flow<Boolean> {
        return dataStore.data.map { preferences -> preferences[key_theme] ?: false }
    }

    suspend fun setThemeMode(isNightMode: Boolean) {
        dataStore.edit { preferences -> preferences [key_theme] = isNightMode }
    }
}