package com.sdxxtop.network.load

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.network.helper.data.BaseResponse
import com.sdxxtop.network.helper.exception.ApiException
import kotlinx.coroutines.CoroutineScope

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-30 11:09
 * Version: 1.0
 * Description:
 */
interface ILoadData {

    /**
     * 判断是否有网的上下文
     */
    val context: Context

    /**
     * 用于进行协程的协程域
     */
    val viewModelScope: CoroutineScope

    /**
     * 比simple多带了一个 catch 的方法回调
     */
    fun <T> loadCatchOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                          successBlock: (T) -> Unit,
                          failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                          catchBack: suspend CoroutineScope.(t: Throwable) -> Unit = {},
                          finallyBack: suspend CoroutineScope.() -> Unit = {}, throwable: MutableLiveData<ApiException>)

    /**
     * 返回值全部放在了 failBlock 回调
     */
    fun <T> loadOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                     successBlock: (T) -> Unit,
            //空实现带参方法
                     failBlock: (code: Int, msg: String, t: Throwable) -> Unit = { code, msg, t -> },
                     finallyBack: suspend CoroutineScope.() -> Unit = {}, throwable: MutableLiveData<ApiException>)

    /**
     * 返回值全部放在了 failBlock 回调
     *
     * 用于处理 返回 baseResponse 的特殊处理，可以减少T的编写
     *
     */
    fun <T> loadBaseOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                         successBlock: (BaseResponse<T>) -> Unit,
                         failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                         finallyBack: suspend CoroutineScope.() -> Unit = {}, throwable: MutableLiveData<ApiException>)
}