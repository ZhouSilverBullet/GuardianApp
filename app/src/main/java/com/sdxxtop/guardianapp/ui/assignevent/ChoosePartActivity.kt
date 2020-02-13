package com.sdxxtop.guardianapp.ui.assignevent

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityChosePartBinding
import com.sdxxtop.guardianapp.ui.assignevent.adapter.ChoosePartAdapter
import com.sdxxtop.guardianapp.ui.assignevent.assignmodel.ChoosePartModel
import kotlinx.android.synthetic.main.activity_chose_part.*

class ChoosePartActivity : BaseKTActivity<ActivityChosePartBinding, ChoosePartModel>() {

    private val adapter: ChoosePartAdapter by lazy {
        var result = ChoosePartAdapter()
        result.setDrawable(resources.getDrawable(R.drawable.choose_pm_icon_normal), resources.getDrawable(R.drawable.choose_pm_icon_select))
        result.setText(tvSelectNum)
        result
    }

    override fun layoutId() = R.layout.activity_chose_part
    override fun vmClazz() = ChoosePartModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.activity = this
    }

    override fun loadSirBindView(): View {
        return mBinding.smartRefresh
    }

    override fun preLoad() {
        mBinding.vm?.postData()
    }

    override fun initObserve() {
        mBinding.vm?.partAndUserData?.observe(this, Observer {
            if (it.part != null) {
                adapter.replaceData(it.part)
                showLoadSir(false)
            } else {
                showLoadSir(true)
            }
        })
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun initEvent() {
        if (intent != null) {
            mBinding.vm?.postData()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvSelectBtn -> {
                val list = adapter.getSelectItem()
                var intent = Intent()
                intent.putExtra("result", list)
                setResult(3, intent)
                finish()
            }
        }
    }
}
