package com.raflisalam.bfaa_github.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raflisalam.bfaa_github.pref.SettingsPreference
import kotlinx.coroutines.launch

class ThemeModeViewModel(private val preferences: SettingsPreference) : ViewModel() {

    fun getThemeMode(): LiveData<Boolean> {
        return preferences.getThemeMode().asLiveData()
    }

    fun setThemeMode(isNightMode: Boolean) {
        viewModelScope.launch { preferences.setThemeMode(isNightMode) }
    }
}