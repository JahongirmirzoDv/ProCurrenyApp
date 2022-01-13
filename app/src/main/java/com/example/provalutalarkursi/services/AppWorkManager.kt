package com.example.provalutalarkursi.services

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.provalutalarkursi.db.AppDatabase
import com.example.provalutalarkursi.models.Data
import com.example.provalutalarkursi.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppWorkManager(
    var context: Context,
    workerParameters: WorkerParameters,
) :
    Worker(context, workerParameters) {
    private val TAG = "GetData"
    override fun doWork(): Result {
        loadata()
        Log.d(TAG, "doWork: Running...")
        return Result.success()
    }

    private fun loadata() {
        var db = AppDatabase.getInstance(context)
        db.dataDao().deleteAll()
        ApiClient.apiService.getData().enqueue(object :
            Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    for (i in body!!) {
                        db.dataDao().addData(i)
                    }
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}