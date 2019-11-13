package com.sdxxtop.guardianapp.model.http.api;

import com.sdxxtop.guardianapp.model.NetWorkSession;
import com.sdxxtop.guardianapp.model.bean.AllarticleBean;
import com.sdxxtop.guardianapp.model.bean.ArticleIndexBean;
import com.sdxxtop.guardianapp.model.bean.AuthDataBean;
import com.sdxxtop.guardianapp.model.bean.AutoLoginBean;
import com.sdxxtop.guardianapp.model.bean.ContactIndexBean;
import com.sdxxtop.guardianapp.model.bean.DeviceDataBean;
import com.sdxxtop.guardianapp.model.bean.DeviceListBean;
import com.sdxxtop.guardianapp.model.bean.DeviceMapBean;
import com.sdxxtop.guardianapp.model.bean.EarlyWarningBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseCompanyBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseIndexBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseSecurityBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseTrailBean;
import com.sdxxtop.guardianapp.model.bean.EnterpriseUserdetailsBean;
import com.sdxxtop.guardianapp.model.bean.EventChartBean;
import com.sdxxtop.guardianapp.model.bean.EventDiscretionListBean;
import com.sdxxtop.guardianapp.model.bean.EventIndexBean;
import com.sdxxtop.guardianapp.model.bean.EventListBean;
import com.sdxxtop.guardianapp.model.bean.EventModeBean;
import com.sdxxtop.guardianapp.model.bean.EventReadIndexBean;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.EventShowBean;
import com.sdxxtop.guardianapp.model.bean.ExamineFinishBean;
import com.sdxxtop.guardianapp.model.bean.FlyEventDetailBean;
import com.sdxxtop.guardianapp.model.bean.FlyEventListBean;
import com.sdxxtop.guardianapp.model.bean.FlyEventPartBean;
import com.sdxxtop.guardianapp.model.bean.GERPIndexBean;
import com.sdxxtop.guardianapp.model.bean.GridEventCountBean;
import com.sdxxtop.guardianapp.model.bean.GridEventListBean;
import com.sdxxtop.guardianapp.model.bean.GridreportIndexBean;
import com.sdxxtop.guardianapp.model.bean.GridreportOperatorBean;
import com.sdxxtop.guardianapp.model.bean.GridreportPatrolBean;
import com.sdxxtop.guardianapp.model.bean.GridreportUserreportBean;
import com.sdxxtop.guardianapp.model.bean.InitBean;
import com.sdxxtop.guardianapp.model.bean.LoginBean;
import com.sdxxtop.guardianapp.model.bean.MainIndexBeanNew;
import com.sdxxtop.guardianapp.model.bean.MainMapBean;
import com.sdxxtop.guardianapp.model.bean.PartEventListBean;
import com.sdxxtop.guardianapp.model.bean.PatrolAddBean;
import com.sdxxtop.guardianapp.model.bean.PatrolReadBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.SectionEventBean;
import com.sdxxtop.guardianapp.model.bean.ShowPartBean;
import com.sdxxtop.guardianapp.model.bean.SignLogBean;
import com.sdxxtop.guardianapp.model.bean.StudyCourseBean;
import com.sdxxtop.guardianapp.model.bean.StudyQuestionBean;
import com.sdxxtop.guardianapp.model.bean.TrackBean;
import com.sdxxtop.guardianapp.model.bean.TrackPointBean;
import com.sdxxtop.guardianapp.model.bean.UcenterIndexBean;
import com.sdxxtop.guardianapp.model.bean.UnreadIndexBean;
import com.sdxxtop.guardianapp.model.bean.UnreadNewslistBean;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;

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

    String BASE_URL = false ? "http://envir.test.sdxxtop.com/api/" : "http://envir.sdxxtop.com/api/";
//    String BASE_URL = "http://envir.test.sdxxtop.com/api/";  // 测试
//    String BASE_URL = "http://envir.dev.sdxxtop.com/api/";  // 预发布环境的

    @FormUrlEncoded
    @POST("app/init")
    Observable<RequestBean<InitBean>> postAppInit(@Field("data") String data);

    @FormUrlEncoded
    @POST("article/index")
    Observable<RequestBean<ArticleIndexBean>> postArticleIndex(@Field("data") String data);

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
    Observable<RequestBean<MainIndexBeanNew>> postMainIndex(@Field("data") String data);

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

    @Multipart
    @POST("patrol/add")
    Observable<RequestBean<PatrolAddBean>> postPatrolAdd(@PartMap Map<String, RequestBody> data);

    @FormUrlEncoded
    @POST("event/read")
    Observable<RequestBean<EventReadIndexBean>> postEventRead(@Field("data") String data);

    @Multipart
    @POST("event/modify")
    Observable<RequestBean> postEventModify(@PartMap HashMap<String, RequestBody> data);

    @Multipart
    @POST("event/failed")
    Observable<RequestBean> postEventFailed(@PartMap HashMap<String, RequestBody> data);

    @FormUrlEncoded
//    @POST("event/showPart")
    @POST("event/report")
    Observable<RequestBean<ShowPartBean>> postEventShowPart(@Field("data") String data);

    @FormUrlEncoded
//    @POST("event/showPart")
    @POST("event/sector")
    Observable<RequestBean<EventShowBean>> postEventSector(@Field("data") String data);

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
    @POST("event/mode")
    Observable<RequestBean<EventModeBean>> postEventMode(@Field("data") String data);


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
    @POST("eventreport/eventShow")
    Observable<RequestBean<EventShowBean>> postEventreportShow(@Field("data") String data);

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

    @FormUrlEncoded
    @POST("patrol/index")
    Observable<RequestBean<EventDiscretionListBean>> postPatrolIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("patrol/read")
    Observable<RequestBean<PatrolReadBean>> postPatrolRead(@Field("data") String data);

    @Multipart
    @POST("patrol/handle")
    Observable<RequestBean> postPatrolHandle(@PartMap Map<String, RequestBody> data);

    @FormUrlEncoded
    @POST("unread/index")
    Observable<RequestBean<UnreadIndexBean>> postUnreadIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("device/index")
    Observable<RequestBean<AuthDataBean>> posDeviceIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("device/deviceMap")
    Observable<RequestBean<DeviceMapBean>> postDeviceDeviceMap(@Field("data") String data);

    @FormUrlEncoded
    @POST("unread/newslist")
    Observable<RequestBean<UnreadNewslistBean>> postUnreadNewslist(@Field("data") String data);

    @FormUrlEncoded
    @POST("device/deviceRead")
    Observable<RequestBean<DeviceDataBean>> postDeviceDeviceRead(@Field("data") String data);

    @FormUrlEncoded
    @POST("device/deviceReadList")
    Observable<RequestBean<DeviceDataBean>> postDeviceDeviceReadList(@Field("data") String data);

    @FormUrlEncoded
    @POST("device/deviceList")
    Observable<RequestBean<DeviceListBean>> postDeviceDeviceList(@Field("data") String data);

    @FormUrlEncoded
    @POST("device/earlyRead")
    Observable<RequestBean<EarlyWarningBean>> postDeviceEarlyRead(@Field("data") String data);

    @FormUrlEncoded
    @POST("device/modify")
    Observable<RequestBean> postDeviceModify(@Field("data") String data);

    @FormUrlEncoded
    @POST("eventreport/eventChart")
    Observable<RequestBean<EventChartBean>> postEventChart(@Field("data") String data);

    @FormUrlEncoded
    @POST("event/sectoral")
    Observable<RequestBean<SectionEventBean>> postSectionEvent(@Field("data") String data);

    @FormUrlEncoded
    @POST("event/operate")
    Observable<RequestBean> postEventOperate(@Field("data") String data);

    @FormUrlEncoded
    @POST("main/getlocus")
    Observable<RequestBean<TrackBean>> postFalconReturnTime(@Field("data") String data);

    @FormUrlEncoded
    @POST("main/signlog")
    Observable<RequestBean<TrackPointBean>> postMoreTrack(@Field("data") String data);

    @FormUrlEncoded
    @POST("uav/showPart")
    Observable<RequestBean<FlyEventPartBean>> postFlyEventPartBean(@Field("data") String data);

    @FormUrlEncoded
    @POST("uav/add")
    Observable<RequestBean> postFlyEventAdd(@Field("data") String data);

    @FormUrlEncoded
    @POST("uav/uavlist")
    Observable<RequestBean<FlyEventListBean>> postFlyEventList(@Field("data") String data);

    @FormUrlEncoded
    @POST("uav/mothuav")
    Observable<RequestBean<FlyEventListBean>> postMonthFlyEvent(@Field("data") String data);

    @FormUrlEncoded
    @POST("uav/read")
    Observable<RequestBean<FlyEventDetailBean>> postFlyEventDetail(@Field("data") String data);

    @FormUrlEncoded
    @POST("uav/search")
    Observable<RequestBean<FlyEventListBean>> postSearchEvent(@Field("data") String data);

    @FormUrlEncoded
    @POST("working/index")
    Observable<RequestBean<WorkIndexBean>> postWorkIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("article/allarticle")
    Observable<RequestBean<AllarticleBean>> postAllarticleData(@Field("data") String data);

    @FormUrlEncoded
    @POST("working/gridevent")
    Observable<RequestBean<GridEventCountBean>> postGridEventCount(@Field("data") String data);

    @FormUrlEncoded
    @POST("working/{name}")
    Observable<RequestBean<GridEventListBean>> postGridEventList(@Path("name") String name, @Field("data") String data);

}
