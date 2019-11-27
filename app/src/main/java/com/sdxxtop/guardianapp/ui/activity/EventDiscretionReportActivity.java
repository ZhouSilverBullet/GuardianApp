package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.PatrolAddBean;
import com.sdxxtop.guardianapp.model.bean.ShowPartBean;
import com.sdxxtop.guardianapp.presenter.EventDiscretionReportPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventDiscretionReportContract;
import com.sdxxtop.guardianapp.ui.adapter.EventSearchTitleAdapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.CustomVideoImgSelectView;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.ui.widget.SingleStyleView;
import com.sdxxtop.guardianapp.ui.widget.TextAndEditView;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.MyTextWatcher;
import com.sdxxtop.guardianapp.utils.SingleClickListener;
import com.sdxxtop.guardianapp.utils.TimeSelectBottomDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventDiscretionReportActivity extends BaseMvpActivity<EventDiscretionReportPresenter> implements EventDiscretionReportContract.IView,
        SingleStyleView.OnItemSelectLintener {

    @BindView(R.id.cvisv_view)
    CustomVideoImgSelectView cvisvView;
    @BindView(R.id.taev_title)
    TextAndEditView taevTitle;
    @BindView(R.id.tatv_end_time)
    TextAndTextView tatvEndTime;
    @BindView(R.id.net_content)
    NumberEditTextView netContent;
    @BindView(R.id.title_recycler)
    RecyclerView titleRecycler;
    @BindView(R.id.ll_search_data_layout)
    RelativeLayout llSearchDataLayout;
    @BindView(R.id.btn_push)
    Button btnPush;
    @BindView(R.id.titleView)
    TitleView titleView;


    @BindView(R.id.col_happen)
    ConstraintLayout col_happen;
    @BindView(R.id.tv_place_title)
    TextView tvPlaceTitle;
    @BindView(R.id.tv_place_desc)
    TextView tvPlaceDesc;
    @BindView(R.id.tatv_event_type)
    TextAndTextView tatvEventType;
    @BindView(R.id.cb_into_voice)
    CheckBox cbIntoVoice;


    private String lonLng;//经纬度
    private TimeSelectBottomDialog dialog;

    private EventSearchTitleAdapter adapter;
    private boolean isSearchEnable = true;


    private List<SingleStyleView.ListDataBean> categoryList = new ArrayList<>();
    private SingleStyleView categorySelectView;
    private int category_id;   // 事件分类id

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
    protected void initData() {
        mPresenter.searchTitle();
    }

    @Override
    protected void initView() {
        super.initView();
        InputFilter[] filters = {new InputFilter.LengthFilter(16)};
        taevTitle.getEditText().setFilters(filters);
        cvisvView.setTvDesc(true);

        tatvEndTime.setTextRightImage2Show(true);
        dialog = new TimeSelectBottomDialog(this, tatvEndTime.getTextRightText());
        netContent.setEditHint("在此录入事件描述");
        netContent.setMaxLength(200);


        titleRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventSearchTitleAdapter();
        titleRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter titleAdapter, View view, int position) {
                isSearchEnable = false;
                ShowPartBean.KeywordInfoBean item = (ShowPartBean.KeywordInfoBean) titleAdapter.getItem(position);
                taevTitle.getEditText().setText(item.classify_keyword);
                llSearchDataLayout.setVisibility(View.GONE);
                mPresenter.keywordMatch(item.classify_keyword, item.classify_keyword_id);
            }
        });

        btnPush.setOnClickListener(new SingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                toReport();
            }
        });

        taevTitle.getEditText().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isSearchEnable) {
                    mPresenter.keywordMatch(s.toString().trim(), 0);
                } else {
                    isSearchEnable = true;
                }
            }
        });

//        tvDismiss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                llSearchDataLayout.setVisibility(View.GONE);
//            }
//        });

        titleView.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDiscretionReportActivity.this, EventDiscretionListActivity.class);
                startActivity(intent);
            }
        });

        categorySelectView = new SingleStyleView(this, categoryList);
        categorySelectView.setOnItemSelectLintener(this);

        cbIntoVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tatvEndTime.setVisibility(cbIntoVoice.isChecked() ? View.VISIBLE : View.GONE);
                tatvEndTime.getTextRightText().setText("");
                tatvEndTime.getTextRightText().setHint("请选择整改时效");
            }
        });
    }

    @OnClick({R.id.tatv_end_time, R.id.col_happen, R.id.tatv_event_type})
    public void onViewClicked(View view) {
        hideKeyboard(view);
        switch (view.getId()) {
            case R.id.tatv_event_type:
                if (categorySelectView != null) {
                    categorySelectView.show();
                }
                break;
            case R.id.col_happen:
                selectHappen();
                break;
            case R.id.tatv_end_time:
                if (cbIntoVoice.isChecked()) {
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
//                        toReport();
                    }
                })
                .setHeightMsg("确定上报事件?")
                .show();
    }

    // todo 网络请求
    private void toReport() {
        List<File> imagePushPath = cvisvView.getImageOrVideoPushPath(1);
        List<File> videoPushPath = cvisvView.getImageOrVideoPushPath(2);
        if ((imagePushPath.size() + videoPushPath.size()) < 3) {
            showToast("需要提供3个以上图片或者视频");
            return;
        }

        String title = taevTitle.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请填写事件标题");
            return;
        }

        if (category_id == 0) {
            showToast("请选择事件分类");
            return;
        }

        //发生地点
        String place = tvPlaceTitle.getText().toString();
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
        if (cbIntoVoice.isChecked() && TextUtils.isEmpty(endTime)) {
            showToast("请选择整改时效");
            return;
        }

        mPresenter.pushReport(cbIntoVoice.isChecked() ? 1 : 2, title, place, lonLng, editValue, endTime, imagePushPath, videoPushPath,category_id);
    }


    /************************   获取地点/图片/视频 回调 *********************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hideLoadingDialog();
        if (resultCode == RESULT_OK) {
            cvisvView.callActivityResult(requestCode, resultCode, data);
        } else if (requestCode == 100 && resultCode == 10087 && data != null) {
            String title = data.getStringExtra("ar");
            String desc = data.getStringExtra("ad");
            String lt = data.getStringExtra("lt");

            lonLng = lt;
            tvPlaceTitle.setText(title);
            tvPlaceDesc.setText(desc);
        }
    }

    @Override
    public void skipDetail(PatrolAddBean bean) {
        showToast("提交成功");
        hideLoadingDialog();
        Intent intent = new Intent(this, PatrolAddDetailActivity.class);
        intent.putExtra("patrol_id", bean.getPatrol_id());
        startActivity(intent);
        finish();
    }

    /**
     * 初始化信息
     *
     * @param bean
     */
    @Override
    public void showSearchData(EventSearchTitleBean bean) {
//        if (bean.getKey_info() != null && bean.getKey_info().size() > 0) {
//            llSearchDataLayout.setVisibility(View.VISIBLE);
//            titleAdapter.replaceData(bean.getKey_info());
//        } else {
//            llSearchDataLayout.setVisibility(View.GONE);
//        }

        //分类信息
        List<EventSearchTitleBean.CategoryBean> category = bean.category;
        categoryList.clear();
        for (EventSearchTitleBean.CategoryBean item : category) {
            categoryList.add(new SingleStyleView.ListDataBean(item.category_id, item.category_name));
        }
        categorySelectView.replaceData(categoryList);
    }

    /**
     * 展示搜索出来的标题
     *
     * @param
     */
    @Override
    public void showKeywordInfo(ShowPartBean bean, int keywordId) {
        ShowPartBean.CategoryInfoBean category = bean.category;
        if (category != null) {
            tatvEventType.getTextRightText().setText(category.category_name);
            category_id = category.category_id;
        }

        if (keywordId == 0) {   // 有id代表点击条目搜索不展示
            if (bean.list != null && bean.list.size() > 0) {
                llSearchDataLayout.setVisibility(View.VISIBLE);
                adapter.replaceData(bean.list);
            } else {
                llSearchDataLayout.setVisibility(View.GONE);
            }
        } else {    // 点击列表item不展示联想
            llSearchDataLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 选择事件分类结果
     *
     * @param id
     * @param result
     */
    @Override
    public void onItemSelect(int id, String result) {
        category_id = id;
        tatvEventType.getTextRightText().setText(result);
    }
}
