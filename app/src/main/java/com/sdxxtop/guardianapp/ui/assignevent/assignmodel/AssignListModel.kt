package com.sdxxtop.guardianapp.ui.assignevent.assignmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel

/**
 * Date:2020-02-07
 * author:lwb
 * Desc:
 */
class AssignListModel : BaseViewModel() {

    val isShowEmpty = MutableLiveData<Boolean>(true)

    fun stopLoad() {
        isShowEmpty.value = false
        Log.e("stopLoad==", "chicked")
    }

}