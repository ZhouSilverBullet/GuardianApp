package com.sdxxtop.network.helper;

import com.sdxxtop.network.helper.base.BaseParams;
import com.sdxxtop.network.utils.SpUtil;

/**
 * Created by Administrator on 2018/5/7.
 */

public class DefaultParams extends BaseParams {

    @Override
    public void defaultValue() {
        put("ui", SpUtil.getInt(HttpConstantValue.USER_ID, 0));
        put("pti", SpUtil.getInt(HttpConstantValue.PART_ID, 0));
        put("plid", "1");
    }

}
