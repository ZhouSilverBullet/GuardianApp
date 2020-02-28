package com.sdxxtop.guardianapp.ui.activity.problemgj.model

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.ui.activity.problemgj.bean.ListData
import com.sdxxtop.guardianapp.ui.activity.problemgj.bean.ProblemGJIndexBean
import com.sdxxtop.guardianapp.utils.UIUtils

/**
 * Date:2020/2/28
 * author:lwb
 * Desc:
 */
class ProblemGJListModel : BaseViewModel() {

    var adapterData = MutableLiveData<ListData>()

    fun postList(startPage: Int) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("sp", startPage)
            //这里实际上返回了结果
            RetrofitClient.apiService.postGJListData(params.data)
        }, {
            showLoadingDialog(false)
            adapterData.value = it
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

}