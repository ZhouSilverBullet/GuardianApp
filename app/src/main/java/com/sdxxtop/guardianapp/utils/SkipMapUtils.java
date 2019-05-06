package com.sdxxtop.guardianapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.orhanobut.logger.Logger;
import com.sdxxtop.guardianapp.app.App;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @Author: zhousaito
 * @Date: 2019-04-24 14:42
 * @Version 1.0
 * @UserWhat what
 *
 * 两个官方文档
 *
 * https://lbs.amap.com/api/amap-mobile/guide/android/marker
 *
 * http://lbsyun.baidu.com/index.php?title=uri/api/android
 *
 */
public class SkipMapUtils {

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

    /**
     * 跳转百度地图
     *
     * baidumap://map/direction
     *
     * 一定要加入 coord_type=gcj02 定位才是正确的
     */
    public static void goToBaiduMap2(Context context, String address, String lat, String lon) {
        if (!isInstallByread("com.baidu.BaiduMap")) {
            UIUtils.showToast("请先安装百度地图客户端");
            return;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=latlng:"
                + lat + ","
                + lon + "|name:" + address + // 终点
                "&mode=driving" + // 导航路线方式
                "&coord_type=gcj02"+
                "&src=" + App.getContext().getPackageName()));
        context.startActivity(intent); // 启动调用
    }

 /**
     * 跳转百度地图
     *
     * baidumap://map/direction
     *
     * 一定要加入 coord_type=gcj02 定位才是正确的
  *
  * i1.setData(Uri.parse("baidumap://map/marker?location=40.057406655722,116.2964407172&title=Marker&content=makeamarker&traffic=on&src=andr.baidu.openAPIdemo"));
  *
  * startActivity(i1);
     */
    public static void goToBaiduMap3(Context context, String address, String lat, String lon) {
        if (!isInstallByread("com.baidu.BaiduMap")) {
            UIUtils.showToast("请先安装百度地图客户端");
            return;
        }
        Intent intent = new Intent();
        String uriString = "baidumap://map/marker?location="
                + lat + ","
                + lon + "&title=" + address + // 终点
                "&traffic=on" +
                "&coord_type=gcj02" +
                "&src=" + App.getContext().getPackageName();

        intent.setData(Uri.parse(uriString));
        Logger.e(uriString);
        context.startActivity(intent); // 启动调用
    }

    /**
     * 跳转高德地图
     */
    public static void goToGaodeMap(Context context, String address, String lat, String lon) {
        if (!isInstallByread("com.autonavi.minimap")) {
            UIUtils.showToast("请先安装高德地图客户端");
            return;
        }
//        LatLng endPoint = GCJ2BD(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));//坐标转换
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=").append("amap");
        stringBuffer.append("&lat=").append(Double.parseDouble(lat))
                .append("&lon=").append(Double.parseDouble(lon)).append("&keywords=" + address)
                .append("&dev=").append(0)
                .append("&style=").append(2);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 跳转高德地图
     * 规划
     * act=android.intent.action.VIEW
     * cat=android.intent.category.DEFAULT
     * dat=amapuri://route/plan/?sid=BGVIS1&slat=39.92848272&slon=116.39560823&sname=A&did=BGVIS2&dlat=39.98848272&dlon=116.47560823&dname=B&dev=0&t=0
     * pkg=com.autonavi.minimap
     *
     * amapuri://route/plan/?dlat=39.98848272&dlon=116.47560823&dev=0&t=0
     */
    public static void goToGaodeMap2(Context context, String address, String lat, String lon) {
        if (!isInstallByread("com.autonavi.minimap")) {
            UIUtils.showToast("请先安装高德地图客户端");
            return;
        }
//        LatLng endPoint = GCJ2BD(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));//坐标转换
        StringBuffer stringBuffer = new StringBuffer("amapuri://route/plan/?sourceApplication=").append("amap");
        stringBuffer.append("&dlat=").append(Double.parseDouble(lat))
                .append("&dlon=").append(Double.parseDouble(lon))
                .append("&dname=").append(address)
                .append("&dev=0&t=0");
        Logger.e(stringBuffer.toString());
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 跳转高德地图
     * 标注
     * act=android.intent.action.VIEW
     * cat=android.intent.category.DEFAULT
     *
     * androidamap://viewMap?sourceApplication=appname&poiname=abc&lat=36.2&lon=116.1&dev=0
     */
    public static void goToGaodeMap3(Context context, String address, String lat, String lon) {
        if (!isInstallByread("com.autonavi.minimap")) {
            UIUtils.showToast("请先安装高德地图客户端");
            return;
        }
//        LatLng endPoint = GCJ2BD(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));//坐标转换
        StringBuffer stringBuffer = new StringBuffer("androidamap://viewMap?sourceApplication=").append("amap");
        stringBuffer.append("&lat=").append(Double.parseDouble(lat))
                .append("&lon=").append(Double.parseDouble(lon))
                .append("&poiname=").append(address)
                .append("&dev=0");
        Logger.e(stringBuffer.toString());
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
