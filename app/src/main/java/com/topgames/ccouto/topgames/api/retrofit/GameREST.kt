package com.topgames.ccouto.topgames.api.retrofit

import com.topgames.ccouto.topgames.domain.GameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by ccouto on 14/11/2017.
 */
interface GameREST {

    @Headers("Client-ID: xgwzpcly2uvohf8g5sxfm6w6u0kp63","Accept: application/vnd.twitchtv.v5+json")
    @GET("top")
    fun getTopGames(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<GameResponse>
}