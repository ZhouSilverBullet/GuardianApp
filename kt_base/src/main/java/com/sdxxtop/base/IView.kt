package com.sdxxtop.base

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 22:24
 * Version: 1.0
 * Description:
 */

interface IView {
    /**
     * activity的布局
     */
    fun layoutId(): Int


    /**
     * 初始化界面的一些操作
     */
    fun initView()

    /**
     * 做一些事件初始化
     */
    fun initEvent()

    /**
     * 初始化数据，或者获取数据
     *
     * 所有的base进行了实现，提供子类使用，比loadData前面一步被调用
     * {@link IView.loadData()}
     * 这个感觉没什么必要了，有个loadData的情况下
     */
    fun initData()

    /**
     * 网路请求 或者 做耗时操作
     */
    fun loadData()
}