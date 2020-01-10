package com.sdxxtop.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import com.sdxxtop.base.lifecycle.ActivityLifecycleImpl

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

        mBinding.lifecycleOwner = this
        mBinding.executePendingBindings()

        initView()
        initEvent()
        initData()
        loadData()
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

}