package com.sdxxtop.guardianapp.api

import com.sdxxtop.guardianapp.aakt.AboutBean
import com.sdxxtop.guardianapp.model.bean.AssignDetailBean
import com.sdxxtop.guardianapp.model.bean.AssignListBean
import com.sdxxtop.guardianapp.model.bean.CategoryStatusBean
import com.sdxxtop.guardianapp.model.bean.PartAndUserBean
import com.sdxxtop.network.helper.data.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.*

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
    suspend fun postZXData(@Field("data") data: String): BaseResponse<AssignListBean>

    @FormUrlEncoded
    @POST("assign/lists")
    suspend fun postJBData(@Field("data") data: String): BaseResponse<AssignListBean>

    @FormUrlEncoded
    @POST("assign/cat")
    suspend fun postCategoryData(@Field("data") data: String): BaseResponse<CategoryStatusBean>

    @FormUrlEncoded
    @POST("assign/user")
    suspend fun postUserData(@Field("data") data: String): BaseResponse<PartAndUserBean>

    @FormUrlEncoded
    @POST("assign/part")
    suspend fun postPartData(@Field("data") data: String): BaseResponse<PartAndUserBean>

    @Multipart
    @POST("assign/add")
    suspend fun addAssignEvent(@PartMap map: HashMap<String, RequestBody>): BaseResponse<String>

    @FormUrlEncoded
    @POST("assign/details")
    suspend fun postAssignDetail(@Field("data") data: String): BaseResponse<AssignDetailBean>

}