package com.sdxxtop.guardianapp.ui.assignevent.assignmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.bean.CategoryStatusBean
import com.sdxxtop.guardianapp.model.http.net.ImageAndVideoParams
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.utils.UIUtils
import com.sdxxtop.guardianapp.utils.VideoCompressUtil
import com.sdxxtop.guardianapp.utils.VideoCompressUtil.OnVideoCompress
import java.io.File

/**
 * Date:2020-02-13
 * author:lwb
 * Desc:
 */
class AddAssignEventModel : BaseViewModel() {

    var category = MutableLiveData<List<CategoryStatusBean.CategoryBean>>()
    var assignId = MutableLiveData<String>()

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
            context: Context,
            title: String, // 标题
            type: Int,   // 类型 1.个人 2.部门
            level: Int,  //紧急程度(1:低 2:中 3:高)
            is_return: Int,  //是否需要回传文件(1:是 2:否)
            cat_id: Int,    //分类id
            main_id: String,    //选中的部门id/人员id
            due_time: String,    //截止日期
            content: String,    //描述
            imagePushPath: List<File>,
            videoPushPath: List<File>
    ) {
        val params = ImageAndVideoParams()
        params.put("te", title)
        params.put("ty", type)
        params.put("ge", level)
        params.put("ir", is_return)
        params.put("ci", cat_id)
        params.put("mi", main_id)
        params.put("dt", due_time)
        params.put("ct", content)
        params.addImagePathList("img", imagePushPath)

        if (videoPushPath.isNotEmpty()) {
            val file: File = videoPushPath[0]
            var util = VideoCompressUtil(context)
            util.videoCompress(file.path)
            util.setOnVideoCompress(object : OnVideoCompress {
                override fun success(path: String) {
                    params.addCompressVideoPath("video", File(path))
                    request(params)
                }

                override fun fail() {
                    UIUtils.showToast("压缩失败,请重新尝试")
                }
            })
        } else {
            request(params)
        }
    }

    fun request(params: ImageAndVideoParams) {
        showLoadingDialog(true)
        loadOnUI({
            //这里实际上返回了结果
            var data = params.imgAndVideoData
            Log.e("data==", "" + data.toString())
            RetrofitClient.apiService.addAssignEvent(data)
        }, {
            showLoadingDialog(false)
            assignId.value = it
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }
}