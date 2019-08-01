package com.sdxxtop.skipsettings.impl;

import androidx.core.util.Pair;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-22 15:31
 * Version: 1.0
 * Description:
 */
public class VivoSkipSetting extends BaseSkipSetting {

    @Override
    void childAddSelfStarting() {
        mSelfStartingList.add(Pair.create("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"));
        mSelfStartingList.add(Pair.create("com.iqoo.secure", ""));
        mSelfStartingList.add(Pair.create("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity"));
    }

    @Override
    void childAddPowerSaving() {
        mSelfPowerSaving.add(Pair.create("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity"));
        mSelfPowerSaving.add(Pair.create("com.iqoo.secure", ""));
    }
}
