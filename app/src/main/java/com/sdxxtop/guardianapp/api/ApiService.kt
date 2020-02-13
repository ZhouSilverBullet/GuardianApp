package com.sdxxtop.guardianapp.api

import com.sdxxtop.guardianapp.aakt.AboutBean
import com.sdxxtop.guardianapp.model.NetWorkSession
import com.sdxxtop.guardianapp.model.bean.CategoryStatusBean
import com.sdxxtop.guardianapp.model.bean.PartAndUserBean
import com.sdxxtop.network.helper.data.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-29 20:21
 * Version: 1.0
 * Description:
 */

interface ApiService {

    companion object {
//        const val BASE_URL = "http://yinanapi.sdxxtop.com/api/"
//        const val BASE_URL = "http://envir.test.sdxxtop.com/api/"
    }

    @FormUrlEncoded
    @POST("record/index")
    suspend fun postAppInit(@Field("data") data: String): BaseResponse<AboutBean>

    @FormUrlEncoded
    @POST("login/sendCode")
    suspend fun postLoginSendCode(@Field("data") data: String): BaseResponse<Any>

    @FormUrlEncoded
    @POST("assignexec/lists")
    suspend fun postZXData(@Field("data") data: String): BaseResponse<Any>

    @FormUrlEncoded
    @POST("assign/lists")
    suspend fun postJBData(@Field("data") data: String): BaseResponse<Any>

    @FormUrlEncoded
    @POST("assign/cat")
    suspend fun postCategoryData(@Field("data") data: String): BaseResponse<CategoryStatusBean>

    @FormUrlEncoded
    @POST("assign/user")
    suspend fun postUserData(@Field("data") data: String): BaseResponse<PartAndUserBean>

    @FormUrlEncoded
    @POST("assign/part")
    suspend fun postPartData(@Field("data") data: String): BaseResponse<PartAndUserBean>

    @FormUrlEncoded
    @POST("assign/add")
    suspend fun addAssignEvent(@Field("data") data: String): BaseResponse<Any>

}