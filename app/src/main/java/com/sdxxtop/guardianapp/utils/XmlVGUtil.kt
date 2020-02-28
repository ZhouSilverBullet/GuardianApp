package com.sdxxtop.guardianapp.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView


/**
 * Date:2020/2/28
 * author:lwb
 * Desc:
 */

@BindingAdapter("bind:show")
fun showView(view: View?, isShow: Boolean?) {
    view?.visibility = if (isShow!!) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:text")
fun setText(view: TextAndTextView?, str: String?) {
    view?.textRightText?.text = str
}