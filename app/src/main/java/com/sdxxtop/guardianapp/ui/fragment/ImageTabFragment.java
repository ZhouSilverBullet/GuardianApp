package com.sdxxtop.guardianapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.activity.ExamineActivity;

import butterknife.BindView;

public class ImageTabFragment extends BaseFragment {
    @BindView(R.id.iv_tab)
    ImageView ivTab;
    private int mImg;

    public static ImageTabFragment newInstance(int drawableId) {

        Bundle args = new Bundle();
        args.putInt("img", drawableId);
        ImageTabFragment fragment = new ImageTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_image_tab;
    }

    @Override
    protected void initView() {
        super.initView();
        mImg = getArguments().getInt("img");
        ivTab.setImageResource(mImg);

        ivTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipActivity();
            }
        });

    }

    private void skipActivity() {
        switch (mImg) {
            case R.drawable.exam:
                getContext().startActivity(new Intent(getContext(), ExamineActivity.class));
                break;
        }
    }
}
