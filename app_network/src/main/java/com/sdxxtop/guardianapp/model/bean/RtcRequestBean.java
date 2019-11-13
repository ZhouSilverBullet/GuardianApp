package com.sdxxtop.guardianapp.model.bean;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-11 18:52
 * Version: 1.0
 * Description:
 */
public class RtcRequestBean {

    /**
     * token_uid : 006b69c204049cf435a92b493991926d69fIADzUMAcW1PK/LhhdaXaKGcu+RriWYfA2pVIZJ92GprCqbdIfRBZRrEiIgBPTQAA0o7KXQQAAQBiS8ldAwBiS8ldAgBiS8ldBABiS8ld
     * token_account : 006b69c204049cf435a92b493991926d69fIADOet3uit4dbh6dgtRO0X5KuTz3NgpJZYbmx1wFDo/+WrdIfRBZRrEiIgAUCAEA0o7KXQQAAQBiS8ldAwBiS8ldAgBiS8ldBABiS8ld
     * token_rtm : 006b69c204049cf435a92b493991926d69fIABlxn48r0d6blPxppxN/USMyKFnT4kcktz1kxPVFRkIbLdIfRBZRrEiEAAd+AAA0o7KXQEA6ANiS8ld
     * channel : 7d72365eb983485397e3e3f9d460bdda
     */

    private String token_uid;
    private String token_account;
    private String token_rtm;
    private String channel;

    public String getToken_uid() {
        return token_uid;
    }

    public void setToken_uid(String token_uid) {
        this.token_uid = token_uid;
    }

    public String getToken_account() {
        return token_account;
    }

    public void setToken_account(String token_account) {
        this.token_account = token_account;
    }

    public String getToken_rtm() {
        return token_rtm;
    }

    public void setToken_rtm(String token_rtm) {
        this.token_rtm = token_rtm;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
