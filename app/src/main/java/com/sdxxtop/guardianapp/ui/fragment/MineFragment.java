package com.sdxxtop.guardianapp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.UcenterIndexBean;
import com.sdxxtop.guardianapp.model.db.UserData;
import com.sdxxtop.guardianapp.presenter.MinePresenter;
import com.sdxxtop.guardianapp.presenter.contract.MineContract;
import com.sdxxtop.guardianapp.ui.activity.CenterMessageActivity;
import com.sdxxtop.guardianapp.ui.activity.ContactActivity;
import com.sdxxtop.guardianapp.ui.activity.LoginActivity;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.imgservice.OnlineServiceActivity;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.IView {


    @BindView(R.id.civ_header)
    CircleImageView civHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.ll_contacts)
    LinearLayout llContacts;
    @BindView(R.id.ll_message)
    LinearLayout llMessage;
    @BindView(R.id.ll_online_service)
    LinearLayout llOnlineService;
    @BindView(R.id.ll_banben)
    LinearLayout llBanben;
    @BindView(R.id.ll_guanyu)
    LinearLayout llGuanyu;

    private boolean isAdmin;

    private int IMAGE_STORE = 100;
    private String mPartName;
    private String partUnit; //什么单位
    private int is_mail; //是否有权限进入通讯录

    public static MineFragment newInstance(boolean isAdmin) {
        Bundle args = new Bundle();
        args.putBoolean("isAdmin", isAdmin);
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(false);
        if (getArguments() != null) {
            isAdmin = getArguments().getBoolean("isAdmin");
        }
//        topViewPadding(mTitleView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData();
    }

    private void logoutDialog() {
        new IosAlertDialog(getActivity()).builder()
                .setHeightMsg("确定退出吗?")
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout();
                    }
                })
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
            mPresenter.loadData();
        }
    }

    public void logout() {
        UserData.getInstance().logout();

        Intent intent = new Intent(getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showList(UcenterIndexBean bean) {
        is_mail = bean.is_mail;
        String img = bean.img;
        if (!TextUtils.isEmpty(img)) {
            SpUtil.putString(Constants.USER_IMG, img);
            Glide.with(this).load(img).into(civHeader);
        }
        tvName.setText(bean.name);
        tvPlace.setText(new StringBuilder().append(bean.part_name).append(" ").append(bean.getStringPosition()));
        mPartName = bean.part_name;
        switch (bean.type) { //1:区级 2: 乡镇 3:企业
            case 1:
                partUnit = "区级单位";
                break;
            case 2:
                partUnit = "街道单位";
                break;
            case 3:
                partUnit = "企业单位";
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_STORE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList != null && selectList.size() > 0) {
                    LocalMedia media = selectList.get(0);
                    String path = media.getCompressPath();
                    if (TextUtils.isEmpty(path)) {
                        path = media.getCutPath();
                    }
                    if (!TextUtils.isEmpty(path)) {
                        mPresenter.changeIcon(path);
                    } else {
                        UIUtils.showToast("上传头像失败");
                    }
                }
            }
        }
    }

    @Override
    public void changeIconSuccess() {
        mPresenter.loadData();
    }

    @OnClick({R.id.civ_header, R.id.ll_contacts, R.id.ll_message, R.id.ll_online_service, R.id.ll_banben, R.id.ll_guanyu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_header:
                PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                        .cropCompressQuality(90)
                        .minimumCompressSize(200)
                        .withAspectRatio(1, 1)
                        .enableCrop(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .circleDimmedLayer(true)
                        .compress(true).selectionMode(PictureConfig.SINGLE).maxSelectNum(1).forResult(IMAGE_STORE);
                break;
            case R.id.ll_contacts:
                if (is_mail == 1) {
                    startActivity(new Intent(getContext(), ContactActivity.class));
                }
                break;
            case R.id.ll_message:
                Intent messageIntent = new Intent(getActivity(), CenterMessageActivity.class);
                startActivity(messageIntent);
                break;
            case R.id.ll_online_service:
                Intent intent = new Intent(getContext(), OnlineServiceActivity.class);
                intent.putExtra("href", "https://tb.53kf.com/code/client/b722216fa3d928f41a494d544ac54dcb/2?device=android");
                startActivity(intent);
                break;
            case R.id.ll_banben:
                break;
            case R.id.ll_guanyu:
                break;
        }
    }
}
