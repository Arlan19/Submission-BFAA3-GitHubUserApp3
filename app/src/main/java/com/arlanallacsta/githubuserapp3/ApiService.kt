package com.arlanallacsta.githubuserapp3

import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q")
        query: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getUsersDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getUsersFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getUsersFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>


}