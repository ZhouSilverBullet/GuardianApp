package com.sdxxtop.guardianapp.ui.activity.problemgj

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityProblemGjAddBinding
import com.sdxxtop.guardianapp.ui.activity.AmapPoiActivity
import com.sdxxtop.guardianapp.ui.activity.problemgj.bean.Category
import com.sdxxtop.guardianapp.ui.activity.problemgj.bean.ReportPath
import com.sdxxtop.guardianapp.ui.activity.problemgj.model.ProblemGJAddModel
import com.sdxxtop.guardianapp.ui.activity.problemgj.weight.ThreeSelectView
import com.sdxxtop.guardianapp.ui.widget.SingleStyleView
import com.sdxxtop.guardianapp.utils.*
import kotlinx.android.synthetic.main.activity_problem_gj_add.*

class ProblemGJAddActivity : BaseKTActivity<ActivityProblemGjAddBinding, ProblemGJAddModel>() {
    override fun vmClazz() = ProblemGJAddModel::class.java
    override fun layoutId() = R.layout.activity_problem_gj_add
    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.activity = this
    }

    private var partSelect: SingleStyleView? = null
    private var streamEventPermission: ReportPath? = null
    private var select: ThreeSelectView? = null  // 三级联动
    private var selectPartId = 0   // 选中的部门ID
    private var selectCategoryId = 0   // 选中的分类ID
    private var longitude = ""  // 经纬度
    private var dialog: TimeSelectBottomDialog? = null


    override fun initObserve() {
        mBinding.vm?.reportSuccess?.observe(this, Observer {
            if (it) {
                startActivity(Intent(this@ProblemGJAddActivity, ProblemGJListActivity::class.java))
                finish()
            }
        })
        mBinding.vm?.indexBean?.observe(this, Observer {
            //分类
            if (it.category.isNotEmpty()) {
                categorySelect(it.category)
            }
            //部门信息
            if (it.part_info.isNotEmpty()) {
                var list = arrayListOf<SingleStyleView.ListDataBean>()
                it.part_info.forEach { it -> list.add(SingleStyleView.ListDataBean(it.part_id, it.part_name)) }
                partSelect = SingleStyleView(this@ProblemGJAddActivity, list)
                partSelect?.setOnItemSelectLintener { id, result ->
                    selectPartId = id
                    tatvReportPath.textRightText.text = result
                }
            }

            //限制信息
            //输入标题 字数限制
            StringUtil.setEditTextInhibitInputSpaChat(taevTitle.editText, (streamEventPermission?.title)
                    ?: 10)
            taevTitle.editText.hint = "事件类目关键词（限制${(streamEventPermission?.title)
                    ?: 10}个字）"
            //定位补充 字数限制
            netContentPosition.setMaxLength((streamEventPermission?.supplementNumber) ?: 100)
            //问题描述 字数限制
            netContent.setMaxLength((streamEventPermission?.reportDescribe) ?: 100)
//            //选择图片和视频的总数量
//            cvisvView.setMaxImgCount((streamEventPermission?.img) ?: 9)

            if (it.part != null) {
                selectPartId = it.part.part_id
            }
        })
    }

    /**
     * 处理三级联动
     */
    private fun categorySelect(category: List<Category>) {
        select = ThreeSelectView(this, category)
        select?.setOnPickerSelect(object : ThreeSelectView.OnPickerSelect {
            override fun pickerSelect(categoryId: Int, categoryName: String) {
                tatvEventType.textRightText.text = categoryName
                selectCategoryId = categoryId
            }
        })
    }

    override fun initView() {
        netContent.setEditHint("在此录入事件描述")
        netProblem.setEditHint("在此录入问题清单")
        netShixiao.setEditHint("在此录入时效清单")
        netCuoshi.setEditHint("在此录入措施清单")
        netZeren.setEditHint("在此录入责任清单")
        cbIntoVoice.setOnClickListener(View.OnClickListener {
            tatvEndTime.visibility = if (cbIntoVoice.isChecked) View.VISIBLE else View.GONE
            tatvEndTime.textRightText.text = ""
            tatvEndTime.textRightText.hint = "请选择整改时效"
        })
        /**
         * 复查时间dialog
         */
        dialog = TimeSelectBottomDialog(this, tatvEndTime.textRightText)

        /**
         * 定位
         */
        if (GpsUtils.isOPen(this)) {
            val oneLoaction = LocationUtilOne()
            oneLoaction.startLocate(this)
            oneLoaction.setLocationCallBack { _, _, _, aMapLocation ->
                val address = aMapLocation.address
                val poiName = aMapLocation.poiName
                if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(poiName)) {
                    tvPlaceDesc.text = address
                    tvPlaceTitle.text = poiName
                    //                        String value = longitude + "," + latitude;
                    longitude = aMapLocation.longitude.toString() + "," + aMapLocation.latitude
                    oneLoaction.stopLocation()
                }
            }
        }
    }

    override fun initData() {
        mBinding.vm?.postIndexData()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tatvEventType -> {  // 分类
                KeyboardktUtils.hideKeyboard(v)
                if (select == null) {
                    UIUtils.showToast("获取分类数据失败！！！")
                    mBinding.vm?.postIndexData()
                    return
                }
                select?.show()
            }
            R.id.btnPush -> {  // 提交
                reportProblem()
            }
            R.id.tatvEndTime -> {
                KeyboardktUtils.hideKeyboard(v)
                if (cbIntoVoice.isChecked) {
                    if (dialog != null) {
                        dialog?.show()
                    } else {
                        dialog = TimeSelectBottomDialog(this, tatvEndTime.textRightText)
                        dialog?.show()
                    }
                }
            }
            R.id.colHappen -> {  // 位置选择
                KeyboardktUtils.hideKeyboard(v)
                selectHappen()
            }
            R.id.tatvReportPath -> {  // 上报部门
                KeyboardktUtils.hideKeyboard(v)
                partSelect?.show()
            }
        }
    }

    /**
     * 上报问题攻坚
     */
    private fun reportProblem() {
        if (taevTitle.editValue.isEmpty()) {
            UIUtils.showToast("请填写标题")
            return
        }
        if (selectCategoryId == 0) {
            UIUtils.showToast("请选择分类")
            return
        }
        if (selectPartId == 0) {
            UIUtils.showToast("请选择部门")
            return
        }
        //问题清单
        if (netProblem.editValue.isEmpty()) {
            UIUtils.showToast("请描述问题清单")
            return
        }
        //措施清单
        if (netCuoshi.editValue.isEmpty()) {
            UIUtils.showToast("请描述措施清单")
            return
        }
        //时效清单
        if (netShixiao.editValue.isEmpty()) {
            UIUtils.showToast("请描述时效清单")
            return
        }
        //责任清单
        if (netZeren.editValue.isEmpty()) {
            UIUtils.showToast("请描述责任清单")
            return
        }

        mBinding.vm?.reportProblem(
                this,
                netProblem.editValue,
                netCuoshi.editValue,
                netShixiao.editValue,
                netZeren.editValue,
                selectCategoryId,
                1,
                tatvEndTime.rightTVString,
                if (cbIntoVoice.isChecked) 1 else 2,
                netContentPosition.editValue,
                taevTitle.editValue,
                selectPartId,
                1,
                tvPlaceTitle.text.toString(),
                longitude,
                netContent.editValue,
                cvisvView.getImageOrVideoPushPath(1),
                arrayListOf()
        )
    }

    /**
     * 选择位置信息
     */
    private fun selectHappen() { //由于启动map界面比较慢,所以弄个进度条,在回到页面的情况下 hideLoadingDialog
        mBinding.vm?.showLoadingDialog(true)
        val intent = Intent(this, AmapPoiActivity::class.java)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.vm?.showLoadingDialog(false)
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (data == null) {
            return
        }
        if (resultCode == Activity.RESULT_OK) {
            cvisvView.callActivityResult(requestCode, resultCode, data)
        }
    }

}
