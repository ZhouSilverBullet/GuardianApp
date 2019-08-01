package com.sdxxtop.skipsettings.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.core.util.Pair;

import com.sdxxtop.skipsettings.ISkipSetting;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-22 15:30
 * Version: 1.0
 * Description:
 */
public abstract class BaseSkipSetting implements ISkipSetting {

    protected List<Pair<String, String>> mSelfStartingList = new ArrayList<>();
    protected List<Pair<String, String>> mSelfPowerSaving = new ArrayList<>();

    protected WeakReference<Context> mContextRef;

    public void setContext(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    public BaseSkipSetting() {
        childAddSelfStarting();
        childAddPowerSaving();
    }

    @Override
    public boolean skipSelfStarting() {

        return skip(mSelfStartingList);

    }

    @Override
    public boolean skipPowerSaving() {

        return skip(mSelfPowerSaving);

    }

    private boolean skip(List<Pair<String, String>> pairList) {
        for (Pair<String, String> stringStringPair : pairList) {
            try {
                Intent intent = new Intent();
                intent.setClassName(stringStringPair.first, stringStringPair.second);

                if (!(getContext() instanceof Activity)) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                getContext().startActivity(intent);
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }

    public Context getContext() {
        return mContextRef.get();
    }

    abstract void childAddSelfStarting();

    abstract void childAddPowerSaving();
}
