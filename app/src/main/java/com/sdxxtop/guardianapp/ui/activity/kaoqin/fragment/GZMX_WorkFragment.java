package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;

import android.view.View;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.GZMX_Adapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MyAssessCalendarView;
import com.sdxxtop.guardianapp.ui.widget.SingleStyleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc: GZMX = 工作明细
 */
public class GZMX_WorkFragment extends BaseFragment {
    @BindView(R.id.mcv_view)
    MyAssessCalendarView mcvView;
    @BindView(R.id.tvEventTypeSelect)
    TextView tvEventTypeSelect;
    @BindView(R.id.tvEventStatusSelect)
    TextView tvEventStatusSelect;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SingleStyleView typeSelect;
    private SingleStyleView eventStatusSelect;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_gzmx;
    }

    @Override
    protected void initView() {
        GZMX_Adapter adapter = new GZMX_Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        recyclerView.setAdapter(adapter);
        adapter.replaceData(list);

        List<SingleStyleView.ListDataBean> typeData = new ArrayList<>();
        typeData.add(new SingleStyleView.ListDataBean(1, "环保类"));
        typeData.add(new SingleStyleView.ListDataBean(2, "安全类"));
        typeData.add(new SingleStyleView.ListDataBean(3, "稳定类"));
        typeData.add(new SingleStyleView.ListDataBean(4, "城管类"));
        typeData.add(new SingleStyleView.ListDataBean(5, "其他类"));
        typeSelect = new SingleStyleView(getActivity(), typeData);
        typeData.clear();
        typeData.add(new SingleStyleView.ListDataBean(1, "已完成"));
        typeData.add(new SingleStyleView.ListDataBean(2, "已派发"));
        typeData.add(new SingleStyleView.ListDataBean(3, "未解决"));
        typeData.add(new SingleStyleView.ListDataBean(4, "已反馈"));
        typeData.add(new SingleStyleView.ListDataBean(5, "已解决"));
        eventStatusSelect = new SingleStyleView(getActivity(), typeData);

        typeSelect.setOnItemSelectLintener((id, result) -> tvEventTypeSelect.setText(result));
        eventStatusSelect.setOnItemSelectLintener((id, result) -> tvEventStatusSelect.setText(result));
    }

    @OnClick({R.id.tvEventTypeSelect, R.id.tvEventStatusSelect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvEventTypeSelect:
                if (typeSelect != null) {
                    typeSelect.show();
                }
                break;
            case R.id.tvEventStatusSelect:
                if (eventStatusSelect != null) {
                    eventStatusSelect.show();
                }
                break;
        }
    }
}
