package com.example.provalutalarkursi.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewPagerViewmodel:ViewModel() {
    private var liveData = MutableLiveData<Int>()

    fun set(list:Int){
        liveData.value = list
    }
    fun get(): MutableLiveData<Int> {
        return liveData
    }
}