package com.xuxin.guardianapp.ui.activity;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.orhanobut.logger.Logger;
import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseMvpActivity;
import com.xuxin.guardianapp.presenter.HomePresenter;
import com.xuxin.guardianapp.presenter.contract.HomeContract;
import com.xuxin.guardianapp.ui.fragment.HomeFragment;
import com.xuxin.guardianapp.ui.fragment.LearningFragment;
import com.xuxin.guardianapp.ui.fragment.MineFragment;
import com.xuxin.guardianapp.utils.ReflectUtils;

import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class HomeActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.IView {

    @BindView(R.id.ahn_home_navigation)
    AHBottomNavigation mAHBottomNavigation;
    private int prePosition;
    private SupportFragment[] mFragments = new SupportFragment[3];

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
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
        initAHNavigation();

        switchFragment(0);
    }

    private void initAHNavigation() {
        int[] tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu);
        navigationAdapter.setupWithBottomNavigation(mAHBottomNavigation, tabColors);

        // Set background color
        mAHBottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        // Disable the translation inside the CoordinatorLayout
        mAHBottomNavigation.setBehaviorTranslationEnabled(false);
        mAHBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Change colors
        mAHBottomNavigation.setAccentColor(Color.parseColor("#34B26D"));
        mAHBottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        mAHBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Logger.i("position = " + position + "wasSelected = " + wasSelected);
//                if (position == 2) {
//                    return false;
//                }
                itemSelectAnimator(position, wasSelected);
                switchFragment(position);
                return true;
            }
        });
    }

    private void switchFragment(int position) {
        HomeFragment fragment = findFragment(HomeFragment.class);
        if (fragment == null) {
            mFragments[0] = new HomeFragment();
            mFragments[1] = new LearningFragment();
            mFragments[2] = new MineFragment();

            loadMultipleRootFragment(R.id.fl_home_container, position,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]);
        } else {
            showHideFragment(mFragments[position], mFragments[prePosition]);
        }
        prePosition = position;
    }

    private void itemSelectAnimator(int position, boolean wasSelected) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Logger.i("版本不符合,不进行动画");
            return;
        }

        List<View> views = ReflectUtils.getFieldValueByFieldName("views", mAHBottomNavigation);
        if (views != null) {
            View view = views.get(position);
            ImageView icon = view.findViewById(R.id.bottom_navigation_item_icon);
            final int width = icon.getMeasuredWidth();
            final int height = icon.getMeasuredHeight();
            final float radius = (float) Math.sqrt(width * width + height * height) / 2;//半径
            Animator animator = ViewAnimationUtils.createCircularReveal(icon, width / 2, height / 2, 0, radius);
            animator.setDuration(300);
            animator.start();
        }
    }
}
