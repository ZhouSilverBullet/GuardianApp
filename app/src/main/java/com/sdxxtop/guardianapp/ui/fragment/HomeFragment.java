package com.sdxxtop.guardianapp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.sdxxtop.guardianapp.ui.activity.ContactActivity;
import com.sdxxtop.guardianapp.ui.activity.GridMapActivity;
import com.sdxxtop.guardianapp.ui.activity.MyFaceLivenessActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolRecordActivity;
import com.sdxxtop.guardianapp.ui.adapter.HomeRecyclerAdapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.GuardianUtils;
import com.sdxxtop.guardianapp.utils.SpUtil;

import java.util.ArrayList;
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

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_place)
    TextView tvPlace;

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_container)
    LinearLayout mllContainer;
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
//        ArrayList<Integer> data = getRecyclerData(isAdmin);

        mRecyclerAdapter = new HomeRecyclerAdapter(R.layout.item_home_recycler, new ArrayList<>());
        mRecyclerView.setAdapter(mRecyclerAdapter);

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
                            if (mIsFace) {
                                intent = new Intent(getContext(), MyFaceLivenessActivity.class);
                                intent.putExtra("isFace", true);
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
                .setMsg("您还没注册人脸，是否去注册人脸")
                .setPositiveButton("", new View.OnClickListener() {
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
        tvName.setText(mainIndexBean.getName());
        String jobName = GuardianUtils.getJobName(mainIndexBean.getPosition());
        String partName = mainIndexBean.getPart_name();
        tvPlace.setText(new StringBuilder().append(partName).append(" ").append(jobName));

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
