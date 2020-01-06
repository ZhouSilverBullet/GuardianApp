package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sdxxtop.guardianapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc:
 */
public class GZTJ_Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int clickPosition = -1;

    public GZTJ_Adapter() {
        super(R.layout.item_gztj_view);
    }

    @SuppressLint("NewApi")
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tvDesc = helper.getView(R.id.tvDesc);
        SmartRefreshLayout smartRefresh = helper.getView(R.id.smart_refresh);

        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        SecondAdapter adapter = new SecondAdapter();
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        recyclerView.setAdapter(adapter);
        adapter.replaceData(list);

        if (helper.getAdapterPosition() == clickPosition) {
            tvDesc.setTextColor(mContext.getColor(R.color.black));
            smartRefresh.setVisibility(View.VISIBLE);
        }else{
            tvDesc.setTextColor(mContext.getColor(R.color.color_999999));
            smartRefresh.setVisibility(View.GONE);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickPosition == helper.getAdapterPosition()){
                    clickPosition = -1;
                }else{
                    clickPosition = helper.getAdapterPosition();
                }
                notifyDataSetChanged();
            }
        });
    }


    class SecondAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public SecondAdapter() {
            super(R.layout.item_gztj_second_view);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

}
