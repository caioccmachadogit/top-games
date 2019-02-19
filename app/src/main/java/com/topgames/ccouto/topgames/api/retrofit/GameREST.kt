package com.topgames.ccouto.topgames.api.retrofit

import com.topgames.ccouto.topgames.api.ApiConstants
import com.topgames.ccouto.topgames.domain.GameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by ccouto on 19/02/2019.
 */
interface GameREST {

    @Headers(ApiConstants.CLIENT_ID,ApiConstants.ACCEPT)
    @GET(ApiConstants.GET_TOP)
    fun getTopGames(@Query(ApiConstants.QR_LIMIT) limit: Int, @Query(ApiConstants.QR_OFFSET) offset: Int): Call<GameResponse>
}