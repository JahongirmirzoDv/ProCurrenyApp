package com.example.provalutalarkursi.retrofit

import com.example.provalutalarkursi.models.Data
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("json/")
    fun getData(): Call<List<Data>>
}