package com.sdxxtop.guardianapp.utils;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * @author :  lwb
 * Date: 2019/7/5
 * Desc:
 */
public class LocationUtilOne implements AMapLocationListener {

    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption clientOption;
    private ILocationCallBack callBack;

    public void startLocate(Context context) {
        aMapLocationClient = new AMapLocationClient(context);

        //设置监听回调
        aMapLocationClient.setLocationListener(this);

        //初始化定位参数
        clientOption = new AMapLocationClientOption();
        clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        clientOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        clientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        clientOption.setMockEnable(false);

//        clientOption.setOnceLocationLatest(true);
//        clientOption.setOnceLocation(true);
        //设置定位间隔
        aMapLocationClient.setLocationOption(clientOption);

        aMapLocationClient.startLocation();
    }

    //完成定位回调
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功完成回调
                String country = aMapLocation.getCountry();
                String province = aMapLocation.getProvince();
                String city = aMapLocation.getCity();
                String district = aMapLocation.getDistrict();
                String street = aMapLocation.getStreet();
                double lat = aMapLocation.getLatitude();
                double lgt = aMapLocation.getLongitude();

                if (callBack != null) {
                    callBack.callBack(country + province + city + district + street, lat, lgt, aMapLocation);
//                    aMapLocationClient.stopLocation();
                }

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }


    public interface ILocationCallBack {
        void callBack(String str, double lat, double lgt, AMapLocation aMapLocation);
    }

    public void setLocationCallBack(ILocationCallBack callBack) {
        this.callBack = callBack;
    }

    public void stopLocation() {
        if (aMapLocationClient != null) {
            // 停止定位
            aMapLocationClient.stopLocation();
        }
    }

}
