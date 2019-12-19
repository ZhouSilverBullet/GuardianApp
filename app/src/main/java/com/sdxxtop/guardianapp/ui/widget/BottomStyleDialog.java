package com.sdxxtop.guardianapp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventStreamDetailBean;
import com.sdxxtop.guardianapp.ui.activity.EventMoveActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity_new;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailSecondActivity;
import com.sdxxtop.guardianapp.ui.pop.ERCheckResultWindow;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author :  lwb
 * Date: 2018/8/6
 * Desc:
 */

public class BottomStyleDialog extends Dialog {

    private EventStreamDetailBean bean;
    private int solveUnableDescribeNumber;  // 无法解决字符数量限制
    private int solveImg;  // 解决 可以上传解决图片
    private int solveDescribeNumber;  // 解决 字符数量限制
    private int solveImgNumber;  // 解决 图片数量限制

    private SmartRefreshLayout smartRefresh;
    private RecyclerView recyclerView;

    private Context mActivity;
    private String mEventId;
    private MyAdapter adapter;
    private ERCheckResultWindow erCheckResultWindow;
    private View popwindow_bg;

    public BottomStyleDialog(@NonNull Context context, String eventId) {
        super(context, R.style.MyDialogStyleBottom);
        this.mActivity = context;
        this.mEventId = eventId;
        //拿到Dialog的Window, 修改Window的属性
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);

        setContentView(R.layout.dialog_choose_caozuo_type);
        initView();
    }

    public BottomStyleDialog(@NonNull Context context, String eventId, View popwindow_bg) {
        this(context, eventId);
        this.popwindow_bg = popwindow_bg;
    }

    public void setData(EventStreamDetailBean bean) {
        this.bean = bean;
        /************ 反馈信息限制 *************/
        /*****解决/无法解决******/
        solveUnableDescribeNumber = bean.solveUnableDescribeNumber;
        solveImg = bean.solveImg;
        solveDescribeNumber = bean.solveDescribeNumber;
        solveImgNumber = bean.solveImgNumber;

    }

    private void initView() {
        smartRefresh = findViewById(R.id.smart_refresh);
        recyclerView = findViewById(R.id.recyclerView);

        smartRefresh.setEnableLoadMore(false);
        smartRefresh.setEnableRefresh(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void setData(List<String> data) {
        if (adapter != null) {
            adapter.replaceData(data);
        }
    }

    /**
     * if (bean.is_claim_auth == 1) list.add("认领");
     * if (bean.is_Operate_auth == 1) list.add("流转");
     * if (bean.unable_solve == 1) list.add("无法解决");
     * if (bean.is_solve == 1) list.add("解决");
     * if (bean.is_modify == 1) list.add("验收通过");
     * if (bean.unable_check == 1) list.add("验收不通过");
     */
    class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_text);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_text, item);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (bean == null) return;
                    if (!(mActivity instanceof EventReportDetailActivity_new)) {
                        return;
                    }
                    switch (item) {
                        case "解决":
                            skipSecondPush(EventReportDetailSecondActivity.TYPE_SOLVE, bean.solveImg, bean.solveImgNumber, bean.solveDescribeNumber,
                                    bean.checkEvaluate, bean.checkEvaluateNumber);
                            break;
                        case "无法解决":
                            showWFSolveDialog();
                            break;
                        case "认领":
                            ((EventReportDetailActivity_new) mActivity).renling();
                            break;
                        case "流转":
                            Intent intent = new Intent(mActivity, EventMoveActivity.class);
                            intent.putExtra("eventId", mEventId);
                            ((EventReportDetailActivity_new) mActivity).startActivityForResult(intent, 321);
                            break;
                        case "验收通过":
                            skipSecondPush(EventReportDetailSecondActivity.TYPE_CHACK_SUCCSESS, bean.checkImg, bean.checkImgNumber, bean.checkDescribeNumber,
                                    bean.checkEvaluate, bean.checkEvaluateNumber);
                            break;
                        case "验收不通过":
                            skipSecondPush(EventReportDetailSecondActivity.TYPE_CHACK_FAILE, 2, 0, bean.checkNoNumber, 2, 0);
                            break;
                    }
                }
            });
        }
    }

    /**
     * 无法解决
     */
    private void showWFSolveDialog() {
        if (erCheckResultWindow == null) {
            if (popwindow_bg == null) return;
            erCheckResultWindow = new ERCheckResultWindow((EventReportDetailActivity_new) mActivity, popwindow_bg);
            erCheckResultWindow.show(R.layout.activity_event_report_detail, false);
            erCheckResultWindow.etNumContent.setMaxLength(solveUnableDescribeNumber);
            erCheckResultWindow.setOnConfirmClick(new ERCheckResultWindow.OnConfirmClick() {
                @Override
                public void onButtonClick(String str) {
                    try {
                        ((EventReportDetailActivity_new) mActivity).onButtonClick(str);
                    } finally {
                        erCheckResultWindow.dismiss();
                    }
                }
            });
        } else {
            erCheckResultWindow.etNumContent.setMaxLength(solveUnableDescribeNumber);
            erCheckResultWindow.show(R.layout.activity_event_report_detail, false);
        }
    }

    private void skipSecondPush(int type, int isShowImgSelect, int maxSelectCount, int maxTextDesc, int isShowPinlun, int pinlunTextCount) {
        Intent intent = new Intent(mActivity, EventReportDetailSecondActivity.class);
        intent.putExtra("eventId", mEventId);
        intent.putExtra("eventType", type);
        intent.putExtra("isShowImgSelect", isShowImgSelect);
        intent.putExtra("maxSelectCount", maxSelectCount);
        intent.putExtra("maxTextDesc", maxTextDesc);
        intent.putExtra("isShowPinlun", isShowPinlun);
        intent.putExtra("pinlunTextCount", pinlunTextCount);
        ((EventReportDetailActivity_new) mActivity).startActivityForResult(intent, 100);
    }
}
