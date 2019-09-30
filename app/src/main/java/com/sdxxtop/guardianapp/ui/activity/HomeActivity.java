package com.sdxxtop.guardianapp.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.luck.picture.lib.permissions.RxPermissions;
import com.orhanobut.logger.Logger;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.ArticleIndexBean;
import com.sdxxtop.guardianapp.model.bean.InitBean;
import com.sdxxtop.guardianapp.presenter.HomePresenter;
import com.sdxxtop.guardianapp.presenter.contract.HomeContract;
import com.sdxxtop.guardianapp.service.ForegroundService;
import com.sdxxtop.guardianapp.service.NotificationMonitor;
import com.sdxxtop.guardianapp.ui.dialog.DownloadDialog;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.fragment.DataMonitoringFragment;
import com.sdxxtop.guardianapp.ui.fragment.HomeFragment;
import com.sdxxtop.guardianapp.ui.fragment.LearningFragment;
import com.sdxxtop.guardianapp.ui.fragment.MineFragment;
import com.sdxxtop.guardianapp.ui.fragment.WorkFragment;
import com.sdxxtop.guardianapp.ui.widget.bottom_tab.CustomBottomTab;
import com.sdxxtop.guardianapp.utils.ExcludePhoneModel;
import com.sdxxtop.guardianapp.utils.ReflectUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.SupportFragment;

public class HomeActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.IView {

    @BindView(R.id.ahn_home_navigation)
    AHBottomNavigation mAHBottomNavigation;

    @BindView(R.id.cbt_view)
    public CustomBottomTab cbtView;

    private int prePosition;
    private SupportFragment[] mFragments = new SupportFragment[5];
    private boolean isAdmin;
    private RxPermissions mRxPermissions;
    private int currentPosition = 0;
    private boolean isEnabledNLS = false;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

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
    protected void initVariables() {
        super.initVariables();
        if (getIntent() != null) {
            isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
//        initAHNavigation();
        switchFragment(0);

        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });

        mRxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    startPatrolService();
                    mPresenter.startUploadingPoint();
                }
            }
        });

        cbtView.setOnMenuClickListener(new CustomBottomTab.OnMenuClickListener() {
            @Override
            public boolean netResult(int menu) {
                if (menu == 3) {
                    mPresenter.articleIndex(menu, true);
                    return false;
                } else {
                    switchFragment(menu);
                    return true;
                }
            }
        });

        if (!isServiceExisted(NotificationMonitor.class.getName())) {
            startService(new Intent(this, NotificationMonitor.class));
        }
        toggleNotificationListenerService(this);
    }

    private void startPatrolService() {
        // TODO  定位服务 暂时关闭
        ignoreBatteryOptimization(this);
//        Logger.e("开启了服务");
//        Intent intent = new Intent(this, PatrolRecordService.class);
//        startService(intent);
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
                if (position == 2) {
                    mPresenter.articleIndex(position, wasSelected);
                    return false;
                } else {
                    currentPosition = position;
//                    switchFragment(position);
//                    itemSelectAnimator(position, wasSelected);
                    return true;
                }
            }
        });
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

    private List<ArticleIndexBean.ShowBean> showList = new ArrayList<>();

    @Override
    public void showPermission(ArticleIndexBean bean, int position, boolean wasSelected) {
        showList.clear();
        if (bean.getShow() != null && bean.getShow().size() > 0) {
            List<ArticleIndexBean.ShowBean> list = bean.getShow();
            for (ArticleIndexBean.ShowBean showBean : list) {
                if (showBean.getIs_show() == 1) {
                    showList.add(showBean);
                }
            }

            if (showList.size() > 0) {
                if (mFragments[3] != null) {
                    ((LearningFragment) mFragments[3]).replaceList(showList);
                    switchFragment(position);
                    cbtView.setCurrentItem(position);
                }
//                itemSelectAnimator(position, wasSelected);
//                mAHBottomNavigation.setCurrentItem(2, false);
            } else {
                showToast("没有操作权限");
//                mAHBottomNavigation.setCurrentItem(currentPosition, false);
            }
        } else {
//            mAHBottomNavigation.setCurrentItem(currentPosition, false);
            showToast("没有操作权限");
        }
    }

    private void switchFragment(int position) {
        HomeFragment fragment = findFragment(HomeFragment.class);
        if (fragment == null) {
            mFragments[0] = HomeFragment.newInstance(isAdmin);
            mFragments[1] = WorkFragment.newInstance();
            mFragments[2] = DataMonitoringFragment.newInstance();
            mFragments[3] = LearningFragment.newInstance();
            mFragments[4] = MineFragment.newInstance(isAdmin);

            loadMultipleRootFragment(R.id.fl_home_container, position,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4]);
        } else {
            showHideFragment(mFragments[position], mFragments[prePosition]);
        }
        prePosition = position;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.initApp();
    }

    @Override
    protected void onResume() {
        if (!isServiceExisted(NotificationMonitor.class.getName())) {
            startService(new Intent(this, NotificationMonitor.class));
        }
        super.onResume();
        isEnabledNLS = isEnabled();
        if (!isEnabledNLS) {
            showConfirmDialog();
        }
//        clearAllNotifications();
    }

    /**
     * 点击首页无人机页面跳转
     */
    public void setWurenjiClick() {
        cbtView.setWurenjiClick();
        switchFragment(2);
        if (mFragments[2] != null) {
            for (SupportFragment fragment : mFragments) {
                if (fragment instanceof DataMonitoringFragment) {
                    DataMonitoringFragment workFragment = (DataMonitoringFragment) fragment;
                    workFragment.setCurrentItem();
                    break;
                }
            }
        }
    }

    @Override
    public void showInit(InitBean initBean) {
        if (initBean != null) {
            DownloadDialog downloadDialog = new DownloadDialog(this, initBean, new RxPermissions(this));
            downloadDialog.show();
        }
    }

    /**
     * 忽略电池优化
     */
    public void ignoreBatteryOptimization(Activity activity) {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean hasIgnored = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            boolean exclude = ExcludePhoneModel.isExclude();
            if (exclude) {  //是否忽略
                return;
            }
            hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.getPackageName());
            //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
            if (!hasIgnored) {
//                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:"+activity.getPackageName()));
//                startActivity(intent);
                new IosAlertDialog(this)
                        .builder()
                        .setCancelable(false)
                        .setTitle(" ")
                        .setMsg(" 请设置手机权限,否则定位轨迹会有偏移 ")
                        .setMsg2("  ")
                        .setPositiveButton("去设置", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(HomeActivity.this, SystemSettingActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }
        }
    }

    private static final int TIME_EXIT = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressedSupport() {
        if (mBackPressed + TIME_EXIT > System.currentTimeMillis()) {
            //用户退出处理
            finish();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            if (isServiceExisted(ForegroundService.class.getName())) {
                Intent intent = new Intent(this, ForegroundService.class);
                stopService(intent);
            }
            if (isServiceExisted(NotificationMonitor.class.getName())) {
                Intent intent = new Intent(this, NotificationMonitor.class);
                stopService(intent);
            }
            super.onBackPressedSupport();
            return;
        } else {
            Toast.makeText(this, "再点击一次返回退出程序", Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
        }
    }

    /**
     * 判断service是否已经运行
     * 必须判断uid,因为可能有重名的Service,所以要找自己程序的Service,不同进程只要是同一个程序就是同一个uid,个人理解android系统中一个程序就是一个用户
     * 用pid替换uid进行判断强烈不建议,因为如果是远程Service的话,主进程的pid和远程Service的pid不是一个值,在主进程调用该方法会导致Service即使已经运行也会认为没有运行
     * 如果Service和主进程是一个进程的话,用pid不会出错,但是这种方法强烈不建议,如果你后来把Service改成了远程Service,这时候判断就出错了
     *
     * @param className Service的全名,例如PushService.class.getName()
     * @return true:Service已运行 false:Service未运行
     */
    public boolean isServiceExisted(String className) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(Integer.MAX_VALUE);
        int myUid = android.os.Process.myUid();
        for (ActivityManager.RunningServiceInfo runningServiceInfo : serviceList) {
            if (runningServiceInfo.uid == myUid && runningServiceInfo.service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void showConfirmDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("请打开通知使用权,确保消息能够准时送达!")
                .setTitle("通知使用权")
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                openNotificationAccess();
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // do nothing
                            }
                        })
                .create();
        dialog.show();
    }

    private void openNotificationAccess() {
        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    public void toggleNotificationListenerService(Context context) {
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(context, NotificationMonitor.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(new ComponentName(context, NotificationMonitor.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
}
