package com.sdxxtop.guardianapp.ui.fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.model.bean.ArticleIndexBean;
import com.sdxxtop.guardianapp.ui.adapter.HomePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class LearningFragment extends BaseFragment {

    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.tab)
    TabLayout mTabLayout;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_learning;
    }

    public static LearningFragment newInstance() {
        Bundle args = new Bundle();
        LearningFragment fragment = new LearningFragment();
        fragment.setArguments(args);
        return fragment;
    }

    Map<String, SupportFragment> map = new HashMap<>();
    ArrayList<SupportFragment> fragments = new ArrayList<>();
    ArrayList<String> titleList = new ArrayList<>();
    private HomePagerAdapter homePagerAdapter;

    public void replaceList(List<ArticleIndexBean.ShowBean> showList) {
        if (showList != null && showList.size() > 0) {
            titleList.clear();
            fragments.clear();
            for (ArticleIndexBean.ShowBean showBean : showList) {
                String title = showBean.getTitle();
                titleList.add(title);
                fragments.add(map.get(title));
            }
        }

        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(),
                titleList, fragments);
        mViewPager.setAdapter(homePagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(fragments.size());
    }

//    private Fragment getFragment(String title) {
//        Fragment fragment = null;
//        switch (title) {
//            case "新闻学习":
//                fragment = NewsListFragment.newInstance(1);
//                break;
//            case "罗庄发布":
//                fragment = NewsListFragment.newInstance(2);
//                break;
//            case "课程":
//                fragment = CourseListFragment.newInstance(1);
//                break;
//            case "考核":
//                fragment = CourseListFragment.newInstance(2);
//                break;
//        }
//        return fragment;
//    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(true);

        titleList.add("新闻中心");
        titleList.add("权威发布");
        titleList.add("线上培训");
//        titleList.add("评分");
        titleList.add("作业考核");


        fragments.add(NewsListFragment.newInstance(1));
        fragments.add(NewsListFragment.newInstance(2));
        fragments.add(CourseListFragment.newInstance(1));
        fragments.add(CourseListFragment.newInstance(2));
//
//
        for (int i = 0; i < titleList.size(); i++) {
            String title = titleList.get(i);
            map.put(title, fragments.get(i));
        }
//
//        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(),
//                titleList, fragments);
//        mViewPager.setAdapter(homePagerAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);
//        mViewPager.setOffscreenPageLimit(fragments.size());

//        fragments.add(ImageTabFragment.newInstance(R.drawable.course));
//        fragments.add(ImageTabFragment.newInstance(R.drawable.score));
//        fragments.add(ImageTabFragment.newInstance(R.drawable.exam));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
        }
    }
}
