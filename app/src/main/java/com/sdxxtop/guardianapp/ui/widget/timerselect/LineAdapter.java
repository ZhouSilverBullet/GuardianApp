package com.sdxxtop.guardianapp.ui.widget.timerselect;


import com.contrarywind.adapter.WheelAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author :  lwb
 *         Date: 2018/10/22
 *         Desc:
 */

public class LineAdapter implements WheelAdapter<String> {
    List<String> minList = new ArrayList<>();

    public LineAdapter() {
        for (int i = 0; i <= 59; i++) {
            minList.add(":");
        }
    }

    @Override
    public int getItemsCount() {
        return minList.size();
    }

    @Override
    public String getItem(int index) {
        return minList.get(index);
    }

    @Override
    public int indexOf(String o) {
        return 0;
    }
}
