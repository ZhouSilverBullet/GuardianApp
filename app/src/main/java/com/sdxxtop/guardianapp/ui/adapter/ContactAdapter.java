package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.ContactIndexBean;

public class ContactAdapter extends BaseQuickAdapter<ContactIndexBean.ContactBean, BaseViewHolder> {
    public ContactAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactIndexBean.ContactBean item) {
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvContent = helper.getView(R.id.tv_content);

        String name = item.getName();
        String img = item.getImg();

        if (!TextUtils.isEmpty(img)) {
            Glide.with(mContext).load(img).into(ivIcon);
        }

        tvName.setText(name);
        tvContent.setText(item.getPart_name());

        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.getMobile()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }
}
