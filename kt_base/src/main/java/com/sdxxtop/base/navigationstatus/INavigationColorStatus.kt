package com.sdxxtop.base.navigationstatus

import android.view.Window

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-16 10:05
 * Version: 1.0
 * Description: 状态栏的颜色控制
 */
interface INavigationColorStatus {
    fun setDarkStatusIcon(window: Window, bDark: Boolean)
}