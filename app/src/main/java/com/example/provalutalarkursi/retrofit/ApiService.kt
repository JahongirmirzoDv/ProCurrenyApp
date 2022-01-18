package com.example.provalutalarkursi.retrofit

import com.example.provalutalarkursi.models.Data
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("json/")
    suspend fun getData(): List<Data>
}