package com.sdxxtop.guardianapp.api

import com.sdxxtop.network.api.BaseRetrofitClient
import okhttp3.OkHttpClient

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-29 20:18
 * Version: 1.0
 * Description:
 */
object RetrofitClient : BaseRetrofitClient() {
    val apiService by lazy {
        getService(ApiService::class.java, ApiService.BASE_URL)
    }

    override fun isDebug(): Boolean {
        return true
    }

    override fun handleBuilder(builder: OkHttpClient.Builder) {

    }

}