package com.sdxxtop.skipsettings;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.sdxxtop.skipsettings.impl.BaseSkipSetting;
import com.sdxxtop.skipsettings.impl.HuaweiSkipSetting;
import com.sdxxtop.skipsettings.impl.MeizuSkipSetting;
import com.sdxxtop.skipsettings.impl.OppoSkipSetting;
import com.sdxxtop.skipsettings.impl.SamsungSkipSetting;
import com.sdxxtop.skipsettings.impl.UlongSkipSetting;
import com.sdxxtop.skipsettings.impl.VivoSkipSetting;
import com.sdxxtop.skipsettings.impl.XiaomiSkipSetting;

import java.util.HashMap;
import java.util.Map;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-22 15:51
 * Version: 1.0
 * Description:
 */
public class SkipSetting {
    private static final String TAG = "SkipSetting";
    private static final Map<String, BaseSkipSetting> MAP = new HashMap<>();

    private SkipSetting() {
        init();
    }

    private void init() {
        MAP.put("xiaomi", new XiaomiSkipSetting());
        MAP.put("oppo", new OppoSkipSetting());
        MAP.put("vivo", new VivoSkipSetting());
        MAP.put("huawei", new HuaweiSkipSetting());
        MAP.put("samsung", new SamsungSkipSetting());
        MAP.put("meizu", new MeizuSkipSetting());
        MAP.put("ulong", new UlongSkipSetting());

    }

    public static SkipSetting getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private static class SingletonHolder {
        private static final SkipSetting INSTANCE = new SkipSetting();
    }


    /**
     * 跳转自启动
     */
    public boolean toSelfStarting(Context context) {
        String key = getBrand().toLowerCase();
        BaseSkipSetting baseSkipSetting = MAP.get(key);
        if (baseSkipSetting != null) {
            baseSkipSetting.setContext(context);
            return baseSkipSetting.skipSelfStarting();
        }
        return false;
    }

    /**
     * 跳转电量
     */
    public boolean toPowerSaving(Context context) {
        String key = getBrand().toLowerCase();
        BaseSkipSetting baseSkipSetting = MAP.get(key);
        if (baseSkipSetting != null) {
            baseSkipSetting.setContext(context);
            return baseSkipSetting.skipPowerSaving();
        }
        return false;
    }


    public void printBrand(Context context) {
        String brand = getBrand();


        String product = Build.PRODUCT;
        Log.i(TAG, "bind: " + brand + "---" + product);
    }

    public String getDeviceBrand(){
        return getBrand();
    }

    private String getBrand() {
        String brand = Build.BRAND;
        if (TextUtils.isEmpty(brand)) {
            return "";
        }
        return brand;
    }
}
