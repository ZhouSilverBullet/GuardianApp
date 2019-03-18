package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.utils.UIUtils;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.ui.adapter.EventReportRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class EventReportActivity extends BaseActivity implements EventReportRecyclerAdapter.HorListener {
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_push)
    Button btnPush;

    private EventReportRecyclerAdapter mAdapter;
    private List<LocalMedia> localMediaList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report;
    }

    @Override
    protected void initView() {
        super.initView();
        setSwipeBackEnable(true);

        setPhotoRecycler(mRecyclerView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("上传成功");
                finish();
            }
        });
    }

    protected void setPhotoRecycler(RecyclerView recycler) {
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new EventReportRecyclerAdapter(R.layout.item_event_report_recycler);
        recycler.setAdapter(mAdapter);
        LocalMedia localMedia = new LocalMedia();
        localMedia.setDuration(-1);
        localMediaList.add(localMedia);
        localMedia = new LocalMedia();
        localMedia.setDuration(-100);
        localMediaList.add(localMedia);
        mAdapter.addData(localMediaList);
        mAdapter.setListener(this);
    }

    @Override
    public void click() {
        goGallery();
    }

    public void removeLocalListTemp() {
        for (int i = 0; i < localMediaList.size(); i++) {
            if (localMediaList.get(i).getDuration() == -100) {
                localMediaList.remove(localMediaList.get(i));
                break;
            }
        }
    }

    public void goGallery() {
        removeLocalListTemp();

        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .compress(true)
                .selectionMedia(localMediaList)
                .maxSelectNum(9).forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void delete(LocalMedia item) {
        localMediaList.remove(item);
        for (int i = 0; i < localMediaList.size(); i++) {
            LocalMedia media = localMediaList.get(i);
            if (media.getDuration() == -100) {
                break;
            }
            if (i == localMediaList.size() - 1) {
                localMediaList.add(getTemp());
            }
        }

        //删除也是重新刷新数据
        //本来想定义 onDelete回调，发现也是 adapter.replaceData(),所以也用onResult 了
        onResult(localMediaList);
    }

    protected void onResult(List<LocalMedia> selectList) {
        if (mAdapter != null) {
            mAdapter.replaceData(selectList);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() > 0) {
                        localMediaList.clear();
                        int size = selectList.size();
                        if (size < 3) {
                            localMediaList.addAll(selectList);
                            localMediaList.add(getTemp());
                        } else {
                            localMediaList.addAll(selectList);
                        }
                        onResult(localMediaList);
                    }
                    break;
            }
        }
    }

    protected LocalMedia getTemp() {
        LocalMedia localMedia = new LocalMedia();
        localMedia.setDuration(-100);
        return localMedia;
    }
}
