package com.sdxxtop.guardianapp.model.bean;

public class InitBean {
//    {
//        "apk_url":"",//app更新地址
//            "version_code":"20100",//更新后的版本号
//            "version_name":"V2.1.0",//版本名称
//            "content":"1.增加学生考勤功能 | 2.as科技的", //版本更新内容,用|代表换行,客户端需要自行处理
//            "force_update":0,//是否为强制更新0:非强更 1:强更
//    }
    private String apk_url;
    private String version_code;
    private String version_name;
    private String content;
    private int force_update;


    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getForce_update() {
        return force_update;
    }

    public void setForce_update(int force_update) {
        this.force_update = force_update;
    }
}
