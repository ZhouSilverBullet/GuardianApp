package com.sdxxtop.guardianapp.model.http.api;

import com.sdxxtop.guardianapp.model.bean.AutoLoginBean;
import com.sdxxtop.guardianapp.model.bean.ContactIndexBean;
import com.sdxxtop.guardianapp.model.bean.EventIndexBean;
import com.sdxxtop.guardianapp.model.bean.EventReadBean;
import com.sdxxtop.guardianapp.model.bean.InitBean;
import com.sdxxtop.guardianapp.model.bean.LoginBean;
import com.sdxxtop.guardianapp.model.bean.MainIndexBean;
import com.sdxxtop.guardianapp.model.bean.MainMapBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.SignLogBean;
import com.sdxxtop.guardianapp.model.bean.StudyCheckBean;
import com.sdxxtop.guardianapp.model.bean.StudyCourseBean;
import com.sdxxtop.guardianapp.model.bean.StudyQuestionBean;
import com.sdxxtop.guardianapp.model.bean.UcenterIndexBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @POST("main/map")
    Observable<RequestBean<MainMapBean>> postMainMap(@Field("data") String data);

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


    @FormUrlEncoded
    @POST("contact/index")
    Observable<RequestBean<ContactIndexBean>> postContactIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("contact/search")
    Observable<RequestBean<ContactIndexBean>> postContactSearch(@Field("data") String data);


    @FormUrlEncoded
    @POST("ucenter/index")
    Observable<RequestBean<UcenterIndexBean>> postUcenterIndex(@Field("data") String data);

    @Multipart
    @POST("ucenter/modImg")
    Observable<RequestBean> postUcenterModImg(@PartMap Map<String, RequestBody> data);

    /**
     * exam
     * course
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("study/{name}")
    Observable<RequestBean<StudyCourseBean>> postStudyCourse(@Path("name") String name, @Field("data") String data);

    /**
     * exam
     * course
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("study/question")
    Observable<RequestBean<StudyQuestionBean>> postStudyQuestion(@Field("data") String data);

    @FormUrlEncoded
    @POST("study/check")
    Observable<RequestBean> postStudyCheck(@Field("data") String data);


}
