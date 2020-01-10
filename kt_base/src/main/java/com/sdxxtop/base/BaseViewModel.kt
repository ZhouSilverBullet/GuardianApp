package com.sdxxtop.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdxxtop.network.helper.data.BaseResponse
import com.sdxxtop.network.helper.exception.ApiException
import com.sdxxtop.network.load.ILoadData
import com.sdxxtop.network.load.LoadDataImpl
import kotlinx.coroutines.CoroutineScope

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-24 20:01
 * Version: 1.0
 * Description:
 */
abstract class BaseViewModel : ViewModel() {
    val mIsLoadingShow = MutableLiveData(false)
    val mThrowable = MutableLiveData<ApiException>()
    //抽取了LoadData
    private val loadData: ILoadData = LoadDataImpl(BaseApplication.INSTANCE, viewModelScope)


    fun <T> loadCatchOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                          successBlock: (T) -> Unit,
                          failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                          catchBack: suspend CoroutineScope.(t: Throwable) -> Unit = {},
                          finallyBack: suspend CoroutineScope.() -> Unit = {}) {
        loadData.loadCatchOnUI(block, successBlock, failBlock, catchBack, finallyBack, mThrowable)
    }


    fun <T> loadOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                     successBlock: (T) -> Unit,
            //空实现带参方法
                     failBlock: (code: Int, msg: String, t: Throwable) -> Unit = { code, msg, t -> },
                     finallyBack: suspend CoroutineScope.() -> Unit = {}) {
        loadData.loadOnUI(block, successBlock, failBlock, finallyBack, mThrowable)
    }


    fun <T> loadBaseOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                         successBlock: (BaseResponse<T>) -> Unit,
                         failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                         finallyBack: suspend CoroutineScope.() -> Unit = {}) {
        loadData.loadBaseOnUI(block, successBlock, failBlock, finallyBack, mThrowable)
    }

    fun startActivity(context: Context?, intent: Intent): Boolean {
        if (context == null) {
            return false
        }

        try {
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            return true
        } catch (e: Exception) {

        }
        return false
    }

    fun <T> startActivity(context: Context?, activityClazz: Class<T>): Boolean {
        if (context == null) {
            return false
        }

        try {
            val intent = Intent(context, activityClazz)
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            return true
        } catch (e: Exception) {

        }
        return false
    }

    fun showLoadingDialog(isShow: Boolean) {
        mIsLoadingShow.value = isShow
    }
}