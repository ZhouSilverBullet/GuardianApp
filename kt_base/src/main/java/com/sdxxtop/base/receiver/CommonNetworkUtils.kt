package com.sdxxtop.base.receiver

import android.content.Context
import android.net.ConnectivityManager

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 14:41
 * Version: 1.0
 * Description:
 */

object CommonNetworkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val systemService = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = systemService.activeNetworkInfo
        return activeNetworkInfo?.isAvailable ?: false
    }
}