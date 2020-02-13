package com.sdxxtop.guardianapp.ui.assignevent.assignmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.bean.CategoryStatusBean
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.utils.UIUtils
import java.io.File

/**
 * Date:2020-02-13
 * author:lwb
 * Desc:
 */
class AddAssignEventModel : BaseViewModel() {

    var category = MutableLiveData<List<CategoryStatusBean.CategoryBean>>()

    fun postLevelData() {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            //这里实际上返回了结果
            RetrofitClient.apiService.postCategoryData(params.data)
        }, {
            showLoadingDialog(false)
            category.value = it.category
            Log.e("AddAssignEventModel", "$it")
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

    fun pushReport(
            title: String, // 标题
            type: Int,   // 类型 1.个人 2.部门
            level: Int,  //紧急程度(1:低 2:中 3:高)
            is_return: Int,  //是否需要回传文件(1:是 2:否)
            cat_id: Int,    //分类id
            main_id: String,    //选中的部门id/人员id
            due_time: String,    //截止日期
            content: String,    //描述
            imagePushPath: List<File>,
            vedioPushPath: List<File>
    ) {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("te", title)
            params.put("ty", type)
            params.put("ge", level)
            params.put("ir", is_return)
            params.put("ci", cat_id)
            params.put("mi", main_id)
            params.put("dt", due_time)
            params.put("ct", content)
            //这里实际上返回了结果
            RetrofitClient.apiService.addAssignEvent(params.data)
        }, {
            showLoadingDialog(false)
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }
}