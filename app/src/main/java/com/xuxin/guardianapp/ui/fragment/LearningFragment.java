package com.xuxin.guardianapp.ui.fragment;

import com.google.android.material.tabs.TabLayout;
import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseFragment;
import com.xuxin.guardianapp.ui.adapter.HomePagerAdapter;
import com.xuxin.guardianapp.ui.widget.TitleView;

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
        fragments.add(NewsListFragment.newInstance("aaa"));
        fragments.add(NewsListFragment.newInstance("aaa"));
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
