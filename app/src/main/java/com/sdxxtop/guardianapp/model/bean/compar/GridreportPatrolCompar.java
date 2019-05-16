package com.sdxxtop.guardianapp.model.bean.compar;

import com.sdxxtop.guardianapp.model.bean.GridreportPatrolBean;

import java.util.Comparator;

/**
 * @author :  lwb
 * Date: 2019/5/16
 * Desc:
 */
public class GridreportPatrolCompar  implements Comparator<GridreportPatrolBean.TrailUser> {

    public static final int COMPANY_CHOOSE_AREA = 1;  // 选中所属街道
    public static final int COMPANY_CHOOSE_DAYS = 2;  // 选中巡逻天数
    public static final int COMPANY_CHOOSE_DISTANCE = 3;  // 选中巡逻距离

    private int mType;
    private boolean isOrder;

    public GridreportPatrolCompar(int type,boolean isOrder) {
        this.mType = type;
        this.isOrder = isOrder;
    }

    @Override
    public int compare(GridreportPatrolBean.TrailUser o1, GridreportPatrolBean.TrailUser o2) {
        int result = 0;
        switch (mType) {
            case COMPANY_CHOOSE_AREA:// 选中所属街道
                result = o1.getPr_name().compareTo(o2.getPr_name());
                break;
            case COMPANY_CHOOSE_DAYS:// 选中巡逻天数
                if (o1.getDays() > o2.getDays()) {
                    result = 1;
                } else if (o1.getDays() < o2.getDays()) {
                    result = -1;
                }
                break;
            case COMPANY_CHOOSE_DISTANCE:// 选中巡逻距离
                if (o1.getDistance() > o2.getDistance()) {
                    result = 1;
                } else if (o1.getDistance() < o2.getDistance()) {
                    result = -1;
                }
                break;
        }
        return isOrder?-result:result;
    }
}
