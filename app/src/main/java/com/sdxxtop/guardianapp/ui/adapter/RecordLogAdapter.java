package com.sdxxtop.guardianapp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.MediaBean;
import com.sdxxtop.guardianapp.model.bean.RecordLogBean;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author :  lwb
 * Date: 2019/12/2
 * Desc:
 */
public class RecordLogAdapter extends BaseQuickAdapter<RecordLogBean.EventLogBean, BaseViewHolder> {

    public RecordLogAdapter() {
        super(R.layout.item_record_log);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordLogBean.EventLogBean item) {
        View tvLine = helper.getView(R.id.tv_line);
        View tvVerLine = helper.getView(R.id.tv_ver_line);
        TextView tvTemp1 = helper.getView(R.id.tv_temp_1);
        TextView tvTemp2 = helper.getView(R.id.tv_temp_2);
        ConstraintLayout colImgLayout = helper.getView(R.id.col_img_layout);

        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        PatrolDetailImgAdapter adapter = new PatrolDetailImgAdapter(R.layout.gv_filter_image, new ArrayList<>());
        recyclerView.setAdapter(adapter);


        helper.setText(R.id.tv_num, "" + (helper.getPosition() + 1));
        helper.setText(R.id.tv_time, item.operate_time);
        helper.setText(R.id.tv_name, "操作人：" + item.name);
        helper.setText(R.id.tv_part, "操作人部门：" + item.part_name);
        helper.setText(R.id.tv_action, "操作功能：" + item.operazione);

        tvLine.setVisibility(helper.getPosition() == 0 ? View.VISIBLE : View.GONE);
        if (getData().size() > 0) {
            tvVerLine.setVisibility(helper.getPosition() == getData().size() - 1 ? View.GONE : View.VISIBLE);
        }

        //type类型1、流转 2、联办 3、派发 4、解决 5、验收 6、无法解决 7、验收不通过 8、删除 9、驳回 10、撤销删除

        colImgLayout.setVisibility(View.GONE);
        tvTemp1.setVisibility(View.GONE);
        tvTemp2.setVisibility(View.GONE);
        switch (item.type) {
            case 1:  // 流转
                tvTemp1.setVisibility(View.VISIBLE);
                tvTemp2.setVisibility(View.VISIBLE);
                tvTemp1.setVisibility(TextUtils.isEmpty(item.part_name)?View.GONE:View.VISIBLE);
                tvTemp2.setVisibility(TextUtils.isEmpty(item.extra)?View.GONE:View.VISIBLE);
                tvTemp1.setText("被流转部门：" + item.part_name);
                tvTemp2.setText("流转原因：指挥中心" + item.extra);
                break;
            case 2:  // 联办
                tvTemp1.setVisibility(View.VISIBLE);
                tvTemp2.setVisibility(View.VISIBLE);
                tvTemp1.setVisibility(TextUtils.isEmpty(item.union_name)?View.GONE:View.VISIBLE);
                tvTemp2.setVisibility(TextUtils.isEmpty(item.extra)?View.GONE:View.VISIBLE);
                tvTemp1.setText("被联办部门：" + item.union_name);
                tvTemp2.setText("联办原因：" + item.extra);
                break;
            case 3:  // 派发
                tvTemp1.setVisibility(View.VISIBLE);
                tvTemp2.setVisibility(View.VISIBLE);
                tvTemp1.setVisibility(TextUtils.isEmpty(item.settle_name)?View.GONE:View.VISIBLE);
                tvTemp2.setVisibility(TextUtils.isEmpty(item.settle_part)?View.GONE:View.VISIBLE);
                tvTemp1.setText("被派发人：" + item.settle_name);
                tvTemp2.setText("被派发人部门：" + item.settle_part);
                break;
            case 4:   //解决
                tvTemp1.setVisibility(View.VISIBLE);
                tvTemp1.setVisibility(TextUtils.isEmpty(item.extra)?View.GONE:View.VISIBLE);
                tvTemp1.setText("解决描述：" + item.extra);
                if (!TextUtils.isEmpty(item.img)||!TextUtils.isEmpty(item.video)){
                    colImgLayout.setVisibility(View.VISIBLE);
                    bandImgAndVideo(item.img, item.video, recyclerView, adapter);

                }
                break;
            case 5:   //验收
                tvTemp1.setVisibility(View.VISIBLE);
                tvTemp1.setVisibility(TextUtils.isEmpty(item.extra)?View.GONE:View.VISIBLE);
                tvTemp1.setText("描述：" + item.extra);
                if (!TextUtils.isEmpty(item.img)||!TextUtils.isEmpty(item.video)){
                    colImgLayout.setVisibility(View.VISIBLE);
                    bandImgAndVideo(item.img, item.video, recyclerView, adapter);

                }
                break;
            case 6:   //无法解决
                tvTemp1.setVisibility(View.VISIBLE);
                tvTemp1.setVisibility(TextUtils.isEmpty(item.extra)?View.GONE:View.VISIBLE);
                tvTemp1.setText("无法解决原因：" + item.extra);
                break;
            case 7:   //验收不通过
                tvTemp1.setVisibility(View.VISIBLE);
                tvTemp1.setVisibility(TextUtils.isEmpty(item.extra)?View.GONE:View.VISIBLE);
                tvTemp1.setText("描述：" + item.extra);
                break;
            case 8:   //删除
            case 10:  //撤销删除
                break;
            case 9:  //驳回
                tvTemp1.setVisibility(View.VISIBLE);
                tvTemp1.setVisibility(TextUtils.isEmpty(item.extra)?View.GONE:View.VISIBLE);
                tvTemp1.setText("驳回原因：" + item.extra);
                break;
        }

    }

    private void bandImgAndVideo(String img, String vedio, RecyclerView recyclerView, PatrolDetailImgAdapter adapter) {
        List<MediaBean> list = new ArrayList<>();
        if (!TextUtils.isEmpty(vedio)) {
            list.add(new MediaBean(vedio, 2));
        }

        if (!TextUtils.isEmpty(img)) {
            String[] split = img.split(",");
            for (int i = 0; i < split.length; i++) {
                list.add(new MediaBean(split[i], 1));
            }
        }
        if (list.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter.replaceData(list);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }

}
