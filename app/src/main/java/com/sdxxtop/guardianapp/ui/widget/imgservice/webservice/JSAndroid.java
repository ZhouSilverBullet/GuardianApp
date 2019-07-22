package com.sdxxtop.guardianapp.ui.widget.imgservice.webservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.webkit.JavascriptInterface;

import com.sdxxtop.guardianapp.ui.widget.imgservice.ServiceWebActivity;


/**
 * @author :  lwb
 * Date: 2019/4/16
 * Desc:
 */
public class JSAndroid extends Object {
    private Context context;

    public JSAndroid(Context context) {
        this.context = context;
    }

    private ConfigerManagner configerManagner;

    @JavascriptInterface
    public void openAndroid(String msg) {
        ServiceWebActivity activity = (ServiceWebActivity) context;
        activity.finish();
    }

    @JavascriptInterface
    public void writeData(String msg) {
        configerManagner = ConfigerManagner.getInstance(context);
        configerManagner.setString("js", msg);
    }

    @JavascriptInterface
    public String giveInformation(String msg) {
        configerManagner = ConfigerManagner.getInstance(context);
        String msg1 = configerManagner.getString("js");
        return msg1;
    }

    public static class ConfigerManagner {
        private static ConfigerManagner configManger;
        private static Context context;

        ConfigerManagner(Context context) {
            this.context = context.getApplicationContext();
        }

        public static ConfigerManagner getInstance(Context context) {
            if (configManger == null) {
                configManger = new ConfigerManagner(context);
            }
            return configManger;
        }

        protected SharedPreferences getMySharedPreferences() {
            return PreferenceManager.getDefaultSharedPreferences(context);
        }

        public boolean setString(String name, String value) {
            return getMySharedPreferences().edit().putString(name, value).commit();
        }

        public String getString(String name) {
            return getMySharedPreferences().getString(name, "");
        }
    }
}
