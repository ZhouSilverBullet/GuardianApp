package com.sdxxtop.guardianapp.ui.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.sdxxtop.guardianapp.R
import kotlinx.android.synthetic.main.cus_assign_top_progress.view.*

/**
 * Date:2020-02-14
 * author:lwb
 * Desc:
 */
class CusAssignTopProgress : LinearLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.cus_assign_top_progress, this, true)
    }

    //status状态(1:待部门确认 2:待个人确认 3:待解决 4:已完成 5:已退回)
    fun setStatus(status: Int) {
        tvDJJtx.text = "待解决"
        tvLine1.setBackgroundColor(Color.parseColor("#1A32B16C"))
        tvDJJ.setBackgroundResource(R.drawable.shape_assign_status_top_normal)
        tvLine2.setBackgroundColor(Color.parseColor("#1A32B16C"))
        tvYWC.setBackgroundResource(R.drawable.shape_assign_status_top_normal)
        tvYWC.visibility = View.VISIBLE
        tvYWCtx.visibility = View.VISIBLE
        when (status) {
            5 -> {
                tvLine2.visibility = View.GONE
                tvYWC.visibility = View.GONE
                tvYWCtx.visibility = View.GONE
                tvDJJtx.text = "已退回"
                tvLine1.setBackgroundColor(Color.parseColor("#32B16C"))
                tvDJJ.setBackgroundResource(R.drawable.choose_pm_icon_select)
            }
            3 -> {
                tvLine1.setBackgroundColor(Color.parseColor("#32B16C"))
                tvDJJ.setBackgroundResource(R.drawable.choose_pm_icon_select)
                tvLine2.setBackgroundColor(Color.parseColor("#1A32B16C"))
                tvYWC.setBackgroundResource(R.drawable.shape_assign_status_top_normal)
            }
            4 -> {
                tvLine1.setBackgroundColor(Color.parseColor("#32B16C"))
                tvDJJ.setBackgroundResource(R.drawable.choose_pm_icon_select)
                tvLine2.setBackgroundColor(Color.parseColor("#32B16C"))
                tvYWC.setBackgroundResource(R.drawable.choose_pm_icon_select)
            }
        }
    }
}