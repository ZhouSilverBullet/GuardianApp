package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;

import butterknife.BindView;

public class LoginConfirmActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_jobs)
    TextView tvJobs;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.btn_no_people)
    Button btnNoPeople;
    private boolean isAdmin;
    private String phone;
    private String name;
    private String partName;
    private int position;

    @Override
    protected int getLayout() {
        return R.layout.activity_login_confirm;
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        if (getIntent() != null) {
            isAdmin = getIntent().getBooleanExtra("isAdmin", false);
            phone = getIntent().getStringExtra("phone");
            name = getIntent().getStringExtra("name");
            partName = getIntent().getStringExtra("partName");
            position = getIntent().getIntExtra("position", 1);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        tvName.setText(name);
        if (!TextUtils.isEmpty(phone)) {
            tvPhone.setText(phone);
        } else {
            tvPhone.setText("13333333333");
        }
        tvCompany.setText(partName);

        handleJob(position);
    }

    private void handleJob(int position) {
        //1:网格员 2: 企业员工 3:街道管理员 4:区级管理员
        String positionName = "";
        switch (position) {
            case 1:
                positionName = "网格员";
                break;
            case 2:
                positionName = "企业员工";
                break;
            case 3:
                positionName = "街道管理员";
                break;
            case 4:
                positionName = "区级管理员";
                break;
        }
        tvJobs.setText(positionName);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });

        btnNoPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });
    }

    private void startActivity() {
        notifyLoginFinish();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("isAdmin", isAdmin);
        startActivity(intent);
        finish();
    }

    private void notifyLoginFinish() {
        Intent intent = new Intent(LoginActivity.ACTION_LOGIN_CONFIRM_SUCCESS);
        sendBroadcast(intent);
    }
}
