package com.sdxxtop.skipsettings.impl;

import androidx.core.util.Pair;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-22 15:31
 * Version: 1.0
 * Description:
 */
public class MeizuSkipSetting extends BaseSkipSetting {

    @Override
    void childAddSelfStarting() {
        mSelfStartingList.add(Pair.create("com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity"));
        mSelfStartingList.add(Pair.create("com.meizu.safe", "com.meizu.safe.permission.PermissionMainActivity"));
        mSelfStartingList.add(Pair.create("com.meizu.safe", "com.meizu.safe.security.HomeActivity"));
    }

    @Override
    void childAddPowerSaving() {
        mSelfPowerSaving.add(Pair.create("com.meizu.safe", "com.meizu.safe.powerui.PowerAppPermissionActivity"));
        mSelfPowerSaving.add(Pair.create("com.meizu.safe", ""));
    }
}
