package com.sdxxtop.openlive.utils;

import android.widget.RelativeLayout;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-12 18:05
 * Version: 1.0
 * Description:
 */
public class VideoLayoutUtils {

    public static void open4Status(int width, int height, RelativeLayout.LayoutParams[] array) {
        if (array.length  <= 9) {
            open9Video(width, height, array);
        }
    }

    private static void open9Video(int width, int height, RelativeLayout.LayoutParams[] array) {
//        for (int i = 0; i < array.length; i++) {
//            if (i == 0) {
//                array[0] = new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.MATCH_PARENT,
//                        RelativeLayout.LayoutParams.MATCH_PARENT);
//                array[0].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//                array[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//            } else if (i == 1) {
//                array[1] = new RelativeLayout.LayoutParams(width, height / 2);
//                array[0].height = array[1].height;
//                array[1].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());
//                array[1].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//            } else if (i == 2) {
//                array[i] = new RelativeLayout.LayoutParams(width / 2, height / 2);
//                array[i - 1].width = array[i].width;
//                array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(i - 1)).getId());
//                array[i].addRule(RelativeLayout.ALIGN_TOP, mUserViewList.get(mUidList.get(i - 1)).getId());
//            } else if (i == 3) {
//                array[i] = new RelativeLayout.LayoutParams(width / 2, height / 2);
//                array[0].width = width / 2;
//                array[1].addRule(RelativeLayout.BELOW, 0);
//                array[1].addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
//                array[1].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());
//                array[1].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//                array[2].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//                array[2].addRule(RelativeLayout.RIGHT_OF, 0);
//                array[2].addRule(RelativeLayout.ALIGN_TOP, 0);
//                array[2].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());
//                array[3].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
//                array[3].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(2)).getId());
//            }
//        }
    }
}
