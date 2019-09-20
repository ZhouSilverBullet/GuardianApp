package com.sdxxtop.guardianapp.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.GridEventCountBean;
import com.sdxxtop.guardianapp.presenter.GridEventPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GridEventContract;
import com.sdxxtop.guardianapp.ui.fragment.GridEventFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.widget.PopupWindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class GridEventActivity extends BaseMvpActivity<GridEventPresenter> implements GridEventContract.IView {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.ll_group)
    LinearLayout llGroup;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"解决事件", "上报事件", "认领事件", "巡查汇报"};
    private TestPopupWindow mWindow;
    private int currentPos;

    @Override
    protected int getLayout() {
        return R.layout.activity_grid_event;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadData();
    }

    private Map<Integer, String[]> map = new HashMap<>();

    @Override
    public void showData(GridEventCountBean bean) {
        map.clear();
        if (bean == null) return;
        if (bean.solve != null && tabLayout.getTabAt(0) != null) {
            tabLayout.getTabAt(0).setText("解决事件(" + bean.solve.count + ")");
            String[] value = {String.valueOf(bean.solve.stay_solve), String.valueOf(bean.solve.stay_check), String.valueOf(bean.solve.complete)};
            map.put(0, value);
        }
        if (bean.report != null && tabLayout.getTabAt(1) != null) {
            tabLayout.getTabAt(1).setText("上报事件(" + bean.report.count + ")");
            String[] value = {String.valueOf(bean.report.distribute), String.valueOf(bean.report.stay_solve), String.valueOf(bean.report.stay_check),
                    String.valueOf(bean.report.complete)};
            map.put(1, value);
        }
        if (bean.claim != null && tabLayout.getTabAt(2) != null) {
            tabLayout.getTabAt(2).setText("认领事件(" + bean.claim.count + ")");
            String[] value = {String.valueOf(bean.claim.stay_solve), String.valueOf(bean.claim.stay_check), String.valueOf(bean.claim.complete)};
            map.put(2, value);
        }
        if (bean.partol != null && tabLayout.getTabAt(3) != null) {
            tabLayout.getTabAt(3).setText("巡查汇报(" + bean.partol.count + ")");
            String[] value = {String.valueOf(bean.partol.stay_review), String.valueOf(bean.partol.complete)};
            map.put(3, value);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(GridEventFragment.newInstance(i));
        }

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), Arrays.asList(titles), fragments);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(fragments.size());

        mWindow = new TestPopupWindow(GridEventActivity.this);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                selectTab(tab);
            }
        });
    }

    /**
     * 选中tab的标签
     * @param tab
     */
    private void selectTab(TabLayout.Tab tab) {
        currentPos = tab.getPosition();
        if (mWindow != null) {
            if (mWindow.isShowing()) {
                mWindow.dismiss();
            }
            mWindow.setData(currentPos, map.get(currentPos));
            PopupWindowCompat.showAsDropDown(mWindow, tab.view, 0, 0, Gravity.CENTER);
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

    public class TestPopupWindow extends PopupWindow implements View.OnClickListener {

        private TextView tvTitle1;
        private TextView tvTitle2;
        private TextView tvTitle3;
        private TextView tvTitle4;

        public TestPopupWindow(Context context) {
            super(context);
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();

            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            setWidth(width / 4);
            setOutsideTouchable(true);
            setFocusable(true);
            setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            View contentView = LayoutInflater.from(context).inflate(R.layout.popup_item_list, null, false);
            setContentView(contentView);
            tvTitle1 = contentView.findViewById(R.id.tv_title_1);
            tvTitle2 = contentView.findViewById(R.id.tv_title_2);
            tvTitle3 = contentView.findViewById(R.id.tv_title_3);
            tvTitle4 = contentView.findViewById(R.id.tv_title_4);
            tvTitle1.setOnClickListener(this);
            tvTitle2.setOnClickListener(this);
            tvTitle3.setOnClickListener(this);
            tvTitle4.setOnClickListener(this);
        }

        public void setData(int eventType, String... tx) {
            tvTitle1.setVisibility(View.GONE);
            tvTitle2.setVisibility(View.GONE);
            tvTitle3.setVisibility(View.GONE);
            tvTitle4.setVisibility(View.GONE);
            switch (eventType) {
                case 0:   // 解决事件   待解决、待验收、已完成
                    if (tx.length != 3) return;
                    tvTitle1.setVisibility(View.VISIBLE);
                    tvTitle2.setVisibility(View.VISIBLE);
                    tvTitle3.setVisibility(View.VISIBLE);
                    tvTitle1.setText("待解决(" + tx[0] + ")");
                    tvTitle2.setText("待验收(" + tx[1] + ")");
                    tvTitle3.setText("已完成(" + tx[2] + ")");
                    break;
                case 1:  //上报事件  带派发、带解决、待验收、已完成
                    if (tx.length != 4) return;
                    tvTitle1.setVisibility(View.VISIBLE);
                    tvTitle2.setVisibility(View.VISIBLE);
                    tvTitle3.setVisibility(View.VISIBLE);
                    tvTitle4.setVisibility(View.VISIBLE);
                    tvTitle1.setText("待派发(" + tx[0] + ")");
                    tvTitle2.setText("待解决(" + tx[1] + ")");
                    tvTitle3.setText("待验收(" + tx[2] + ")");
                    tvTitle4.setText("已完成(" + tx[3] + ")");
                    break;
                case 2:   //认领事件   已认领、待评价、已完成
                    if (tx.length != 3) return;
                    tvTitle1.setVisibility(View.VISIBLE);
                    tvTitle2.setVisibility(View.VISIBLE);
                    tvTitle3.setVisibility(View.VISIBLE);
                    tvTitle1.setText("已认领(" + tx[0] + ")");
                    tvTitle2.setText("待评价(" + tx[1] + ")");
                    tvTitle3.setText("已完成(" + tx[2] + ")");
                    break;
                case 3:  //自行处理事件   待复查、已完成
                    if (tx.length != 2) return;
                    tvTitle1.setVisibility(View.VISIBLE);
                    tvTitle2.setVisibility(View.VISIBLE);
                    tvTitle1.setText("待复查(" + tx[0] + ")");
                    tvTitle2.setText("已完成(" + tx[1] + ")");
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (fragments == null) return;
            GridEventFragment fragment = null;
            switch (v.getId()) {
                case R.id.tv_title_1:
                    fragment = (GridEventFragment) fragments.get(currentPos);
                    if (fragment != null) {
                        fragment.type = 1;
                        fragment.loadData(1, 0);
                    }
                    break;
                case R.id.tv_title_2:
                    fragment = (GridEventFragment) fragments.get(currentPos);
                    if (fragment != null) {
                        fragment.type = 2;
                        fragment.loadData(2, 0);
                    }
                    break;
                case R.id.tv_title_3:
                    fragment = (GridEventFragment) fragments.get(currentPos);
                    if (fragment != null) {
                        fragment.type = 3;
                        fragment.loadData(3, 0);
                    }
                    break;
                case R.id.tv_title_4:
                    fragment = (GridEventFragment) fragments.get(currentPos);
                    if (fragment != null) {
                        fragment.type = 4;
                        fragment.loadData(4, 0);
                    }
                    break;
            }
            dismiss();
        }
    }
}
