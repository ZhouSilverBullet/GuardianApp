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

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class LearningFragment extends BaseFragment {

    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.tab)
    TabLayout mTabLayout;

    private List<ArticleIndexBean.ShowBean> showList = new ArrayList<>();

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_learning;
    }

    public static LearningFragment newInstance(ArrayList<ArticleIndexBean.ShowBean> list) {
        Bundle args = new Bundle();
        args.putSerializable("tabList", list);
        LearningFragment fragment = new LearningFragment();
        fragment.setArguments(args);
        return fragment;
    }

    Map<String, Fragment> map = new HashMap<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> titleList = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        statusBar(true);

        Map<String, Fragment> mapResult = new HashMap<>();
        mapResult.put("新闻中心", NewsListFragment.newInstance(1));
        mapResult.put("权威发布", NewsListFragment.newInstance(2));
        mapResult.put("线上培训", CourseListFragment.newInstance(1));
        mapResult.put("作业考核", CourseListFragment.newInstance(2));

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            showList = (List<ArticleIndexBean.ShowBean>) bundle.getSerializable("tabList");
        }

        if (showList != null && showList.size() > 0) {
            titleList.clear();
            fragments.clear();
            for (ArticleIndexBean.ShowBean showBean : showList) {
                String title = showBean.getTitle();
                titleList.add(title);
                fragments.add(mapResult.get(title));
            }
        }

        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(),
                titleList, fragments);
        mViewPager.setAdapter(homePagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
        }
    }
}
