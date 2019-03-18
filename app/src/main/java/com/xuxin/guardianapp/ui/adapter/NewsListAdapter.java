package com.xuxin.guardianapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.model.bean.LearnNewsBean;
import com.xuxin.guardianapp.ui.activity.NewsDetailsActivity;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/3/18
 * Desc:
 */
public class NewsListAdapter extends BaseMultiItemQuickAdapter<LearnNewsBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context mContext;
    private int mType;

    public NewsListAdapter(List<LearnNewsBean> data, Context context, int type) {
        super(data);
        addItemType(1, R.layout.item_learn_news_01);
        addItemType(2, R.layout.item_learn_news_02);
        this.mContext = context;
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, LearnNewsBean item) {
        switch (helper.getItemViewType()) {
            case 1:
                break;
            case 2:
                helper.setText(R.id.tv_title, item.getTitle());
                if (mType == 1) { // 新闻学习
                    if (item.getComment_num() == 0) {
                        helper.setText(R.id.tv_come_from, item.getSource());
                    } else {
                        helper.setText(R.id.tv_come_from, item.getSource() + "  " + item.getComment_num() + "评");
                    }
                } else {
                    helper.setText(R.id.tv_come_from, item.getComment_num() == 0 ? "" : item.getComment_num() + "评");
                }
                helper.setText(R.id.tv_time, item.getAdd_time());
                Glide.with(mContext).load(item.getTitle_img()).into((ImageView) helper.getView(R.id.iv_img));

                helper.getView(R.id.ll_containor).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                        intent.putExtra("article_id",item.getArticle_id());
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }
}
