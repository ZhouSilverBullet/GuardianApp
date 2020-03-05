package com.sdxxtop.guardianapp.ui.activity

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.Calendar.Scheme
import com.haibin.calendarview.CalendarView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityEventReportListBinding
import com.sdxxtop.guardianapp.model.EventReportListModel
import com.sdxxtop.guardianapp.ui.adapter.EventReportListAdapter
import com.sdxxtop.guardianapp.ui.widget.SingleStyleView
import com.sdxxtop.guardianapp.utils.Date2Util
import kotlinx.android.synthetic.main.activity_add_assign_event.*
import kotlinx.android.synthetic.main.activity_event_report_list.*
import java.util.*

class EventReportListActivity : BaseKTActivity<ActivityEventReportListBinding, EventReportListModel>() {

    //1 => '待派发',
    //2 => '待处理',
    //3 => '待验收',
    //4 => '已完成',
    //5 => '已驳回',
    private val stausSelect: SingleStyleView by lazy {
        val list = arrayListOf<SingleStyleView.ListDataBean>()
        list.add(SingleStyleView.ListDataBean(0, "全部"))
        list.add(SingleStyleView.ListDataBean(1, "待派发"))
        list.add(SingleStyleView.ListDataBean(2, "待处理"))
        list.add(SingleStyleView.ListDataBean(3, "待验收"))
        list.add(SingleStyleView.ListDataBean(4, "已完成"))
        SingleStyleView(this@EventReportListActivity, list)
    }

    private var categorySelect: SingleStyleView? = null
    private val adapter = EventReportListAdapter()
    private var categoryId: Int = 0
    private var statusId = 0
    private var selectStartDate = Date2Util.getToday()
    private var selectEndDate = Date2Util.getToday()
    private var spSize = 0    // 刷新加载标识

    override fun layoutId() = R.layout.activity_event_report_list
    override fun vmClazz() = EventReportListModel::class.java
    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.activity = this
    }

    override fun initObserve() {
        /**
         * 分类信息
         */
        mBinding.vm?.category?.observe(this, Observer {
            var list = arrayListOf<SingleStyleView.ListDataBean>()
            list.add(SingleStyleView.ListDataBean(0, "全部"))
            it.forEach { item -> list.add(SingleStyleView.ListDataBean(item.category_id, item.category_name)) }
            categorySelect = SingleStyleView(this@EventReportListActivity, list)
            categorySelect?.setOnItemSelectLintener { id, result ->
                categoryId = id
                tvCategory.text = result
                mBinding.vm?.posData(statusId, categoryId, 0, selectStartDate, selectEndDate)
            }
        })

        /**
         * 列表数据
         */
        mBinding.vm?.adapterList?.observe(this, Observer {
            if (spSize == 0) {
                adapter.replaceData(it)
            } else {
                adapter.addData(it)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        stausSelect.setOnItemSelectLintener { id, result ->
            statusId = id
            tvStatus.text = result
            mBinding.vm?.posData(statusId, categoryId, 0, selectStartDate, selectEndDate)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        macvView.setOnDataChoose {
            if (it.size > 0) {
                selectStartDate = Date2Util.getCalendarDate(it[0])
                selectEndDate = Date2Util.getCalendarDate(it[it.size - 1])
                mBinding.vm?.posData(statusId, categoryId, 0, selectStartDate, selectEndDate)
            }
        }

        smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                spSize = adapter.data.size
                mBinding.vm?.posData(statusId, categoryId, spSize, selectStartDate, selectEndDate)
                refreshLayout?.finishLoadMore()
                refreshLayout?.finishRefresh()
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                spSize = 0
                mBinding.vm?.posData(statusId, categoryId, 0, selectStartDate, selectEndDate)
                refreshLayout?.finishLoadMore()
                refreshLayout?.finishRefresh()
            }
        })
    }

    override fun initData() {
        mBinding.vm?.postCategoryData()
    }

    override fun onResume() {
        super.onResume()
        mBinding.vm?.posData(statusId, categoryId, 0, selectStartDate, selectEndDate)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvCategory -> {
                //分类选择
                categorySelect?.show()
            }
            R.id.tvStatus -> {
                //状态选择
                stausSelect.show()
            }
        }
    }
}
