package com.sdxxtop.guardianapp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.utils.SystemUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/8/23.
 */

public class DownloadAppDialog implements DialogInterface.OnKeyListener {
    private String apkPath;
    private Context context;
    private Dialog parentDialog;
    private Dialog downloadDialog;
    private String apkUrl;
    private ProgressBar progressBar;
    private Handler handler = new Handler(Looper.getMainLooper());
    private TextView progressValue;

    public DownloadAppDialog(Context context, String apkUrl, String appPath, Dialog dialog) {
        this.context = context;
        this.apkUrl = apkUrl;
        this.apkPath = appPath + File.separator + "guardianApp.apk";
        this.parentDialog = dialog;
    }

    public void show() {
        downloadDialog = new Dialog(context);
        downloadDialog.setContentView(R.layout.dialog_progress_download);
        downloadDialog.setCancelable(true);
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.show();
        if (parentDialog != null) {
            parentDialog.hide();
        }

        downloadDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (parentDialog != null) {
                    parentDialog.show();
                }
            }
        });

        progressBar = (ProgressBar) downloadDialog.findViewById(R.id.progress_dialog_progress);
        LinearLayout deleteLayout = (LinearLayout) downloadDialog.findViewById(R.id.progress_dialog_delete_layout);
        progressValue = (TextView) downloadDialog.findViewById(R.id.progress_dialog_progress_value);
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCloseDialog();
            }
        });
        toDownload();
        downloadDialog.setOnKeyListener(this);
    }

    private void toDownload() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(apkUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showToast("下载失败，请稍后重试");
                dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body == null) {
                    showToast("下载失败，请稍后重试");
                    dismiss();
                    return;
                }
                long contentLength = body.contentLength();
                if (contentLength <= 0) {
                    showToast("获取下载内容失败，请稍后重试");
                    dismiss();
                    return;
                }

                //判断文件是否存在
                if (checkFile(contentLength)) {
                    return;
                }

                InputStream is = null;
                FileOutputStream fos = null;

                try {
                    is = body.byteStream();
                    fos = new FileOutputStream(apkPath);
                    int len = -1;
                    byte[] bs = new byte[1024 * 8];
                    long totalProgress = 0;
                    while ((len = is.read(bs)) != -1) {
                        fos.write(bs, 0, len);
                        fos.flush();
                        totalProgress = totalProgress + len;
                        int progress = (int) (totalProgress * 100 / contentLength + 0.5);
                        Log.e("DownloadAppDialog", "progress = " + progress + "");
                        toProgress(progress);
                    }
                    toProgress(100);
                    checkFile(contentLength);
                } catch (Exception e) {
                    showToast("下载失败，请稍后重试");
                    checkFile(contentLength);
                    dismiss();
                } finally {
                    closeStream(fos);
                    closeStream(is);
                }
            }
        });
    }

    private void showToast(final String value) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                UIUtils.showToast(value);
            }
        });
    }

    private void toProgress(int progress) {
        if (progress > 100) {
            progress = 100;
        }
        if (progress < 0) {
            progress = 0;
        }
        if (progressBar != null) {
            progressBar.setProgress(progress);
            setProgressTextValue(progress + "%");

        }
    }

    private void setProgressTextValue(final String progress) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                progressValue.setText(progress);
            }
        });
    }

    private boolean checkFile(long contentLength) {
        File file = new File(apkPath);
        if (file.exists()) {
            if (file.length() == contentLength) {
                setProgressTextValue(100 + "%");
                progressBar.setProgress(100);
                SystemUtil.installApk(context, apkPath);
                dismiss();
                return true;
            } else {
                if (!file.delete()) {
                    UIUtils.showToast("删除文件失败");
                    dismiss();
                    return true;
                }
            }
        }
        return false;
    }

    private void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dismiss() {
        if (downloadDialog != null && downloadDialog.isShowing()) {
            downloadDialog.dismiss();
        }
    }

    private void deleteApkFile() {
        File file = new File(apkPath);
        if (file.exists()) {
            if (!file.delete()) {
            }
        }
    }

    private boolean isShow; //按返回键会出现两次，所以加个控制参数

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && dialog == downloadDialog && !isShow) {
            showCloseDialog();
            isShow = true;
        }
        return false;
    }

    private void showCloseDialog() {
        new IosAlertDialog(context).builder()
                .setTitle("提示")
                .setMsg("是否停止下载更新包")
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (downloadDialog != null) {
                            deleteApkFile();
                            downloadDialog.dismiss();
                        }
                    }
                }).setNegativeButton("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).setDismissListener(new IosAlertDialog.DialogDismissListener() {
            @Override
            public void dismiss() {
                isShow = false;
            }
        }).show();
    }

}
