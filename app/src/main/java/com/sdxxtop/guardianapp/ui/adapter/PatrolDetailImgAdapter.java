package com.sdxxtop.guardianapp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/30
 * Desc:
 */
public class PatrolDetailImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PatrolDetailImgAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setGone(R.id.ll_del,false);
        Glide.with(mContext).load(item).into((ImageView) helper.getView(R.id.fiv));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast(item);
            }
        });
    }
}
