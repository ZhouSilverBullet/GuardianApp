package com.sdxxtop.guardianapp.ui.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.permissions.RxPermissions;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.InitBean;
import com.sdxxtop.guardianapp.utils.StringUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/8/23.
 */

public class DownloadDialog {
    private String apkUrl;
    private int forceUpdate;
    private String versionCode;
    private RxPermissions rxPermissions;
    private String updateContent;
    private String versionName;
    private Context context;
    private TextView titleTextView;
    private TextView contentTextView;
    private TextView cancel;
    private TextView update;
    private Dialog dialog;

    public DownloadDialog(Context context, InitBean dataEntity, RxPermissions rxPermissions) {
        this.context = context;
        this.rxPermissions = rxPermissions;
        apkUrl = dataEntity.getApk_url();
        forceUpdate = dataEntity.getForce_update();
        versionCode = dataEntity.getVersion_code();
        updateContent = dataEntity.getContent();
        versionName = dataEntity.getVersion_name();
    }

    private String appPath;

    private boolean isShow() {
        try {
            if (getVersionCode() < Integer.parseInt(versionCode)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void show() {
        if (isShow()) {
            File cacheDir = context.getExternalCacheDir() == null ? context.getCacheDir() : context.getExternalCacheDir();
            if (cacheDir != null) {
                if (!cacheDir.exists() && !cacheDir.mkdirs()) {
                    UIUtils.showToast("文件路径获取失败");
                    return;
                }

                appPath = cacheDir.getAbsolutePath() + File.separator + "download_apk";
                File file = new File(appPath);
                if (file != null && !file.exists() && !file.mkdirs()) {
                    UIUtils.showToast("创建文件路径失败");
                    return;
                }
            }

            dialog = new Dialog(context, R.style.dialog_style);
            dialog.setContentView(R.layout.dialog_download);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            titleTextView = (TextView) dialog.findViewById(R.id.dialog_download_title_text);
            titleTextView.setText("发现新版本" + versionName);
            contentTextView = (TextView) dialog.findViewById(R.id.dialog_download_content_text);
            String text = StringUtil.stringNotNull(updateContent);
            if (text.length() > 0) {
                String[] split = text.split("\\|");
                String value = "";
                for (int i = 0; i < split.length; i++) {
                    if (i > 0) {
                        value = value + "\n" + split[i];
                    } else {
                        value = value + split[i];
                    }
                }
                contentTextView.setText(value);
            } else {
                contentTextView.setText(text);
            }
            cancel = (TextView) dialog.findViewById(R.id.dialog_download_cancel);
            update = (TextView) dialog.findViewById(R.id.dialog_download_update);
            if (forceUpdate == 1) { //强制更新
                cancel.setVisibility(View.GONE);
            } else {
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rxPermissions == null) {
                        return;
                    }

                    if (!isUrl(apkUrl)) {
                        UIUtils.showToast("apk的下载地址不正确，请稍后重试");
                        return;
                    }

                    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                showProgressDownload();
                            } else {
                                UIUtils.showToast("请给予网格员读写权限");
                            }
                        }
                    });
                }
            });
        }
    }

    private void showProgressDownload() {
        DownloadAppDialog downloadAppDialog = new DownloadAppDialog(context, apkUrl, appPath, dialog);
        downloadAppDialog.show();
    }

    private void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private int getVersionCode() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            return 10000;
        }
    }

    public boolean isUrl(String str) {
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return match(regex, str);
    }

    private boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
