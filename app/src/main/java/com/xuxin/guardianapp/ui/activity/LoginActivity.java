package com.xuxin.guardianapp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseMvpActivity;
import com.xuxin.guardianapp.presenter.LoginPresenter;
import com.xuxin.guardianapp.presenter.contract.LoginContract;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.IView, Handler.Callback {
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_code)
    Button btnCode;
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
        Intent intent = new Intent(this, HomeActivity.class);
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
}
