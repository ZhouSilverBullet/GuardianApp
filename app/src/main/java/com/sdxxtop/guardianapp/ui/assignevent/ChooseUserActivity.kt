package com.sdxxtop.guardianapp.ui.assignevent

import android.annotation.SuppressLint
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityChooseUserBinding
import com.sdxxtop.guardianapp.ui.assignevent.adapter.ChooseUserAdapter
import com.sdxxtop.guardianapp.ui.assignevent.assignmodel.ChooseUserModel
import com.sdxxtop.guardianapp.utils.MyTextWatcher
import kotlinx.android.synthetic.main.activity_chose_part.*

class ChooseUserActivity : BaseKTActivity<ActivityChooseUserBinding, ChooseUserModel>() {

    private val adapter: ChooseUserAdapter by lazy {
        val result = ChooseUserAdapter()
        result.setDrawable(resources.getDrawable(R.drawable.choose_pm_icon_normal), resources.getDrawable(R.drawable.choose_pm_icon_select))
        result.setText(tvSelectNum)
        result
    }

    override fun layoutId() = R.layout.activity_choose_user
    override fun vmClazz() = ChooseUserModel::class.java
//    override fun loadSirBindView(): View {
//        return mBinding.smartRefresh
//    }

    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.activity = this
    }

    override fun initObserve() {
        mBinding.vm?.partAndUserData?.observe(this, Observer {
            if (it.user != null) {
                adapter.replaceData(it.user)
                showLoadSir(false)
            } else {
                showLoadSir(true)
            }
        })
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        editTextView.addTextChangedListener(object : MyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                mBinding.vm?.postData(s?.trim().toString())
            }
        })
    }

    override fun preLoad() {
        mBinding.vm?.postData("")
    }

    override fun initData() {
        mBinding.vm?.postData("")
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
