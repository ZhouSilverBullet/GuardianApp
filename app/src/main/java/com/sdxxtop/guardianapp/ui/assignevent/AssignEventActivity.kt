package com.sdxxtop.guardianapp.ui.assignevent

import android.content.Intent
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
import com.sdxxtop.guardianapp.utils.Date2Util
import com.sdxxtop.guardianapp.utils.UIUtils
import kotlinx.android.synthetic.main.activity_assign_event.*

class AssignEventActivity : BaseKTActivity<ActivityAssignEventBinding, AssignListModel>() {

    private var isAssignment = 2  // 是否有权限
    private var statusNum = 0
    private val zx_adapter = AssignListAdapter(1)
    private val jb_adapter = AssignListAdapter(2)
    private var zxSp = 0
    private var jbSp = 0
    private var currentSelectItem = 1   //  当前选中的列表
    private var selectStartDate = Date2Util.getCurrentDateTime()  // 选中的开始日期
    private var selectEndDate = Date2Util.getCurrentDateTime()    // 选中的结束日期


    private val statusSelect: SingleStyleView by lazy {
        val list = arrayListOf<SingleStyleView.ListDataBean>()
//        状态（1:待部门确认 2:待个人确认 3:待解决 4:已完成 5:已退回）
        list.add(SingleStyleView.ListDataBean(0, "全部"))
        list.add(SingleStyleView.ListDataBean(1, "待部门确认"))
        list.add(SingleStyleView.ListDataBean(2, "待个人确认"))
        list.add(SingleStyleView.ListDataBean(3, "待解决"))
        list.add(SingleStyleView.ListDataBean(4, "已完成"))
        list.add(SingleStyleView.ListDataBean(5, "已退回"))
        SingleStyleView(this@AssignEventActivity, list)
    }

    override fun layoutId() = R.layout.activity_assign_event
    override fun vmClazz() = AssignListModel::class.java
    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun initObserve() {
        mBinding.vm?.zxData?.observe(this, Observer {
            if (zxSp == 0) {
                zx_adapter.replaceData(it.list)
            } else {
                zx_adapter.addData(it.list)
            }
        })

        mBinding.vm?.jbData?.observe(this, Observer {
            smartRefresh_jb.finishLoadMore()
            smartRefresh_jb.finishRefresh()

            if (jbSp == 0) {
                jb_adapter.replaceData(it.list)
            } else {
                jb_adapter.addData(it.list)
            }
        })

    }

    override fun preLoad() {

    }

    override fun onResume() {
        super.onResume()
        zxSp = 0
        jbSp = 0
        mBinding.vm?.postZXData(0, 0, selectStartDate, selectEndDate)
        mBinding.vm?.postJBData(0, selectStartDate, selectEndDate)
    }

    override fun initView() {
        isAssignment = intent.getIntExtra("isAssignment", 2)

        smartRefresh_zx.visibility = View.VISIBLE
        smartRefresh_jb.visibility = View.GONE

        titleView.tvRight.setOnClickListener {
            if (isAssignment == 1) {
                startActivity(Intent(AssignEventActivity@ this, AddAssignEventActivity::class.java))
            } else {
                UIUtils.showToast("暂无权限")
            }
        }

        tvStatus.setOnClickListener(this)

        val stringlist = arrayListOf("我执行的", "我交办的")
        for (item in stringlist) {
            val newTab = tablayout.newTab()
            newTab.text = item
            tablayout.addTab(newTab)
        }

        recyclerView_zx.layoutManager = LinearLayoutManager(this)
        recyclerView_zx.adapter = zx_adapter
        recyclerView_jb.layoutManager = LinearLayoutManager(this)
        recyclerView_jb.adapter = jb_adapter


        //状态选择
        statusSelect.setOnItemSelectLintener { id, result ->
            statusNum = id
            tvStatus.text = result
            mBinding.vm?.postZXData(zxSp, statusNum, selectStartDate, selectEndDate)
        }
        //日历选择
        macvView.setOnDataChoose {
            if (it.size > 0) {
                selectStartDate = Date2Util.getCalendarDate(it[0])
                selectEndDate = Date2Util.getCalendarDate(it[it.size - 1])
                if (currentSelectItem == 1) {
                    mBinding.vm?.postZXData(zxSp, statusNum, selectStartDate, selectEndDate)
                } else {
                    mBinding.vm?.postJBData(jbSp, selectStartDate, selectEndDate)
                }
            }
        }

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //选中之后再次点击即复选时触发
                if (tab.text == "我交办的") {
                    mBinding.vm?.postJBData(0, selectStartDate, selectEndDate)
                    currentSelectItem = 2
                    tvStatus.visibility = View.INVISIBLE
                    smartRefresh_zx.visibility = View.GONE
                    smartRefresh_jb.visibility = View.VISIBLE
                } else {
                    mBinding.vm?.postZXData(0, statusNum, selectStartDate, selectEndDate)
                    currentSelectItem = 1
                    tvStatus.visibility = View.VISIBLE
                    smartRefresh_zx.visibility = View.VISIBLE
                    smartRefresh_jb.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        //交办刷新
        smartRefresh_jb.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                jbSp = jb_adapter.data.size
                mBinding.vm?.postJBData(jbSp, selectStartDate, selectEndDate)

                smartRefresh_jb.finishLoadMore()
                smartRefresh_jb.finishRefresh()
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                jbSp = 0
                mBinding.vm?.postJBData(jbSp, selectStartDate, selectEndDate)

                smartRefresh_jb.finishLoadMore()
                smartRefresh_jb.finishRefresh()

            }

        })

        //执行刷新
        smartRefresh_zx.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout?) {
                zxSp = 0
                mBinding.vm?.postZXData(zxSp, statusNum, selectStartDate, selectEndDate)

                smartRefresh_zx.finishLoadMore()
                smartRefresh_zx.finishRefresh()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                zxSp = zx_adapter.data.size
                mBinding.vm?.postZXData(zxSp, statusNum, selectStartDate, selectEndDate)

                smartRefresh_zx.finishLoadMore()
                smartRefresh_zx.finishRefresh()
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
