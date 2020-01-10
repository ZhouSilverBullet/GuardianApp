package com.sdxxtop.base.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sdxxtop.base.BaseKTFragment
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.base.R
import com.sdxxtop.base.databinding.FragmentBaseTitleBinding


/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 18:29
 * Version: 1.0
 * Description:
 */
abstract class BaseTitleFragment<DB : ViewDataBinding, VM : BaseViewModel> : BaseKTFragment<DB, VM>(), ITitleView {
    companion object {
        const val TAG = "BaseTitleFragment"
    }

    lateinit var mParentBinding: FragmentBaseTitleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mParentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base_title, container, false)

        val childView = super.onCreateView(inflater, container, savedInstanceState)
        mParentBinding.flContent.addView(childView)

        return mParentBinding.root
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

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    /**
     * 不一定有页面一定要重写这个方法
     */
    override fun loadData() {
    }

    override fun getTitleView():TitleView {
        return mParentBinding.stvTitle
    }

    override fun setTitleValue(titleValue: String) {
        mParentBinding.stvTitle.setTitleValue(titleValue)
    }

    override fun setTitleColor(titleColor: Int) {
        mParentBinding.stvTitle.setTitleColor(titleColor)
    }

    override fun setBgColor(bgColor: Int) {
        mParentBinding.stvTitle.setBgColor(bgColor)
    }


}