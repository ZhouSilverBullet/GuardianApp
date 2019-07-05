package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/7/4
 * Desc:
 */
public class AuthDataBean {

    private List<AuthBean> auth;

    public List<AuthBean> getAuth() {
        return auth;
    }

    public void setAuth(List<AuthBean> auth) {
        this.auth = auth;
    }

    public static class AuthBean {
        private int is_auth;
        private String title;
        private int type;

        public int getIs_auth() {
            return is_auth;
        }

        public void setIs_auth(int is_auth) {
            this.is_auth = is_auth;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
