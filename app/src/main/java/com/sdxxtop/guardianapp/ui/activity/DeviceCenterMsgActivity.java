package com.sdxxtop.guardianapp.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.UnreadNewslistBean;
import com.sdxxtop.guardianapp.presenter.DeviceCenterMsgPresenter;
import com.sdxxtop.guardianapp.presenter.contract.DeviceCenterMsgContract;
import com.sdxxtop.guardianapp.ui.adapter.DeviceCenterMsgAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class DeviceCenterMsgActivity extends BaseMvpActivity<DeviceCenterMsgPresenter> implements DeviceCenterMsgContract.IView {

    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_header)
    TextView tv_header;

    private DeviceCenterMsgAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_device_center_msg;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadData();
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DeviceCenterMsgAdapter(R.layout.item_device_message_info, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showData(UnreadNewslistBean bean) {
        List<UnreadNewslistBean.EventItemBean> data = bean.getOverdue_event();
        if (data != null) {
            adapter.replaceData(data);
        }
        if (data.size() > 0&&data!=null) {
            tv_header.setText("扬尘预警事件");
            tv_header.setVisibility(View.VISIBLE);
            tv_header.setBackgroundColor(Color.parseColor("#FAFBFF"));
        } else {
            tv_header.setVisibility(View.GONE);
        }
    }
}
