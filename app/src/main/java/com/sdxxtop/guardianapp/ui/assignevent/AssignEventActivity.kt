package com.sdxxtop.guardianapp.ui.assignevent

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityAssignEventBinding
import com.sdxxtop.guardianapp.ui.assignevent.adapter.AssignListAdapter
import com.sdxxtop.guardianapp.ui.assignevent.assignmodel.AssignListModel
import com.sdxxtop.guardianapp.ui.widget.SingleStyleView
import kotlinx.android.synthetic.main.activity_assign_event.*

class AssignEventActivity : BaseKTActivity<ActivityAssignEventBinding, AssignListModel>() {

    private var statusTx = "全部"
    private val adapter = AssignListAdapter()
    private var zxSp = 0
    private var jbSp = 0
    private var currentSelectItem = 1

    private val statusSelect: SingleStyleView by lazy {
        val list = arrayListOf<SingleStyleView.ListDataBean>()
        //待确认，待解决，已拒绝，已完成
        list.add(SingleStyleView.ListDataBean(0, "全部"))
        list.add(SingleStyleView.ListDataBean(1, "待确认"))
        list.add(SingleStyleView.ListDataBean(2, "待解决"))
        list.add(SingleStyleView.ListDataBean(3, "已拒绝"))
        list.add(SingleStyleView.ListDataBean(4, "已完成"))
        SingleStyleView(this@AssignEventActivity, list)
    }

    override fun layoutId() = R.layout.activity_assign_event
    override fun vmClazz() = AssignListModel::class.java
    override fun bindVM() {
        mBinding.vm = mViewModel
    }

//    override fun loadSirBindView(): View {
//        return mBinding.smartRefresh
//    }

    override fun initObserve() {
        mBinding.vm?.isShowEmpty?.observe(this, Observer {
//            showLoadSir(false)
        })
    }

    override fun preLoad() {

    }

    override fun initData() {
//        mBinding.vm?.postZXData()
//        mBinding.vm?.postJBData()
    }

    override fun initView() {
        titleView.tvRight.setOnClickListener { startActivity(Intent(AssignEventActivity@ this, AddAssignEventActivity::class.java)) }
        adapter.setOnItemClickListener { adapter, view, position -> startActivity(Intent(AssignEventActivity@ this, AssignEventDetailActivity::class.java)) }
        tvStatus.setOnClickListener(this)

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
        statusSelect.setOnItemSelectLintener { _, result ->
            statusTx = result
            tvStatus.text = statusTx
        }

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //选中之后再次点击即复选时触发
                if (tab.text == "我交办的") {
                    currentSelectItem = 2
                    tvStatus.visibility = View.INVISIBLE
                } else {
                    currentSelectItem = 1
                    tvStatus.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                if (currentSelectItem == 1) {
                    mBinding.vm?.postZXData()
                }
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
            }

        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvStatus -> {
                statusSelect.show()
            }
        }
    }

}
