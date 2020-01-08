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
import com.sdxxtop.guardianapp.TrackService.TrackServiceUtil;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.UcenterIndexBean;
import com.sdxxtop.guardianapp.model.db.UserData;
import com.sdxxtop.guardianapp.presenter.MinePresenter;
import com.sdxxtop.guardianapp.presenter.contract.MineContract;
import com.sdxxtop.guardianapp.ui.activity.CenterMessageActivity;
import com.sdxxtop.guardianapp.ui.activity.ContactActivity;
import com.sdxxtop.guardianapp.ui.activity.LoginActivity;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.RatingBar;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.MineMenuAdapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;

    private boolean isAdmin;

    private int IMAGE_STORE = 100;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MineMenuAdapter());
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

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            mPresenter.loadData();
        }
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
            mPresenter.loadData();
        }
    }

    public void logout() {
        //关闭猎鹰
        TrackServiceUtil instance = TrackServiceUtil.getInstance();
        instance.stopTrackService();

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
        if (bean.grade==0){
            ratingbar.setVisibility(View.INVISIBLE);
        }else{
            ratingbar.setVisibility(View.VISIBLE);
            ratingbar.setStar(bean.grade);
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

    @OnClick({R.id.civ_header, R.id.ll_contacts, R.id.ll_message, R.id.rl_login_out})
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
                } else {
                    showToast("暂无权限");
                }
                break;
            case R.id.ll_message:
                Intent messageIntent = new Intent(getActivity(), CenterMessageActivity.class);
                startActivity(messageIntent);
                break;
            case R.id.rl_login_out:  // 退出
                logoutDialog();
                break;
        }
    }
}
