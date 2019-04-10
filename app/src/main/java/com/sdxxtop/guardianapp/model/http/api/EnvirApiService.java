package com.sdxxtop.guardianapp.model.http.api;

import com.sdxxtop.guardianapp.model.bean.AutoLoginBean;
import com.sdxxtop.guardianapp.model.bean.EventIndexBean;
import com.sdxxtop.guardianapp.model.bean.LoginBean;
import com.sdxxtop.guardianapp.model.bean.MainIndexBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EnvirApiService {

    String BASE_URL = "http://envir.sdxxtop.com/api/";

    @FormUrlEncoded
    @POST("login/sendCode")
    Observable<RequestBean> postLoginSendCode(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/mobileLogin")
    Observable<RequestBean<LoginBean>> postLoginMobileLogin(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/autoLogin")
    Observable<RequestBean<AutoLoginBean>> postLoginAutoLogin(@Field("data") String data);

    @FormUrlEncoded
    @POST("main/index")
    Observable<RequestBean<MainIndexBean>> postMainIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("event/index")
    Observable<RequestBean<EventIndexBean>> postEventIndex(@Field("data") String data);


}
