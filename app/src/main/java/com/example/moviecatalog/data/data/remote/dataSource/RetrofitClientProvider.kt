package com.example.moviecatalog.data.data.remote.dataSource

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientProvide {
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofitProvider = Retrofit
        .Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL.BASE_URL)
        .build()
}