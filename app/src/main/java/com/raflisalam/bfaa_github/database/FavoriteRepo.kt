package com.raflisalam.bfaa_github.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.bumptech.glide.util.Executors
import com.raflisalam.bfaa_github.model.FavoriteModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService

class FavoriteRepo(application: Application) {
    private val favUserDao: FavoriteDao?
    private val executorService: ExecutorService = java.util.concurrent.Executors.newSingleThreadExecutor()

    init {
        val db = DatabaseFavUser.getDatabase(application)
        favUserDao = db?.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteModel>> = favUserDao!!.getAllFavorite()

    fun delete(id: Int) {
        executorService.execute { favUserDao?.deleteUser(id) }
    }

    fun insert(favoriteModel: FavoriteModel) {
        executorService.execute { favUserDao?.addFavorite(favoriteModel) }
    }

    fun getUser(username: String): String? = favUserDao?.getUser(username)!!

}