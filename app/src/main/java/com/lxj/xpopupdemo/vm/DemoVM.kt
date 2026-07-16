package com.lxj.xpopupdemo.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DemoVM : ViewModel() {
    var liveData: MutableLiveData<String?> = MutableLiveData<String?>()

    override fun onCleared() {
        super.onCleared()
    }
}