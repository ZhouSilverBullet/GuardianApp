package com.sdxxtop.guardianapp.ui.assignevent.adapter

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.model.bean.AssignListBean
import com.sdxxtop.guardianapp.ui.assignevent.AssignEventDetailActivity
import com.sdxxtop.guardianapp.ui.assignevent.AssignSolveActivity
import java.lang.Exception
import java.text.SimpleDateFormat
import kotlin.math.abs

/**
 * Date:2020-02-07
 * author:lwb
 * Desc:
 */
class AssignListAdapter(private val isZXDetail: Int) : BaseQuickAdapter<AssignListBean.ListBean, BaseViewHolder>(R.layout.item_assign_event) {

    private var isWorkAdapter: Boolean = false

    override fun convert(helper: BaseViewHolder?, item: AssignListBean.ListBean?) {
        if (helper == null || item == null) return
        val tvStatusIcon = helper.getView<TextView>(R.id.tvStatusIcon)
        val tvWorkOverTime = helper.getView<TextView>(R.id.tvWorkOverTime)
        val tvRemainTime = helper.getView<TextView>(R.id.tvRemainTime)
        val tvSolveBtn = helper.getView<TextView>(R.id.tvSolveBtn)
        val conOverTimeLayout = helper.getView<ConstraintLayout>(R.id.conOverTimeLayout)

        helper.setText(R.id.tvWorkOverTime, getOverTimeStr(item.due_day))
        helper.setText(R.id.tvRemainTime, getOverTimeStr(item.due_day))
        if (isWorkAdapter) {
            tvRemainTime.visibility = View.GONE

            if (item.due_day == 0) {
                tvWorkOverTime.visibility = View.GONE
            } else {
                tvWorkOverTime.visibility = View.VISIBLE
            }

            if (item.status == 3) {
                tvSolveBtn.visibility = View.VISIBLE
            } else {
                tvSolveBtn.visibility = View.GONE
            }
        } else {
            tvRemainTime.visibility = View.VISIBLE
            tvWorkOverTime.visibility = View.GONE
            tvSolveBtn.visibility = View.GONE

            if (item.due_day == 0) {
                conOverTimeLayout.visibility = View.GONE
            } else {
                conOverTimeLayout.visibility = View.VISIBLE
            }
        }


        helper.setText(R.id.tvTitle, item.title)
        helper.setText(R.id.tvStatus, if (isZXDetail == 2) "" else "任务状态：${getStatusTx(item.status)}")
        helper.setText(R.id.tvAssignTime, "交办时间：${item.add_date.replace("-", "/")}")
        helper.setText(R.id.tvLastMakeTime, "最后操作时间：${getFormatDate(item.update_time)}")

        when (item.grade) {
            1 -> {
                tvStatusIcon.text = "低"
                tvStatusIcon.setBackgroundResource(R.drawable.shape_assign_item_status_low_bg)
            }
            2 -> {
                tvStatusIcon.text = "中"
                tvStatusIcon.setBackgroundResource(R.drawable.shape_assign_item_status_middle_bg)
            }
            3 -> {
                tvStatusIcon.text = "高"
                tvStatusIcon.setBackgroundResource(R.drawable.shape_assign_item_status_high_bg)
            }
        }

        helper.itemView.setOnClickListener {
            var intent = Intent(mContext, AssignEventDetailActivity::class.java)
            intent.putExtra("assignId", item.assign_id.toString())
            intent.putExtra("isZXDetail", isZXDetail)
            mContext.startActivity(intent)
        }

        tvSolveBtn.setOnClickListener {
            val intent = Intent(mContext, AssignSolveActivity::class.java)
            intent.putExtra("assignId", item.assign_id.toString())
            intent.putExtra("execId", item.exec_id)
            mContext.startActivity(intent)
        }

    }

    //status状态(1:待部门确认 2:待个人确认 3:待解决 4:已完成 5:已退回)
    private fun getStatusTx(status: Int): String {
        var str = ""
        when (status) {
            1 -> str = "待部门确认"
            2 -> str = "待个人确认"
            3 -> str = "待解决"
            4 -> str = "已完成"
            5 -> str = "已退回"
        }
        return str
    }

    private fun getOverTimeStr(day: Int): String {
        return if (day > 0) "任务剩余${abs(day)}天" else "任务超期${abs(day)}天"
    }

    fun getFormatDate(time: String): String {
        var str = ""
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val format1 = SimpleDateFormat("yyyy/MM/dd")
            val parse = format.parse(time)
            str = format1.format(parse)
        } catch (e: Exception) {

        }
        return str
    }

    fun replaceData(data: MutableCollection<out AssignListBean.ListBean>, isWork: Boolean) {
        this.isWorkAdapter = isWork
        super.replaceData(data)
    }

}