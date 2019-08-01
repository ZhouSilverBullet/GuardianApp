package com.sdxxtop.skipsettings.impl;

import androidx.core.util.Pair;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-22 15:31
 * Version: 1.0
 * Description:
 */
public class OppoSkipSetting extends BaseSkipSetting {
    public OppoSkipSetting() {


    }

    @Override
    void childAddSelfStarting() {

        //装载好可以进行跳转的数据
        mSelfStartingList.add(Pair.create("com.color.safecenter", "com.color.safecenter.permission.startup.StartupAppListActivity"));
        mSelfStartingList.add(Pair.create("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
        mSelfStartingList.add(Pair.create("com.coloros.safecenter", "com.coloros.safecenter.permission.startupapp.StartupAppListActivity"));
        mSelfStartingList.add(Pair.create("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity"));
        mSelfStartingList.add(Pair.create("com.coloros.safecenter", "com.coloros.privacypermissionsentry.PermissionTopActivity"));
        mSelfStartingList.add(Pair.create("com.coloros.safecenter", "com.coloros.safecenter.newrequest.SecurityScanActivity"));
    }

    @Override
    void childAddPowerSaving() {
        mSelfPowerSaving.add(Pair.create("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerConsumptionActivity"));
        mSelfPowerSaving.add(Pair.create("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"));
        mSelfPowerSaving.add(Pair.create("com.coloros.oppoguardelf", "com.coloros.oppoguardelf.MonitoredPkgActivity"));
    }


}
