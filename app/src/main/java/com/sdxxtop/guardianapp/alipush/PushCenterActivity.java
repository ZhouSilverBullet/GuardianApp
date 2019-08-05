package com.sdxxtop.guardianapp.alipush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.sdxxtop.guardianapp.ui.activity.CenterMessage2Activity;
import com.sdxxtop.guardianapp.ui.activity.CenterMessageActivity;
import com.sdxxtop.guardianapp.ui.activity.DeviceWarnDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolAddDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Administrator on 2018/6/15.
 * 处理多个
 */

public class PushCenterActivity extends AppCompatActivity {

    private String extraMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
    }

    protected void initVariables() {
        if (getIntent() != null) {
            extraMap = getIntent().getStringExtra("extraMap");
            if (!TextUtils.isEmpty(extraMap)) {
                startToAc(extraMap);
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            extraMap = intent.getStringExtra("extraMap");
            if (!TextUtils.isEmpty(extraMap)) {
                startToAc(extraMap);
            } else {
                finish();
            }
        }
    }

    private void startToAc(String extraMap) {
        try {
            JSONObject jsonObject = new JSONObject(extraMap);
            if (!jsonObject.has("type")) {
                finish();
                return;
            }
            int type = jsonObject.optInt("type");
            Intent intent;
            switch (type) {
                case 1:  // 事件
                    if (!jsonObject.has("event_id")) {
                        finish();
                        return;
                    }
                    intent = new Intent(this, EventReportDetailActivity.class);
                    intent.putExtra("eventId", String.valueOf(jsonObject.optInt("event_id")));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 2:  // 自行处理
                    if (!jsonObject.has("patrol_id")) {
                        finish();
                        return;
                    }
                    intent = new Intent(this, PatrolAddDetailActivity.class);
                    intent.putExtra("patrol_id", jsonObject.optInt("patrol_id"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                case 3:  // 消息中心
                    intent = new Intent(this, CenterMessageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 5:  //跳转推送的预警详情
                    if (!jsonObject.has("early_id")) {
                        finish();
                        return;
                    }
                    intent = new Intent(this, DeviceWarnDetailActivity.class);
                    intent.putExtra("early_id", jsonObject.optInt("early_id"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 6:  //跳转推送的预警详情
                    intent = new Intent(this, CenterMessage2Activity.class);
                    intent.putExtra("name", "认领事件提醒");
                    intent.putExtra("type", 6);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
            }

            finish();
        } catch (JSONException e) {
            Log.e("MyMessageReceiver", e.getMessage());
            e.printStackTrace();
            finish();
        }
    }

    public static void startActivityToReceiver(final Context context, String extraMap) {
        Intent intent = new Intent();
        intent.setClassName("com.sdxxtop.guardianapp", "com.sdxxtop.guardianapp.alipush.PushCenterActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("extraMap", extraMap);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
