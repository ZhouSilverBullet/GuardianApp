package com.sdxxtop.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kingja.loadsir.core.LoadSir
import com.sdxxtop.base.dialog.LoadingDialog
import com.sdxxtop.base.lifecycle.ActivityLifecycleImpl
import com.sdxxtop.base.loadsir.EmptyCallback
import com.sdxxtop.base.loadsir.ErrorCallback
import com.sdxxtop.base.loadsir.LoadingCallback
import com.sdxxtop.base.navigationstatus.INavigationColorStatus

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 15:19
 * Version: 1.0
 * Description:
 */
abstract class BaseKTActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), IVMView<VM>, INavigationColorStatus, View.OnClickListener {
    companion object {
        const val TAG = "BaseActivity"
    }

    val mBinding: DB by lazy {
        setContentView<DB>(this, layoutId())
    }

    val mViewModel: VM by lazy {
        ViewModelProviders.of(this@BaseKTActivity)[vmClazz()]

//        下面这种方式是反射获取的，有时候会比较影响性能
//        ViewModelProviders.of(this@BaseActivity)[getVmClass()]
    }

    val loadService by lazy {
        LoadSir.getDefault().register(loadSirBindView()) {
            preLoad();
        }
    }

    open fun loadSirBindView(): View {
        return View(this)
    }

    val mLoadingDialog by lazy {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(ActivityLifecycleImpl(this))

        mBinding.lifecycleOwner = this
        bindVM()
        mBinding.executePendingBindings()

        setDarkStatusIcon(window, isDarkStatusIcon())

        loadService.showCallback(LoadingCallback::class.java)
        //加载错误页面
        mViewModel.mThrowable.observe(this, Observer {
            if (it == null) {
                showErrorCallback(-100, "数据发生异常")
            } else {
                showErrorCallback(it.code, it.errorMsg)
            }
        })

        initView()
        initSelfObserve()
        initObserve()
        initEvent()
        initData()
        loadData()
    }

    /**
     * 默认显示errorCallback
     * 有的时候，后台会出现
     * {
    "code": 201,
    "msg": "暂无上报记录",
    "data": {}
    }
     * 这个就可能就是显示数据为空的界面了
     * 所以加入这个方法让子类对应可以重写来判断
     */
    open fun showErrorCallback(code: Int?, errorMsg: String?) {
        if (code == 201) {
            loadService.showCallback(EmptyCallback::class.java)
        } else {
            loadService.showCallback(ErrorCallback::class.java)
        }
    }

    private fun initSelfObserve() {
        mViewModel.mIsLoadingShow.observe(this, Observer {
            if (it) {
                mLoadingDialog.show()
            } else {
                mLoadingDialog.dismiss()
            }
        })
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    /**
     * 不一定有页面一定要重写这个方法
     */
    override fun loadData() {
    }

    /**
     * 没有数据的时候显示页面点击时回调
     */
    open fun preLoad() {
    }


    override fun onClick(v: View) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    override fun setDarkStatusIcon(window: Window, bDark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            var vis = decorView.systemUiVisibility
            vis = if (bDark) {
                vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            decorView.systemUiVisibility = vis
        }
    }

    open fun isDarkStatusIcon(): Boolean {
        return false
    }

    fun showLoadSir(isEmpty: Boolean) {
        if (isEmpty) {
            loadService.showCallback(EmptyCallback::class.java)
        } else {
            loadService.showSuccess()
        }
    }

//    inline fun <reified vm : ViewModel> bindViewModel(): vm {
//        return ViewModelProviders.of(this@BaseActivity)[vm::class.java]
//    }

    //反射获取到baseActivity上面的泛型
//    fun getVmClass(): Class<VM> {
//        return (this@BaseActivity::javaClass.get().genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
//    }

}