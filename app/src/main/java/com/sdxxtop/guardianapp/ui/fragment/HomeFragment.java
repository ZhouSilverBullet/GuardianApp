package com.sdxxtop.guardianapp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        statusBar(false);

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
                startActivity(new Intent(getContext(), EventReportActivity.class));
            }
        });
        llEventDiscretion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EventDiscretionReportActivity.class));
            }
        });

        itlvView1.setOnClick(1);
        itlvView2.setOnClick(2);
        itlvView3.setOnClick(3);

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
//                            UIUtils.showToast("通讯录");
                            intent = new Intent(getContext(), ContactActivity.class);
                            break;
                        case 2:
//                            UIUtils.showToast("网格地图");
                            intent = new Intent(getContext(), GridMapActivity.class);
                            break;
                        case 3:
//                            UIUtils.showToast("打卡");

                            //判断一次打卡，gps是否打开

                            if (mIsFace) {
                                if (GpsUtils.isOPen(getContext())) {
                                    intent = new Intent(getContext(), MyFaceLivenessActivity.class);
                                    intent.putExtra("isFace", true);
                                } else {
                                    GpsUtils.showCode332ErrorDialog(getContext());
                                }
                            } else {
                                toFace();
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
        if (!TextUtils.isEmpty(mainIndexBean.getRotation_img())){
            List<String> bannerList = Arrays.asList(mainIndexBean.getRotation_img().split(","));
            banner.setImages(bannerList).setImageLoader(new GlideImageLoader()).start();
        }

        if (mainIndexBean.getPending_event()!=null&&mainIndexBean.getPending_event().size()>0){
            List<ImgAndTextLinearView.TagEventBean> data = new ArrayList<>();
            for (int i = 0; i < mainIndexBean.getPending_event().size(); i++) {
                MainIndexBean.PendingEventBean bean = mainIndexBean.getPending_event().get(i);
                data.add(new ImgAndTextLinearView.TagEventBean(bean.getEvent_id(),bean.getTitle(),bean.getEnd_date()));
            }
            itlvView1.setData(data);
        }else{
            itlvView1.setNoDate();
        }
        if (mainIndexBean.getAdd_event()!=null&&mainIndexBean.getAdd_event().size()>0){
            List<ImgAndTextLinearView.TagEventBean> data = new ArrayList<>();
            for (int i = 0; i < mainIndexBean.getAdd_event().size(); i++) {
                MainIndexBean.AddEventBean bean = mainIndexBean.getAdd_event().get(i);
                data.add(new ImgAndTextLinearView.TagEventBean(bean.getEvent_id(),bean.getTitle(),""));
            }
            itlvView2.setData(data);
        }else{
            itlvView2.setNoDate();
        }
        if (mainIndexBean.getAdd_patrol()!=null&&mainIndexBean.getAdd_patrol().size()>0){
            List<ImgAndTextLinearView.TagEventBean> data = new ArrayList<>();
            for (int i = 0; i < mainIndexBean.getAdd_patrol().size(); i++) {
                MainIndexBean.AddPatrolBean bean = mainIndexBean.getAdd_patrol().get(i);
                data.add(new ImgAndTextLinearView.TagEventBean(bean.getPatrol_id(),bean.getTitle(),bean.getRectify_date()));
            }
            itlvView3.setData(data);
        }else{
            itlvView3.setNoDate();
        }
        ivMessageIcon.setImageResource(mainIndexBean.getUnread_count()==0?R.drawable.message_normal:R.drawable.message_notice);
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
            statusBar(false);
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
}
