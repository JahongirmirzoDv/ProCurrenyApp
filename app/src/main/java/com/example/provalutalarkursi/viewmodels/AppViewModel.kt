package com.example.provalutalarkursi.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.provalutalarkursi.models.Data
import com.example.provalutalarkursi.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppViewModel :
    ViewModel() {
    private var liveData = MutableLiveData<List<Data>>()
    private val TAG = "HTTPService"

    fun getData(): MutableLiveData<List<Data>> {
        ApiClient.apiService.getData().enqueue(object :
            Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    liveData.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
        return liveData
    }
}