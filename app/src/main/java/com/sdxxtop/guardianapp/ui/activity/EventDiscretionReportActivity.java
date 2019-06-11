package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.PatrolAddBean;
import com.sdxxtop.guardianapp.presenter.EventDiscretionReportPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventDiscretionReportContract;
import com.sdxxtop.guardianapp.ui.adapter.EventSearchTitleAdapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.CustomVideoImgSelectView;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.ui.widget.TextAndCheckBoxView;
import com.sdxxtop.guardianapp.ui.widget.TextAndEditView;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.MyTextChangeListener;
import com.sdxxtop.guardianapp.utils.TimeSelectBottomDialog;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventDiscretionReportActivity extends BaseMvpActivity<EventDiscretionReportPresenter> implements EventDiscretionReportContract.IView {

    @BindView(R.id.cvisv_view)
    CustomVideoImgSelectView cvisvView;
    @BindView(R.id.taev_title)
    TextAndEditView taevTitle;
    @BindView(R.id.tatv_happen)
    TextAndTextView tatvHappen;
    @BindView(R.id.tatv_end_time)
    TextAndTextView tatvEndTime;
    @BindView(R.id.net_content)
    NumberEditTextView netContent;
    @BindView(R.id.tacbv_view)
    TextAndCheckBoxView tacbvView;
    @BindView(R.id.tv_dismiss)
    TextView tvDismiss;
    @BindView(R.id.title_recycler)
    RecyclerView titleRecycler;
    @BindView(R.id.ll_search_data_layout)
    RelativeLayout llSearchDataLayout;
    @BindView(R.id.btn_push)
    Button btnPush;
    @BindView(R.id.titleView)
    TitleView titleView;

    private String lonLng;//经纬度
    private TimeSelectBottomDialog dialog;

    private EventSearchTitleAdapter titleAdapter;
    private boolean isSearchEnable = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_event_discretion_report;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        super.initView();
        InputFilter[] filters = {new InputFilter.LengthFilter(16)};
        taevTitle.getEditText().setFilters(filters);

        tatvEndTime.setTextRightImage2Show(true);
        dialog = new TimeSelectBottomDialog(this, tatvEndTime.getTextRightText());
        netContent.setEditHint("");
        netContent.setMaxLength(200);
        tacbvView.setOnCheckBoxClick(new TextAndCheckBoxView.OnCheckBoxClick() {
            @Override
            public void refreshStatus(boolean isShow) {
                tatvEndTime.setVisibility(isShow?View.VISIBLE:View.GONE);
                tatvEndTime.getTextRightText().setText("");
                tatvEndTime.getTextRightText().setHint("请选择整改时效");
            }
        });

        titleRecycler.setLayoutManager(new LinearLayoutManager(this));
        titleAdapter = new EventSearchTitleAdapter(R.layout.item_text, null);
        titleRecycler.setAdapter(titleAdapter);
        titleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter titleAdapter, View view, int position) {
                isSearchEnable = false;
                EventSearchTitleBean.KeyInfo item = (EventSearchTitleBean.KeyInfo) titleAdapter.getItem(position);
                taevTitle.getEditText().setText(item.getKeyword());
                taevTitle.getEditText().setSelection(item.getKeyword().length());
                llSearchDataLayout.setVisibility(View.GONE);
            }
        });

        taevTitle.getEditText().addTextChangedListener(new MyTextChangeListener(new MyTextChangeListener.OnTextChangedListener() {
            @Override
            public void textChange(String str) {
                if (isSearchEnable) {
                    mPresenter.searchTitle(str);
                } else {
                    isSearchEnable = true;
                }
            }
        }));

        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSearchDataLayout.setVisibility(View.GONE);
            }
        });

        titleView.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDiscretionReportActivity.this, EventDiscretionListActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.btn_push, R.id.tatv_happen, R.id.tatv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_push:
                showReportConfirmDialog();
                break;
            case R.id.tatv_happen:
                selectHappen();
                break;
            case R.id.tatv_end_time:
                if (tacbvView.getEnableClick()) {
                    if (dialog != null) {
                        dialog.show();
                    } else {
                        dialog = new TimeSelectBottomDialog(this, tatvEndTime.getTextRightText());
                        dialog.show();
                    }
                }
                break;
        }

    }

    private void selectHappen() {
        //由于启动map界面比较慢,所以弄个进度条,在回到页面的情况下 hideLoadingDialog
        showLoadingDialog();
        Intent intent = new Intent(this, AmapPoiActivity.class);
        startActivityForResult(intent, 100);
    }

    private void showReportConfirmDialog() {
        new IosAlertDialog(this).builder()
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toReport();
                    }
                })
                .setHeightMsg("确定上报事件?")
                .show();
    }

    // todo 网络请求
    private void toReport() {
        List<File> imagePushPath = cvisvView.getImageOrVideoPushPath(1);
        List<File> videoPushPath = cvisvView.getImageOrVideoPushPath(2);
        if (imagePushPath.size() == 0 && videoPushPath.size() == 0) {
            showToast("请选择图片或者视频");
            return;
        }

        String title = taevTitle.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请填写事件标题");
            return;
        }

        //发生地点
        String place = tatvHappen.getRightTVString();
        if (TextUtils.isEmpty(place) || TextUtils.isEmpty(lonLng)) {
            showToast("请选择发生地点");
            return;
        }

        String editValue = netContent.getEditValue();
        if (TextUtils.isEmpty(editValue)) {
            showToast("请填写事件描述内容");
            return;
        }

        String endTime = tatvEndTime.getRightTVString();
        if (tacbvView.getEnableClick() && TextUtils.isEmpty(endTime)) {
            showToast("请选择整改时效");
            return;
        }

        mPresenter.pushReport(tacbvView.getEnableClick() ? 1 : 2, title, place, lonLng, editValue, endTime, imagePushPath, videoPushPath);
    }


    /************************   获取地点/图片/视频 回调 *********************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hideLoadingDialog();
        if (resultCode == RESULT_OK) {
            cvisvView.callActivityResult(requestCode, resultCode, data);
        } else if (requestCode == 100 && resultCode == 10087 && data != null) {
            String address = data.getStringExtra("ad");
            String lt = data.getStringExtra("lt");
            lonLng = lt;
            tatvHappen.getTextRightText().setText(address);
        }
    }

    @Override
    public void skipDetail(PatrolAddBean bean) {
        hideLoadingDialog();
        Intent intent = new Intent(this, PatrolAddDetailActivity.class);
        intent.putExtra("patrol_id", bean.getPatrol_id());
        startActivity(intent);
        finish();
    }

    @Override
    public void showSearchData(EventSearchTitleBean bean) {
        if (bean.getKey_info() != null && bean.getKey_info().size() > 0) {
            llSearchDataLayout.setVisibility(View.VISIBLE);
            titleAdapter.replaceData(bean.getKey_info());
        } else {
            llSearchDataLayout.setVisibility(View.GONE);
        }
    }
}
