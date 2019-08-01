package com.sdxxtop.skipsettings.impl;

import androidx.core.util.Pair;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-22 15:31
 * Version: 1.0
 * Description:
 */
public class HuaweiSkipSetting extends BaseSkipSetting {

    @Override
    void childAddSelfStarting() {
        mSelfStartingList.add(Pair.create("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"));
        mSelfStartingList.add(Pair.create("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.bootstart.BootStartActivity"));
        mSelfStartingList.add(Pair.create("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupAwakedAppListActivity"));
        mSelfStartingList.add(Pair.create("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity") );
    }

    @Override
    void childAddPowerSaving() {
        mSelfPowerSaving.add(Pair.create("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.HwPowerManagerActivity"));
    }
}
