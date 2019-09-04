package com.sdxxtop.guardianapp.ui.widget.autoscroll;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.FlyEventDetailBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * author      : Z_B
 * date       : 2018/10/18
 * function  : 垂直滚动的RecyclerView的adapter
 */
public class NoticeRecyclerViewAdapter extends BaseQuickAdapter<FlyEventDetailBean.UavExcel, BaseViewHolder> {


    public NoticeRecyclerViewAdapter(@Nullable List<FlyEventDetailBean.UavExcel> data) {
        super(R.layout.item_device_fly_data, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FlyEventDetailBean.UavExcel item) {
        helper.setText(R.id.tv_num, "" + item.sort);
        helper.setText(R.id.tv_time,item.date);
        helper.setText(R.id.tv_pm1, "" + item.tpfpm);
        helper.setText(R.id.tv_pm2, "" + item.tenpm);
        helper.setText(R.id.tv_temperature, "" + item.temperature);
        helper.setText(R.id.tv_humidity, "" + item.humidity);
    }

    @Nullable
    @Override
    public FlyEventDetailBean.UavExcel getItem(int position) {
        int newPosition = position % getData().size();
        return getData().get(newPosition);
    }

    @Override
    public int getItemViewType(int position) {
        //刚开始进入包含该类的activity时,count为0。就会出现0%0的情况，这会抛出异常，所以我们要在下面做一下判断
        int count = getHeaderLayoutCount() + getData().size();
        if (count <= 0) {
            count = 1;
        }
        int newPosition = position % count;
        return super.getItemViewType(newPosition);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

}
