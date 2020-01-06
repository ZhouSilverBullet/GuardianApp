package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import com.google.android.material.tabs.TabLayout;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment.GZMX_WorkFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment.GZTJ_WorkFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment.XLJL_WorkFragment;
import com.sdxxtop.guardianapp.ui.adapter.TablayoutFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MyWorkActivity extends BaseActivity {

    @BindView(R.id.attendance_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private List<Fragment> fragments;
    private List<String> stringList;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_work;
    }

    @Override
    protected void initView() {
        fragments = new ArrayList<>();

        fragments.add(new GZTJ_WorkFragment());
        fragments.add(new XLJL_WorkFragment());
        fragments.add(new GZMX_WorkFragment());

        stringList = new ArrayList<>();
        stringList.add("工作统计");
        stringList.add("巡逻距离");
        stringList.add("工作明细");

        viewPager.setAdapter(new TablayoutFragmentPagerAdapter(getSupportFragmentManager(), mContext, stringList, fragments));
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
    }
}
