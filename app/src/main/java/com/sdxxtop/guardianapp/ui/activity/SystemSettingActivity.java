package com.sdxxtop.guardianapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.skipsettings.SkipSetting;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class SystemSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] brandList = {"huawei", "oppo", "xiaomi"};
    private String[] availableBrandList = {"huawei", "oppo", "xiaomi", "smartisan"/*锤子*/, "vivo", "samsung", "lenovo", "letv", "meizu"};

    private SkipSetting instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        initView();
        instance = SkipSetting.getInstance();
    }

    private void initView() {
        findViewById(R.id.tv_open_protect).setOnClickListener(this);
        findViewById(R.id.tv_open_start).setOnClickListener(this);
        findViewById(R.id.tv_look_course).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        String brand = instance.getDeviceBrand().toLowerCase();
        switch (v.getId()) {
            case R.id.tv_open_protect:  // 后台省电保护
                boolean b1 = instance.toPowerSaving(getApplicationContext());
                if (!b1) {
                    if (Arrays.asList(brandList).contains(brand)) {
                        getAppDetailSettingIntent(this);
                    } else {
                        showToast("该机型暂不支持快速设置,请手动设置。");
                    }
                }
                break;
            case R.id.tv_open_start:    //  自启动
                boolean b = instance.toSelfStarting(getApplicationContext());
                if (!b) {
                    if (Arrays.asList(brandList).contains(brand)) {
                        getAppDetailSettingIntent(this);
                    } else {
                        showToast("该机型暂不支持快速设置,请手动设置。");
                    }
                }
                break;
            case R.id.tv_look_course:   //  查看教程
                Intent intent1 = new Intent(this, ImageCourseActivity.class);
                intent1.putExtra("href", brand);
                startActivity(intent1);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * 跳转到权限设置界面
     */
    private void getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(intent);
    }

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
//        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
}
