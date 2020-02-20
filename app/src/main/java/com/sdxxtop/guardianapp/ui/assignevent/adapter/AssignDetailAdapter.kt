package com.sdxxtop.guardianapp.ui.assignevent.adapter

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.model.bean.AssignDetailBean
import com.sdxxtop.guardianapp.model.bean.MediaBean
import com.sdxxtop.guardianapp.ui.activity.custom_event.Custom2TextView
import com.sdxxtop.guardianapp.ui.adapter.PatrolDetailImgAdapter
import java.util.*

/**
 * Date:2020-02-11
 * author:lwb
 * Desc:
 */
class AssignDetailAdapter : BaseQuickAdapter<AssignDetailBean.ListBean.ChildBean, BaseViewHolder>(R.layout.item_assign_detail_desc) {
    //status状态(1:待部门确认 2:待个人确认 3:待解决 4:已完成 5:已退回)
    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder?, item: AssignDetailBean.ListBean.ChildBean?) {
        if (helper == null || item == null) return

        val c2TxSolveName = helper.getView<Custom2TextView>(R.id.c2TxSolveName)
        val c2TxSolvePart = helper.getView<Custom2TextView>(R.id.c2TxSolvePart)
        val c2TxSolveTime = helper.getView<Custom2TextView>(R.id.c2TxSolveTime)
        val c2TxSendBack = helper.getView<Custom2TextView>(R.id.c2TxSendBack)
        val c2TxSendBackReason = helper.getView<Custom2TextView>(R.id.c2TxSendBackReason)
        val c2TxOverTime = helper.getView<Custom2TextView>(R.id.c2TxOverTime)
        val c2TxEventCont = helper.getView<Custom2TextView>(R.id.c2TxEventCont)
        val eventNum = helper.getView<TextView>(R.id.eventNum)
        val tvUpDown = helper.getView<TextView>(R.id.tvUpDown)
        val llHorLayout = helper.getView<LinearLayout>(R.id.llHorLayout)

        if (item.status == 4) {
            c2TxEventCont.visibility = View.VISIBLE
        } else {
            c2TxEventCont.visibility = View.GONE
        }


        c2TxSolveName.tvRight.text = item.duty_name
        c2TxSolvePart.tvRight.text = item.duty_part_name
        c2TxSolveTime.tvRight.text = item.finish_time
        c2TxSendBackReason.tvLeft.text = "退回原因：${item.reject_desc}"
        c2TxEventCont.tvLeft.text = "事件问题描述：${item.content}"
        eventNum.text = "事件附件（${item.img.size}）"
        c2TxOverTime.tvRight.text = "--"

        if (item.status != 5) {
            c2TxSendBack.tvLeft.text = "退回情况：无"
        } else {
            c2TxSendBack.tvLeft.text = "退回情况：已退回"
        }


        tvUpDown.setOnClickListener {
            if (llHorLayout?.visibility == View.VISIBLE) {
                llHorLayout.visibility = View.GONE
                tvUpDown.text = "展开"
            } else {
                llHorLayout?.visibility = View.VISIBLE
                tvUpDown.text = "收起"
            }
        }

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
        val recyclerView2 = helper.getView<RecyclerView>(R.id.recyclerView2)
        recyclerView?.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        recyclerView2?.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)

        val list: MutableList<MediaBean> = ArrayList()
        if (item.img.isNotEmpty()) {
            item.img.forEach {
                if (it.isNotEmpty()) {
                    list.add(MediaBean(it, 1))
                }
            }
        }
        recyclerView?.adapter = PatrolDetailImgAdapter(R.layout.gv_filter_image, list)
        var arrayListOf = arrayListOf<String>("http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/app/20200216170813233692.xlsx")
        var adapter = OtherFileAdapter()
        recyclerView2?.adapter = adapter
        adapter.replaceData(arrayListOf)

    }
}