package com.sdxxtop.guardianapp.ui.fragment;

import com.google.android.material.tabs.TabLayout;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.model.bean.ArticleIndexBean;
import com.sdxxtop.guardianapp.ui.adapter.HomePagerAdapter;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    Map<String, Fragment> map = new HashMap<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> titleList = new ArrayList<>();
    private HomePagerAdapter homePagerAdapter;

    public void replaceList(List<ArticleIndexBean.ShowBean> showList){
        if (showList != null && showList.size() > 0) {
//            showList.remove(0);
//            showList.remove(0);
//            showList.remove(0);
            titleList.clear();
            fragments.clear();
            for (ArticleIndexBean.ShowBean showBean : showList) {
                titleList.add(showBean.getTitle());
                fragments.add(map.get(showBean.getTitle()));
            }
        }
        for (int i = 0; i < fragments.size(); i++) {
            BaseFragment fragment = (BaseFragment) fragments.get(i);
            fragment.setIsFirstLoading(false);
        }
        homePagerAdapter.replaceData(titleList,fragments);

    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(true);
        topViewPadding(mTitleView);

        titleList.add("新闻学习");
        titleList.add("罗庄发布");
        titleList.add("课程");
//        titleList.add("评分");
        titleList.add("考核");


        fragments.add(NewsListFragment.newInstance(1));
        fragments.add(NewsListFragment.newInstance(2));
        fragments.add(CourseListFragment.newInstance(1));
        fragments.add(CourseListFragment.newInstance(2));


        for (int i = 0; i < titleList.size(); i++) {
            String title = titleList.get(i);
            map.put(title, fragments.get(i));
        }

        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(),
                titleList, fragments);
        mViewPager.setAdapter(homePagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(fragments.size());

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
