package com.sdxxtop.guardianapp.ui.assignevent.assignmodel

import android.content.Context
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.http.net.ImageAndVideoParams
import com.sdxxtop.guardianapp.ui.assignevent.AssignSolveActivity
import com.sdxxtop.guardianapp.utils.UIUtils
import com.sdxxtop.guardianapp.utils.VideoCompressUtil
import java.io.File

/**
 * Date:2020-02-15
 * author:lwb
 * Desc:
 */
class AssignSolveModel : BaseViewModel() {

    /**
     * 执行交办 - 解决
     */
    fun postSolveEvent(context: Context, assignId: String, execId: Int, editValue: String, imagePushPath: List<File>, videoPushPath: List<File>) {
        val params = ImageAndVideoParams()
        params.put("ai", assignId)
        params.put("ei", execId)
        params.put("ry", editValue)
        params.addImagePathList("img", imagePushPath)

        if (videoPushPath.isNotEmpty()) {
            val file: File = videoPushPath[0]
            var util = VideoCompressUtil(context)
            util.videoCompress(file.path)
            util.setOnVideoCompress(object : VideoCompressUtil.OnVideoCompress {
                override fun success(path: String) {
                    params.addCompressVideoPath("video", File(path))
                    request(params, context)
                }

                override fun fail() {
                    UIUtils.showToast("压缩失败,请重新尝试")
                }
            })
        } else {
            request(params, context)
        }
    }

    fun request(params: ImageAndVideoParams, context: Context) {
        showLoadingDialog(true)
        loadOnUI({
            //这里实际上返回了结果
            var data = params.imgAndVideoData
            RetrofitClient.apiService.postSolveEvent(data)
        }, {
            showLoadingDialog(false)
            val activity = context as AssignSolveActivity
            activity.finish()
        }, { _, msg, _ ->
            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }

}