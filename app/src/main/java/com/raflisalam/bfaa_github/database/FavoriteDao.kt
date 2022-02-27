package com.raflisalam.bfaa_github.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raflisalam.bfaa_github.model.FavoriteModel

@Dao
interface FavoriteDao {
    @Insert
    fun addFavorite(favoriteModel: FavoriteModel)

    @Query("SELECT * FROM FavoriteModel")
    fun getAllFavorite(): LiveData<List<FavoriteModel>>

    @Query("SELECT count(*) FROM FavoriteModel WHERE username = (:username)")
    fun getUser(username: String): String

    @Query("DELETE FROM FavoriteModel WHERE id = (:id)")
    fun deleteUser(id: Int) : Int
}