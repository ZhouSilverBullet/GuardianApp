package com.sdxxtop.guardianapp.model.bean;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/16
 * Desc:
 */
public class GridreportOperatorBean {

    private int report_count;
    private int adopt;
    private int pending;
    private int part_count;

    private List<EnterpriseSecurityBean.SignData>sign_data;

    public List<EnterpriseSecurityBean.SignData> getSign_data() {
        return sign_data;
    }

    public void setSign_data(List<EnterpriseSecurityBean.SignData> sign_data) {
        this.sign_data = sign_data;
    }

    public int getReport_count() {
        return report_count;
    }

    public void setReport_count(int report_count) {
        this.report_count = report_count;
    }

    public int getAdopt() {
        return adopt;
    }

    public void setAdopt(int adopt) {
        this.adopt = adopt;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getPart_count() {
        return part_count;
    }

    public void setPart_count(int part_count) {
        this.part_count = part_count;
    }




}
