package com.sdxxtop.guardianapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.sdxxtop.guardianapp.app.App;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @Author: zhousaito
 * @Date: 2019-04-24 14:42
 * @Version 1.0
 * @UserWhat what
 */
public class SkipMapUtils {
    public void invokingBD(Context context, String position) {

        //  com.baidu.BaiduMap这是高德地图的包名
        //调起百度地图客户端try {
        Intent intent = null;
        try {
            String uri = "intent://map/direction?origin=latlng:0,0|name:我的位置&destination=" + "需要导航的地址" + "&mode=drivingion=" + "城市" + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";

            intent = Intent.getIntent(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (isInstallByread("com.baidu.BaiduMap")) {
            context.startActivity(intent); //启动调用
            Log.e("GasStation", "百度地图客户端已经安装");
        } else {
            Toast.makeText(App.getContext(), "没有安装百度地图客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转百度地图
     */
    public static void goToBaiduMap(Context context, String address, String lat, String lon) {
        if (!isInstallByread("com.baidu.BaiduMap")) {
            UIUtils.showToast("请先安装百度地图客户端");
            return;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=latlng:"
                + lat + ","
                + lon + "|name:" + address + // 终点
                "&mode=driving" + // 导航路线方式
                "&src=" + App.getContext().getPackageName()));
        context.startActivity(intent); // 启动调用
    }


    public static void invokingGD(Context context, String address, String lat, String lon) {

        //  com.autonavi.minimap这是高德地图的包名
        Intent intent = new Intent("android.intent.action.VIEW"/*,android.net.Uri.parse("androidamap://navi?sourceApplication=应用名称&lat="+ "&dev=0")*/);
        intent.setPackage("com.autonavi.minimap");
//        intent.setData(Uri.parse("androidamap://poi?sourceApplication=softname&keywords="+ 需要导航的地址));
        intent.setData(Uri.parse("amapuri://poi/detail?poiname=" + address + "&lat=" + lat + "&lon=" + lon));

        if (isInstallByread("com.autonavi.minimap")) {
            context.startActivity(intent);
            Log.e("GasStation", "高德地图客户端已经安装");
        } else {
            Toast.makeText(App.getContext(), "没有安装高德地图客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转高德地图
     */
    public static void goToGaodeMap(Context context, String address, String lat, String lon) {
        if (!isInstallByread("com.autonavi.minimap")) {
            UIUtils.showToast("请先安装高德地图客户端");
            return;
        }
        LatLng endPoint = GCJ2BD(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));//坐标转换
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=").append("amap");
        stringBuffer.append("&lat=").append(endPoint.latitude)
                .append("&lon=").append(endPoint.longitude).append("&keywords=" + address)
                .append("&dev=").append(0)
                .append("&style=").append(2);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }
//
//---------------------
//    作者：Ever69
//    来源：CSDN
//    原文：https://blog.csdn.net/ever69/article/details/82427085
//    版权声明：本文为博主原创文章，转载请附上博文链接！

    /**
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


//    /**
//     * BD-09 坐标转换成 GCJ-02 坐标
//     */
//    public static LatLng BD2GCJ(LatLng bd) {
//        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
//        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
//        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
//
//        double lng = z * Math.cos(theta);//lng
//        double lat = z * Math.sin(theta);//lat
//        return new LatLng(lat, lng);
//    }

    /**
     * GCJ-02 坐标转换成 BD-09 坐标
     */
    public static LatLng GCJ2BD(LatLng bd) {
        double x = bd.longitude, y = bd.latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new LatLng(tempLat, tempLon);
    }
}
