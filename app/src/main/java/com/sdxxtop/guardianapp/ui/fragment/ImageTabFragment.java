package com.sdxxtop.guardianapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.ui.activity.ExamineActivity;
import com.sdxxtop.guardianapp.ui.activity.VideoPlayActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageTabFragment extends BaseFragment {
    @BindView(R.id.iv_tab)
    ImageView ivTab;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_leanrning)
    TextView tvLeanrning;
    @BindView(R.id.tv_article)
    TextView tvArticle;
    @BindView(R.id.tv_leanrning_02)
    TextView tvLeanrning02;
    @BindView(R.id.ll_containor)
    LinearLayout ll_containor;
    @BindView(R.id.inclued_video)
    View inclued_video;

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

        if (mImg==R.drawable.course){
            ivTab.setVisibility(View.GONE);
            ll_containor.setVisibility(View.VISIBLE);
        }else{
            ivTab.setVisibility(View.VISIBLE);
            ll_containor.setVisibility(View.GONE);
        }

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

    @OnClick({R.id.tv_video, R.id.tv_leanrning, R.id.tv_article, R.id.tv_leanrning_02,R.id.ll_containor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_containor:
                Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                intent.putExtra("video_path","http://flv4mp4.people.com.cn/videofile6/pvmsvideo/2019/3/12/WuTing_c0035e0a4affb0af7e3f511efabb7b69.mp4");
                intent.putExtra("title","习近平的两会时间|在福建团，总书记讲话的三个关键词，你get到了吗？");
                getActivity().startActivity(intent);
                break;
        }
    }
}
