package com.sdxxtop.skipsettings.impl;

import androidx.core.util.Pair;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-22 15:31
 * Version: 1.0
 * Description:
 */
public class UlongSkipSetting extends BaseSkipSetting {

    @Override
    void childAddSelfStarting() {
        mSelfStartingList.add(Pair.create("com.yulong.android.coolsafe", ".ui.activity.autorun.AutoRunListActivity"));
    }

    @Override
    void childAddPowerSaving() {

    }
}
