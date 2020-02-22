package com.sdxxtop.guardianapp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.gson.internal.LinkedTreeMap;
import com.luck.picture.lib.permissions.RxPermissions;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.TrackService.TrackServiceUtil;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.ArticleIndexBean;
import com.sdxxtop.guardianapp.model.bean.InitBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.RtcRequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.RetrofitHelper;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.HomePresenter;
import com.sdxxtop.guardianapp.presenter.contract.HomeContract;
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
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.imagora.receiver.LoginIMReceiver;
import com.sdxxtop.openlive.activities.presenter.im.AgoraIMLoginPresenter;
import com.sdxxtop.openlive.activities.presenter.im.IAgoraIMLoginView;
import com.sdxxtop.sdkagora.AgoraSession;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.IView, IAgoraIMLoginView {

    private static final String TAG = "HomeActivity";

    private String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @BindView(R.id.ahn_home_navigation)
    AHBottomNavigation mAHBottomNavigation;
    @BindView(R.id.cbt_view)
    public CustomBottomTab cbtView;

    private RxPermissions mRxPermissions;
    private boolean isEnabledNLS = false;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private AlertDialog dialog;   // 消息通知提示框
    private AgoraIMLoginPresenter loginPresenter;
    private AgoraLoginReceiver agoraLoginReceiver;

    private ArrayList<ArticleIndexBean.ShowBean> showList = new ArrayList<>();

    //fragment
    private Fragment currentFragment;
    private Fragment newFragment;
    private Fragment homeFragment;
    private Fragment workFragment;
    private Fragment applyFragment;
    private LearningFragment learnFragment;
    private MineFragment mineFragment = MineFragment.newInstance();

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

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        initFirstFragment();

        loginPresenter = new AgoraIMLoginPresenter(this);
        agoraLoginReceiver = new AgoraLoginReceiver(loginPresenter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LoginIMReceiver.ACTION_LOGIN_RECEIVER);
        intentFilter.addAction(LoginIMReceiver.ACTION_LOGOUT_RECEIVER);
        registerReceiver(agoraLoginReceiver, intentFilter);


        switchFragment(0);

        mRxPermissions = new RxPermissions(this);

        mRxPermissions.request(PERMISSIONS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {

                }
            }
        });

        //TODO  猎鹰相关关闭
        TrackServiceUtil instance = TrackServiceUtil.getInstance();
        instance.stsrtTrackService(null);
        mPresenter.startUploadingPoint();

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

//        if (!isServiceExisted(NotificationMonitor.class.getName())) {
//            startService(new Intent(this, NotificationMonitor.class));
//        }
        toggleNotificationListenerService(this);
    }

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
                learnFragment = LearningFragment.newInstance(showList);
                switchFragment(position);
                cbtView.setCurrentItem(position);
            } else {
                showToast("没有操作权限");
            }
        } else {
            showToast("没有操作权限");
        }
    }

    /**
     * 初始化第一个Fragment
     */
    private void initFirstFragment() {
        homeFragment = new HomeFragment();
        currentFragment = homeFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home_container, currentFragment).commitAllowingStateLoss();
    }

    /**
     * 切换Fragment
     *
     * @param position
     */
    private void switchFragment(int position) {
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                }
                newFragment = homeFragment;
                break;
            case 1:
                if (workFragment == null) {
                    workFragment = WorkFragment.newInstance();
                }
                newFragment = workFragment;
                break;
            case 2:
                if (applyFragment == null) {
                    applyFragment = DataMonitoringFragment.newInstance();
                }
                newFragment = applyFragment;
                break;
            case 3:
                if (learnFragment == null) {
                    learnFragment = LearningFragment.newInstance(showList);
                }
                newFragment = learnFragment;
                break;
            case 4:
                if (mineFragment == null) {
                    mineFragment = MineFragment.newInstance();
                }
                newFragment = mineFragment;
                break;
        }
        setCurrentFragment();
    }

    //设置底部按钮被选中
    private void setCurrentFragment() {
        if (newFragment != currentFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (newFragment.isAdded()) {
                transaction.show(newFragment);
                newFragment.onResume();
            } else {
                transaction.add(R.id.fl_home_container, newFragment);
            }
            transaction.hide(currentFragment).commit();
            currentFragment = newFragment;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.initApp();

        getRtmToken();
        getAudioLists();
    }

    private void getAudioLists() {
        Observable<RequestBean> observable = RetrofitHelper.getWapApi().postAudioLists();
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean bean) {
                if (bean != null) {

//                    ((LinkedTreeMap<String,String>)bean.getData()).get("50173")
                    try {
                        AgoraSession.setMessage((LinkedTreeMap<String, String>) bean.getData());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
    }

    private void getRtmToken() {
        final int userID = SpUtil.getInt(Constants.USER_ID, 0);
        Observable<RequestBean<RtcRequestBean>> observable = RetrofitHelper.getWapApi().postAudioRtc(String.valueOf(userID));
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<RtcRequestBean>() {
            @Override
            public void onSuccess(RtcRequestBean bean) {
//                if (mView != null) {
//                    mView.showData(bean);
//                }
                Log.e(TAG,""+bean.toString());
                String token_rtm = bean.getToken_rtm();
                loginPresenter.doLogin(token_rtm, String.valueOf(userID));
            }

            @Override
            public void onFailure(int code, String error) {
                Log.e(TAG, "onFailure -- " + code + " ----- " + error);
//                if (mView != null) {
//                    mView.showError(error);
//                }
            }
        });
    }

    @Override
    protected void onResume() {
//        if (!isServiceExisted(NotificationMonitor.class.getName())) {
//            startService(new Intent(this, NotificationMonitor.class));
//        }
        super.onResume();
        isEnabledNLS = isEnabled();
        if (!isEnabledNLS) {
            if (dialog == null) {
                showConfirmDialog();
            } else {
                dialog.show();
            }
        }
    }

    /**
     * 点击首页无人机页面跳转
     */
    public void setWurenjiClick() {
        cbtView.setWurenjiClick();
        switchFragment(2);
        if (applyFragment != null) {
            if (applyFragment instanceof DataMonitoringFragment) {
                DataMonitoringFragment workFragment = (DataMonitoringFragment) applyFragment;
                workFragment.setCurrentItem();
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
    public void onBackPressed() {
        if (mBackPressed + TIME_EXIT > System.currentTimeMillis()) {
//            用户退出处理
//            finish();
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
//                finishAffinity();
//            }
//
//            if (isServiceExisted(ForegroundService.class.getName())) {
//                Intent intent = new Intent(this, ForegroundService.class);
//                stopService(intent);
//            }
//            if (isServiceExisted(NotificationMonitor.class.getName())) {
//                Intent intent = new Intent(this, NotificationMonitor.class);
//                stopService(intent);
//            }
//            super.onBackPressedSupport();

//            //返回键直接返回桌面---不关闭页面
//            Intent home = new Intent(Intent.ACTION_MAIN);
//            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            home.addCategory(Intent.CATEGORY_HOME);
//            startActivity(home);
            //模拟Home键操作
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        } else {
//            Toast.makeText(this, "再点击一次返回退出程序", Toast.LENGTH_SHORT).show();
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

    /**
     * 消息通知 弹框
     */
    private void showConfirmDialog() {
        dialog = new AlertDialog.Builder(this)
                .setMessage("请打开通知使用权,确保消息能够准时送达!")
                .setTitle("通知使用权")
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));  //  跳转系统开启通知服务
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

    public void toggleNotificationListenerService(Context context) {
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(context, NotificationMonitor.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(new ComponentName(context, NotificationMonitor.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (agoraLoginReceiver != null) {
            unregisterReceiver(agoraLoginReceiver);
        }

        if (loginPresenter != null) {
            loginPresenter.doLogout();
        }

    }

    public static class AgoraLoginReceiver extends BroadcastReceiver {
        AgoraIMLoginPresenter agoraIMLoginPresenter;

        public AgoraLoginReceiver(AgoraIMLoginPresenter presenter) {
            this.agoraIMLoginPresenter = presenter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();

            if (LoginIMReceiver.ACTION_LOGIN_RECEIVER.equals(action)) {
                agoraIMLoginPresenter.doLogout();
                agoraIMLoginPresenter.doLogin(null, String.valueOf(SpUtil.getInt(Constants.USER_ID, 0)));
            }

        }
    }
}
