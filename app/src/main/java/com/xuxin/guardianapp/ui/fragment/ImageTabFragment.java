package com.xuxin.guardianapp.ui.fragment;

import android.os.Bundle;
import android.widget.ImageView;

import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.base.BaseFragment;

import butterknife.BindView;

public class ImageTabFragment extends BaseFragment {
    @BindView(R.id.iv_tab)
    ImageView ivTab;

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
        int img = getArguments().getInt("img");
        ivTab.setImageResource(img);
    }
}
