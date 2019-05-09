package com.sdxxtop.guardianapp.utils;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/7
 * Desc:
 */
public class MarkerUtil {
    /**
     * by moos on 2018/01/12
     * func:添加位置模拟数据
     *
     * @param centerPoint 中心点
     * @param num         数量
     * @param offset      经纬度模拟的可调偏移参数
     * @return
     */
    public static List<LatLng> addSimulatedData(LatLng centerPoint, int num, double offset) {
        List<LatLng> data = new ArrayList<>();
        if (num > 0) {
            for (int i = 0; i < num; i++) {
                double lat = centerPoint.latitude + (Math.random() - 0.5) * offset;
                double lon = centerPoint.longitude + (Math.random() - 0.5) * offset;
                LatLng latlng = new LatLng(lat, lon);
                data.add(latlng);
            }
        }
        return data;
    }
}
