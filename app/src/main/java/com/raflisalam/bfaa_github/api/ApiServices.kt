package com.raflisalam.bfaa_github.api

import com.raflisalam.bfaa_github.model.DataUser
import com.raflisalam.bfaa_github.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUsers(
        @Query("q") query: String
    ) : Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun detailUser(
        @Path("username") username: String
    ) : Call<DataUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ) : Call<ArrayList<DataUser>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<DataUser>>

}