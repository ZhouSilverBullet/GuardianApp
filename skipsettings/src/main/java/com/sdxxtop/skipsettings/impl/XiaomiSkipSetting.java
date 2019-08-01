package com.sdxxtop.skipsettings.impl;

import androidx.core.util.Pair;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-22 15:31
 * Version: 1.0
 * Description:
 */
public class XiaomiSkipSetting extends BaseSkipSetting {

    @Override
    void childAddSelfStarting() {
        mSelfStartingList.add(Pair.create("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"));
    }

    @Override
    void childAddPowerSaving() {
        mSelfPowerSaving.add(Pair.create("com.miui.powerkeeper",
                "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity"));
    }
}
