package com.sdxxtop.guardianapp.utils;

import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sdxxtop.guardianapp.app.App;

public class AMapFindLocation2 implements AMapLocationListener {
    private static AMapFindLocation2 location;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private static double latitude; //精度
    private static double longitude; //维度
    private String address;
    private String TAG = "AMapFindLocation2";

    /**
     * 最多尝试3次
     */
    private static final int TIMES_MAX = 3;

    /**
     * time init or cancel
     */
    private static final int TIMES_INIT_OR_CACNEL = 0;

    //尝试次数
    private int time;
    //打卡的状态  TackCardHelper.TACK_FIELD 为这个的时候进行判断地址为空时
    //然后进行次数判断
    private int state;


    public void setState(int state) {
        this.state = state;
    }

    private AMapFindLocation2() {

    }

    public static AMapFindLocation2 getInstance() {
        if (location == null) {
            location = new AMapFindLocation2();
        }
        return location;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        Log.e(TAG, "onLocationChanged: amapLocation.getLocationType() = " + amapLocation.getLocationType() + " -- "
                + amapLocation.getLatitude() + ", " + amapLocation.getLongitude());

        if (locationCompanyListener != null) {
            try {
                String address = amapLocation.getAddress();
                //外勤打卡的时候才会进行地址判空
                if (TextUtils.isEmpty(address) && time < TIMES_MAX) {
                    //如果再次进来时，不停止定位，再次去获取定位
                    time++;
//                    ToastUtil.show("获取地址失败，第"+ time + "次重新获取");

                    Log.e(TAG, "onLocationChanged: step 1 " + time);
                } else {
                    time = TIMES_INIT_OR_CACNEL;
                    locationCompanyListener.onAddress(amapLocation);
                    Log.e(TAG, "onLocationChanged: step 2 " + time);
                }
            } finally {
                //重置次数
                if (time == TIMES_INIT_OR_CACNEL || time >= TIMES_MAX) {
                    Log.e(TAG, "onLocationChanged: step 3 " + time);
                    removeLocationCompanyListener();
                }
                Log.e(TAG, "onLocationChanged: step 4 " + time);
            }
        }
    }

    /**
     * 0
     * 定位失败
     * 请通过AMapLocation.getErrorCode()方法获取错误码，并参考错误码对照表进行问题排查。
     * <p>
     * 1
     * GPS定位结果
     * 通过设备GPS定位模块返回的定位结果，精度较高，在10米－100米左右
     * <p>
     * 2
     * 前次定位结果
     * 网络定位请求低于1秒、或两次定位之间设备位置变化非常小时返回，设备位移通过传感器感知。
     * <p>
     * 4
     * 缓存定位结果
     * 返回一段时间前设备在同样的位置缓存下来的网络定位结果
     * <p>
     * 5
     * Wifi定位结果
     * 属于网络定位，定位精度相对基站定位会更好，定位精度较高，在5米－200米之间。
     * <p>
     * 6
     * 基站定位结果
     * 纯粹依赖移动、联通、电信等移动网络定位，定位精度在500米-5000米之间。
     * <p>
     * 8
     * 离线定位结果
     * -
     *
     * @param type
     * @return
     */
    private boolean filterLocation(int type) {
        return type == 1 || type == 2 || type == 5;
    }

    public void location() {
        mlocationClient = new AMapLocationClient(App.getInstance());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mLocationOption.setWifiScan(true);
        mLocationOption.setLocationCacheEnable(false);
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        // 启动定位
        mlocationClient.startLocation();
    }

    public void stopLocation() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
        }
    }


    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public interface LocationCompanyListener {
        void onAddress(AMapLocation address);
    }

    private LocationCompanyListener locationCompanyListener;

    public void setLocationCompanyListener(LocationCompanyListener listener) {
        this.locationCompanyListener = listener;
    }

    public void removeLocationCompanyListener() {
        locationCompanyListener = null;
    }
}
