package com.sdxxtop.guardianapp.model.http.api;

import com.sdxxtop.guardianapp.model.bean.AutoLoginBean;
import com.sdxxtop.guardianapp.model.bean.EventIndexBean;
import com.sdxxtop.guardianapp.model.bean.EventReadBean;
import com.sdxxtop.guardianapp.model.bean.InitBean;
import com.sdxxtop.guardianapp.model.bean.LoginBean;
import com.sdxxtop.guardianapp.model.bean.MainIndexBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.SignLogBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface EnvirApiService {

    String BASE_URL = "http://envir.sdxxtop.com/api/";

    @FormUrlEncoded
    @POST("app/init")
    Observable<RequestBean<InitBean>> postAppInit(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/sendCode")
    Observable<RequestBean> postLoginSendCode(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/mobileLogin")
    Observable<RequestBean<LoginBean>> postLoginMobileLogin(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/autoLogin")
    Observable<RequestBean<AutoLoginBean>> postLoginAutoLogin(@Field("data") String data);

    ////////////// 首页 ////////////
    @FormUrlEncoded
    @POST("main/index")
    Observable<RequestBean<MainIndexBean>> postMainIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("main/sign")
    Observable<RequestBean> postMainSign(@Field("data") String data);

    @FormUrlEncoded
    @POST("main/signlog")
    Observable<RequestBean<SignLogBean>> postMainSignLog(@Field("data") String data);

    //////////////首页 ////////////


    @FormUrlEncoded
    @POST("event/index")
    Observable<RequestBean<EventIndexBean>> postEventIndex(@Field("data") String data);

    @Multipart
    @POST("event/add")
    Observable<RequestBean> postEventAdd(@PartMap Map<String, RequestBody> data);

    @FormUrlEncoded
    @POST("event/read")
    Observable<RequestBean<EventReadBean>> postEventRead(@Field("data") String data);

    @FormUrlEncoded
    @POST("event/modify")
    Observable<RequestBean> postEventModify(@Field("data") String data);


}
