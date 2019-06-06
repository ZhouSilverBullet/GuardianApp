package com.sdxxtop.guardianapp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventReadIndexBean;
import com.sdxxtop.guardianapp.model.bean.MediaBean;
import com.sdxxtop.guardianapp.utils.Date2Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author :  lwb
 * Date: 2019/6/1
 * Desc:
 */
public class YanshouAdapter extends BaseQuickAdapter<EventReadIndexBean.ExtraInfoBean,BaseViewHolder> {

    public YanshouAdapter(int layoutResId, @Nullable List<EventReadIndexBean.ExtraInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventReadIndexBean.ExtraInfoBean item) {
        if (helper.getAdapterPosition()>=0){
            helper.setGone(R.id.v_line,false);
        }
        helper.setText(R.id.tv_yanshou_time,"验收时间："+ Date2Util.handleTime(item.getOperate_time()));
        helper.setText(R.id.tv_yanshou_result,"验收结果："+(item.getStatus()==4?"验收通过":"验收不通过"));
        helper.setText(R.id.tv_remark,(item.getStatus()==4?"备注: ":"验收不通过原因: ")+item.getExtra());

        //图片
        List<MediaBean> list = new ArrayList<>();
        if (!TextUtils.isEmpty(item.getVideo())){
            list.add(new MediaBean(item.getVideo(),2));
        }
        if (!TextUtils.isEmpty(item.getImg())){
            String[] split = item.getImg().split(",");
            for (int i = 0; i < split.length; i++) {
                list.add(new MediaBean(split[i],1));
            }
        }
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        if (list.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
            recyclerView.setAdapter(new PatrolDetailImgAdapter(R.layout.gv_filter_image,list));
        }else{
            recyclerView.setVisibility(View.GONE);
        }
    }

    private String handleShortTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = sdf.parse(time);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

}
