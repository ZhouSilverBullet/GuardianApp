package com.sdxxtop.guardianapp.aakt;

import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

/**
 * @author :  lwb
 * Date: 2020/1/7
 * Desc:
 */
public class UserInfo {

    private TabLayout tableLayout;

    public String date;
    public String update;
    public String title;
    public String status;
    public String score;
    public String type;

    public void setssss(){
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public UserInfo(String date, String update, String title, String status, String score, String type) {
        this.date = date;
        this.update = update;
        this.title = title;
        this.status = status;
        this.score = score;
        this.type = type;
    }
}
