package com.sdxxtop.guardianapp.ui.assignevent

import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityAssignEventDetailBinding
import com.sdxxtop.guardianapp.ui.assignevent.adapter.AssignDetailAdapter
import com.sdxxtop.guardianapp.ui.assignevent.assignmodel.AssignEventDetailModel
import kotlinx.android.synthetic.main.activity_assign_event_detail.*

class AssignEventDetailActivity : BaseKTActivity<ActivityAssignEventDetailBinding, AssignEventDetailModel>() {

    private val adapter = AssignDetailAdapter()
    override fun vmClazz() = AssignEventDetailModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun initObserve() {
    }

    override fun layoutId() = R.layout.activity_assign_event_detail

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


}
