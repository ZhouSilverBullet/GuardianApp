package com.sdxxtop.guardianapp.api

import com.sdxxtop.guardianapp.aakt.AboutBean
import com.sdxxtop.guardianapp.model.bean.*
import com.sdxxtop.guardianapp.ui.activity.problemgj.bean.AddEventResult
import com.sdxxtop.guardianapp.ui.activity.problemgj.bean.ListData
import com.sdxxtop.guardianapp.ui.activity.problemgj.bean.ProblemGJIndexBean
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

    @Multipart
    @POST("assign/edit")
    suspend fun editAssignEvent(@PartMap map: HashMap<String, RequestBody>): BaseResponse<String>

    @FormUrlEncoded
    @POST("assign/details")
    suspend fun postAssignDetail(@Field("data") data: String): BaseResponse<AssignDetailBean>

    @FormUrlEncoded
    @POST("assignexec/settle")
    suspend fun postConfirmEvent(@Field("data") data: String): BaseResponse<Any>

    @FormUrlEncoded
    @POST("assignexec/urge")
    suspend fun postCuiBanEvent(@Field("data") data: String): BaseResponse<Any>

    @Multipart
    @POST("assignexec/finish")
    suspend fun postSolveEvent(@PartMap map: HashMap<String, RequestBody>): BaseResponse<Any>

    @FormUrlEncoded
    @POST("assignexec/reject")
    suspend fun postRejectEvent(@Field("data") data: String): BaseResponse<Any>

    @FormUrlEncoded
    @POST("assign/close")
    suspend fun postColseEvent(@Field("data") data: String): BaseResponse<Any>

    @FormUrlEncoded
    @POST("event/monthEvent")
    suspend fun postMonthEvnet(@Field("data") data: String): BaseResponse<FlyEventListBean>

    @FormUrlEncoded
    @POST("event/eventList")
    suspend fun postData(@Field("data") data: String): BaseResponse<EventIndexBean>

    @FormUrlEncoded
    @POST("Problemevent/Report")
    suspend fun postGJData(@Field("data") data: String): BaseResponse<ProblemGJIndexBean>

    @FormUrlEncoded
    @POST("problemEvent/index")
    suspend fun postGJListData(@Field("data") data: String): BaseResponse<ListData>

    @Multipart
    @POST("Problemevent/Eventadd")
    suspend fun reportProblem(@PartMap map: HashMap<String, RequestBody>): BaseResponse<AddEventResult>

    @FormUrlEncoded
    @POST("app/init")
    suspend fun checkAppVersion(@Field("data") data: String): BaseResponse<InitBean>

}