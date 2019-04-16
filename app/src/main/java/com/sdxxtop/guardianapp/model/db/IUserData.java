package com.sdxxtop.guardianapp.model.db;

/**
 * @Author: zhousaito
 * @Date: 2019/4/16 18:10
 * @Version 1.0
 *
 * 应该用数据库来进行存储的，到时应该会进行这样修改
 *
 */
public interface IUserData {
    /**
     * 保存登录信息
     *
     * @param autoToken
     * @param expireTime
     * @param partId
     * @param userid
     * @param mobile
     */
    void saveInfo(String autoToken, int expireTime, int partId,
                  int userid, String mobile);

    /**
     * 删除自动登录信息
     */
    void removeAutoLoginInfo();
}
