package com.sdxxtop.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Log
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import org.apache.http.conn.ConnectTimeoutException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 14:41
 * Version: 1.0
 * Description:
 */

object NetworkUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val systemService = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = systemService.activeNetworkInfo
        return activeNetworkInfo?.isAvailable ?: false
    }

    fun getHttpExceptionMsg(exception: Throwable?, errorMsg: String = ""): String {
        var defaultMsg = "未知错误"
        if (exception != null) {
            Log.e("NetworkUtils","Request Exception:" + exception.message)
            if (exception is UnknownHostException) {
                defaultMsg = "您的网络可能有问题,请确认连接上有效网络后重试"
            } else if (exception is ConnectTimeoutException) {
                defaultMsg = "连接超时,您的网络可能有问题,请确认连接上有效网络后重试"
            } else if (exception is SocketTimeoutException) {
                defaultMsg = "请求超时,您的网络可能有问题,请确认连接上有效网络后重试"
            } else if (exception is JsonParseException) {
                defaultMsg = "数据解析出现错误"
            } else if (exception is JsonSyntaxException) {
                defaultMsg = "数据解析语法错误"
            } else {
                defaultMsg = "未知的网络错误, 请重试"
            }
        } else {
            if (!TextUtils.isEmpty(errorMsg)) {
                Log.e("NetworkUtils", "Request Exception:Request Exception errorMsg: $errorMsg")
                val lowerMsg = errorMsg.toLowerCase(Locale.ENGLISH)
                if (lowerMsg.contains("java")
                        || lowerMsg.contains("exception")
                        || lowerMsg.contains(".net")
                        || lowerMsg.contains("java")) {
                    defaultMsg = "未知错误, 请重试"
                } else {
                    defaultMsg = "未知错误, 请重试"
                }
            }
        }
        return defaultMsg
    }
}