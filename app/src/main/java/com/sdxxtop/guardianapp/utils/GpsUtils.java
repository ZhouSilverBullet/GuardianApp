package com.sdxxtop.guardianapp.utils;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.view.View;

import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-17 16:49
 * Version: 1.0
 * Description:
 */
public class GpsUtils {
    public static boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
//        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps) {
            return true;
        }

        return false;
    }

    /**
     * 开启gps的对话框
     *
     * @param context
     */
    public static void showCode332ErrorDialog(Context context) {
        new IosAlertDialog(context)
                .builder()
                .setTitle("提示")
                .setMsg("GPS定位失败，请开启手机的“GPS定位”重试打卡")
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        skipGpsSystem(v.getContext());
                    }
                })
                .show();
    }

    private static void skipGpsSystem(Context context) {
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }
}
