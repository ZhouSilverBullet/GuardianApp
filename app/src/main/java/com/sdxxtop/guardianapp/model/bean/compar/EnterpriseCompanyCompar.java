package com.sdxxtop.guardianapp.model.bean.compar;

import com.sdxxtop.guardianapp.model.bean.EnterpriseCompanyBean;

import java.util.Comparator;

/**
 * @author :  lwb
 * Date: 2019/5/15
 * Desc:
 */
public class EnterpriseCompanyCompar implements Comparator<EnterpriseCompanyBean.PartInfo> {

    public static final int COMPANY_CHOOSE_AREA = 1;  // 选中所属街道
    public static final int COMPANY_CHOOSE_SEU_COUNT = 2;  // 选中安全管理员人数
    public static final int COMPANY_CHOOSE_TRAIN_COUNT = 3;  // 选中学习培训次数
    public static final int COMPANY_CHOOSE_REPORT_INFO = 4;  // 选中学习培训次数

    private int mType;
    private boolean isOrder;

    public EnterpriseCompanyCompar(int type,boolean isOrder) {
        this.mType = type;
        this.isOrder = isOrder;
    }

    @Override
    public int compare(EnterpriseCompanyBean.PartInfo o1, EnterpriseCompanyBean.PartInfo o2) {
        int result = 0;
        switch (mType) {
            case COMPANY_CHOOSE_AREA:// 选中所属街道
                result = o1.getName().compareTo(o2.getName());
                break;
            case COMPANY_CHOOSE_SEU_COUNT:// 选中安全管理员人数
                if (o1.getSeu_count() > o2.getSeu_count()) {
                    result = 1;
                } else if (o1.getSeu_count() < o2.getSeu_count()) {
                    result = -1;
                }
                break;
            case COMPANY_CHOOSE_TRAIN_COUNT:// 选中学习培训次数
                if (o1.getTrain_count() > o2.getTrain_count()) {
                    result = 1;
                } else if (o1.getTrain_count() < o2.getTrain_count()) {
                    result = -1;
                }
                break;
            case COMPANY_CHOOSE_REPORT_INFO:// 选中学习培训次数
                if (o1.getReport_info() > o2.getReport_info()) {
                    result = 1;
                } else if (o1.getReport_info() < o2.getReport_info()) {
                    result = -1;
                }
                break;
        }
        return isOrder?-result:result;
    }
}
