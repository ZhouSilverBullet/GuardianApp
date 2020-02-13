package com.sdxxtop.guardianapp.ui.assignevent.assignmodel

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.utils.UIUtils

/**
 * Date:2020-02-07
 * author:lwb
 * Desc:
 */
class AssignListModel : BaseViewModel() {

    val isShowEmpty = MutableLiveData<Boolean>(true)

    /**
     * 执行数据列表接口
     */
    fun postZXData() {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("sp", "1")
            //这里实际上返回了结果
            RetrofitClient.apiService.postZXData(params.data)
        }, {
            showLoadingDialog(false)
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

    fun postJBData() {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("sp", "1")
            //这里实际上返回了结果
            RetrofitClient.apiService.postJBData(params.data)
        }, {
            showLoadingDialog(false)
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

}