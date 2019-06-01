package com.sdxxtop.guardianapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventReadIndexBean;
import com.sdxxtop.guardianapp.model.bean.MediaBean;
import com.sdxxtop.guardianapp.presenter.EventReportDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.ImageHorizontalAdapter;
import com.sdxxtop.guardianapp.ui.adapter.PaifaAdapter;
import com.sdxxtop.guardianapp.ui.adapter.PatrolDetailImgAdapter;
import com.sdxxtop.guardianapp.ui.adapter.YanshouAdapter;
import com.sdxxtop.guardianapp.ui.pop.ERCheckResultWindow;
import com.sdxxtop.guardianapp.ui.pop.SelectMapPopView;
import com.sdxxtop.guardianapp.ui.widget.CustomProgressBar;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.SkipMapUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.tv_content_title)
    TextView tvContentTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    @BindView(R.id.tv_check_method)
    TextView tvCheckMethod;
    @BindView(R.id.rl_check_method)
    RelativeLayout rlCheckMethod;
    @BindView(R.id.tv_happen)
    TextView tvHappen;
    @BindView(R.id.rl_happen)
    RelativeLayout rlHappen;
    @BindView(R.id.tv_report_path)
    TextView tvReportPath;
    @BindView(R.id.rl_report_path)
    RelativeLayout rlReportPath;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.paifa_recy)
    RecyclerView paifaRecy;
    @BindView(R.id.tv_jiejue_time)
    TextView tvJiejueTime;
    @BindView(R.id.jiejue_recy)
    RecyclerView jiejue_recy;
    @BindView(R.id.tv_jiejue_remark)
    TextView tvJiejueRemark;
    @BindView(R.id.v_line_2)
    View vLine2;
    @BindView(R.id.chuli_time)
    TextView chuliTime;
    @BindView(R.id.chuli_result_type)
    TextView chuliResultType;
    @BindView(R.id.chuli_result)
    TextView chuliResult;
    @BindView(R.id.ll_containor)
    LinearLayout llContainor;
    @BindView(R.id.btn_check_faile)
    Button btnCheckFaile;
    @BindView(R.id.btn_check_success)
    Button btnCheckSuccess;
    @BindView(R.id.ll_root_layout)
    LinearLayout llRootLayout;
    @BindView(R.id.yanshou_recy)
    RecyclerView yanshou_recy;

    //用于提交
    private int eventStatus;

    private String mEventId;
    private PatrolDetailImgAdapter mAdapter;
    //先反馈问题
    private PatrolDetailImgAdapter mFinishAdapter;
    //显示check的图片
    private ImageHorizontalAdapter mCheckAdapter;
    private ERCheckResultWindow erCheckResultWindow;
    private PaifaAdapter paifaAdapter;
    private YanshouAdapter yanshouAdapter;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mAdapter = new PatrolDetailImgAdapter(R.layout.gv_filter_image, new ArrayList<>());
        recyclerView.setAdapter(mAdapter);

        jiejue_recy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mFinishAdapter = new PatrolDetailImgAdapter(R.layout.gv_filter_image, new ArrayList<>());
        jiejue_recy.setAdapter(mFinishAdapter);

        /*********** 派发 **********/
        paifaRecy.setLayoutManager(new LinearLayoutManager(this));
        paifaAdapter = new PaifaAdapter(R.layout.item_paifa_view, null);
        paifaRecy.setAdapter(paifaAdapter);

        /*********** 验收 **********/
        yanshou_recy.setLayoutManager(new LinearLayoutManager(this));
        yanshouAdapter = new YanshouAdapter(R.layout.item_yanshou_view, null);
        yanshou_recy.setAdapter(yanshouAdapter);
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

    @Override
    public void modifyRefresh() {
        mPresenter.loadData(mEventId);
    }

    @Override
    public void readData(EventReadIndexBean bean) {
        eventStatus = bean.status;
        //显示进度条状态

        showBottomButton(bean);  // 底部按钮显示隐藏
        collectHeadData(bean);   // 统一数据管理

        /********* 派发 **********/
        paifaAdapter.replaceData(bean.extra_date);
        /********* 解决 **********/
        List<EventReadIndexBean.SolveBean> solve = bean.solve;
        if (solve != null && solve.size() > 0) {
            handleFinish(solve.get(0));
        } else {
            tvJiejueTime.setVisibility(View.GONE);
            tvJiejueRemark.setVisibility(View.GONE);
            jiejue_recy.setVisibility(View.GONE);
        }

        /********* 验收 **********/
        yanshouAdapter.replaceData(bean.extra_info);
//        cpbProgress.setStatus(bean.status, getTime(bean));
    }

    private List<String> getTime(EventReadIndexBean bean) {
        List<String> time = new ArrayList<>();
        switch (bean.status) {
            case 1:
                time.add(bean.add_time);
                break;
            case 2:
                time.add(bean.add_time);
                if (bean.extra_date != null && bean.extra_date.size() > 0) {
                    time.add(bean.extra_date.get(bean.extra_date.size() - 1).getSend_time() + " 10:29:19");
                }
                break;
            case 3:
                time.add(bean.add_time);
                if (bean.extra_date != null && bean.extra_date.size() > 0) {
                    time.add(bean.extra_date.get(bean.extra_date.size() - 1).getSend_time() + " 10:29:19");
                }
                if (bean.solve != null && bean.solve.size() > 0) {
                    time.add(bean.solve.get(bean.solve.size() - 1).getOperate_time());
                }
                break;
            case 4:
                break;
        }
        return time;
    }

    /************ 派发 ******************/


    /******** 头部统一显示数据 **********/
    private void collectHeadData(EventReadIndexBean bean) {
        //图片加载
        bandImgAndVideo(bean.img, bean.video, recyclerView,mAdapter);
        tvContentTitle.setText(bean.title);
        tvTime.setText(bean.add_time);
        handlePatrol(bean.patrol_type);
        tvHappen.setText(bean.place);
        tvReportPath.setText(bean.part_name);
        tvDescription.setText(new StringBuilder().append("事件简要描述：").append(bean.content));

    }

    /*** 底部按钮显示隐藏********/
    private void showBottomButton(EventReadIndexBean bean) {
        //1.是已经解决 2.未解决
        if (bean.is_finish == 1) {
            switch (bean.status) {
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
        if (bean.is_modify == 1) {
            switch (bean.status) {
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

    /***  解决反馈*********/
    private void handleFinish(EventReadIndexBean.SolveBean bean) {
        String finishTime1 = bean.getOperate_time();
        //由于后台会发送1000-01-01 00：00：00 所以 加入了 status 的判断
        if (!TextUtils.isEmpty(finishTime1)) {
            tvJiejueTime.setVisibility(View.VISIBLE);
            StringBuilder finishTime = new StringBuilder().append("解决反馈时间：").append(handleTime(finishTime1));
            tvJiejueTime.setText(finishTime);
        } else {
            tvJiejueTime.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(bean.getExtra())) {
            tvJiejueRemark.setVisibility(View.VISIBLE);
            tvJiejueRemark.setText("事件问题描述："+bean.getExtra());
        } else {
            tvJiejueTime.setVisibility(View.GONE);
        }

        bandImgAndVideo(bean.getImg(),bean.getVideo(),jiejue_recy,mFinishAdapter);
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

    private void bandImgAndVideo(String img, String vedio, RecyclerView recyclerView,PatrolDetailImgAdapter adapter) {
        List<MediaBean> list = new ArrayList<>();
        if (!TextUtils.isEmpty(vedio)) {
            list.add(new MediaBean(vedio,2));
        }

        if (!TextUtils.isEmpty(img)) {
            String[] split = img.split(",");
            for (int i = 0; i < split.length; i++) {
                list.add(new MediaBean(split[i],1));
            }
        }
        if (list.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            adapter.replaceData(list);
        }else{
            recyclerView.setVisibility(View.GONE);
        }
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


    @OnClick({R.id.btn_check_success, R.id.rl_happen, R.id.btn_check_faile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check_success:  //右边的按钮点击事件
                //1.已经派发,确定的时候
//
                //2.已经反馈,然后弹出验收结果
                if (eventStatus == 2) {
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_SOLVE);
                } else if (eventStatus == 3) {
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_CHACK_FAILE);
                }

                break;
            case R.id.btn_check_faile:    //左边的按钮点击事件
                //1.已经派发,确定的时候
//
                //2.已经反馈,然后弹出验收结果
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
}
