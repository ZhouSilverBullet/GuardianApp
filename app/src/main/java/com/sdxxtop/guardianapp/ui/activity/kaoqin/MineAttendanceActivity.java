package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import com.google.android.material.tabs.TabLayout;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment.KQST_AttendanceFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment.KQMX_AttendanceFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment.KQTJ_AttendanceFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment.PJGS_AttendanceFragment;
import com.sdxxtop.guardianapp.ui.adapter.TablayoutFragmentPagerAdapter;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MineAttendanceActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.attendance_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private List<Fragment> fragments;
    private List<String> stringList;

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_attendance;
    }

    @Override
    protected void initView() {
        fragments = new ArrayList<>();

        fragments.add(new KQST_AttendanceFragment());
        fragments.add(new KQTJ_AttendanceFragment());
        fragments.add(new PJGS_AttendanceFragment());
        fragments.add(new KQMX_AttendanceFragment());

        stringList = new ArrayList<>();
        stringList.add("考勤视图");
        stringList.add("考勤统计");
        stringList.add("平均工时");
        stringList.add("考勤明细");

        viewPager.setAdapter(new TablayoutFragmentPagerAdapter(getSupportFragmentManager(), mContext, stringList, fragments));
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
    }
}
