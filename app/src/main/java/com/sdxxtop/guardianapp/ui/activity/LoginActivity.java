package com.sdxxtop.guardianapp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.alipush.AnalyticsHome;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.LoginBean;
import com.sdxxtop.guardianapp.model.bean.TrackInfoBean;
import com.sdxxtop.guardianapp.presenter.LoginPresenter;
import com.sdxxtop.guardianapp.presenter.contract.LoginContract;
import com.sdxxtop.guardianapp.ui.control.DelTextWatcher;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.IView, Handler.Callback {
    public static final String ACTION_LOGIN_CONFIRM_SUCCESS = "action_login_confirm_success";

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.fl_code)
    FrameLayout flCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_phone_del)
    ImageView ivPhoneDel;
    @BindView(R.id.iv_code_del)
    ImageView ivCodeDel;
    private Handler mHandler;
    private boolean isSending;
    private LoginReceiver mLoginReceiver;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();

        registerLoginReceiver();

        etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        String mobile = SpUtil.getString(Constants.MOBILE);
        if (TextUtils.isEmpty(mobile)) {
            mobile = "";
        }
        etPhone.setText(mobile);
        etPhone.setSelection(mobile.length());
    }

    private void registerLoginReceiver() {
        mLoginReceiver = new LoginReceiver(this);
        IntentFilter intentFilter = new IntentFilter(ACTION_LOGIN_CONFIRM_SUCCESS);
        registerReceiver(mLoginReceiver, intentFilter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });

        flCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });

        etPhone.addTextChangedListener(new DelTextWatcher(etPhone, ivPhoneDel));
        etCode.addTextChangedListener(new DelTextWatcher(etCode, ivCodeDel));
    }

    private void toLogin() {
        String trim = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            UIUtils.showToast("请输入正确的手机号码");
            return;
        }

        String code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            UIUtils.showToast("验证码不能为空");
            return;
        }

        mPresenter.login(trim, code, "0");
    }

    private void sendCode() {
        if (mHandler == null) {
            mHandler = new Handler(this);
        }

        String trim = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            UIUtils.showToast("请输入正确的手机号码");
            return;
        }

        if (!isSending) {
            isSending = true;
            mPresenter.sendCode(trim);
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        String autoToken = loginBean.getAuto_token();
        int expireTime = loginBean.getExpire_time();
        int partId = loginBean.getPart_id();
        String partName = loginBean.getPart_name();
        String name = loginBean.getName();
        String mobile = loginBean.getMobile();
        int position = loginBean.getPosition();
        int userid = loginBean.getUserid();
        String img = loginBean.getImg();

//        SpUtil.putString(Constants.AUTO_TOKEN, autoToken);
//        SpUtil.putInt(Constants.EXPIRE_TIME, expireTime);
//        SpUtil.putInt(Constants.PART_ID, partId);
//        SpUtil.putInt(Constants.USER_ID, userid);
//        SpUtil.putString(Constants.MOBILE, mobile);

        //阿里云推送绑定账号
        AnalyticsHome.bindAccount(mobile);

        /******** 猎鹰相关 ********/
        SpUtil.putLong(Constants.IS_TRACK, loginBean.getIs_track(), false);
        TrackInfoBean trackInfo = loginBean.track_info;
        if (trackInfo != null) {
            SpUtil.putLong(Constants.SERVICE_ID, trackInfo.getSid(), false);
            SpUtil.putLong(Constants.TERMINAL_ID, trackInfo.getTid(), false);
            SpUtil.putLong(Constants.TRACK_ID, trackInfo.getTrid(), false);
        }

        Intent intent = new Intent(this, LoginConfirmActivity.class);
        intent.putExtra("isAdmin", true);
        intent.putExtra("autoToken", autoToken);
        intent.putExtra("expireTime", expireTime);
        intent.putExtra("partId", partId);
        intent.putExtra("userid", userid);
        intent.putExtra("phone", mobile);
        intent.putExtra("name", name);
        intent.putExtra("partName", partName);
        intent.putExtra("position", position);
        intent.putExtra("img", img);
        startActivity(intent);
//        finish();
    }

    @Override
    public boolean handleMessage(Message msg) {
        int value = (int) msg.obj;
        if (tvCode != null) {
            handleCode(value);
        }

        if (value == 0) {
            isSending = false;
            handleCode(value);
        } else {
            Message message = Message.obtain();
            message.what = 100;
            message.obj = value - 1;
            if (mHandler != null) {
                mHandler.sendMessageDelayed(message, 1000);
            }
        }

        return true;
    }

    private void handleCode(int value) {
        if (tvCode == null || llCode == null) {
            return;
        }
        if (isSending) {
            tvTime.setText(String.valueOf(value));
            tvCode.setVisibility(View.INVISIBLE);
            llCode.setVisibility(View.VISIBLE);
        } else {
            tvCode.setVisibility(View.VISIBLE);
            llCode.setVisibility(View.INVISIBLE);
        }

    }

    private void toFinish() {
        if (mLoginReceiver != null) {
            unregisterReceiver(mLoginReceiver);
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(100);
            mHandler = null;
        }
    }

    @Override
    public void sendCodeSuccess() {
        if (mHandler != null) {
            mHandler.obtainMessage(100, 60).sendToTarget();
        }
    }

    @Override
    public void sendCodeError() {
        isSending = false;
    }

    private static class LoginReceiver extends BroadcastReceiver {
        private WeakReference<LoginActivity> mLoginActivityDef;

        public LoginReceiver(LoginActivity loginActivity) {
            mLoginActivityDef = new WeakReference<>(loginActivity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }

            if (ACTION_LOGIN_CONFIRM_SUCCESS.equals(intent.getAction())) {
                if (mLoginActivityDef.get() != null) {
                    mLoginActivityDef.get().toFinish();
                }
            }
        }
    }
}
