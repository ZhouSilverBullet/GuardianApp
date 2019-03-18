package com.sdxxtop.guardianapp.model.http.net;

import android.content.Context;
import android.text.TextUtils;

import com.sdxxtop.guardianapp.model.http.util.NetUtil;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.StringUtil;
import com.sdxxtop.guardianapp.app.AppSession;
import com.sdxxtop.guardianapp.model.http.util.DeviceUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/5/7.
 */

public class Params {
    protected Context context;
    protected HashMap<String, String> map;

    public Params() {
        map = new HashMap<>();
        context = AppSession.getInstance().getContext();
        defaultValue();
    }

    private void defaultValue() {
//        map.put("ci", SpUtil.getString(HttpConstantValue.COMPANY_ID));
//        map.put("ui", SpUtil.getString(HttpConstantValue.USER_ID));
    }

    public String getUserId() {
        String ui = map.get("ui");
        if (TextUtils.isEmpty(ui)) {
            ui = SpUtil.getString(HttpConstantValue.USER_ID);
        }
        return ui;
    }

    public String getCompanyId() {
        String ci = map.get("ci");
        if (TextUtils.isEmpty(ci)) {
            ci = SpUtil.getString(HttpConstantValue.COMPANY_ID);
        }
        return ci;
    }

    public void removeKey(String key) {
        if (map.containsKey(key)) {
            map.remove(key);
        }
    }

    public void put(String key, String value) {
        map.put(key, StringUtil.stringNotNull(value));
    }

    public void put(String key, long value) {
        map.put(key, value + "");
    }

    public void put(String key, int value) {
        map.put(key, value + "");
    }

    public String getData() {
        return NetUtil.getBase64Data(map);
    }

    public void putDeviceNo() {
        map.put("dn", DeviceUtil.getDeviceNo(context));
    }
}
