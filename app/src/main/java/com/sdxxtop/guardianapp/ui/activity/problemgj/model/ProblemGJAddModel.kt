package com.sdxxtop.guardianapp.ui.activity.problemgj.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.http.net.ImageAndVideoParams
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.ui.activity.problemgj.bean.ProblemGJIndexBean
import com.sdxxtop.guardianapp.utils.UIUtils
import com.sdxxtop.guardianapp.utils.VideoCompressUtil
import java.io.File

/**
 * Date:2020/2/28
 * author:lwb
 * Desc:
 */
class ProblemGJAddModel : BaseViewModel() {

    var indexBean = MutableLiveData<ProblemGJIndexBean>()
    var reportSuccess = MutableLiveData<Boolean>(false)

    /**
     * 初始化数据
     */
    fun postIndexData() {
        showLoadingDialog(true)
        loadOnUI({
            val params = Params()
            params.put("esid", 1)
            //这里实际上返回了结果
            RetrofitClient.apiService.postGJData(params.data)
        }, {
            showLoadingDialog(false)
            indexBean.value = it
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

    fun reportProblem(
            context: Context,
            problem: String, // 问题清单
            cuoshi: String,  // 措施清单
            shixiao: String,  // 时效清单
            zeren: String,   //责任清单
            category_id: Int,  //分类id
            event_settings_id: Int = 1,  //事件流id
            end_time: String,  // 复查事件截止日期
            status: Int,  //是否是复查 默认传0普通事件
            supplement: String,  //定位补充描述
            title: String,  //标题
            path_type: Int,  // 上报路径
            patrol_type: Int,  // 巡查方式
            place: String,  // 上报地址
            longitude: String,   //上报地址经纬度(经度,纬度)
            content: String,   //事件内容
            imagePushPath: List<File>,  //多张图片用img[],暂时不上传视频,只上传图片
            videoPushPath: List<File>  //多张图片用img[],暂时不上传视频,只上传图片
    ) {
        val params = ImageAndVideoParams()
        params.put("prm", problem)
        params.put("mes", cuoshi)
        params.put("prtion", shixiao)
        params.put("resty", zeren)
        params.put("cgid", category_id)
        params.put("esid", event_settings_id)
        params.put("etime", end_time)
        params.put("sta", status)
        params.put("spt", supplement)
        params.put("tl", title)
        params.put("pt", path_type)
        params.put("plt", patrol_type)
        params.put("pl", place)
        params.put("lt", longitude)
        params.put("ct", content)

        params.addImagePathList("img[]", imagePushPath)

        if (videoPushPath.isNotEmpty()) {
            val file: File = videoPushPath[0]
            var util = VideoCompressUtil(context)
            util.videoCompress(file.path)
            util.setOnVideoCompress(object : VideoCompressUtil.OnVideoCompress {
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

    private fun request(params: ImageAndVideoParams) {
        showLoadingDialog(true)
        loadOnUI({
            //这里实际上返回了结果
            var data = params.imgAndVideoData
            Log.e("data==", "" + data.toString())
            RetrofitClient.apiService.reportProblem(data)
        }, {
            showLoadingDialog(false)
            reportSuccess.value = true
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

}