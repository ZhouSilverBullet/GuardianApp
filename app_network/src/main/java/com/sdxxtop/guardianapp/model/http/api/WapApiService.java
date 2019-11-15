package com.sdxxtop.guardianapp.model.http.api;

import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.RtcRequestBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WapApiService {

//    String BASE_URL =  "http://wap.sdxxtop.com/";
    String BASE_URL =  "https://datascreen.sdxxtop.com/";


//    @FormUrlEncoded
    @POST("grid/audio/lists")
    Observable<RequestBean> postAudioLists();

    @FormUrlEncoded
    @POST("grid/audio/rtc")
    Observable<RequestBean<RtcRequestBean>> postAudioRtc(@Field("userid") String data);
}
