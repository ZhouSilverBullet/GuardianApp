package com.sdxxtop.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.sdxxtop.base.lifecycle.FragmentLifecycleImpl

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 18:29
 * Version: 1.0
 * Description:
 * 这个类只进行一次加载
 * 剩下的再次来到的时候，要用户手动加载
 *
 */
abstract class BaseLazyFragment<DB : ViewDataBinding, VM : ViewModel> : Fragment(), IVMView<VM> {
    companion object {
        const val TAG = "BaseFragment"
    }

    /**
     * 是否已经请求了下载
     *  todo 这个标识数据是否加载为空
     */
    var isLoad = false

    lateinit var mBinding: DB

    val mViewModel: VM by lazy {
        ViewModelProviders.of(this@BaseLazyFragment)[vmClazz()]

//        下面这种方式是反射获取的，有时候会比较影响性能
//        ViewModelProviders.of(this@BaseKTActivity)[getVmClass()]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate<DB>(inflater, layoutId(), container, false)
        lifecycle.addObserver(FragmentLifecycleImpl(javaClass.simpleName))
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindVM()
        mBinding.executePendingBindings()

        initView()
        initObserve()
        initEvent()
        initData()
    }

    override fun bindVM() {

    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        if (!isLoad) {
            isLoad = true
            loadData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

}