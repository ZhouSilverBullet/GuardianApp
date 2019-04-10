package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
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
        if (isAdmin) {
            tvJobs.setText("网格员");
        } else {
            tvJobs.setText("企业员工");
        }
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
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("isAdmin", isAdmin);
        startActivity(intent);
    }
}
