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

}