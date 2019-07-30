package com.sdxxtop.guardianapp.ui.activity;

import com.google.android.material.tabs.TabLayout;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.SectionEventPresenter;
import com.sdxxtop.guardianapp.presenter.contract.SectionEventContract;
import com.sdxxtop.guardianapp.ui.fragment.SectionEventFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class SectionEventActivity extends BaseMvpActivity<SectionEventPresenter> implements SectionEventContract.IView {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"全部", "待认领", "已认领", "已完成"};

    @Override
    protected int getLayout() {
        return R.layout.activity_section_event;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(SectionEventFragment.newInstance(i));
        }

        viewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), Arrays.asList(titles), fragments));
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(fragments.size());
    }

    public void setChengeCount(List<String> count) {
        if (tabLayout == null) return;
        for (int i = 0; i < count.size(); i++) {
            setTabText(i, count.get(i));
        }
    }

    public void setTabText(int type, String count) {
        switch (type) {
            case 0:
                tabLayout.getTabAt(type).setText("全部（" + count + "）");
                break;
            case 1:
                tabLayout.getTabAt(type).setText("待认领（" + count + "）");
                break;
            case 2:
                tabLayout.getTabAt(type).setText("已认领（" + count + "）");
                break;
            case 3:
                tabLayout.getTabAt(type).setText("已完成（" + count + "）");
                break;
        }
    }

    class MyAdapter extends FragmentStatePagerAdapter {
        private List<String> mTitleList;
        private List<Fragment> mFragmentList;

        public MyAdapter(FragmentManager fm, List<String> titleList, List<Fragment> fragmentList) {
            super(fm);
            mTitleList = titleList;
            mFragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList == null ? 0 : mFragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

        public int getItemPosition(Object object) {
            return POSITION_UNCHANGED;
        }
    }

}
