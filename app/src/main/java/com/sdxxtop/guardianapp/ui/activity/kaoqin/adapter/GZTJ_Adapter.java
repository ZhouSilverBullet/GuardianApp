package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.GztjMonthBean;

import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc:
 */
public class GZTJ_Adapter extends BaseQuickAdapter<GztjMonthBean.SignLogBean, BaseViewHolder> {

    private int clickPosition = -1;

    public GZTJ_Adapter() {
        super(R.layout.item_gztj_view);
    }

    @Override
    public void replaceData(@NonNull Collection<? extends GztjMonthBean.SignLogBean> data) {
        clickPosition = -1;
        super.replaceData(data);
    }

    @SuppressLint("NewApi")
    @Override
    protected void convert(BaseViewHolder helper, GztjMonthBean.SignLogBean item) {
        TextView tvDesc = helper.getView(R.id.tvDesc);
        ImageView icon = helper.getView(R.id.icon);
        SmartRefreshLayout smartRefresh = helper.getView(R.id.smart_refresh);

        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        tvDesc.setText(item.title);

        if (item.sign_score != null && item.sign_score.size() > 0) {
            SecondAdapter adapter = new SecondAdapter();
            recyclerView.setAdapter(adapter);
            adapter.replaceData(item.sign_score);
            icon.setVisibility(View.VISIBLE);
        } else {
            icon.setVisibility(View.GONE);
        }

        if (clickPosition == -1) {
            icon.setImageResource(R.drawable.assess_icon_list_down);
        }
        
        if (item.sign_score.size() == 0) {
            return;
        }

        if (helper.getAdapterPosition() == clickPosition) {
            tvDesc.setTextColor(mContext.getColor(R.color.black));
            smartRefresh.setVisibility(View.VISIBLE);
        } else {
            tvDesc.setTextColor(mContext.getColor(R.color.color_999999));
            smartRefresh.setVisibility(View.GONE);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickPosition == helper.getAdapterPosition()) {
                    clickPosition = -1;
                } else {
                    clickPosition = helper.getAdapterPosition();
                }

                if (smartRefresh.getVisibility() == View.VISIBLE) {
                    icon.setImageResource(R.drawable.assess_icon_list_down);
                } else {
                    icon.setImageResource(R.drawable.assess_icon_list_up);
                }
                notifyDataSetChanged();
            }
        });
    }


    class SecondAdapter extends BaseQuickAdapter<GztjMonthBean.SignScoreBean, BaseViewHolder> {

        public SecondAdapter() {
            super(R.layout.item_gztj_second_view);
        }

        @Override
        protected void convert(BaseViewHolder helper, GztjMonthBean.SignScoreBean item) {
            helper.setText(R.id.tvTitle, item.category_name);
            helper.setText(R.id.tvScore, item.score);
        }
    }

}
