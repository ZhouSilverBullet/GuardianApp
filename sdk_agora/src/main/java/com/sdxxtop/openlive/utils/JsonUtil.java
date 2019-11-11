package com.sdxxtop.openlive.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-09 16:22
 * Version: 1.0
 * Description:
 */
public class JsonUtil {
    public static String getClient(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            return jsonObject.optString("client");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
