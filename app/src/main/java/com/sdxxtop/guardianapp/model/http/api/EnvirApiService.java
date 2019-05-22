package com.sdxxtop.guardianapp.model.http.api;

import com.sdxxtop.guardianapp.model.bean.AutoLoginBean;
import com.sdxxtop.guardianapp.model.bean.ContactIndexBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseCompanyBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseIndexBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseSecurityBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseTrailBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseUserdetailsBean;
import com.sdxxtop.guardianapp.model.bean.EventIndexBean;
import com.sdxxtop.guardianapp.model.bean.EventListBean;
import com.sdxxtop.guardianapp.model.bean.EventReadBean;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.ExamineFinishBean;
import com.sdxxtop.guardianapp.model.bean.GERPIndexBean;
import com.sdxxtop.guardianapp.model.bean.GridreportIndexBean;
import com.sdxxtop.guardianapp.model.bean.GridreportOperatorBean;
import com.sdxxtop.guardianapp.model.bean.GridreportPatrolBean;
import com.sdxxtop.guardianapp.model.bean.GridreportUserreportBean;
import com.sdxxtop.guardianapp.model.bean.InitBean;
import com.sdxxtop.guardianapp.model.bean.LoginBean;
import com.sdxxtop.guardianapp.model.bean.MainIndexBean;
import com.sdxxtop.guardianapp.model.bean.MainMapBean;
import com.sdxxtop.guardianapp.model.bean.PartEventListBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.ShowPartBean;
import com.sdxxtop.guardianapp.model.bean.SignLogBean;
import com.sdxxtop.guardianapp.model.bean.StudyCourseBean;
import com.sdxxtop.guardianapp.model.bean.StudyQuestionBean;
import com.sdxxtop.guardianapp.model.bean.UcenterIndexBean;

import java.util.HashMap;
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

    String BASE_URL = "http://envir.test.sdxxtop.com/api/";
//    String BASE_URL = "http://envir.sdxxtop.com/api/";

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

    @Multipart
    @POST("event/modify")
    Observable<RequestBean> postEventModify(@PartMap HashMap<String, RequestBody> data);

    @FormUrlEncoded
    @POST("event/showPart")
    Observable<RequestBean<ShowPartBean>> postEventShowPart(@Field("data") String data);

    @FormUrlEncoded
    @POST("event/search")
    Observable<RequestBean<EventSearchTitleBean>> postEventSearch(@Field("data") String data);

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

    @FormUrlEncoded
    @POST("study/finish")
    Observable<RequestBean<ExamineFinishBean>> postStudyFinish(@Field("data") String data);


    @Multipart
    @POST("face/reg")
    Observable<RequestBean> postFaceReg(@PartMap Map<String, RequestBody> data);

    @Multipart
    @POST("face/verify")
    Observable<RequestBean> postFaceVerify(@PartMap Map<String, RequestBody> data);

    @FormUrlEncoded
    @POST("eventreport/index")
    Observable<RequestBean<GERPIndexBean>> postIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("eventreport/eventlist")
    Observable<RequestBean<EventListBean>> postEventlist(@Field("data") String data);

    @FormUrlEncoded
    @POST("eventreport/eventdetails")
    Observable<RequestBean<PartEventListBean>> postPartEventList(@Field("data") String data);

    @FormUrlEncoded
    @POST("enterprise/index")
    Observable<RequestBean<EnterpriseIndexBean>> postEnterpriseIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("enterprise/company")
    Observable<RequestBean<EnterpriseCompanyBean>> postEnterpriseCompany(@Field("data") String data);

    @FormUrlEncoded
    @POST("enterprise/security")
    Observable<RequestBean<EnterpriseSecurityBean>> postEnterpriseSecurity(@Field("data") String data);

    @FormUrlEncoded
    @POST("enterprise/trail")
    Observable<RequestBean<EnterpriseTrailBean>> postEnterpriseTrail(@Field("data") String data);

    @FormUrlEncoded
    @POST("gridreport/trail")
    Observable<RequestBean<EnterpriseTrailBean>> postGridreportTrail(@Field("data") String data);

    @FormUrlEncoded
    @POST("enterprise/userdetails")
    Observable<RequestBean<EnterpriseUserdetailsBean>> postEnterpriseUserdetails(@Field("data") String data);

    @FormUrlEncoded
    @POST("gridreport/index")
    Observable<RequestBean<GridreportIndexBean>> postGridreportIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("gridreport/patrol")
    Observable<RequestBean<GridreportPatrolBean>> postGridreportPatrol(@Field("data") String data);

    @FormUrlEncoded
    @POST("gridreport/operator")
    Observable<RequestBean<GridreportOperatorBean>> postGridreportOperator(@Field("data") String data);

    @FormUrlEncoded
    @POST("gridreport/userreport")
    Observable<RequestBean<GridreportUserreportBean>> postGridreportUserreport(@Field("data") String data);

}
