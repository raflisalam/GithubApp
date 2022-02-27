package com.raflisalam.bfaa_github.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavFactoryViewModel private constructor(private val application: Application): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: FavFactoryViewModel? = null

        @JvmStatic
        fun newInstance(application: Application): FavFactoryViewModel {
            if (instance == null) {
                synchronized(FavFactoryViewModel::class.java) {
                    instance = FavFactoryViewModel(application)
                }
            }
            return instance as FavFactoryViewModel
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class " + modelClass.name)
    }
}