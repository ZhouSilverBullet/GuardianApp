package com.sdxxtop.guardianapp.model.http.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.sdxxtop.guardianapp.model.NetWorkSession;

import java.io.File;


public class SystemUtil {
    public static boolean isNetworkConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NetWorkSession.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo() != null;
        }
        return false;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


//    public static void installApk(Context context, String filePath) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri localUri;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            localUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(filePath));
//            intent.setDataAndType(localUri, "application/vnd.android.package-archive");
//        } else {
//            intent.addCategory("android.intent.category.DEFAULT");
//            localUri = Uri.fromFile(new File(filePath));
//            intent.setDataAndType(localUri, "application/vnd.android.package-archive");
//        }
//        context.startActivity(intent);
//    }

    public static  void installApk(Context context,String downloadApk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(downloadApk);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName()+".provider", file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}
