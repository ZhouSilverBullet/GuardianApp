package com.sdxxtop.guardianapp.ui.activity;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.CenterMessage2Presenter;
import com.sdxxtop.guardianapp.presenter.contract.CenterMessage2Contract;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CenterMessage2Activity extends BaseMvpActivity<CenterMessage2Presenter> implements CenterMessage2Contract.IView {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String title;

    @Override
    protected int getLayout() {
        return R.layout.activity_center_message2;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        super.initView();
        title = getIntent().getStringExtra("name");
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.unreadNewslist(title);
    }
}
