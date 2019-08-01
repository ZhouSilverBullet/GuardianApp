package com.sdxxtop.guardianapp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.MainIndexBean;
import com.sdxxtop.guardianapp.presenter.HomeFragmentPresenter;
import com.sdxxtop.guardianapp.presenter.contract.HomeFragmentContract;
import com.sdxxtop.guardianapp.ui.activity.CenterMessageActivity;
import com.sdxxtop.guardianapp.ui.activity.ContactActivity;
import com.sdxxtop.guardianapp.ui.activity.EventDiscretionReportActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GridMapActivity;
import com.sdxxtop.guardianapp.ui.activity.MyFaceLivenessActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolRecordActivity;
import com.sdxxtop.guardianapp.ui.adapter.HomeRecyclerAdapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.ImgAndTextLinearView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.AMapFindLocation2;
import com.sdxxtop.guardianapp.utils.GlideImageLoader;
import com.sdxxtop.guardianapp.utils.GpsUtils;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link HomeFragment} subclass.
 */
public class HomeFragment extends BaseMvpFragment<HomeFragmentPresenter> implements HomeFragmentContract.IView {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.civ_header)
    CircleImageView civHeader;

    @BindView(R.id.tv_part_name)
    TextView tvPartName;
    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_container)
    LinearLayout mllContainer;

    @BindView(R.id.itlv_view_4)
    ImgAndTextLinearView itlvView4;
    @BindView(R.id.itlv_view_1)
    ImgAndTextLinearView itlvView1;
    @BindView(R.id.itlv_view_2)
    ImgAndTextLinearView itlvView2;
    @BindView(R.id.itlv_view_3)
    ImgAndTextLinearView itlvView3;
    @BindView(R.id.ll_event_report)
    LinearLayout llEventReport;
    @BindView(R.id.ll_event_discretion)
    LinearLayout llEventDiscretion;
    @BindView(R.id.iv_message_icon)
    ImageView ivMessageIcon;
    @BindView(R.id.ll_containor)
    LinearLayout llContainor;


    private boolean isAdmin;
    private HomeRecyclerAdapter mRecyclerAdapter;
    private boolean mIsFace;
    private int isReport;
    private int isPatrol;
    private int isMail;
    private int isMap;
    private int isClock;

    public static HomeFragment newInstance(boolean isAdmin) {

        Bundle args = new Bundle();
        args.putBoolean("isAdmin", isAdmin);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(true);

        if (getArguments() != null) {
            isAdmin = getArguments().getBoolean("isAdmin");
        }

        if (isAdmin) { // 管理员 姓名 地址
//            ivTop.setImageResource(R.drawable.top_1);

        } else {
//            ivTop.setImageResource(R.drawable.top_2);

        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerAdapter = new HomeRecyclerAdapter(R.layout.item_home_recycler, new ArrayList<>());
        mRecyclerView.setAdapter(mRecyclerAdapter);

        llEventReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReport == 1) {
                    startActivity(new Intent(getContext(), EventReportActivity.class));
                } else {
                    showToast("没有操作权限");
                }
            }
        });
        llEventDiscretion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPatrol == 1) {
                    startActivity(new Intent(getContext(), EventDiscretionReportActivity.class));
                } else {
                    showToast("没有操作权限");
                }
            }
        });

        itlvView1.setOnClick(1);
        itlvView2.setOnClick(2);
        itlvView3.setOnClick(3);
        itlvView4.setOnClick(4);

        llContainor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CenterMessageActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Integer> getRecyclerData(boolean isAdmin) {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.drawable.icon_1_list);
        data.add(R.drawable.icon_2_list);
//        if (isAdmin) {
//            data.add(R.drawable.list_3);
//            data.add(R.drawable.list_4);
//            data.add(R.drawable.list_5);
//        } else {
//            data.add(R.drawable.list_6);
//            data.add(R.drawable.list_5);
//        }
        return data;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadData();
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        tabEvent();
    }

    private void tabEvent() {
        for (int i = 0; i < mllContainer.getChildCount(); i++) {
            final int j = i;
            mllContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    switch (j) {
                        case 0:
//                            UIUtils.showToast("查询记录");
//                            SkipMapUtils.invokingGD(v.getContext(),"北京天池家园酒店","39.989652", "116.476604");
                            intent = new Intent(getContext(), PatrolRecordActivity.class);

                            break;
                        case 1:
                            if (isMail == 1) {
//                            UIUtils.showToast("通讯录");
                                intent = new Intent(getContext(), ContactActivity.class);
                            } else {
                                showToast("没有操作权限");
                            }
                            break;
                        case 2:
                            if (isMap == 1) {
//                            UIUtils.showToast("网格地图");
                                intent = new Intent(getContext(), GridMapActivity.class);
                            } else {
                                showToast("没有操作权限");
                            }
                            break;
                        case 3:
//                            UIUtils.showToast("打卡");

                            //判断一次打卡，gps是否打开
                            if (isClock == 1) {
                                if (mIsFace) {
                                    showLoadingDialog();
                                    if (GpsUtils.isOPen(getContext())) {
                                        AMapFindLocation2 instance = AMapFindLocation2.getInstance();
                                        instance.location();
                                        instance.setLocationCompanyListener(new AMapFindLocation2.LocationCompanyListener() {
                                            @Override
                                            public void onAddress(AMapLocation aMapLocation) {
                                                String address = aMapLocation.getAddress();
                                                if (TextUtils.isEmpty(address)) {
                                                    showToast("定位获取位置失败,请稍后重试");
                                                } else {
                                                    closeLoadingDialog();
                                                    instance.stopLocation();
                                                    Intent intent = new Intent(getContext(), MyFaceLivenessActivity.class);
                                                    intent.putExtra("isFace", true);
                                                    intent.putExtra("address", address);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                                    } else {
                                        GpsUtils.showCode332ErrorDialog(getContext());
                                    }
                                } else {
                                    toFace();
                                }
                            } else {
                                showToast("没有操作权限");
                            }
                            break;
                    }
                    if (intent != null) {
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void toFace() {
        new IosAlertDialog(getActivity())
                .builder()
                .setTitle("提示")
                .setMsg("您还没注册人脸信息，是否去录入")
                .setPositiveButton("录入", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), MyFaceLivenessActivity.class);
                        intent.putExtra("isFace", false);
                        startActivityForResult(intent, 100);
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
    public void showError(String error) {
//        mTextView.setText(error);
    }

    @Override
    public void showData(MainIndexBean mainIndexBean) {
        Logger.e("HomeFragment", mainIndexBean);
        /********* 权限 **********/
        isReport = mainIndexBean.getIs_report();
        isPatrol = mainIndexBean.getIs_patrol();
        isClock = mainIndexBean.getIs_clock();
        isMail = mainIndexBean.getIs_mail();
        isMap = mainIndexBean.getIs_map();

        itlvView2.setVisibility(isReport == 1 ? View.VISIBLE : View.GONE);
        itlvView3.setVisibility(isPatrol == 1 ? View.VISIBLE : View.GONE);

        tvPartName.setText(mainIndexBean.getPart_name());

        List<MainIndexBean.EventBean> eventBean = mainIndexBean.getEventBean();
        mRecyclerAdapter.replaceData(eventBean);
        String img = mainIndexBean.getImg();
        if (!TextUtils.isEmpty(img)) {
            SpUtil.putString(Constants.USER_IMG, img);
            Glide.with(this).load(img).into(civHeader);
        }
        // 1.注册成功 2.注册失败
        mIsFace = mainIndexBean.getIs_face() == 1;
//        mTextView.setText(data);

        /*********** 轮播图 ************/
        if (!TextUtils.isEmpty(mainIndexBean.getRotation_img())) {
            List<String> bannerList = Arrays.asList(mainIndexBean.getRotation_img().split(","));
            banner.setImages(bannerList).setImageLoader(new GlideImageLoader()).start();
        }

        if (mainIndexBean.getPending_event() != null && mainIndexBean.getPending_event().size() > 0) {
            List<ImgAndTextLinearView.TagEventBean> data = new ArrayList<>();
            for (int i = 0; i < mainIndexBean.getPending_event().size(); i++) {
                MainIndexBean.PendingEventBean bean = mainIndexBean.getPending_event().get(i);
                data.add(new ImgAndTextLinearView.TagEventBean(bean.getEvent_id(), bean.getTitle(), bean.getEnd_date(), getStatus(1, bean.getStatus(),
                        bean.getIs_claim())));
            }
            itlvView1.setData(data);
        } else {
            itlvView1.setNoDate();
        }
        if (mainIndexBean.getAdd_event() != null && mainIndexBean.getAdd_event().size() > 0) {
            List<ImgAndTextLinearView.TagEventBean> data = new ArrayList<>();
            for (int i = 0; i < mainIndexBean.getAdd_event().size(); i++) {
                MainIndexBean.AddEventBean bean = mainIndexBean.getAdd_event().get(i);
                data.add(new ImgAndTextLinearView.TagEventBean(bean.getEvent_id(), bean.getTitle(), "", getStatus(2, bean.getStatus(), bean.getIs_claim())));
            }
            itlvView2.setData(data);
        } else {
            itlvView2.setNoDate();
        }
        if (mainIndexBean.getAdd_patrol() != null && mainIndexBean.getAdd_patrol().size() > 0) {
            List<ImgAndTextLinearView.TagEventBean> data = new ArrayList<>();
            for (int i = 0; i < mainIndexBean.getAdd_patrol().size(); i++) {
                MainIndexBean.AddPatrolBean bean = mainIndexBean.getAdd_patrol().get(i);
                data.add(new ImgAndTextLinearView.TagEventBean(bean.getPatrol_id(), bean.getTitle(), bean.getRectify_date(), getStatus(3, bean.getStatus(),
                        0)));
            }
            itlvView3.setData(data);
        } else {
            itlvView3.setNoDate();
        }

        if (mainIndexBean.getPart_event() != null && mainIndexBean.getPart_event().size() > 0) {
            List<ImgAndTextLinearView.TagEventBean> data = new ArrayList<>();
            for (int i = 0; i < mainIndexBean.getPart_event().size(); i++) {
                MainIndexBean.PendingEventBean bean = mainIndexBean.getPart_event().get(i);
                data.add(new ImgAndTextLinearView.TagEventBean(bean.getEvent_id(), bean.getTitle(), bean.getEnd_date(), getStatus(4, bean.getStatus(),
                        bean.getIs_claim())));
            }
            itlvView4.setData(data);
        } else {
            itlvView4.setNoDate();
        }

        ivMessageIcon.setImageResource(mainIndexBean.getUnread_count() == 0 ? R.drawable.message_normal : R.drawable.message_notice);
    }

    @Override
    public void showInfo() {


        if (isAdmin) { // 管理员 姓名 地址
//            ivTop.setImageResource(R.drawable.top_1);

        } else {
//            ivTop.setImageResource(R.drawable.top_2);

        }

//        mRecyclerAdapter.add();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
            mPresenter.loadData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            mPresenter.loadData();
        }
    }

    public String getStatus(int type, int status, int isClaim) {
        String str = "";
        switch (type) {
            case 1:    // 代办任务
                if (isClaim == 1) {
                    switch (status) {
                        case 2:
                            str = "已认领";
                            break;
                        case 3:
                            str = "待评价";
                            break;
                        case 4:
                            str = "已完成";
                            break;
                        default:
                            str = "待认领";
                            break;
                    }
                } else {
                    switch (status) {
                        case 2:
                            str = "待解决";
                            break;
                        case 3:
                            str = "待验收";
                            break;
                    }
                }
                break;
            case 2:    // 我的上报
                if (isClaim == 1) {
                    switch (status) {
                        case 2:
                            str = "已认领";
                            break;
                        case 3:
                            str = "待评价";
                            break;
                        case 4:
                            str = "已完成";
                            break;
                        default:
                            str = "待认领";
                            break;
                    }
                } else {
                    switch (status) {
                        case 1:
                            str = "待派发";
                            break;
                        case 2:
                            str = "待解决";
                            break;
                        case 3:
                            str = "待验收";
                            break;
                        case 4:
                            str = "已完成";
                            break;
                        case 5:
                            str = "驳回";
                            break;
                    }
                }
                break;
            case 3:    //  我的巡查
                switch (status) {
                    case 2:
                        str = "已完成";
                        break;
                    case 1:
                        str = "待复查";
                        break;

                }
                break;
            case 4:    //部门事件认领状态 1、带认领 2、已认领 3、待评价 4、已完成
                switch (status) {
                    case 1:
                        str = "待认领";
                        break;
                    case 2:
                        str = "已认领";
                        break;
                    case 3:
                        str = "待评价";
                        break;
                    case 4:
                        str = "已完成";
                        break;
                }
                break;
        }
        return str;
    }
}
