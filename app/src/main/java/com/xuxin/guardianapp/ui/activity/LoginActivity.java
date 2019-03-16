package com.xuxin.guardianapp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseMvpActivity;
import com.xuxin.guardianapp.presenter.LoginPresenter;
import com.xuxin.guardianapp.presenter.contract.LoginContract;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.IView {
    @BindView(R.id.btn_login)
    Button btnLogin;

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
}
