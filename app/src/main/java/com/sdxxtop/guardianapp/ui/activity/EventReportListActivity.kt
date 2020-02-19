package com.sdxxtop.guardianapp.ui.activity

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.Calendar.Scheme
import com.haibin.calendarview.CalendarView
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

class EventReportListActivity : BaseKTActivity<ActivityEventReportListBinding, EventReportListModel>(), CalendarView.OnCalendarSelectListener, CalendarView.OnMonthChangeListener {

    //1 => '待派发',
    //2 => '待处理',
    //3 => '待验收',
    //4 => '已完成',
    //5 => '已驳回',
    private val stausSelect: SingleStyleView by lazy {
        val list = arrayListOf<SingleStyleView.ListDataBean>()
        list.add(SingleStyleView.ListDataBean(1, "待派发"))
        list.add(SingleStyleView.ListDataBean(2, "待处理"))
        list.add(SingleStyleView.ListDataBean(3, "待验收"))
        list.add(SingleStyleView.ListDataBean(4, "已完成"))
        list.add(SingleStyleView.ListDataBean(5, "已驳回"))
        SingleStyleView(this@EventReportListActivity, list)
    }

    private var categorySelect: SingleStyleView? = null
    private val adapter = EventReportListAdapter()
    private var categoryId: Int = 0
    private var selectDate = ""
    private var statusId = 0

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
            it.forEach { item -> list.add(SingleStyleView.ListDataBean(item.category_id, item.category_name)) }
            categorySelect = SingleStyleView(this@EventReportListActivity, list)
            categorySelect?.setOnItemSelectLintener { id, result ->
                categoryId = id
                tvCategory.text = result
                mBinding.vm?.posData(statusId, categoryId, selectDate)
            }
        })

        /**
         * 更新每个月的事件信息
         */
        mBinding.vm?.monthData?.observe(this, Observer {
            Log.e("item ----- ", "消息到达")
            if (it != null) {
                Log.e("item ----- ", "${it != null}")
                calendarView.clearSchemeDate()
                val map: MutableMap<String, Calendar> = HashMap()
                for (item in it) {
                    Log.e("item ----- ", item.add_date)
                    val y = Date2Util.getDateInt(item.add_date, 0)
                    val m = Date2Util.getDateInt(item.add_date, 1)
                    val d = Date2Util.getDateInt(item.add_date, 2)
                    map[getSchemeCalendar(y, m, d, -0x123a93, "事").toString()] = getSchemeCalendar(y, m, d, -0x123a93, "事")
                }
                calendarView.setSchemeDate(map)
            }
        })
        /**
         * 列表数据
         */
        mBinding.vm?.adapterList?.observe(this, Observer {
            adapter.replaceData(it)
        })

    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        tvDate.text = "${calendarView.curYear}年${calendarView.curMonth}月${calendarView.curDay}日"
        calendarView.setOnCalendarSelectListener(this)
        calendarView.setOnMonthChangeListener(this)
        stausSelect.setOnItemSelectLintener { id, result ->
            statusId = id
            tvStatus.text = result
            mBinding.vm?.posData(statusId, categoryId, selectDate)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun initData() {
        mBinding.vm?.postCategoryData()
        mBinding.vm?.postMonthEvnet("${calendarView.curYear}-${Date2Util.getZeroTime(calendarView.curMonth)}-01")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvDate -> {
                finish()
            }
            R.id.flCurrentLayout -> {
                //选中当天
                if (calendarView != null) {
                    calendarView.scrollToCurrent(true)
                    calendarView.closeYearSelectLayout()
                }
            }
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

    /**
     * 选中日期回掉
     */
    @SuppressLint("SetTextI18n")
    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        tvDate.text = "${calendar?.year}年${calendar?.month}月${calendar?.day}日"
        selectDate = "${calendar?.year}-${calendar?.month}-${calendar?.day}"
        mBinding.vm?.posData(statusId, categoryId, selectDate)
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 切换月分回掉
     */
    override fun onMonthChange(year: Int, month: Int) {
        mBinding.vm?.postMonthEvnet("${year}-${Date2Util.getZeroTime(month)}-01")
    }

    private fun getSchemeCalendar(year: Int, month: Int, day: Int, color: Int, text: String): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        calendar.addScheme(Scheme())
        calendar.addScheme(-0xff7800, "假")
        calendar.addScheme(-0xff7800, "节")
        return calendar
    }
}
