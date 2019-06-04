package com.sdxxtop.guardianapp.ui.activity;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.UnreadIndexBean;
import com.sdxxtop.guardianapp.presenter.CenterMessagePresenter;
import com.sdxxtop.guardianapp.presenter.contract.CenterMessageContract;
import com.sdxxtop.guardianapp.ui.adapter.CenterMessageAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CenterMessageActivity extends BaseMvpActivity<CenterMessagePresenter> implements CenterMessageContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private CenterMessageAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_center_message;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.unreadIndex();
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CenterMessageAdapter(R.layout.item_center_message, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showData(UnreadIndexBean bean) {
        adapter.replaceData(bean.getUnreadIndexList());
    }
}
