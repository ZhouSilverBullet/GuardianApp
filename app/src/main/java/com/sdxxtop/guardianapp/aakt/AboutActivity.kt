package com.sdxxtop.guardianapp.aakt

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.luck.picture.lib.permissions.RxPermissions
import com.sdxxtop.base.BaseKTActivity
import com.sdxxtop.guardianapp.BuildConfig
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityAboutBinding
import com.sdxxtop.guardianapp.ui.dialog.DownloadDialog
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseKTActivity<ActivityAboutBinding, AboutModel>() {
    override fun vmClazz() = AboutModel::class.java
    override fun layoutId() = R.layout.activity_about
    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.activity = this
    }

    /**
     * 初始化 检查更新的弹框
     */
    private val dialog by lazy {
        val dialog = Dialog(this, R.style.dialog)
        dialog.setContentView(R.layout.dialog_chack_app_updata)
        val window: Window = dialog.window
        window.setGravity(Gravity.CENTER)
        val attributes = window.attributes
        attributes.width = WindowManager.LayoutParams.WRAP_CONTENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.attributes = attributes
        dialog
    }

    override fun initObserve() {
        //是否更新
        mBinding.vm?.initBean?.observe(this, Observer {
            val downloadDialog = DownloadDialog(this, it, RxPermissions(this))
            downloadDialog.show()
        })
    }

    override fun initView() {
        us_ban_hao.text = "${getString(R.string.app_name)} v${BuildConfig.VERSION_NAME}"
        textUpdateVersion.textRightText.text = "V" + BuildConfig.VERSION_NAME
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.textCallMine -> {
                // 联系我们
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4008090778"))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            R.id.textUpdateVersion -> {
                mBinding.vm?.checkUpDateApp(dialog)
            }
        }
    }

}
