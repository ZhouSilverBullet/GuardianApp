package com.sdxxtop.guardianapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.sdxxtop.guardianapp.utils.GuardianUtils;
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

public class EventReportDetailActivity extends BaseMvpActivity<EventReportDetailPresenter> implements EventReportDetailContract.IView {

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

    //用于提交
    private int eventStatus;

    private String mEventId;
    private ImageHorizontalAdapter mAdapter;
    //先反馈问题
    private ImageHorizontalAdapter mFinishAdapter;
    //显示check的图片
    private ImageHorizontalAdapter mCheckAdapter;

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

    @OnClick({R.id.btn_check_success, R.id.rl_happen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check_success:
                //1.已经派发,确定的时候
//
                //2.已经反馈,然后弹出验收结果
//                showPop();
                if (eventStatus == 2) {
//                    showConfirmDialog();
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_FINISH);
                } else if (eventStatus == 3) {
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_REPORT);
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
                        mPresenter.modify(mEventId, eventStatus + 1, "");
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

        btnCheckSuccess.setVisibility(View.GONE);

        address = eventReadBean.getPlace();
        longitude = eventReadBean.getLongitude();

        int status = eventReadBean.getStatus();
        List<String> dateStrList = getStrList(eventReadBean);
        //显示进度条状态
        cpbProgress.setStatus(status, dateStrList);
        //图片加载
        handleImg(eventReadBean.getImg());

        //check的图片加载
        handleCheckImg(eventReadBean.getCheck_img());

        //返回的图片加载
        handleFinishImg(eventReadBean.getFinish_img());

        tvContentTitle.setText(eventReadBean.getTitle());
        tvTime.setText(eventReadBean.getAdd_time());
        handlePatrol(eventReadBean.getPatrol_type());
        tvHappen.setText(eventReadBean.getPlace());
        handlePath(eventReadBean.getPart_name());
        tvDescription.setText(new StringBuilder().append("事件简要描述：").append(eventReadBean.getContent()));

        boolean isShowLine = false;

        String sendTime = eventReadBean.getSend_time();
        if (!TextUtils.isEmpty(sendTime) && status > 1) {
            isShowLine = true;
            StringBuilder append = new StringBuilder().append("派发时间：").append(handleTime(sendTime));
            handleTV(tvDistributedTime, append.toString());
        } else {
            tvDistributedTime.setVisibility(View.GONE);
        }

        String sendName = eventReadBean.getSend_name();
        if (!TextUtils.isEmpty(sendName)) {
            tvDistributedResult.setText("派发人：" + sendName);
            tvDistributedResult.setVisibility(View.VISIBLE);
        } else {
            tvDistributedResult.setVisibility(View.GONE);
        }

        String finishTime1 = eventReadBean.getFinish_time();
        //由于后台会发送1000-01-01 00：00：00 所以 加入了 status 的判断
        if (!TextUtils.isEmpty(finishTime1) && status > 2) {
            isShowLine = true;
            StringBuilder finishTime = new StringBuilder().append("解决反馈时间：").append(handleTime(finishTime1));
            handleTV(tvJiejueTime, finishTime.toString());
        } else {
            tvJiejueTime.setVisibility(View.GONE);
        }

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

        //1.是已经解决 2.未解决
        if (eventReadBean.getIs_finish() == 1) {
            eventStatus = status;
            switch (status) {
                case 2:
                    btnCheckSuccess.setVisibility(View.VISIBLE);
                    btnCheckSuccess.setText("已解决");
                    break;
            }
        }

        //是否有验收事件权限 1:有 2:否
        if (eventReadBean.getIs_modify() == 1) {
            eventStatus = status;
            switch (status) {
                case 3:
                    btnCheckSuccess.setVisibility(View.VISIBLE);
                    btnCheckSuccess.setText("验收通过");
                    break;

            }
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


        if (status >= 2) {
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
        } else {
            tvEndTime.setVisibility(View.GONE);
            tvEndPoint.setVisibility(View.GONE);
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

    private void handleFinishImg(String checkImg) {
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

}
