package com.sdxxtop.guardianapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventReadBean;
import com.sdxxtop.guardianapp.presenter.EventReportDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.ImageHorizontalAdapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.pop.ERCheckResultWindow;
import com.sdxxtop.guardianapp.ui.pop.SelectMapPopView;
import com.sdxxtop.guardianapp.ui.widget.CustomProgressBar;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.SkipMapUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventReportDetailActivity extends BaseMvpActivity<EventReportDetailPresenter> implements EventReportDetailContract.IView,
        ERCheckResultWindow.OnConfirmClick {

    @BindView(R.id.tv_title)
    TitleView tvTitle;
    @BindView(R.id.cpb_progress)
    CustomProgressBar cpbProgress;
    @BindView(R.id.tv_content_title)
    TextView tvContentTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.rv3)
    RecyclerView rv3;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_check_method)
    TextView tvCheckMethod;
    @BindView(R.id.tv_happen)
    TextView tvHappen;
    @BindView(R.id.tv_report_path)
    TextView tvReportPath;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.btn_check_success)
    Button btnCheckSuccess;
    @BindView(R.id.btn_check_faile)
    Button btnCheckFaile;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.v_line_1)
    View vLine1;
    @BindView(R.id.v_line_2)
    View vLine2;
    @BindView(R.id.tv_distributed_time)
    TextView tvDistributedTime;
    @BindView(R.id.tv_distributed_result)
    TextView tvDistributedResult;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_end_point)
    TextView tvEndPoint;
    @BindView(R.id.tv_jiejue_time)
    TextView tvJiejueTime;
    @BindView(R.id.tv_jiejue_remark)
    TextView tvJiejueRemark;
    @BindView(R.id.tv_yanshou_time)
    TextView tvYanshouTime;
    @BindView(R.id.tv_yanshou_result)
    TextView tvYanshouResult;
    @BindView(R.id.tv_remark)
    TextView tvRemark;

    /**** 无法解决 ****/
    @BindView(R.id.v_line_3)
    View vLine3;
    @BindView(R.id.ll_containor)
    LinearLayout llContainor;
    @BindView(R.id.chuli_time)
    TextView chuliTime;
    @BindView(R.id.chuli_result_type)
    TextView chuliResultType;
    @BindView(R.id.chuli_result)
    TextView chuliResult;

    //用于提交
    private int eventStatus;

    private String mEventId;
    private ImageHorizontalAdapter mAdapter;
    //先反馈问题
    private ImageHorizontalAdapter mFinishAdapter;
    //显示check的图片
    private ImageHorizontalAdapter mCheckAdapter;
    private ERCheckResultWindow erCheckResultWindow;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report_detail;
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        Intent intent = getIntent();
        if (intent != null) {
            mEventId = intent.getStringExtra("eventId");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mAdapter = new ImageHorizontalAdapter(R.layout.item_image_horizontal_view, new ArrayList<>());
        rv.setAdapter(mAdapter);

        rv2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mCheckAdapter = new ImageHorizontalAdapter(R.layout.item_image_horizontal_view, new ArrayList<>());
        rv2.setAdapter(mCheckAdapter);

        rv3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mFinishAdapter = new ImageHorizontalAdapter(R.layout.item_image_horizontal_view, new ArrayList<>());
        rv3.setAdapter(mFinishAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData(mEventId);
    }

    @Override
    public void showError(String error) {

    }

    private String address = "";
    private String longitude = "";

    @OnClick({R.id.btn_check_success, R.id.rl_happen, R.id.btn_check_faile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check_success:  //右边的按钮点击事件
                //1.已经派发,确定的时候
//
                //2.已经反馈,然后弹出验收结果
//                showPop();
                if (eventStatus == 2) {
//                    showConfirmDialog();
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_SOLVE);
                } else if (eventStatus == 3) {
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_CHACK_FAILE);
                }

                break;
            case R.id.btn_check_faile:    //左边的按钮点击事件
                //1.已经派发,确定的时候
//
                //2.已经反馈,然后弹出验收结果
//                showPop();
                if (eventStatus == 2) {
                    if (erCheckResultWindow == null) {
                        erCheckResultWindow = new ERCheckResultWindow(this);
                        erCheckResultWindow.show(getLayout(), false);
                        erCheckResultWindow.setOnConfirmClick(this);
                    } else {
                        erCheckResultWindow.show(getLayout(), false);
                    }
                } else if (eventStatus == 3) {
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_CHACK_SUCCSESS);
                }

                break;
            case R.id.rl_happen:
                if (TextUtils.isEmpty(address)) {
                    return;
                }

                if (TextUtils.isEmpty(longitude)) {
                    showToast("经纬度为空");
                    return;
                }

                String[] split = longitude.split(",");
                if (split.length != 2) {
                    showToast("经纬度不合法");
                    return;
                }


                SelectMapPopView selectMapPopView =
                        new SelectMapPopView(this, findViewById(R.id.ll_root_layout), "高德地图（推荐）", "百度地图");
                selectMapPopView.setSelectMapClickListener(new SelectMapPopView.SelectMapClickListener() {
                    @Override
                    public void clickToGaode() {
                        SkipMapUtils.goToGaodeMap3(mContext, address, split[1], split[0]);
                    }

                    @Override
                    public void clickToBaidu() {
                        SkipMapUtils.goToBaiduMap3(mContext, address, split[1], split[0]);
                    }
                });

                break;
        }
    }

    private void skipSecondPush(int type) {
        Intent intent = new Intent(this, EventReportDetailSecondActivity.class);
        intent.putExtra("eventId", mEventId);
        intent.putExtra("eventType", type);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            if (mPresenter != null) {
                mPresenter.loadData(mEventId);
            }
        }
    }

    private void showConfirmDialog() {
        IosAlertDialog builder = new IosAlertDialog(this)
                .builder();
        builder.setHeightMsg("确认事件上报问题已经解决了吗？")
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mPresenter.modify(mEventId, eventStatus + 1, "");
                    }
                }).show();
    }

    @Override
    public void modifyRefresh() {
        mPresenter.loadData(mEventId);
    }

    //2.已经反馈,然后弹出验收结果
    private void showPop() {
        ERCheckResultWindow erCheckResultWindow = new ERCheckResultWindow(this);
        erCheckResultWindow.show(getLayout());
    }

    @Override
    public void readData(EventReadBean eventReadBean) {
        eventStatus = eventReadBean.getStatus();
        showBottomButton(eventReadBean);
        address = eventReadBean.getPlace();
        longitude = eventReadBean.getLongitude();
        int status = eventReadBean.getStatus();
        collectHeadData(eventReadBean);

        //验收图片地址
        handleCheckImg(eventReadBean.getCheck_img());
        boolean isShowLine = false;
        /******* 派发  *********/
        if (eventReadBean.getStatus() > 1) {
            showPaifaLayout(eventReadBean);
        } else {
            vLine.setVisibility(View.GONE);
            tvEndTime.setVisibility(View.GONE);
            tvDistributedTime.setVisibility(View.GONE);
            tvEndPoint.setVisibility(View.GONE);
        }
        /******** 派发结束 ********/

        /******* 解决 *********/
        //解决图片地址
        if (eventReadBean.getStatus() > 2) {
            handleFinish(eventReadBean.getFinish_img(), eventReadBean);
        } else {
            vLine1.setVisibility(View.GONE);
            tvJiejueTime.setVisibility(View.GONE);
            tvJiejueRemark.setVisibility(View.GONE);
            rv3.setVisibility(View.GONE);
        }

        /****** 无法解决的展示状态 *******/
        if (eventReadBean.getStatus() == 1 && eventReadBean.getSettle_status() == 2) {
            showPaifaLayout(eventReadBean); // 展示派发
            rv2.setVisibility(View.GONE);
            rv3.setVisibility(View.GONE);
            tvJiejueTime.setVisibility(View.GONE);
            tvJiejueRemark.setVisibility(View.GONE);
            tvYanshouTime.setVisibility(View.GONE);
            tvYanshouResult.setVisibility(View.GONE);
            tvRemark.setVisibility(View.GONE);

            vLine2.setVisibility(View.GONE);
            vLine3.setVisibility(View.VISIBLE);
            llContainor.setVisibility(View.VISIBLE);
            chuliResultType.setVisibility(View.VISIBLE);
            tvDistributedResult.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(eventReadBean.getOperate_time())) {
                chuliTime.setVisibility(View.GONE);
            } else {
                chuliTime.setText("处理时间: " + eventReadBean.getOperate_time());
                chuliTime.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(eventReadBean.getUnable())) {
                chuliResult.setVisibility(View.GONE);
            } else {
                chuliResult.setText("无法解决原因: " + eventReadBean.getUnable());
                chuliResult.setVisibility(View.VISIBLE);
            }
            cpbProgress.setNoSolveValue(eventReadBean.getSend_time());
            return;
        }

        /********解决结束********/

        String checkTime = eventReadBean.getCheck_time();
        if (!TextUtils.isEmpty(checkTime) && status > 3) {
            isShowLine = true;
            StringBuilder yanshouTime = new StringBuilder().append("验收时间：").append(handleTime(checkTime));
            handleTV(tvYanshouTime, yanshouTime.toString());
        } else {
            tvYanshouTime.setVisibility(View.GONE);
        }

        if (status >= 2) {
            vLine.setVisibility(View.VISIBLE);
        } else {
            vLine.setVisibility(View.GONE);
        }

        if (status >= 3) {
            vLine1.setVisibility(View.VISIBLE);
        } else {
            vLine1.setVisibility(View.GONE);
        }

        if (status >= 4) {
            vLine2.setVisibility(View.VISIBLE);
        } else {
            vLine2.setVisibility(View.GONE);
        }


        String extra = eventReadBean.getExtra();
        if (!TextUtils.isEmpty(extra)) {
            tvRemark.setText("备注：" + extra);
            tvRemark.setVisibility(View.VISIBLE);
        } else {
            tvRemark.setVisibility(View.INVISIBLE);
        }

        String finishDesc = eventReadBean.getFinish_desc();
        if (!TextUtils.isEmpty(finishDesc)) {
            tvJiejueRemark.setText("解决问题描述：" + finishDesc);
            tvJiejueRemark.setVisibility(View.VISIBLE);
        } else {
            tvJiejueRemark.setVisibility(View.GONE);
        }

        switch (status) {
            case 4:
                tvYanshouResult.setText("验收结果：验收通过");
                tvYanshouResult.setVisibility(View.VISIBLE);
                break;
            case 5:
                tvYanshouResult.setText("验收结果：验收不通过");
                tvYanshouResult.setVisibility(View.VISIBLE);
                break;
            default:
                tvYanshouResult.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /************ 派发 ******************/
    public void showPaifaLayout(EventReadBean eventReadBean) {
        vLine.setVisibility(View.VISIBLE);
        tvEndTime.setVisibility(View.VISIBLE);
        tvDistributedTime.setVisibility(View.VISIBLE);
        tvEndPoint.setVisibility(View.VISIBLE);

        String sendTime = eventReadBean.getSend_time();
        if (!TextUtils.isEmpty(sendTime)) {
            StringBuilder append = new StringBuilder().append("派发时间：").append(handleTime(sendTime));
            handleTV(tvDistributedTime, append.toString());
        } else {
            tvDistributedTime.setVisibility(View.GONE);
        }
        tvEndTime.setText("截止日期：" + handleShortTime(eventReadBean.getEnd_date()));
        switch (eventReadBean.getImportant_type()) { //事件重要性(1:低 2:中 3:高)
            case 1:
                tvEndPoint.setText("事件重要性：低");
                break;
            case 2:
                tvEndPoint.setText("事件重要性：中");
                break;
            default:
                tvEndPoint.setText("事件重要性：高");
                break;
        }
    }

    /******** 头部统一显示数据 **********/
    private void collectHeadData(EventReadBean eventReadBean) {
        vLine3.setVisibility(View.GONE);
        llContainor.setVisibility(View.GONE);

        List<String> dateStrList = getStrList(eventReadBean);
        //显示进度条状态
        cpbProgress.setStatus(eventReadBean.getStatus(), dateStrList);
        //图片加载
        handleImg(eventReadBean.getImg());

        tvContentTitle.setText(eventReadBean.getTitle());
        tvTime.setText(eventReadBean.getAdd_time());
        handlePatrol(eventReadBean.getPatrol_type());
        tvHappen.setText(eventReadBean.getPlace());
        handlePath(eventReadBean.getPart_name());
        tvDescription.setText(new StringBuilder().append("事件简要描述：").append(eventReadBean.getContent()));

        /********** 派发人统一是指挥中心 **********/
//        String sendName = eventReadBean.getSend_name();
        if (eventReadBean.getStatus() > 1) {
            tvDistributedResult.setVisibility(View.VISIBLE);
        } else {
            tvDistributedResult.setVisibility(View.GONE);
        }
    }


    /*** 底部按钮显示隐藏********/
    private void showBottomButton(EventReadBean eventReadBean) {
        //1.是已经解决 2.未解决
        if (eventReadBean.getIs_finish() == 1) {
            switch (eventReadBean.getStatus()) {
                case 2:
                    btnCheckFaile.setTextColor(getResources().getColor(R.color.green));
                    btnCheckFaile.setVisibility(View.VISIBLE);
                    btnCheckFaile.setText("无法解决");
                    btnCheckFaile.setBackgroundResource(R.drawable.btn_white_solid_bg);

                    btnCheckSuccess.setTextColor(getResources().getColor(R.color.white));
                    btnCheckSuccess.setVisibility(View.VISIBLE);
                    btnCheckSuccess.setText("已解决");
                    btnCheckSuccess.setBackgroundResource(R.drawable.btn_green_solid_bg);
                    break;
            }
            return;
        } else {
            btnCheckFaile.setVisibility(View.GONE);
            btnCheckSuccess.setVisibility(View.GONE);
        }

        //是否有验收事件权限 1:有 2:否
        if (eventReadBean.getIs_modify() == 1) {
            switch (eventReadBean.getStatus()) {
                case 3:
                    btnCheckFaile.setTextColor(getResources().getColor(R.color.white));
                    btnCheckFaile.setVisibility(View.VISIBLE);
                    btnCheckFaile.setText("验收通过");
                    btnCheckFaile.setBackgroundResource(R.drawable.btn_green_solid_bg);

                    btnCheckSuccess.setTextColor(getResources().getColor(R.color.green));
                    btnCheckSuccess.setVisibility(View.VISIBLE);
                    btnCheckSuccess.setText("验收不通过");
                    btnCheckSuccess.setBackgroundResource(R.drawable.btn_white_solid_bg);
                    break;
            }
        } else {
            btnCheckFaile.setVisibility(View.GONE);
            btnCheckSuccess.setVisibility(View.GONE);
        }
    }

    private void handlePath(String partName) {
        tvReportPath.setText(partName);
    }

    private void handlePatrol(int patrolType) {
        String strPatrol;
        switch (patrolType) {
            case 2:
                strPatrol = "化验";
                break;
            case 3:
                strPatrol = "感应器报警";
                break;
            case 4:
                strPatrol = "他人反应";
                break;
            default:
                strPatrol = "巡逻";
                break;
        }
        tvCheckMethod.setText(strPatrol);
    }

    private void handleTV(TextView tv, String content) {
        if (!TextUtils.isEmpty(content)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(content);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    private void handleImg(String img) {
        if (TextUtils.isEmpty(img)) {
            rv.setVisibility(View.GONE);
            return;
        }
        rv.setVisibility(View.VISIBLE);
        String[] split = img.split(",");
        mAdapter.replaceData(Arrays.asList(split));
    }

    private void handleCheckImg(String checkImg) {
        if (TextUtils.isEmpty(checkImg)) {
            rv2.setVisibility(View.GONE);
            return;
        }
        rv2.setVisibility(View.VISIBLE);
        String[] split = checkImg.split(",");
        mCheckAdapter.replaceData(Arrays.asList(split));
    }

    /***  解决反馈*********/
    private void handleFinish(String checkImg, EventReadBean eventReadBean) {
        vLine1.setVisibility(View.VISIBLE);
        tvJiejueTime.setVisibility(View.VISIBLE);
        tvJiejueRemark.setVisibility(View.VISIBLE);
        rv3.setVisibility(View.VISIBLE);

        String finishTime1 = eventReadBean.getFinish_time();
        //由于后台会发送1000-01-01 00：00：00 所以 加入了 status 的判断
        if (!TextUtils.isEmpty(finishTime1) && eventReadBean.getStatus() > 2) {
            StringBuilder finishTime = new StringBuilder().append("解决反馈时间：").append(handleTime(finishTime1));
            handleTV(tvJiejueTime, finishTime.toString());
        } else {
            tvJiejueTime.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(checkImg)) {
            rv3.setVisibility(View.GONE);
            return;
        }
        rv3.setVisibility(View.VISIBLE);
        String[] split = checkImg.split(",");
        mFinishAdapter.replaceData(Arrays.asList(split));
    }

    private List<String> getStrList(EventReadBean eventReadBean) {
        List<String> list = new ArrayList<>();
        String addTime = eventReadBean.getAdd_time();
        if (!TextUtils.isEmpty(addTime)) {
            list.add(addTime);
        }

        String sendTime = eventReadBean.getSend_time();
        if (!TextUtils.isEmpty(sendTime)) {
            list.add(sendTime);
        }

        String finishTime = eventReadBean.getFinish_time();
        if (!TextUtils.isEmpty(finishTime)) {
            list.add(finishTime);
        }

        String checkTime = eventReadBean.getCheck_time();
        if (!TextUtils.isEmpty(checkTime)) {
            list.add(checkTime);
        }

        return list;
    }

    private String handleTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = sdf.parse(time);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String handleShortTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = sdf.parse(time);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void startDetailActivity(Context context, String eventId) {
        Intent intent = new Intent(context, EventReportDetailActivity.class);
        intent.putExtra("eventId", eventId);
        context.startActivity(intent);
    }

    @Override
    public void onButtonClick(String str) {
        mPresenter.failed(mEventId, str, 6);
        if (erCheckResultWindow != null) {
            erCheckResultWindow.dismiss();
        }
    }
}
