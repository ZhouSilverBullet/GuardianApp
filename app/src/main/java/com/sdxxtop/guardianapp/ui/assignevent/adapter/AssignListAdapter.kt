package com.sdxxtop.guardianapp.ui.assignevent.adapter

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.model.bean.AssignListBean
import com.sdxxtop.guardianapp.ui.assignevent.AssignEventDetailActivity
import java.lang.Exception
import java.text.SimpleDateFormat

/**
 * Date:2020-02-07
 * author:lwb
 * Desc:
 */
class AssignListAdapter(private val isZXDetail:Int) : BaseQuickAdapter<AssignListBean.ListBean, BaseViewHolder>(R.layout.item_assign_event) {

    override fun convert(helper: BaseViewHolder?, item: AssignListBean.ListBean?) {
        if (helper == null || item == null) return
        val tvStatusIcon = helper.getView<TextView>(R.id.tvStatusIcon)

        helper.setText(R.id.tvTitle, item.title)
        helper.setText(R.id.tvStatus, "任务状态：${getStatusTx(item.grade)}")
        helper.setText(R.id.tvAssignTime, "交办时间：${item.add_date.replace("-", "/")}")
        helper.setText(R.id.tvLastMakeTime, "最后操作时间：${getFormatDate(item.update_time)}")

        val conOverTimeLayout = helper.getView<ConstraintLayout>(R.id.conOverTimeLayout)
        if (item.due_day == 0) {
            conOverTimeLayout.visibility = View.GONE
        } else {
            conOverTimeLayout.visibility = View.VISIBLE
        }
        helper.setText(R.id.tvRemainTime, getOverTimeStr(item.due_day))


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
            intent.putExtra("overTime", getOverTimeStr(item.due_day))
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
        return if (day > 0) "任务剩余${day}天" else "任务超期${day}天"
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

}