package com.sdxxtop.guardianapp.ui.assignevent.assignmodel

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.bean.PartAndUserBean
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.utils.UIUtils

/**
 * Date:2020-02-13
 * author:lwb
 * Desc:
 */
class ChooseUserModel : BaseViewModel() {
    var partAndUserData = MutableLiveData<PartAndUserBean>()

    fun postData(search: String) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("sh", search)
            //这里实际上返回了结果
            RetrofitClient.apiService.postUserData(params.data)
        }, {
            partAndUserData.value = it
            showLoadingDialog(false)
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }
}