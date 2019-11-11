package com.sdxxtop.guardianapp.model.db;

import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.model.http.util.SpUtil;

/**
 * todo
 */
public class UserData implements IUserData {

    public static UserData getInstance() {
        return SingleHolder.sUserSave;
    }


    public static class SingleHolder {
        private static UserData sUserSave = new UserData();
    }

    private UserData() {
    }

    @Override
    public void saveInfo(String autoToken, int expireTime, int partId,
                         int userid, String mobile) {
        SpUtil.putString(Constants.AUTO_TOKEN, autoToken);
        SpUtil.putInt(Constants.EXPIRE_TIME, expireTime);
        SpUtil.putInt(Constants.PART_ID, partId);
        SpUtil.putInt(Constants.USER_ID, userid);
        SpUtil.putString(Constants.MOBILE, mobile);
    }

    @Override
    public void removeAutoLoginInfo() {
        SpUtil.putString(Constants.AUTO_TOKEN, "");
        SpUtil.putInt(Constants.EXPIRE_TIME, 0);
    }

    public void logout() {
        removeAutoLoginInfo();
    }
}
