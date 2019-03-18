package com.sdxxtop.guardianapp.ui.fragment;

import com.google.android.material.tabs.TabLayout;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.adapter.HomePagerAdapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class LearningFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.tab)
    TabLayout mTabLayout;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_learning;
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(true);
        topViewPadding(mTitleView);


        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("新闻学习");
        titleList.add("罗庄发布");
        titleList.add("课程");
        titleList.add("评分");
        titleList.add("考核");

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(NewsListFragment.newInstance(1));
        fragments.add(NewsListFragment.newInstance(2));
        fragments.add(ImageTabFragment.newInstance(R.drawable.course));
        fragments.add(ImageTabFragment.newInstance(R.drawable.score));
        fragments.add(ImageTabFragment.newInstance(R.drawable.exam));

        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(),
                titleList, fragments);
        mViewPager.setAdapter(homePagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
        }
    }
}
