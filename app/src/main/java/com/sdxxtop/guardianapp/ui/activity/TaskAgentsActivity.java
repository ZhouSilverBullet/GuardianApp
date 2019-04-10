package com.sdxxtop.guardianapp.ui.activity;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.ui.adapter.TaskAgentsAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 代办任务
 */
public class TaskAgentsActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    private TaskAgentsAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_task_agents;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TaskAgentsAdapter(R.layout.item_task_agents_recycler);
        mRecyclerView.setAdapter(mAdapter);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("");
        strings.add("");
        strings.add("");
        mAdapter.addData(strings);
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
