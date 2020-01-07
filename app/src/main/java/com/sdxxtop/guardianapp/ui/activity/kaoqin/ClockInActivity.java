package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.KQST_Adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ClockInActivity extends BaseActivity {

    @BindView(R.id.civ_header)
    CircleImageView civHeader;
    @BindView(R.id.tvPosition)
    TextView tvPosition;
    @BindView(R.id.tvPart)
    TextView tvPart;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvClockIn)
    TextView tvClockIn;

    @Override
    protected int getLayout() {
        return R.layout.activity_clock_in;
    }

    @OnClick({R.id.tvClockIn,R.id.tvDate})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.tvClockIn:

                break;
            case R.id.tvDate:
                startActivity(new Intent(this,MineAttendanceActivity.class));
                break;
        }
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        KQST_Adapter adapter = new KQST_Adapter();
        recyclerView.setAdapter(adapter);
    }
}
