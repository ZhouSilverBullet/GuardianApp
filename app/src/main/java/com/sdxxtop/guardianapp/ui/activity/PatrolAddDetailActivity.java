package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.PatrolReadBean;
import com.sdxxtop.guardianapp.presenter.PatrolAddDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PatrolAddDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.PatrolDetailImgAdapter;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.Arrays;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class PatrolAddDetailActivity extends BaseMvpActivity<PatrolAddDetailPresenter> implements PatrolAddDetailContract.IView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_rectify_date)
    TextView tvRectifyDate;
    @BindView(R.id.recyclerview_check)
    RecyclerView recyclerviewCheck;
    @BindView(R.id.tv_content_check)
    TextView tvContentCheck;
    @BindView(R.id.ll_check_layout)
    LinearLayout llCheckLayout;
    @BindView(R.id.ll_containor)
    LinearLayout llContainor;
    @BindView(R.id.btn_push)
    Button btnReCheck;

    private int patrol_id;
    private PatrolDetailImgAdapter adapter, adapterCheck;

    @Override
    protected int getLayout() {
        return R.layout.activity_patrol_add_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {
        UIUtils.showToast(error);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.patrolRead(patrol_id);
    }

    @Override
    protected void initView() {
        super.initView();
        patrol_id = getIntent().getIntExtra("patrol_id", 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter = new PatrolDetailImgAdapter(R.layout.gv_filter_image, null);
        recyclerView.setAdapter(adapter);

        recyclerviewCheck.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapterCheck = new PatrolDetailImgAdapter(R.layout.gv_filter_image, null);
        recyclerviewCheck.setAdapter(adapterCheck);
    }

    @Override
    public void showData(PatrolReadBean bean) {
        if (bean.getStatus() == 1) {
            llContainor.setVisibility(View.VISIBLE);
        } else {
            llContainor.setVisibility(View.GONE);
        }
        tvTitle.setText(bean.getTitle());
        tvPlace.setText("发生地点：" + bean.getPlace());
        tvContent.setText(bean.getContent());
        tvRectifyDate.setText("整改时限：" + bean.getRectify_date());
        adapter.replaceData(Arrays.asList(bean.getImg().split(",")));

        /*************** 复查 *****************/
        if (TextUtils.isEmpty(bean.getCheck_vedio()) && TextUtils.isEmpty(bean.getCheck_img())) {
            llCheckLayout.setVisibility(View.GONE);
        } else {
            llCheckLayout.setVisibility(View.VISIBLE);
        }
        adapterCheck.replaceData(Arrays.asList(bean.getCheck_img().split(",")));
        tvContentCheck.setText(bean.getCheck_content());

    }

    @OnClick(R.id.btn_push)
    public void onViewClicked() {
        Intent intent = new Intent(this,ReCheckActivity.class);
        intent.putExtra("patrol_id",patrol_id);
        startActivity(intent);
    }
}
