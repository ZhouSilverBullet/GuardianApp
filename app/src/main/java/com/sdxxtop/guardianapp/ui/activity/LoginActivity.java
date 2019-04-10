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

import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.model.bean.LoginBean;
import com.sdxxtop.guardianapp.presenter.LoginPresenter;
import com.sdxxtop.guardianapp.presenter.contract.LoginContract;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.IView, Handler.Callback {
    public static final String ACTION_LOGIN_CONFIRM_SUCCESS = "action_login_confirm_success";

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
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
            mobile = "18000000000";
        }
        etPhone.setText(mobile);
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

        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });
    }

    private void toLogin() {
        String trim = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            UIUtils.showToast("账号不能为空");
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

        if (!isSending) {
            mHandler.obtainMessage(100, 60).sendToTarget();
            isSending = true;

            String trim = etPhone.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                UIUtils.showToast("账号不能为空");
                return;
            }
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

        SpUtil.putString(Constants.AUTO_TOKEN, autoToken);
        SpUtil.putInt(Constants.EXPIRE_TIME, expireTime);
        SpUtil.putInt(Constants.PART_ID, partId);
        SpUtil.putInt(Constants.USER_ID, userid);
        SpUtil.putString(Constants.MOBILE, mobile);

        Intent intent = new Intent(this, LoginConfirmActivity.class);
        String trim = etPhone.getText().toString().trim();

        intent.putExtra("isAdmin", true);
        intent.putExtra("phone", mobile);
        intent.putExtra("name", name);
        intent.putExtra("partName", partName);
        intent.putExtra("position", position);
        startActivity(intent);
//        finish();
    }

    @Override
    public boolean handleMessage(Message msg) {
        int value = (int) msg.obj;
        if (btnCode != null) {
            btnCode.setText("获取验证码" + value);
            btnCode.setTextColor(getResources().getColor(R.color.color_999999));
        }

        if (value == 0) {
            isSending = false;
            if (btnCode != null) {
                btnCode.setText("获取验证码");
                btnCode.setTextColor(getResources().getColor(R.color.color_303030));
            }
        } else {
            Message message = Message.obtain();
            message.what = 100;
            message.obj = value - 1;
            mHandler.sendMessageDelayed(message, 1000);
        }

        return true;
    }

    private void toFinish() {
        if (mLoginReceiver != null) {
            unregisterReceiver(mLoginReceiver);
        }

        finish();
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
