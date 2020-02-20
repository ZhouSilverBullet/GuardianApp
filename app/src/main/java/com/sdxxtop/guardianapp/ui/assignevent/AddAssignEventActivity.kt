package com.sdxxtop.guardianapp.ui.assignevent

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityAddAssignEventBinding
import com.sdxxtop.guardianapp.model.bean.AssignDetailBean
import com.sdxxtop.guardianapp.model.bean.PartAndUserBean
import com.sdxxtop.guardianapp.ui.assignevent.assignmodel.AddAssignEventModel
import com.sdxxtop.guardianapp.ui.widget.SingleStyleView
import com.sdxxtop.guardianapp.utils.KeyboardktUtils
import com.sdxxtop.guardianapp.utils.TimeSelectBottomDialog
import com.sdxxtop.guardianapp.utils.UIUtils
import kotlinx.android.synthetic.main.activity_add_assign_event.*

class AddAssignEventActivity : BaseKTActivity<ActivityAddAssignEventBinding, AddAssignEventModel>() {

    private var categorySelect: SingleStyleView? = null
    private var categoryId = 0     // 分类id
    private var categoryName = ""  // 分类名字
    private var selectType = 0     //1.个人 2.部门
    private var selectPartId = ""  // 选中的部门id
    private var selectUserId = ""  // 选中的用户id
    private var eventLevel = 0     // 事件等级
    private var assignId = 0       // 重新派发的交办ID

    private val calendarDialog: TimeSelectBottomDialog by lazy {
        TimeSelectBottomDialog(this, tatv_end_time.textRightText)
    }
    private val levelSelect: SingleStyleView by lazy {
        val list = arrayListOf<SingleStyleView.ListDataBean>()
        //待确认，待解决，已拒绝，已完成
        list.add(SingleStyleView.ListDataBean(1, "低"))
        list.add(SingleStyleView.ListDataBean(2, "中"))
        list.add(SingleStyleView.ListDataBean(3, "高"))
        SingleStyleView(this@AddAssignEventActivity, list)
    }

    private val bottomDialog: BottomSheetDialog by lazy {
        var bottomDialog = BottomSheetDialog(this)
        bottomDialog.setContentView(R.layout.dialog_picture_selector_bottom_layout)
        //给布局设置透明背景色
        var photo = bottomDialog.delegate.findViewById<TextView>(R.id.tv_photo)
        photo?.text = "部门"
        photo?.setOnClickListener(this)
        var video = bottomDialog.delegate.findViewById<TextView>(R.id.tv_video)
        video?.text = "人员"
        video?.setOnClickListener(this)
        bottomDialog
    }

    override fun vmClazz() = AddAssignEventModel::class.java
    override fun layoutId() = R.layout.activity_add_assign_event
    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.activity = this
    }

    override fun initObserve() {
        /**
         * 分类信息
         */
        mBinding.vm?.category?.observe(this, Observer {
            var list = arrayListOf<SingleStyleView.ListDataBean>()
            it.forEach { item -> list.add(SingleStyleView.ListDataBean(item.category_id, item.category_name)) }
            categorySelect = SingleStyleView(this@AddAssignEventActivity, list)
            categorySelect?.setOnItemSelectLintener { id, result ->
                categoryName = result
                categoryId = id
                tatv_event_type.textRightText.text = result
            }
        })

        /**
         * 上报成功
         */
        mBinding.vm?.assignId?.observe(this, Observer { finish() })
    }

    override fun initView() {
        net_content.setEditHint("")
        cbIntoVoice.setOnClickListener(View.OnClickListener {
            //            cvisvView.visibility = if (cbIntoVoice.isChecked) View.VISIBLE else View.GONE
        })

        if (intent != null) {
            val rePlaceDataSer = intent.getSerializableExtra("reReplaceData") ?: null
            if (rePlaceDataSer != null) {
                val rePlaceData = rePlaceDataSer as AssignDetailBean.ListBean
                taevTitle.editText.setText(rePlaceData.title)
                categoryName = rePlaceData.cat_name
                if (rePlaceData.cat_name.isNotEmpty()) categoryId = rePlaceData.cat_id
                tatv_event_type.textRightText.text = categoryName
                eventLevel = rePlaceData.grade
                tatv_event_level.textRightText.text = getLevelStr(rePlaceData.grade)
                net_content.editText.setText(rePlaceData.content)
//                cbIntoVoice.isChecked = rePlaceData.assign_id == 1
                tatv_end_time.textRightText.text = rePlaceData.due_time
                assignId = rePlaceData.assign_id
            }
        }
    }

    override fun initData() {
        mBinding.vm?.postLevelData()
    }

    override fun initEvent() {
        levelSelect.setOnItemSelectLintener { id, result ->
            eventLevel = id
            tatv_event_level.textRightText.text = result
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tatv_event_execute -> {
                KeyboardktUtils.hideKeyboard(v)
                //选择执行人
                bottomDialog.show()
            }
            R.id.tatv_event_type -> {
                KeyboardktUtils.hideKeyboard(v)
                //分类
                if (categorySelect != null) {
                    categorySelect?.show()
                } else {
                    UIUtils.showToast("正在获取分类信息...")
                    mBinding.vm?.postLevelData()
                }
            }
            R.id.tatv_event_level -> {
                KeyboardktUtils.hideKeyboard(v)
                //等级
                levelSelect.show()
            }

            R.id.tatv_end_time -> {
                KeyboardktUtils.hideKeyboard(v)
                //结束时间
                calendarDialog.show()
            }

            R.id.tvAssign -> {
                //上报
                reportEvent()

            }

            R.id.tv_video -> {
                bottomDialog.dismiss()
                //选择人员
                startActivityForResult(Intent(this@AddAssignEventActivity, ChooseUserActivity::class.java), 101)
            }
            R.id.tv_photo -> {
                bottomDialog.dismiss()
                //选择部门
                startActivityForResult(Intent(this@AddAssignEventActivity, ChoosePartActivity::class.java), 100)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (data == null) {
            return
        }
        // 部门
        if (requestCode == 100) {
            var partIdSb = StringBuffer()
            var partNameSb = StringBuffer()

            var result = data.getSerializableExtra("result") as ArrayList<PartAndUserBean.AssignPartBean>
            for ((index, item) in result.withIndex()) {
                if (index != 0) {
                    partIdSb.append(",")
                    partNameSb.append(",")
                }
                partIdSb.append(item.part_id)
                partNameSb.append(item.part_name)
            }
            selectPartId = partIdSb.toString()
            tatv_event_execute.textRightText.text = partNameSb.toString()
            selectType = 2
        }
        // 人员
        if (requestCode == 101) {
            var userIdSb = StringBuffer()
            var userNameSb = StringBuffer()

            var result = data.getSerializableExtra("result") as ArrayList<PartAndUserBean.AssignUserBean>
            for ((index, item) in result.withIndex()) {
                if (index != 0) {
                    userIdSb.append(",")
                    userNameSb.append(",")
                }
                userIdSb.append(item.userid)
                userNameSb.append(item.name)
            }
            selectUserId = userIdSb.toString()
            tatv_event_execute.textRightText.text = userNameSb.toString()
            selectType = 1
        }

        if (resultCode == Activity.RESULT_OK) {
            cvisvView.callActivityResult(requestCode, resultCode, data)
        }
    }


    /**
     * 上报交办事件
     */
    private fun reportEvent() {
        val contx = taevTitle.editText.text.trim().toString()
        if (contx.isEmpty()) {
            UIUtils.showToast("请填写事件标题")
            return
        }
        if (selectPartId.isEmpty() && selectUserId.isEmpty()) {
            UIUtils.showToast("请选择执行信息")
            return
        }
        if (categoryId == 0 && tatv_event_type.rightTVString.isEmpty()) {
            UIUtils.showToast("请选择事件类型")
            return
        }
        if (eventLevel == 0) {
            UIUtils.showToast("请选择事件等级")
            return
        }
        val contentTx = net_content.editValue
        if (contentTx.isEmpty()) {
            UIUtils.showToast("请填写事件描述")
            return
        }
        if (tatv_end_time.rightTVString.isEmpty()) {
            UIUtils.showToast("请选择截止时间")
            return
        }
        val imagePushPath = cvisvView.getImageOrVideoPushPath(1)
        val vedioPushPath = cvisvView.getImageOrVideoPushPath(2)
        mBinding.vm?.pushReport(
                assignId,
                this,
                contx,
                selectType,
                eventLevel,
                if (cbIntoVoice.isChecked) 1 else 2,
                categoryId,
                if (selectType == 1) selectUserId else selectPartId,
                tatv_end_time.rightTVString,
                contentTx,
                imagePushPath,
                vedioPushPath
        )
    }


    /**
     * 获取 等级
     */
    fun getLevelStr(level: Int): String {
        var result = ""
        when (level) {
            1 -> {
                result = "低"
            }
            2 -> {
                result = "中"
            }
            3 -> {
                result = "高"
            }
        }
        return result
    }
}
