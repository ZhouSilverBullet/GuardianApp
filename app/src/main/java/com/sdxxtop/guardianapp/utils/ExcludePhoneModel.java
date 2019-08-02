package com.sdxxtop.guardianapp.utils;

import android.os.Build;

import com.sdxxtop.skipsettings.SkipSetting;

import java.util.Arrays;

/**
 * @author :  lwb
 * Date: 2019/8/2
 * Desc:
 */
public class ExcludePhoneModel {

    private static String[] excludePhoneModel = {"R9sk"};
    private static String[] deviceBrandList = {"oppo"};
    private static SkipSetting instance;

    public static boolean isExclude(){
        instance = SkipSetting.getInstance();
        String deviceBrand = instance.getDeviceBrand().toLowerCase();
        String product = Build.PRODUCT;
        if (Arrays.asList(deviceBrandList).contains(deviceBrand)){
            if (Arrays.asList(excludePhoneModel).contains(product)){
                return true;
            }
        }
        return false;
    }

}
