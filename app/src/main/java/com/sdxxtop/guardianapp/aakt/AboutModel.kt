package com.sdxxtop.guardianapp.aakt

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author :  lwb
 * Date: 2020/1/9
 * Desc:
 */
class AboutModel : ViewModel() {
    var str = MutableLiveData<String>("ssssss")
    var str2 = ObservableField<String>()

    var index = 0
    fun method() {
        index+=1
        str.value = "haha$index"
        str2.set("haha$index")
    }
}