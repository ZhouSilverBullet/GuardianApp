package com.sdxxtop.guardianapp.aakt

import android.app.Dialog
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.guardianapp.api.RetrofitClient
import com.sdxxtop.guardianapp.model.bean.InitBean
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.utils.UIUtils

/**
 * @author :  lwb
 * Date: 2020/1/9
 * Desc:
 */
class AboutModel : BaseViewModel() {
    val initBean = MutableLiveData<InitBean>()

    /**
     * 检查更新
     */
    fun checkUpDateApp(dialog: Dialog?) {
        loadOnUI({
            dialog?.show()
            val params = Params()
            params.put("pi", 1)
            //这里实际上返回了结果
            RetrofitClient.apiService.checkAppVersion(params.data)
        }, {
            initBean.value = it
            if (TextUtils.isEmpty(it.version_name)) {
                UIUtils.showToast("当前已是最新版本")
            }
            dialog?.dismiss()
        }, { _, msg, _ ->
            dialog?.dismiss()
            UIUtils.showToast(msg)
        })
    }

}