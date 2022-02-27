package com.raflisalam.bfaa_github.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity()
data class FavoriteModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "username")
    val login: String? = null,
    val avatar_url: String? = null
):Serializable
