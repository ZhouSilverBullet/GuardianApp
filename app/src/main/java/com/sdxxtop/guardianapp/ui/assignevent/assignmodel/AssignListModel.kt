package com.sdxxtop.guardianapp.ui.assignevent.assignmodel

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.bean.AssignListBean
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.utils.UIUtils

/**
 * Date:2020-02-07
 * author:lwb
 * Desc:
 */
class AssignListModel : BaseViewModel() {

    var zxData = MutableLiveData<AssignListBean>()
    var jbData = MutableLiveData<AssignListBean>()

    /**
     * 执行数据列表接口
     */
    fun postZXData(pageSize: Int, status: Int, startTiem: String, endTime: String) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("sp", pageSize)
            params.put("ss", status)
            params.put("sd", startTiem)
            params.put("ed", endTime)
            //这里实际上返回了结果
            RetrofitClient.apiService.postZXData(params.data)
        }, {
            showLoadingDialog(false)
            zxData.value = it
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

    fun postJBData(pageSize: Int, startTiem: String, endTime: String) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("sp", pageSize)
            params.put("sd", startTiem)
            params.put("ed", endTime)
            //这里实际上返回了结果
            RetrofitClient.apiService.postJBData(params.data)
        }, {
            showLoadingDialog(false)
            jbData.value = it
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

}