package com.raflisalam.bfaa_github.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raflisalam.bfaa_github.pref.SettingsPreference
import java.lang.IllegalArgumentException

class FactoryViewModel(private val preference: SettingsPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeModeViewModel::class.java)) {
            return ThemeModeViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }
}