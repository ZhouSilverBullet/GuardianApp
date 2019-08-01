package com.sdxxtop.skipsettings.impl;

import androidx.core.util.Pair;

/**
 * @author :  lwb
 * Date: 2019/7/23
 * Desc:
 */
public class SamsungSkipSetting extends BaseSkipSetting {
    @Override
    void childAddSelfStarting() {
        //装载好可以进行跳转的数据
        mSelfStartingList.add(Pair.create("com.samsung.android.sm", "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity"));
        mSelfStartingList.add(Pair.create("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.ram.AutoRunActivity"));
        mSelfStartingList.add(Pair.create("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.appmanagement.AppManagementActivity"));
        mSelfStartingList.add(Pair.create("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity"));
    }

    @Override
    void childAddPowerSaving() {
        mSelfPowerSaving.add(Pair.create("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.battery.AppSleepListActivity_dim"));
        mSelfPowerSaving.add(Pair.create("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.battery.BatteryActivity"));
        mSelfPowerSaving.add(Pair.create("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.AppSleepListActivity"));
        mSelfPowerSaving.add(Pair.create("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.BatteryActivity"));
    }
}
