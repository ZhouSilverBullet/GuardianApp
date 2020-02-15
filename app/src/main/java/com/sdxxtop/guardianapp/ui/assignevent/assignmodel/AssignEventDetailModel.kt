package com.sdxxtop.guardianapp.ui.assignevent.assignmodel

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.bean.AssignDetailBean
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.utils.UIUtils

/**
 * Date:2020-02-11
 * author:lwb
 * Desc:
 */
class AssignEventDetailModel : BaseViewModel() {

    var assignDetailData = MutableLiveData<AssignDetailBean>()
    var finishActivity = MutableLiveData<Boolean>(false)


    fun loadAssignDetail(assignId: String) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("ai", assignId)
            //这里实际上返回了结果
            RetrofitClient.apiService.postAssignDetail(params.data)
        }, {
            showLoadingDialog(false)
            assignDetailData.value = it
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

    /**
     * 执行 -- 确认
     */
    fun postConfirmEvent(assignId: String, execId: Int) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("ai", assignId)
            params.put("ei", execId)
            //这里实际上返回了结果
            RetrofitClient.apiService.postConfirmEvent(params.data)
        }, {
            showLoadingDialog(false)
            loadAssignDetail(assignId)
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

    /**
     * 交办 - 催办
     */
    fun postCuiBanEvent(assignId: String) {

    }

    /**
     * 执行 - 申请退回
     */
    fun postRejectEvent(assignId: String, execId: Int, rejectReason: String) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("ai", assignId)
            params.put("ry", rejectReason)
            params.put("ei", execId)
            //这里实际上返回了结果
            RetrofitClient.apiService.postRejectEvent(params.data)
        }, {
            showLoadingDialog(false)
            loadAssignDetail(assignId)
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

    /**
     * 交办 - 关闭任务
     */
    fun postColseEvent(assignId: String) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("ai", assignId)
            //这里实际上返回了结果
            RetrofitClient.apiService.postColseEvent(params.data)
        }, {
            showLoadingDialog(false)
            finishActivity.value = true
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

}