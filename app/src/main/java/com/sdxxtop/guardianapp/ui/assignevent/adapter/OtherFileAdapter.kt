package com.sdxxtop.guardianapp.ui.assignevent.adapter

import android.webkit.WebView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.guardianapp.R

/**
 * Date:2020-02-17
 * author:lwb
 * Desc:
 */
class OtherFileAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.other_file_layout) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        if (helper == null || item == null) return
        helper.itemView.setOnClickListener {
            val webView = WebView(mContext)
//            http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/app/20200216170813233692.xlsx
            webView.loadUrl("http://view.officeapps.live.com/op/view.aspx?src=${item}")
        }
    }
}