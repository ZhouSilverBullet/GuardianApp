package com.sdxxtop.guardianapp.model.bean.compar;

import com.sdxxtop.guardianapp.model.bean.PartEventListBean;

import java.util.Comparator;

/**
 * @author :  lwb
 * Date: 2019/5/14
 * Desc:
 */
public class PartEventListCompar implements Comparator<PartEventListBean.ClData> {

    private boolean isOrder; //是否倒序

    public PartEventListCompar(boolean isOrder) {
        this.isOrder = isOrder;
    }

    @Override
    public int compare(PartEventListBean.ClData o1, PartEventListBean.ClData o2) {
        if (isOrder) {
            if (o1.getStatus() > o2.getStatus()) {
                return -1;
            } else if (o1.getStatus() < o2.getStatus()) {
                return 1;
            }
        } else {
            if (o1.getStatus() > o2.getStatus()) {
                return 1;
            } else if (o1.getStatus() < o2.getStatus()) {
                return -1;
            }
        }
        return 0;
    }
}
