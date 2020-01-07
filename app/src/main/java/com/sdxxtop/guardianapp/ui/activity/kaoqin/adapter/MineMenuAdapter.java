package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.AAKt.AboutActivity;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.MyAssessActivity;
import com.sdxxtop.guardianapp.ui.widget.imgservice.OnlineServiceActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/12/31
 * Desc:
 */
public class MineMenuAdapter extends BaseQuickAdapter<MineMenuAdapter.Data, BaseViewHolder> {

    public MineMenuAdapter() {
        super(R.layout.item_mine_menu);

        List<Data> list = new ArrayList<>();
        list.add(new Data("我的考核", R.drawable.kh_my_new));
//        list.add(new Data("更换绑定人脸", R.drawable.change_face_my_new));
        list.add(new Data("在线客服", R.drawable.service_my_new));
        list.add(new Data("版本说明", R.drawable.set_my_new));
        list.add(new Data("关于我们", R.drawable.aboutus_my_new));

        replaceData(list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Data item) {
        TextView title = helper.getView(R.id.title);
        ImageView icon = helper.getView(R.id.icon);

        title.setText(item.title);
        icon.setImageResource(item.iconRes);

        helper.itemView.setOnClickListener(v -> {
            Intent intent = null;
            switch (item.title) {
                case "我的考核":
                    intent = new Intent(mContext, MyAssessActivity.class);
                    break;
                case "更换绑定人脸":
                    break;
                case "在线客服":
                    intent = new Intent(mContext, OnlineServiceActivity.class);
                    intent.putExtra("href", "https://tb.53kf.com/code/client/b722216fa3d928f41a494d544ac54dcb/2?device=android");
                    break;
                case "版本说明":
//                    intent = new Intent(mContext, VersionsExplainActivity.class);
                    break;
                case "关于我们":
                    intent = new Intent(mContext, AboutActivity.class);
                    break;
            }
            if (intent!=null){
                mContext.startActivity(intent);
            }
        });
    }

    static class Data {
        public String title;
        public int iconRes;

        public Data(String title, int iconRes) {
            this.title = title;
            this.iconRes = iconRes;
        }
    }
}
