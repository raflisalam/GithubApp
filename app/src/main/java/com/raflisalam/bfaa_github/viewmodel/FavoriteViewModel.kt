package com.raflisalam.bfaa_github.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raflisalam.bfaa_github.database.DatabaseFavUser
import com.raflisalam.bfaa_github.database.FavoriteDao
import com.raflisalam.bfaa_github.database.FavoriteRepo
import com.raflisalam.bfaa_github.model.FavoriteModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : ViewModel() {
    private val favoriteRepo: FavoriteRepo = FavoriteRepo(application)
    private val favUserDao: FavoriteDao?

    init {
        val db = DatabaseFavUser.getDatabase(application)
        favUserDao = db?.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteModel>> = favoriteRepo.getAllFavorite()

    fun addFavorite(id: Int, username: String, avatar: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val users = FavoriteModel(
                id,
                username,
                avatar
            )
            favUserDao?.addFavorite(users)
        }
    }

    fun deleteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favUserDao?.deleteUser(id)
        }
    }

    fun getUser(username: String): String? = favoriteRepo.getUser(username)

}