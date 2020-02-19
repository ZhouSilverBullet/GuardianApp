package com.sdxxtop.guardianapp.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.bean.CategoryStatusBean
import com.sdxxtop.guardianapp.model.bean.EventIndexBean
import com.sdxxtop.guardianapp.model.bean.FlyEventListBean
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.utils.UIUtils

/**
 * Date:2020-02-19
 * author:lwb
 * Desc:
 */
class EventReportListModel : BaseViewModel() {

    var monthData = MutableLiveData<List<FlyEventListBean.MonthTash>>()
    var category = MutableLiveData<List<CategoryStatusBean.CategoryBean>>()
    var adapterList = MutableLiveData<List<EventIndexBean.EventBean>>()


    /**
     * 请求每个月的数据
     */
    fun postMonthEvnet(month: String) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("mt", month)
            //这里实际上返回了结果
            RetrofitClient.apiService.postMonthEvnet(params.data)
        }, {
            showLoadingDialog(false)
            monthData.value = it.month_event
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

    /**
     * 分类数据
     */
    fun postCategoryData() {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            //这里实际上返回了结果
            RetrofitClient.apiService.postCategoryData(params.data)
        }, {
            showLoadingDialog(false)
            category.value = it.category
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

    /**
     * 获取某天的数据
     */
    fun posData(statusId: Int, categoryId: Int, date: String) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("st", date)
            params.put("et", 2)
            params.put("su", statusId)
            params.put("cid", categoryId)
            //这里实际上返回了结果
            RetrofitClient.apiService.postData(params.data)
        }, {
            showLoadingDialog(false)
            adapterList.value = it.event
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }
}