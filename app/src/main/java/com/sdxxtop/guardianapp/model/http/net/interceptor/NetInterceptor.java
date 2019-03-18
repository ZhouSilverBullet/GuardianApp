package com.sdxxtop.guardianapp.model.http.net.interceptor;

import android.util.Log;

import com.sdxxtop.guardianapp.utils.SystemUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * 在有网络的情况下，先去读缓存，设置的缓存时间到了，在去网络获取
 */

public class NetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean connected = SystemUtil.isNetworkConnect();
        if(connected){
            //如果有网络，缓存90s
            Log.e("zhanghe","print");
            Response response = chain.proceed(request);
            int maxTime = 90;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxTime)
                    .build();
        }
        //如果没有网络，不做处理，直接返回
        return chain.proceed(request);


        /*if (isNetworkConnected()) {
            Log.e("zhanghe","有网络_common");
            //有网的时候读接口上的@Headers里的配置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } */
    }


}
