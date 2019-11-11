package com.sdxxtop.guardianapp.model.bean.compar;

import com.sdxxtop.guardianapp.model.bean.GridreportUserreportBean;

import java.util.Comparator;

/**
 * @author :  lwb
 * Date: 2019/5/16
 * Desc:
 */
public class GridreportUserreportCompar implements Comparator<GridreportUserreportBean.ClData> {

    private boolean isOrder; //是否倒序

    public GridreportUserreportCompar(boolean isOrder) {
        this.isOrder = isOrder;
    }

    @Override
    public int compare(GridreportUserreportBean.ClData o1, GridreportUserreportBean.ClData o2) {
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
