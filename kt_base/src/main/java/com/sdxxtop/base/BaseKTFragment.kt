package com.sdxxtop.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.sdxxtop.base.dialog.LoadingDialog
import com.sdxxtop.base.load.IPreLoad

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 18:29
 * Version: 1.0
 * Description:
 */
abstract class BaseKTFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment(), IVMView<VM>, IPreLoad, View.OnClickListener {
    companion object {
        const val TAG = "BaseFragment"
    }

    lateinit var mBinding: DB
    lateinit var mLoadService: LoadService<Any>

    val mViewModel: VM by lazy {
        ViewModelProviders.of(this@BaseKTFragment)[vmClazz()]

//        下面这种方式是反射获取的，有时候会比较影响性能
//        ViewModelProviders.of(this@BaseActivity)[getVmClass()]
    }

    val mLoadingDialog by lazy {
        LoadingDialog(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate<DB>(inflater, layoutId(), container, false)
        mBinding.lifecycleOwner = this
        val loadSirBindView = loadSirBindView()
        if (loadSirBindView == null) {
            mLoadService = LoadSir.getDefault().register(mBinding.root) {
                preLoad()
            }
            return mLoadService.loadLayout
        } else {
            //自定义的一个加载界面
            mLoadService = LoadSir.getDefault().register(loadSirBindView) {
                preLoad()
            }
            return mBinding.root
        }
    }

    open fun loadSirBindView(): View? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindVM()
        mBinding.executePendingBindings()

        initView()
        initSelfObserve()
        initObserve()
        initEvent()
        initData()
    }

    private fun initSelfObserve() {
        mViewModel.mIsLoadingShow.observe(viewLifecycleOwner, Observer {
            if (it) {
                mLoadingDialog.show()
            } else {
                mLoadingDialog.dismiss()
            }
        })
    }

    override fun preLoad() {
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onClick(v: View) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    /**
     * 不一定有页面一定要重写这个方法
     */
    override fun loadData() {
    }

}