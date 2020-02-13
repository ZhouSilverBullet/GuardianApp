package com.sdxxtop.guardianapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Date:2020-02-13
 * author:lwb
 * Desc:
 */
object KeyboardktUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}