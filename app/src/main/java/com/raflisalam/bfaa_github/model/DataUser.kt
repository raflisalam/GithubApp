package com.raflisalam.bfaa_github.model

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class UserResponse(
    @SerializedName("items")
    val dataItems: ArrayList<DataUser>
)

data class DataUser(
    val id: Int,
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val imgUser: String,
    val name: String,
    val location: String,
    val company: String,
    @SerializedName("public_repos")
    val repo: Int,
    val followers: Int,
    val following: Int,

)