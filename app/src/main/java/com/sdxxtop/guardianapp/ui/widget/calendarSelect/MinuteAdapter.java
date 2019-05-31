package com.sdxxtop.guardianapp.ui.widget.calendarSelect;


import com.contrarywind.adapter.WheelAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author :  lwb
 *         Date: 2018/10/22
 *         Desc:
 */

public class MinuteAdapter implements WheelAdapter<String> {

    List<String> minList = new ArrayList<>();

    public MinuteAdapter() {
        for (int i = 0; i <= 59; i++) {
            if (i < 10) {
                minList.add("0" + i + "");
            } else {
                minList.add(i + "");
            }
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
        for (int i = 0; i < minList.size(); i++) {
            String s = minList.get(i);
            if (s.equals(o)) {
                return i;
            }
        }
        return 0;
    }
}
