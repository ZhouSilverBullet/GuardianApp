package com.sdxxtop.base.title

import androidx.annotation.ColorRes

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-18 09:53
 * Version: 1.0
 * Description:
 */
interface ITitleView {

    /**
     * 获取titleView
     */
    fun getTitleView():TitleView

    /**
     * 设置标题的值
     */
    fun setTitleValue(titleValue: String)

    /**
     * 设置标题的颜色
     */
    fun setTitleColor(@ColorRes titleColor: Int)

    /**
     * 设置背景颜色
     */
    fun setBgColor(@ColorRes bgColor: Int)
}