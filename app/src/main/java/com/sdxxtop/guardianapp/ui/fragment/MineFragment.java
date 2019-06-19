package com.sdxxtop.guardianapp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.sdxxtop.guardianapp.ui.activity.EventReportListActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantCompanyReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantEventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantGridReportActivity;
import com.sdxxtop.guardianapp.ui.activity.LoginActivity;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.pop.QuitGroupPopView;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.IView {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.civ_header)
    CircleImageView civHeader;
    /***权限***/
    @BindView(R.id.company_report)
    TextAndTextView companyReport;
    @BindView(R.id.grid_member_report)
    TextAndTextView gridMemberReport;
    @BindView(R.id.event_report)
    TextAndTextView eventReport;
    @BindView(R.id.message_center)
    TextAndTextView messageCenter;
    @BindView(R.id.tatv_message)
    TextAndTextView tatvMessage;
    @BindView(R.id.tatv_report)
    TextAndTextView tatvReport;


    private boolean isAdmin;

    private int IMAGE_STORE = 100;
    private String mPartName;
    private String partUnit; //什么单位

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
        topViewPadding(mTitleView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTitleView.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });
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
            statusBar(false);
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
    public void showList(UcenterIndexBean indexBean) {
        String img = indexBean.getImg();
        if (!TextUtils.isEmpty(img)) {
            SpUtil.putString(Constants.USER_IMG, img);
            Glide.with(this).load(img).into(civHeader);
        }
        tvName.setText(indexBean.getName());
        tvPlace.setText(new StringBuilder().append(indexBean.getPart_name()).append(" ").append(indexBean.getStringPosition()));
        mPartName = indexBean.getPart_name();
        int type = indexBean.getType();
        switch (type) { //1:区级 2: 乡镇 3:企业
            case 1:
                partUnit = "区级单位";
                break;
            case 2:
                partUnit = "乡镇单位";
                break;
            case 3:
                partUnit = "企业单位";
                break;
        }

//        if (indexBean.getPosition() == 3 || indexBean.getPosition() == 4) {
//            companyReport.setVisibility(View.GONE);
//            gridMemberReport.setVisibility(View.VISIBLE);
//            eventReport.setVisibility(View.VISIBLE);
//        } else {
//            companyReport.setVisibility(View.GONE);
//            gridMemberReport.setVisibility(View.GONE);
//            eventReport.setVisibility(View.GONE);
//        }

        tatvReport.setVisibility(indexBean.getIs_report() == 1 ? View.VISIBLE : View.GONE);
        eventReport.setVisibility(indexBean.getIs_event() == 1 ? View.VISIBLE : View.GONE);
        gridMemberReport.setVisibility(indexBean.getIs_guider() == 1 ? View.VISIBLE : View.GONE);
        companyReport.setVisibility(indexBean.getIs_business() == 1 ? View.VISIBLE : View.GONE);
        tatvMessage.setVisibility(indexBean.getIs_part() == 1 ? View.VISIBLE : View.GONE);

        messageCenter.setMessageCount(indexBean.getUnread_count());
    }

    @OnClick({R.id.civ_header, R.id.tatv_message, R.id.tatv_report, R.id.event_report, R.id.grid_member_report, R.id.company_report, R.id.message_center})
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
            case R.id.tatv_message:
                new QuitGroupPopView(getActivity(), mRootView, mPartName, partUnit);
                break;
            case R.id.tatv_report:
                startActivity(new Intent(getActivity(), EventReportListActivity.class));
                break;
            case R.id.event_report:
                startActivity(new Intent(getActivity(), GrantEventReportActivity.class));
                break;
            case R.id.grid_member_report: // 网格员报告
                Intent gridMemberIntent = new Intent(getActivity(), GrantGridReportActivity.class);
                startActivity(gridMemberIntent);
                break;
            case R.id.company_report:     // 企业报告
                Intent companyIntent = new Intent(getActivity(), GrantCompanyReportActivity.class);
                startActivity(companyIntent);
                break;
            case R.id.message_center:     // 消息中心
                Intent messageIntent = new Intent(getActivity(), CenterMessageActivity.class);
                startActivity(messageIntent);
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
}
