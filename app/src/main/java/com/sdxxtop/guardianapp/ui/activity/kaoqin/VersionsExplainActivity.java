package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.VersionExplainAdapter;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class VersionsExplainActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayout() {
        return R.layout.activity_versions_explain;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VersionExplainAdapter adapter = new VersionExplainAdapter();
        recyclerView.setAdapter(adapter);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter.replaceData(list);
    }

}
