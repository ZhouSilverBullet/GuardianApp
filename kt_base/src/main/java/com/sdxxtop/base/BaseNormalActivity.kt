package com.sdxxtop.base

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import com.sdxxtop.base.lifecycle.ActivityLifecycleImpl
import com.sdxxtop.base.utils.StatusBarUtil

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 15:19
 * Version: 1.0
 * Description:
 */
abstract class BaseNormalActivity<DB : ViewDataBinding> : AppCompatActivity(), IView {
    companion object {
        const val TAG = "BaseKTActivity"
    }

    val mBinding: DB by lazy {
        setContentView<DB>(this, layoutId())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(ActivityLifecycleImpl(this))
//        setSwipeBackEnable(false);
        if (isInitStatusBar()) {
            initStatusBar()
        }

        mBinding.lifecycleOwner = this
        mBinding.executePendingBindings()

        initView()
        initEvent()
        initData()
        loadData()
    }

    open fun isInitStatusBar(): Boolean {
        return true
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    override fun loadData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }


    /**
     * statusBar 控制
     */
    open fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = window
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (StatusBarUtil.MIUISetStatusBarLightMode(getWindow(), true)) { //小米MIUI系统
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //Android6.0以上系统
                    StatusBarUtil.android6_SetStatusBarLightMode(getWindow())
                    StatusBarUtil.compat(this)
                } else {
                    StatusBarUtil.compat(this)
                }
            } else if (StatusBarUtil.FlymeSetStatusBarLightMode(getWindow(), true)) { //魅族flyme系统
                StatusBarUtil.compat(this)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //Android6.0以上系统
                StatusBarUtil.android6_SetStatusBarLightMode(getWindow())
                StatusBarUtil.compat(this)
            }
        }
    }
}