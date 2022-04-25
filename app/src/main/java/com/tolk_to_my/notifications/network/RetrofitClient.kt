package com.tolk_to_my.notifications.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var BASE_URL: String = "https://fcm.googleapis.com/"

    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)

    var retrofit: Retrofit = builder.build()


    fun makeRetrofitService(): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

}

