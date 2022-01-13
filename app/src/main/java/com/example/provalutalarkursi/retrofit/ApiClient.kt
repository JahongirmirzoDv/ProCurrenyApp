package com.example.provalutalarkursi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = "https://nbu.uz/uz/exchange-rates/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService = getRetrofit().create(ApiService::class.java)
}