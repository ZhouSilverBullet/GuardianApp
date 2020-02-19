package com.sdxxtop.guardianapp.ui.assignevent

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.app.Constants
import com.sdxxtop.guardianapp.databinding.ActivityAssignEventDetailBinding
import com.sdxxtop.guardianapp.model.bean.AssignDetailBean
import com.sdxxtop.guardianapp.model.bean.MediaBean
import com.sdxxtop.guardianapp.model.http.util.SpUtil
import com.sdxxtop.guardianapp.ui.adapter.PatrolDetailImgAdapter
import com.sdxxtop.guardianapp.ui.assignevent.adapter.AssignDetailAdapter
import com.sdxxtop.guardianapp.ui.assignevent.assignmodel.AssignEventDetailModel
import com.sdxxtop.guardianapp.ui.widget.CusAssignTopProgress
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView
import kotlinx.android.synthetic.main.activity_assign_event_detail.*
import kotlinx.android.synthetic.main.assign_top_layout.*
import java.util.*

class AssignEventDetailActivity : BaseKTActivity<ActivityAssignEventDetailBinding, AssignEventDetailModel>() {

    private val adapter = AssignDetailAdapter()
    private var mAdapter = PatrolDetailImgAdapter(R.layout.gv_filter_image, ArrayList())

    private val userId = SpUtil.getInt(Constants.USER_ID, 0)  // 当前用户id
    private var assignId = "0"  // 交办事件id
    private var isZXDetail = 1 // 执行详情：1 交办详情： 2
    private var status = 0     // 事件当前状态
    private var dispalyAgain = 0  // 重新提交按钮  1.显示 2.不显示
    private var execId = 0  // 执行id
    private var overTimeDesc = ""  // 事件状态(超期/剩余)
    private var rejectNetContent: NumberEditTextView? = null   // 退回的输入控件
    private var topHorRecycler: RecyclerView? = null  // 头布局的图片显示列表
    private var reReplaceData: AssignDetailBean.ListBean? = null   // 重新派发的数据

    private val sendBackDialog: BottomSheetDialog by lazy {
        var bottomDialog = BottomSheetDialog(this)
        bottomDialog.setContentView(R.layout.assign_detail_sendback_view)
        //给布局设置透明背景色
        rejectNetContent = bottomDialog.delegate.findViewById<NumberEditTextView>(R.id.netContent)
        rejectNetContent?.setEditHint("")
        var tcSendBack = bottomDialog.delegate.findViewById<TextView>(R.id.tcSendBack)
        tcSendBack?.setOnClickListener(this)
        bottomDialog
    }

    override fun vmClazz() = AssignEventDetailModel::class.java
    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.activity = this
    }

//    override fun loadSirBindView(): View {
//        return mBinding.smartRefresh
//    }

    override fun initObserve() {
        mBinding.vm?.assignDetailData?.observe(this, Observer {
            if (it != null) {
                bindData(it)
            }
        })

        mBinding.vm?.finishActivity?.observe(this, Observer { if (it) finish() })
    }

    override fun layoutId() = R.layout.activity_assign_event_detail

    override fun initView() {
        if (intent != null) {
            assignId = intent.getStringExtra("assignId")
            overTimeDesc = intent.getStringExtra("overTime")
            isZXDetail = intent.getIntExtra("isZXDetail", 1)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val headerView = LayoutInflater.from(this).inflate(R.layout.assign_top_layout, null, false)
        initTopHeadView(headerView)
        adapter.setHeaderView(headerView)
    }

    override fun onResume() {
        super.onResume()
        mBinding.vm?.loadAssignDetail(assignId)
    }

    /**
     * 绑定数据
     */
    @SuppressLint("SetTextI18n")
    private fun bindData(_data: AssignDetailBean) {
        val data = _data.list
        reReplaceData = data
        status = _data.dispaly_status
        dispalyAgain = _data.dispaly_again

        c2TxTitle.tvRight.text = data.title
        c2TxST.tvRight.text = data.add_date
        c2TxET.tvRight.text = data.due_time
        c2TxLevel.tvRight.text = data.levelStr
        c2TxContext.tvLeft.text = "事件简要描述：${data.content}"
        c2TxPart.tvRight.text = data.assign_part_name
        c2TxPerson.tvRight.text = data.user_name


        if (data.child != null && data.child.size > 0) {
            adapter.replaceData(data.child)

            //获取执行id
            for (item in data.child) {
                if (userId == item.duty_id) {
                    execId = item.exec_id
                    break
                }
            }
        }
        //status状态(1:待部门确认 2:待个人确认 3:待解决 4:已完成 5:已退回)
        if (isZXDetail == 1) {
            catpView.setStatus(status)
        }

        setBtnStatus(data)

        //设置 图片视频
        if (topHorRecycler != null) {
            bandImgAndVideo(data.img, "", topHorRecycler, mAdapter)
        }

        eventNum.text = "事件附件（${data.img.size}）"
        tvTop.text = "事件情况（${overTimeDesc}）"
    }

    /**
     * 控制按钮显示隐藏
     * 执行详情页：确认，申请退回、执行
     * 交办详情页：催办，重新派发、关闭任务
     */
    private fun setBtnStatus(data: AssignDetailBean.ListBean) {
        tvGreenBtn.visibility = View.VISIBLE
        tvWhiteBtn.visibility = View.VISIBLE

        //执行详情
        if (isZXDetail == 1) {
            when (status) {
                2 -> {   // 确认/申请退回
                    tvGreenBtn.text = "确认"
                    tvGreenBtn.setTextColor(Color.parseColor("#FFFFFF"))
                    tvGreenBtn.setBackgroundResource(R.drawable.shape_assign_green_bg)
                    tvWhiteBtn.text = "申请退回"
                    tvWhiteBtn.setTextColor(Color.parseColor("#32B16C"))
                    tvWhiteBtn.setBackgroundResource(R.drawable.shape_assign_white_bg)
                }
                3 -> {  //  解决
                    tvGreenBtn.text = "解决"
                    tvGreenBtn.setTextColor(Color.parseColor("#FFFFFF"))
                    tvGreenBtn.setBackgroundResource(R.drawable.shape_assign_green_bg)
                    tvWhiteBtn.visibility = View.GONE
                }
                1, 4, 5 -> {   // 隐藏
                    tvGreenBtn.visibility = View.GONE
                    tvWhiteBtn.visibility = View.GONE
                }
            }
        } else {
            // 交办详情
            if (dispalyAgain == 1) {
                tvGreenBtn.text = "重新派发"
                tvGreenBtn.setTextColor(Color.parseColor("#FFFFFF"))
                tvGreenBtn.setBackgroundResource(R.drawable.shape_assign_green_bg)
                tvWhiteBtn.text = "关闭任务"
                tvWhiteBtn.setTextColor(Color.parseColor("#FFFFFF"))
                tvWhiteBtn.setBackgroundResource(R.drawable.shape_assign_green_bg)
            } else {
                tvGreenBtn.text = "催办"
                tvGreenBtn.setTextColor(Color.parseColor("#FFFFFF"))
                tvGreenBtn.setBackgroundResource(R.drawable.shape_assign_green_bg)
                tvWhiteBtn.visibility = View.GONE
            }
        }
    }

    /**
     *  在执行情况下：
     *  状态：2 ：是确认
     *  状态：3 ：是解决
     *  状态：4 ：完成
     *  状态：5 ：已退回
     *
     *  在交办情况下：
     *  dispaly_again == 1   重新派发，关闭任务  显示
     *  dispaly_again == 2   催办显示
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.tcSendBack -> {
                sendBackDialog.dismiss()
                if (rejectNetContent != null && rejectNetContent!!.editValue.isNotEmpty()) {
                    Log.e("tcSendBack--click", "${rejectNetContent!!.editValue.isNotEmpty()}")
                    mBinding.vm?.postRejectEvent(assignId, execId, rejectNetContent!!.editValue)
                }
            }

            R.id.tvGreenBtn -> {  // 左边按钮
                if (isZXDetail == 1) {
                    /**
                     * 执行页面下， 需要判断状态
                     */
                    when (status) {
                        2 -> {  // 确认
                            mBinding.vm?.postConfirmEvent(assignId, execId)
                        }
                        3 -> {  // 解决
                            val intent = Intent(this@AssignEventDetailActivity, AssignSolveActivity::class.java)
                            intent.putExtra("assignId", assignId)
                            intent.putExtra("execId", execId)
                            startActivity(intent)
                        }
                    }
                } else {
                    /**
                     * 交办页面 ， 根据 "dispaly_again" 来判断
                     */
                    when (dispalyAgain) {
                        1 -> {  // 重新派发
                            if (reReplaceData != null) {
                                val intent = Intent(this@AssignEventDetailActivity, AddAssignEventActivity::class.java)
                                intent.putExtra("reReplaceData", reReplaceData)
                                startActivity(intent)
                            }
                        }
                        2 -> {  // 催办
                            mBinding.vm?.postCuiBanEvent(assignId)
                        }
                    }
                }
            }
            R.id.tvWhiteBtn -> {   // 右边按钮
                if (isZXDetail == 1) {  // 执行
                    if (status == 2) {  // 申请退回
                        sendBackDialog.show()
                    }
                } else {  //交办
                    if (dispalyAgain == 1) {   // 关闭任务
                        mBinding.vm?.postColseEvent(assignId)
                    }
                }
            }
        }
    }


    /**
     * 判断 图片/视频 资源是否有
     *
     * @param img
     * @param vedio
     * @param recyclerView
     * @param adapter
     */
    private fun bandImgAndVideo(img: List<String>, vedio: String, recyclerView: RecyclerView?, adapter: PatrolDetailImgAdapter) {
        val list: MutableList<MediaBean> = ArrayList()
        if (!TextUtils.isEmpty(vedio)) {
            list.add(MediaBean(vedio, 2))
        }

        if (img.isNotEmpty()) {
            img.forEach {
                if (it.isNotEmpty()) {
                    list.add(MediaBean(it, 1))
                }
            }

        }

        if (list.size > 0) {
            recyclerView?.visibility = View.VISIBLE
            adapter.replaceData(list)
        } else {
            recyclerView?.visibility = View.GONE
        }
    }

    /**
     * 初始化头部局
     */
    private fun initTopHeadView(headerView: View) {
        if (headerView == null) return
        /**** 上报的图片和视频  */
        topHorRecycler = headerView.findViewById<RecyclerView>(R.id.imgRecyclerView)
        topHorRecycler?.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        topHorRecycler?.adapter = mAdapter

        val catpView = headerView.findViewById<CusAssignTopProgress>(R.id.catpView)
        catpView.visibility = if (isZXDetail == 1) View.VISIBLE else View.GONE

        val tvUpDown = headerView.findViewById<TextView>(R.id.tvUpDown)
        tvUpDown.setOnClickListener {
            if (topHorRecycler?.visibility == View.VISIBLE) {
                topHorRecycler?.visibility = View.GONE
                tvUpDown.text = "展开"
            } else {
                topHorRecycler?.visibility = View.VISIBLE
                tvUpDown.text = "收起"
            }
        }
    }
}
