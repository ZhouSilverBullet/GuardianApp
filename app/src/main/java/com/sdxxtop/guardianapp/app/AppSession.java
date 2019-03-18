package com.sdxxtop.guardianapp.app;

import android.content.Context;

import java.lang.ref.WeakReference;

public class AppSession {
    private WeakReference<Context> mContextDef;

    private AppSession() {
    }

    public static AppSession getInstance() {
        return SingleHolder.APP_SESSION;
    }

    public interface SingleHolder {
        AppSession APP_SESSION = new AppSession();
    }

    public void init(Context context) {
        mContextDef = new WeakReference<>(context);
    }

    public Context getContext() {
        Context context = mContextDef.get();
        if (context == null) {
            return App.getContext();
        }
        return context;
    }

    public void clear() {
        if (mContextDef != null) {
            mContextDef.clear();
            mContextDef = null;
        }
    }

}
