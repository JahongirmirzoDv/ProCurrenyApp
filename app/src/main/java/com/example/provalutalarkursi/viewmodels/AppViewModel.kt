package com.example.provalutalarkursi.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.provalutalarkursi.db.AppDatabase
import com.example.provalutalarkursi.models.Data
import com.example.provalutalarkursi.retrofit.ApiClient
import com.example.provalutalarkursi.utils.Resource
import kotlinx.coroutines.*

class AppViewModel :
    ViewModel() {
    private var liveData = MutableLiveData<Resource<List<Data>>>()
    private val TAG = "ApiViewModel"
    fun getData(context: Context): MutableLiveData<Resource<List<Data>>> {
        var db = AppDatabase.getInstance(context)
        viewModelScope.launch {
            liveData.postValue(Resource.loading(null))
            try {
                coroutineScope {
                    supervisorScope {
                        var ascync =
                            withContext(Dispatchers.Default) {
                                ApiClient.apiService.getData()
                            }
                        liveData.postValue(Resource.success(ascync))
                        val list = db.dataDao().getList()
                        if (list.isEmpty()) {
                            ascync.forEach {
                                val split = it.date!!.split(" ")
                                val data = Data(
                                    it.code,
                                    it.cb_price,
                                    it.date,
                                    it.nbu_buy_price,
                                    it.nbu_cell_price,
                                    it.title,
                                    split[0],
                                    split[1]
                                )
                                db.dataDao().addData(data)
                            }
                            db.dataDao()
                                .addData(Data("1", "UZS", "12.12.2022 09:00", "1", "1", "sum"))
                        } else {
                            if (ascync[0].date != list[0].date) {
                                ascync.forEach {
                                    Log.d(TAG, "getData: ${it.date}")
                                    val split = it.date!!.split(" ")
                                    val data = Data(
                                        it.code,
                                        it.cb_price,
                                        it.date,
                                        it.nbu_buy_price,
                                        it.nbu_cell_price,
                                        it.title,
                                        split[0],
                                        split[1]
                                    )
                                    db.dataDao().addData(data)
                                }
                                db.dataDao()
                                    .addData(Data("1", "UZS", "12.12.2022 09:00", "1", "1", "sum"))
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                liveData.postValue(Resource.error(e.message ?: "Error", null))
                Log.e(TAG, "getData: ${e.printStackTrace()}")
            }
        }
        return liveData
    }
}