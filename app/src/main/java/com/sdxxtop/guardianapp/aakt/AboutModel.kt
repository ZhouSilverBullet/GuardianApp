package com.sdxxtop.guardianapp.aakt

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.utils.UIUtils

/**
 * @author :  lwb
 * Date: 2020/1/9
 * Desc:
 */
class AboutModel : BaseViewModel() {
    var str = MutableLiveData<String>("ssssss")
    val monthList = MutableLiveData<List<Echart>>()
    var str2 = ObservableField<String>()

    var index = 0

    fun method() {
        index += 1
        str.value = "haha$index"
        str2.set("haha$index")
    }

    fun request() {

        str2.set("haha$index")

        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("de", "2020-01-10")
            //这里实际上返回了结果
            RetrofitClient.apiService.postAppInit(params.data)
        }, {
            str.value = "0"
            monthList.value = it.echarts
            showLoadingDialog(false)
//            val ss = it.lists.usually_score
//            str2.set(""+ss)
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }
}