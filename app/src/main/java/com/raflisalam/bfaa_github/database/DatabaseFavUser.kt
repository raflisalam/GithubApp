package com.raflisalam.bfaa_github.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raflisalam.bfaa_github.model.FavoriteModel

@Database(entities = [FavoriteModel::class], version = 1, exportSchema = false)
abstract class DatabaseFavUser: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        var instance: DatabaseFavUser? = null

        fun getDatabase(context: Context): DatabaseFavUser? {
            if (instance == null) {
                synchronized(DatabaseFavUser::class) {
                    instance = Room.databaseBuilder(context.applicationContext, DatabaseFavUser::class.java, "fav_database").build()
                }
            }
            return instance as DatabaseFavUser
        }
    }
}