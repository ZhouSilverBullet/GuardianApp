package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Context;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author :  lwb
 * Date: 2019/10/15
 * Desc:
 */
public class TablayoutFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<String> list;//tab条目名字集合
    private List<Fragment> fragmentList;//tab对应的fragment集合
    public TablayoutFragmentPagerAdapter(FragmentManager fm, Context mContext, List<String> mList, List<Fragment> mFragmentList) {
        super(fm);
        this.context = mContext;
        this.list = mList;
        this.fragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}