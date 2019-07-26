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
    private String[] titles = new String[]{"全部（10）","待认领（9）","已认领（5）","已完成（1）"};

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
        for(int i=0;i<titles.length;i++){
            fragments.add(SectionEventFragment.newInstance(i+1));
        }

        viewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), Arrays.asList(titles),fragments));
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(fragments.size());
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
