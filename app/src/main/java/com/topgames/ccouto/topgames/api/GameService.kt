package com.topgames.ccouto.topgames.api

import android.content.Context
import android.net.ConnectivityManager
import com.topgames.ccouto.topgames.R
import com.topgames.ccouto.topgames.TopGamesApplicationApplication
import com.topgames.ccouto.topgames.api.retrofit.GameREST
import com.topgames.ccouto.topgames.domain.GameResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by ccouto on 14/11/2017.
 */
object GameService {
    private val BASE_URL = TopGamesApplicationApplication.getInstance().resources.getString(R.string.baseUrl)
    private var service : GameREST
    private val CODE_200 = 200

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        service = retrofit.create(GameREST::class.java)
    }

    fun getTopGames(callBackGeneric: CallBackGeneric<GameResponse>, offset: Int) {
        val call = service.getTopGames(10, offset)
        call.enqueue(object : Callback<GameResponse>{
            override fun onResponse(call: Call<GameResponse>?, response: Response<GameResponse>) {
                if(response.code() == CODE_200){
                    val resp: GameResponse? = response.body()
                    callBackGeneric.onSuccess(resp!!)
                }
            }

            override fun onFailure(call: Call<GameResponse>?, t: Throwable?) {
                t?.printStackTrace()
                var error = fetchErrorMessage(t!!)
                callBackGeneric.onError(GameResponse(null,null,error))
            }
        } )

    }

    private fun fetchErrorMessage(throwable: Throwable): String {
        var errorMsg = TopGamesApplicationApplication.getInstance().getResources().getString(R.string.error_msg_unknown)

        if (!isNetworkConnected()) {
            errorMsg = TopGamesApplicationApplication.getInstance().getResources().getString(R.string.error_msg_no_internet)
        } else if (throwable is TimeoutException) {
            errorMsg = TopGamesApplicationApplication.getInstance().getResources().getString(R.string.error_msg_timeout)
        }

        return errorMsg
    }

    private fun isNetworkConnected(): Boolean {
        val cm = TopGamesApplicationApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null
    }
}