package com.sdxxtop.guardianapp.ui.activity;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.RecordLogBean;
import com.sdxxtop.guardianapp.presenter.RecordLogPresenter;
import com.sdxxtop.guardianapp.presenter.contract.RecordLogContract;
import com.sdxxtop.guardianapp.ui.adapter.RecordLogAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class RecordLogActivity extends BaseMvpActivity<RecordLogPresenter> implements RecordLogContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecordLogAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_record_log;
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
        String eventId = getIntent().getStringExtra("eventId");
        mPresenter.loadLog(eventId);
    }

    @Override
    protected void initView() {
        adapter = new RecordLogAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showData(RecordLogBean bean) {
        adapter.replaceData(bean.event_log);
    }
}
