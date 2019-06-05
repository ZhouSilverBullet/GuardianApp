package com.sdxxtop.guardianapp.utils;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;

import com.sdxxtop.guardianapp.R;
import com.vincent.videocompressor.VideoCompress;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author :  lwb
 * Date: 2019/6/5
 * Desc:
 */
public class VideoCompressUtil {
    private Context mContext;
    public Dialog dialog;
    public String pathResult;

    public VideoCompressUtil(Context context) {
        this.mContext = context;
        initDialog();
    }

    private String outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

    public String videoCompress(String path) {
        String destPath = outputDir + File.separator + "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss", getLocale()).format(new Date()) + ".mp4";
        pathResult = destPath;
        VideoCompress.compressVideoLow(path, destPath, new VideoCompress.CompressListener() {
            @Override
            public void onStart() {
                if (dialog != null) {
                    dialog.show();
                }
            }

            @Override
            public void onSuccess() {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (mListener!=null){
                    mListener.success(pathResult);
                }
            }

            @Override
            public void onFail() {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (mListener!=null){
                    mListener.fail();
                }
            }

            @Override
            public void onProgress(float percent) {
            }
        });
        return destPath;
    }

    private Locale getLocale() {
        Configuration config = mContext.getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }

        return sysLocale;
    }

    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }


    public void initDialog() {
        // 获取Dialog布局
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.view_pb_compress, null);

        // 定义Dialog布局和参数
        dialog = new Dialog(mContext, R.style.loading_dialog);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    private OnVideoCompress mListener;
    public interface OnVideoCompress{
        void success(String path);
        void fail();
    }
    public void setOnVideoCompress(OnVideoCompress listener){
        this.mListener = listener;
    }

}
