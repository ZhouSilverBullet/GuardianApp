package com.sdxxtop.guardianapp.ui.assignevent

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityAssignEventBinding
import com.sdxxtop.guardianapp.ui.assignevent.adapter.AssignListAdapter
import com.sdxxtop.guardianapp.ui.assignevent.assignmodel.AssignListModel
import kotlinx.android.synthetic.main.activity_assign_event.*

class AssignEventActivity : BaseKTActivity<ActivityAssignEventBinding, AssignListModel>() {

    val adapter = AssignListAdapter()

    override fun layoutId() = R.layout.activity_assign_event
    override fun vmClazz() = AssignListModel::class.java
    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun loadSirBindView(): View {
        return mBinding.smartRefresh
    }

    override fun initObserve() {
        mBinding.vm?.mIsLoadingShow?.observe(this, Observer {
            Log.e("回掉==", "${it}")
            showLoadSir(true)
        })

        mBinding.vm?.isShowEmpty?.observe(this, Observer {
            showLoadSir(it)
        })
    }

    override fun preLoad() {
        Log.e("preLoad", "stopLoad")
        mBinding.vm?.stopLoad()
    }

    override fun initView() {
        titleView.tvRight.setOnClickListener { startActivity(Intent(AssignEventActivity@ this, AddAssignEventActivity::class.java)) }


        val stringlist = arrayListOf("我执行的", "我交办的")
        for (item in stringlist) {
            val newTab = tablayout.newTab()
            newTab.text = item
            tablayout.addTab(newTab)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val list = arrayListOf<String>()
        for (index in 1..20) {
            list.add("")
        }
        adapter.replaceData(list)
    }

}
