package com.sdxxtop.guardianapp.ui.assignevent

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityAssignEventDetailBinding
import com.sdxxtop.guardianapp.model.bean.AssignDetailBean
import com.sdxxtop.guardianapp.ui.assignevent.adapter.AssignDetailAdapter
import com.sdxxtop.guardianapp.ui.assignevent.assignmodel.AssignEventDetailModel
import kotlinx.android.synthetic.main.activity_assign_event_detail.*
import kotlinx.android.synthetic.main.activity_assign_event_detail.recyclerView
import kotlinx.android.synthetic.main.assign_top_layout.*

class AssignEventDetailActivity : BaseKTActivity<ActivityAssignEventDetailBinding, AssignEventDetailModel>() {

    private val adapter = AssignDetailAdapter()
    private var assignId = "0"

    override fun vmClazz() = AssignEventDetailModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun loadSirBindView(): View {
        return mBinding.smartRefresh
    }

    override fun initObserve() {
        mBinding.vm?.assignDetailData?.observe(this, Observer {
            if (it != null) {
                bindData(it)
            }
        })
    }

    override fun layoutId() = R.layout.activity_assign_event_detail

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val headerView = LayoutInflater.from(this).inflate(R.layout.assign_top_layout, null)
        adapter.setHeaderView(headerView)
    }

    override fun initData() {
        if (intent != null) {
            assignId = intent.getStringExtra("assignId")
        }

        mBinding.vm?.loadAssignDetail(assignId)
    }


    /**
     * 绑定数据
     */
    @SuppressLint("SetTextI18n")
    private fun bindData(_data: AssignDetailBean) {

        showLoadSir(false)

        val data = _data.list
        c2TxTitle.tvRight.text = data.title
        c2TxST.tvRight.text = data.add_date
        c2TxET.tvRight.text = data.due_time
        c2TxLevel.tvRight.text = data.levelStr
        c2TxContext.tvLeft.text = "事件简要描述：${data.content}"
        c2TxPart.tvRight.text = data.assign_part_name
        c2TxPerson.tvRight.text = data.user_name

        if (data.child != null && data.child.size > 0) {
            adapter.replaceData(data.child)
        }

        //status状态(1:待部门确认 2:待个人确认 3:待解决 4:已完成 5:已退回)
        catpView.setStatus(_data.dispaly_status)
    }

}
