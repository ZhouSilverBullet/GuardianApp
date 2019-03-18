package com.xuxin.guardianapp.ui.activity;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseActivity;
import com.xuxin.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ExamineActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.iv_examine)
    ImageView mImageView;

    private List<Integer> imgs;
    private int tempId;

    @Override
    protected int getLayout() {
        return R.layout.activity_examine;
    }

    @Override
    protected void initView() {
        super.initView();

        showStatusBar();

        if (imgs == null) {
            imgs = new ArrayList<>();
            imgs.add(R.drawable.questions_1);
            imgs.add(R.drawable.questions_2);
            imgs.add(R.drawable.questions_3);
            imgs.add(R.drawable.questions_4);
        }

        tempId = 0;

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempId++;
                mImageView.setImageResource(imgs.get(tempId % 4));
            }
        });
    }

    // https://blog.csdn.net/a872822645/article/details/74482323
    private void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }
}
