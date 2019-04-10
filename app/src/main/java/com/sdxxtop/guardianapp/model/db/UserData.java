package com.sdxxtop.guardianapp.model.db;

import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.utils.SpUtil;

/**
 * todo
 */
public class UserData {
    private static UserData sUserSave;

    public static UserData getInstance() {
        return SingleHolder.sUserSave;
    }


    public static class SingleHolder {
        private static UserData sUserSave = new UserData();
    }

    private UserData() {
    }

    public void saveAuto() {
//        SpUtil.putString(Constants.AUTO_TOKEN, autoToken);
//        SpUtil.putInt(Constants.EXPIRE_TIME, expireTime);
//        SpUtil.putInt(Constants.PART_ID, partId);
//        SpUtil.putInt(Constants.USER_ID, userid);
//        SpUtil.putString(Constants.MOBILE, mobile);
    }

    private void saveInfo() {

    }

    public void logout() {
        SpUtil.putString(Constants.AUTO_TOKEN, "");
        SpUtil.putInt(Constants.EXPIRE_TIME, 0);
    }
}
