package com.sdxxtop.base

import androidx.lifecycle.ViewModel

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 22:25
 * Version: 1.0
 * Description:
 */
interface IVMView<VM : ViewModel> : IView {
    /**
     * vm 的className
     *
     */
    fun vmClazz(): Class<VM>

    /**
     * 用于 dataBind和vm绑定的方法
     * 布局里头如果有 vm 然后进行和 databinding进行绑定
     */
    fun bindVM()

    /**
     * 初始化 vm的Observe
     * 用于需要处理后的数据进行界面交互的
     *
     */
    fun initObserve()


}