package com.sdxxtop.network.load

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.network.helper.data.BaseResponse
import com.sdxxtop.network.helper.exception.ApiException
import com.sdxxtop.network.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-30 11:11
 * Version: 1.0
 * Description:
 */
class LoadDataImpl(override val context: Context, override val viewModelScope: CoroutineScope) : ILoadData {

    override fun <T> loadCatchOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                                   successBlock: (T) -> Unit,
                                   failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                                   catchBack: suspend CoroutineScope.(t: Throwable) -> Unit,
                                   finallyBack: suspend CoroutineScope.() -> Unit,
                                   throwable: MutableLiveData<ApiException>
    ) {
        viewModelScope.launch {
            try {
                if (NetworkUtils.isNetworkAvailable(context)) {
                    converterResponse(block(), successBlock, failBlock, throwable)
                } else {
                    catchBack(UnknownHostException("网络连接失败，请重试"))
                }
            } catch (e: Exception) {
                catchBack(e)
                throwable.value = ApiException()
//                LogUtil.e("LoadDataImpl", e.message ?: "")
            } finally {
                finallyBack()
            }
        }
    }

    override fun <T> loadOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                              successBlock: (T) -> Unit,
            //空实现带参方法
                              failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                              finallyBack: suspend CoroutineScope.() -> Unit,
                              throwable: MutableLiveData<ApiException>

    ) {
        viewModelScope.launch {
            try {
                if (NetworkUtils.isNetworkAvailable(context)) {
                    converterResponse(block(), successBlock, failBlock, throwable)
                } else {
                    failBlock(-100, "网络连接失败，请重试", UnknownHostException())
                }
            } catch (e: Exception) {
                failBlock(-101, NetworkUtils.getHttpExceptionMsg(e), e)
                throwable.value = ApiException()
//                LogUtil.e("LoadDataImpl", e.message ?: "")
            } finally {
                finallyBack()
            }
        }
    }

    /**
     * 转换
     * 成功 successBlock
     * 失败 failBlock
     */
    fun <T> converterResponse(response: BaseResponse<T>,
                              successBlock: (T) -> Unit,
                              failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                              throwable: MutableLiveData<ApiException>) {
        if (response == null) {
            val t = Throwable("正常数据，业务code非200")
            failBlock(-100, "系统发生错误", t)
            return
        }
        if (response.code == 200) {
            successBlock(response.data)
        } else {
            val t = Throwable("正常数据，业务code非200")
            throwable.value = ApiException(response.code, response.msg)
            failBlock(response.code, response.msg, t)
        }
    }


    override fun <T> loadBaseOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                                  successBlock: (BaseResponse<T>) -> Unit,
                                  failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                                  finallyBack: suspend CoroutineScope.() -> Unit,
                                  throwable: MutableLiveData<ApiException>
    ) {
        viewModelScope.launch {
            try {
                if (NetworkUtils.isNetworkAvailable(context)) {
                    converterBaseResponse(block(), successBlock, failBlock, throwable)
                } else {
                    failBlock(-100, "网络连接失败", UnknownHostException())
                }
            } catch (e: Exception) {
                failBlock(-101, NetworkUtils.getHttpExceptionMsg(e), e)
//                LogUtil.e("LoadDataImpl", e.message ?: "")
            } finally {
                finallyBack()
            }
        }
    }

    /**
     * 转换base结果
     * 成功 successBlock
     * 失败 failBlock
     */
    fun <T> converterBaseResponse(response: BaseResponse<T>,
                                  successBlock: (BaseResponse<T>) -> Unit,
                                  failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                                  throwable: MutableLiveData<ApiException>) {
        if (response == null) {
            val t = Throwable("正常数据，业务code非200")
            failBlock(-100, "系统发生错误", t)
            return
        }
        if (response.code == 200) {
            successBlock(response)
        } else {
            val t = Throwable("正常数据，业务code非200")
            throwable.value = ApiException(response.code, response.msg)
            failBlock(response.code, response.msg, t)
        }
    }
}