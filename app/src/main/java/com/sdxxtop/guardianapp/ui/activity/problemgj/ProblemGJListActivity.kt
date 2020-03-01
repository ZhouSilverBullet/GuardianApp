package com.sdxxtop.guardianapp.ui.activity.problemgj

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityProblemGjListBinding
import com.sdxxtop.guardianapp.ui.activity.problemgj.adapter.ProblemGJAdapter
import com.sdxxtop.guardianapp.ui.activity.problemgj.model.ProblemGJListModel
import kotlinx.android.synthetic.main.activity_problem_gj_list.*

class ProblemGJListActivity : BaseKTActivity<ActivityProblemGjListBinding, ProblemGJListModel>() {
    override fun vmClazz() = ProblemGJListModel::class.java
    override fun layoutId() = R.layout.activity_problem_gj_list
    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    private val adapter = ProblemGJAdapter()
    private var spSize = 0

    override fun initObserve() {
        mBinding.vm?.adapterData?.observe(this, Observer {
            if (spSize == 0) {
                adapter.replaceData(it.event)
            } else {
                adapter.addData(it.event)
            }
        })
    }


    override fun initData() {
        mBinding.vm?.postList(spSize)
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                spSize = adapter.data.size
                mBinding.vm?.postList(spSize)
                smartRefresh.finishLoadMore()
                smartRefresh.finishRefresh()
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                spSize = 0
                mBinding.vm?.postList(spSize)
                smartRefresh.finishLoadMore()
                smartRefresh.finishRefresh()
            }
        })
    }

}
