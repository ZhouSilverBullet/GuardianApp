package com.sdxxtop.guardianapp.ui.assignevent

import android.app.Activity
import android.content.Intent
import android.view.View
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityAssignSolveBinding
import com.sdxxtop.guardianapp.ui.assignevent.assignmodel.AssignSolveModel
import com.sdxxtop.guardianapp.utils.UIUtils
import kotlinx.android.synthetic.main.activity_add_assign_event.*
import kotlinx.android.synthetic.main.activity_assign_solve.*
import kotlinx.android.synthetic.main.activity_assign_solve.cvisvView

class AssignSolveActivity : BaseKTActivity<ActivityAssignSolveBinding, AssignSolveModel>() {

    private var assignId = "" // 交办id
    private var execId = 0  // 执行id

    override fun vmClazz() = AssignSolveModel::class.java
    override fun layoutId() = R.layout.activity_assign_solve
    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.activity = this
    }

    override fun initObserve() {
    }


    override fun initView() {
        if (intent != null) {
            assignId = intent.getStringExtra("assignId")
            execId = intent.getIntExtra("execId", 1)
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvGreenBtn -> {
                val editValue = netContent.editValue
                if (editValue.isEmpty()) {
                    UIUtils.showToast("请填写问题描述！！！")
                    return
                }

                val imagePushPath = cvisvView.getImageOrVideoPushPath(1)
                val vedioPushPath = cvisvView.getImageOrVideoPushPath(2)

                mBinding.vm?.postSolveEvent(this, assignId, execId, editValue, imagePushPath, vedioPushPath)

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        
        if (resultCode == Activity.RESULT_OK) {
            cvisvView.callActivityResult(requestCode, resultCode, data)
        }
    }
}
