package com.sdxxtop.guardianapp.model.http.api;


import com.sdxxtop.guardianapp.model.bean.LearnNewsBean;
import com.sdxxtop.guardianapp.model.bean.NewListBean;
import com.sdxxtop.guardianapp.model.bean.NewsBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    //https://blog.csdn.net/jiao_zg/article/details/77897748
//    String BASE_URL = "http://app.sdxxtop.com/api/";
    String BASE_URL = "http://manage.sdxxtop.com/api/";
    String GET_ARTICLE_LIST = "api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752";

    @GET("home")
    Observable<RequestBean<NewsBean>> getData();

    @GET(GET_ARTICLE_LIST)
    Observable<RequestBean<NewListBean>> getNewList(@Query("category") String category, @Query("min_behot_time") long lastTime, @Query("last_refresh_sub_entrance_interval") long currentTime);

    @FormUrlEncoded
    @POST("app/init")
    Observable<RequestBean> getZhiDian(@Field("data") String data);

    @FormUrlEncoded
    @POST("article/allarticle")
    Observable<RequestBean<List<LearnNewsBean>>> getAllArticle(@Field("data") String data);

    @FormUrlEncoded
    @POST("article/article_info")
    Observable<RequestBean> getAllArticleInfo(@Field("data") String data);

    //////////首页/////////

    @FormUrlEncoded
    @POST("app/index")
    Observable<RequestBean<NewsBean>> postIndex(@Field("data") String data);

    //////////首页/////////

}
