package com.sdxxtop.guardianapp.ui.activity.problemgj.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.ui.activity.problemgj.ProblemGJDetailActivity
import com.sdxxtop.guardianapp.ui.activity.problemgj.bean.Event

/**
 * Date:2020/2/28
 * author:lwb
 * Desc:
 */
class ProblemGJAdapter : BaseQuickAdapter<Event, BaseViewHolder>(R.layout.item_problem_gj_layout) {

    override fun convert(helper: BaseViewHolder?, item: Event?) {
        if (helper == null || item == null) {
            return
        }
        helper.setText(R.id.tv_title, item.title)
        helper.setText(R.id.tv_event_category, item.category_name)
        helper.setText(R.id.tv_time, "上报时间：${item.add_time}")
        helper.setText(R.id.tv_status, getStatusStr(item.status))

        val intent = Intent(mContext, ProblemGJDetailActivity::class.java)
        intent.putExtra("eventId",item.event_id.toString())
        helper.itemView.setOnClickListener { mContext.startActivity(intent) }
    }

    /**
     *  1:带派发
     *  2:待解决
     *  3:待验收
     *  4:已完成
     */
    private fun getStatusStr(status: Int): String {
        return when (status) {
            1 -> "待派发"
            2 -> "待解决"
            3 -> "待验收"
            4 -> "已完成"
            else -> "待派发"
        }
    }
}