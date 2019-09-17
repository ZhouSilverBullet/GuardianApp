package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.MediaBean;
import com.sdxxtop.guardianapp.model.bean.PatrolReadBean;
import com.sdxxtop.guardianapp.presenter.PatrolAddDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PatrolAddDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.PatrolDetailImgAdapter;
import com.sdxxtop.guardianapp.ui.pop.SelectMapPopView;
import com.sdxxtop.guardianapp.utils.SkipMapUtils;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class PatrolAddDetailActivity extends BaseMvpActivity<PatrolAddDetailPresenter> implements PatrolAddDetailContract.IView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_happen)
    TextView tvHappen;
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
    @BindView(R.id.tv_img_video_desc)
    TextView tvImgVideoDesc;

    private int patrol_id;
    private Boolean isPartEvent;  // 部门事件跳转到详情默认隐藏按钮
    private PatrolDetailImgAdapter adapter, adapterCheck;
    private String address = "";
    private String longitude = "";
    private List<MediaBean> data = new ArrayList<>();
    private List<MediaBean> checkData = new ArrayList<>();

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
        isPartEvent = getIntent().getBooleanExtra("isPartEvent", false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter = new PatrolDetailImgAdapter(R.layout.gv_filter_image, null);
        recyclerView.setAdapter(adapter);

        recyclerviewCheck.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapterCheck = new PatrolDetailImgAdapter(R.layout.gv_filter_image, null);
        recyclerviewCheck.setAdapter(adapterCheck);
    }

    @Override
    public void showData(PatrolReadBean bean) {
        address = bean.getPlace();
        longitude = bean.getLongitude();

        if (bean.getStatus() == 1) {
            if (isPartEvent) {   // 是部门事件把按钮隐藏
                llContainor.setVisibility(View.GONE);
            } else {
                llContainor.setVisibility(View.VISIBLE);
            }
        } else {
            llContainor.setVisibility(View.GONE);
        }
        tvTitle.setText(bean.getTitle());
        tvHappen.setText(bean.getPlace());
        tvContent.setText(bean.getContent());
        tvRectifyDate.setText("整改时限：" + bean.getRectify_date());

        //视频
        data.clear();
        if (!TextUtils.isEmpty(bean.getVideo())) {
            data.add(new MediaBean(bean.getVideo(), 2));
        }
        if (!TextUtils.isEmpty(bean.getImg())) {
            String[] split = bean.getImg().split(",");
            for (int i = 0; i < split.length; i++) {
                data.add(new MediaBean(split[i], 1));
            }
        }
        adapter.replaceData(data);

        /*************** 复查 *****************/
        llCheckLayout.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(bean.getCheck_video()) && TextUtils.isEmpty(bean.getCheck_img())) {
            recyclerviewCheck.setVisibility(View.GONE);
            tvImgVideoDesc.setVisibility(View.GONE);
            llCheckLayout.setVisibility(View.GONE);
        } else {
            llCheckLayout.setVisibility(View.VISIBLE);
            recyclerviewCheck.setVisibility(View.VISIBLE);
            tvImgVideoDesc.setVisibility(View.VISIBLE);
        }
        //复查视频
        checkData.clear();
        if (!TextUtils.isEmpty(bean.getCheck_video())) {
            checkData.add(new MediaBean(bean.getCheck_video(), 2));
        }
        if (!TextUtils.isEmpty(bean.getCheck_img())) {
            String[] split = bean.getCheck_img().split(",");
            for (int i = 0; i < split.length; i++) {
                checkData.add(new MediaBean(split[i], 1));
            }
        }
        adapterCheck.replaceData(checkData);

        tvContentCheck.setText(bean.getCheck_content());

    }

    @OnClick({R.id.btn_push, R.id.rl_happen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_push:
                Intent intent = new Intent(this, ReCheckActivity.class);
                intent.putExtra("patrol_id", patrol_id);
                startActivity(intent);
                break;
            case R.id.rl_happen:
                if (TextUtils.isEmpty(address)) {
                    return;
                }

                if (TextUtils.isEmpty(longitude)) {
                    showToast("经纬度为空");
                    return;
                }

                String[] split = longitude.split(",");
                if (split.length != 2) {
                    showToast("经纬度不合法");
                    return;
                }

                SelectMapPopView selectMapPopView =
                        new SelectMapPopView(this, findViewById(R.id.ll_root_layout), "高德地图（推荐）", "百度地图");
                selectMapPopView.setSelectMapClickListener(new SelectMapPopView.SelectMapClickListener() {
                    @Override
                    public void clickToGaode() {
                        SkipMapUtils.goToGaodeMap3(mContext, address, split[1], split[0]);
                    }

                    @Override
                    public void clickToBaidu() {
                        SkipMapUtils.goToBaiduMap3(mContext, address, split[1], split[0]);
                    }
                });

                break;
        }
    }
}
