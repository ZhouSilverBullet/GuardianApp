package com.sdxxtop.guardianapp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sdxxtop.guardianapp.presenter.LoginPresenter;
import com.sdxxtop.guardianapp.presenter.contract.LoginContract;
import com.sdxxtop.guardianapp.utils.UIUtils;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.IView, Handler.Callback {
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
    private Handler mHandler;
    private boolean isSending;

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

        etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        etPhone.setText("18000000000");
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login();
            }
        });

        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });
    }

    private void sendCode() {
        if (mHandler == null) {
            mHandler = new Handler(this);
        }

        if (!isSending) {
            mHandler.obtainMessage(100, 60).sendToTarget();
            isSending = true;
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this, LoginConfirmActivity.class);
        String trim = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            UIUtils.showToast("账号不能为空");
            return;
        }
        boolean isAdmin = trim.equals("18000000000");
        intent.putExtra("isAdmin", isAdmin);
        intent.putExtra("phone", trim);
        startActivity(intent);
        finish();
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

    private static class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
