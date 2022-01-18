package com.example.provalutalarkursi.repository

import com.example.provalutalarkursi.retrofit.ApiService

class AppRepository(var apiService: ApiService) {

    suspend fun getAllData() = apiService.getData()

}