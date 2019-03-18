package com.xuxin.guardianapp.ui.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;
import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseMvpFragment;
import com.xuxin.guardianapp.presenter.HomeFragmentPresenter;
import com.xuxin.guardianapp.presenter.contract.HomeFragmentContract;
import com.xuxin.guardianapp.ui.activity.ContactActivity;
import com.xuxin.guardianapp.ui.activity.GridMapActivity;
import com.xuxin.guardianapp.ui.activity.PatrolRecordActivity;
import com.xuxin.guardianapp.ui.adapter.HomeRecyclerAdapter;
import com.xuxin.guardianapp.ui.widget.TitleView;
import com.xuxin.guardianapp.utils.UIUtils;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link HomeFragment} subclass.
 */
public class HomeFragment extends BaseMvpFragment<HomeFragmentPresenter> implements HomeFragmentContract.IView {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_container)
    LinearLayout mllContainer;

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Integer> data = getRecyclerData();

        mRecyclerView.setAdapter(new HomeRecyclerAdapter(R.layout.item_home_recycler, data));

    }

    private ArrayList<Integer> getRecyclerData() {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.drawable.list_1);
        data.add(R.drawable.list_2);
        data.add(R.drawable.list_3);
        data.add(R.drawable.list_4);
        data.add(R.drawable.list_5);
        data.add(R.drawable.list_6);
        return data;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadSignData();
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
                            UIUtils.showToast("查询记录");
                            intent = new Intent(getContext(), PatrolRecordActivity.class);
                            break;
                        case 1:
                            UIUtils.showToast("通讯录");
                            intent = new Intent(getContext(), ContactActivity.class);
                            break;
                        case 2:
                            UIUtils.showToast("网格地图");
                            intent = new Intent(getContext(), GridMapActivity.class);
                            break;
                        case 3:
                            UIUtils.showToast("打卡");
                            break;
                    }
                    if (intent != null) {
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void showError(String error) {
//        mTextView.setText(error);
    }

    @Override
    public void showData(String data) {
        Logger.e(data);
//        mTextView.setText(data);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(false);
        }
    }

}
