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
import com.sdxxtop.guardianapp.ui.adapter.LiuzhuanAdapter;
import com.sdxxtop.guardianapp.ui.adapter.PaifaAdapter;
import com.sdxxtop.guardianapp.ui.adapter.PatrolDetailImgAdapter;
import com.sdxxtop.guardianapp.ui.adapter.WuFaJieJueAdapter;
import com.sdxxtop.guardianapp.ui.adapter.YanshouAdapter;
import com.sdxxtop.guardianapp.ui.pop.ERCheckResultWindow;
import com.sdxxtop.guardianapp.ui.pop.SelectMapPopView;
import com.sdxxtop.guardianapp.ui.pop.SolveEvaluateWindow;
import com.sdxxtop.guardianapp.ui.widget.CustomProgressBar;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.Date2Util;
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
    @BindView(R.id.liuzhuan_recy)
    RecyclerView liuzhuanRecy;

    @BindView(R.id.btn_check_faile)
    Button btnCheckFaile;
    @BindView(R.id.btn_check_success)
    Button btnCheckSuccess;
    @BindView(R.id.ll_root_layout)
    LinearLayout llRootLayout;
    @BindView(R.id.yanshou_recy)
    RecyclerView yanshou_recy;
    @BindView(R.id.wufajiejue_recy)
    RecyclerView wufajiejue_recy;

    /*** 解决结果 ****/
    @BindView(R.id.ll_jiejue_layout)
    LinearLayout llJiejueLayout;
    @BindView(R.id.tv_jiejue_time)
    TextView tvJiejueTime;
    @BindView(R.id.jiejue_recy)
    RecyclerView jiejue_recy;
    @BindView(R.id.tv_jiejue_remark)
    TextView tvJiejueRemark;

    /*** 验收通过 ****/
    @BindView(R.id.ll_yanshou_pass)
    LinearLayout llYanshouPass;
    @BindView(R.id.tv_yanshou_pass_time)
    TextView tvYanshouPassTime;
    @BindView(R.id.yanshou_pass_recy)
    RecyclerView yanshouPassRecy;
    @BindView(R.id.tv_yanshou_pass_remark)
    TextView tvYanshouPassRemark;
    @BindView(R.id.popwindow_bg)
    View popwindow_bg;
    @BindView(R.id.tv_location_desc)
    TextView tvLocationDesc;
    /********* 流转 **********/
    @BindView(R.id.ll_renling_layout)
    LinearLayout llRenlingLayout;
    @BindView(R.id.tv_renling_status)
    TextView tvRenlingStatus;
    @BindView(R.id.tv_renling_name)
    TextView tvRenlingName;
    @BindView(R.id.tv_renling_time)
    TextView tvRenlingTime;
    @BindView(R.id.renling_line)
    View renlingLine;
    /********* 评价 **********/
    @BindView(R.id.ll_renling_pingjia)
    LinearLayout llRenlingPingjia;
    @BindView(R.id.tv_pingjia_status)
    TextView tvPingjiaStatus;
    @BindView(R.id.tv_pingjia_desc)
    TextView tvPingjiaDesc;


    //用于提交
    private int eventStatus;
    private int isClaim;  // 流转/认领
    private int isClaimAuth;  // 流转/认领
    private int isFinish;  // 是否有解决权限
    private int isModify;  // 是否有评价权限

    private String mEventId;
    private PatrolDetailImgAdapter mAdapter;
    //先反馈问题
    private PatrolDetailImgAdapter mFinishAdapter;
    private ERCheckResultWindow erCheckResultWindow;
    private SolveEvaluateWindow solveEvaluateWindow;
    private PaifaAdapter paifaAdapter;
    private LiuzhuanAdapter liuzhuanAdapter;
    private YanshouAdapter yanshouAdapter;
    private WuFaJieJueAdapter wuFaJieJueAdapter;
    private PatrolDetailImgAdapter yanshouPassAdapter;

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

        /*********** 流转 **********/
        liuzhuanRecy.setLayoutManager(new LinearLayoutManager(this));
        liuzhuanAdapter = new LiuzhuanAdapter(R.layout.item_liuzhuan_view, null);
        liuzhuanRecy.setAdapter(liuzhuanAdapter);

        /*********** 派发 **********/
        paifaRecy.setLayoutManager(new LinearLayoutManager(this));
        paifaAdapter = new PaifaAdapter(R.layout.item_paifa_view, null);
        paifaRecy.setAdapter(paifaAdapter);

        /*********** 验收 **********/
        yanshou_recy.setLayoutManager(new LinearLayoutManager(this));
        yanshouAdapter = new YanshouAdapter(R.layout.item_yanshou_view, null);
        yanshou_recy.setAdapter(yanshouAdapter);

        /*********** 无法解决 **********/
        wufajiejue_recy.setLayoutManager(new LinearLayoutManager(this));
        wuFaJieJueAdapter = new WuFaJieJueAdapter(R.layout.item_wufajiejue_view, null);
        wufajiejue_recy.setAdapter(wuFaJieJueAdapter);

        /*********** 验收通过 **********/
        yanshouPassRecy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        yanshouPassAdapter = new PatrolDetailImgAdapter(R.layout.gv_filter_image, new ArrayList<>());
        yanshouPassRecy.setAdapter(yanshouPassAdapter);
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
        } else if (requestCode == 321 && resultCode == 123) {   // 流转
            if (data != null) {
                boolean isClose = data.getBooleanExtra("isClose", false);
                if (isClose) {
                    finish();
                }
            }
        }
    }

    @Override
    public void modifyRefresh() {
        mPresenter.loadData(mEventId);
    }

    @Override
    public void readData(EventReadIndexBean bean) {
        address = bean.place;
        longitude = bean.longitude;
        eventStatus = bean.status;
        isClaim = bean.is_claim;
        isClaimAuth = bean.is_claim_auth;
        isFinish = bean.is_finish;
        isModify = bean.is_modify;
        //显示进度条状态

        showBottomButton(bean);  // 底部按钮显示隐藏
        collectHeadData(bean);   // 统一数据管理

        /********* 流转 **********/
        liuzhuanAdapter.replaceData(bean.circulation);

        /********* 认领 **********/
        setRenlingData(bean);

        /********* 评价 *********/
        setPingjiaData(bean);

        /********* 派发 **********/
        paifaAdapter.replaceData(bean.extra_date);

        /********* 验收 **********/
        yanshouAdapter.replaceData(bean.extra_info);

        /********* 无法解决 **********/
        wuFaJieJueAdapter.replaceData(bean.extra);

        /*********** 验收通过 **********/
        List<EventReadIndexBean.CompletedBean> completed = bean.completed;
        if (completed != null && completed.size() > 0) {
            EventReadIndexBean.CompletedBean item = completed.get(0);
            llYanshouPass.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(item.getExtra())) {
                tvYanshouPassRemark.setVisibility(View.GONE);
            } else {
                tvYanshouPassRemark.setVisibility(View.VISIBLE);
                tvYanshouPassRemark.setText("备注: " + item.getExtra());
            }
            if (TextUtils.isEmpty(item.getOperate_time())) {
                tvYanshouPassTime.setVisibility(View.GONE);
            } else {
                tvYanshouPassTime.setVisibility(View.VISIBLE);
                tvYanshouPassTime.setText("验收时间: " + Date2Util.handleTime(item.getOperate_time()));
            }
            bandImgAndVideo(item.getImg(), item.getVideo(), yanshouPassRecy, yanshouPassAdapter);
        } else {
            llYanshouPass.setVisibility(View.GONE);
        }

        /********* 解决结果 **********/
        List<EventReadIndexBean.SolveBean> solve = bean.solve;
        if (solve != null && solve.size() > 0) {
            llJiejueLayout.setVisibility(View.VISIBLE);
            EventReadIndexBean.SolveBean solveBean = solve.get(0);
            handleFinish(solveBean.getOperate_time(), solveBean.getExtra(), solveBean.getImg(), solveBean.getVideo());
        } else {
            llJiejueLayout.setVisibility(View.GONE);
        }

        /**********  无法解决  ************/
        if (bean.is_claim == 1) {   // 认领状态下不显示进度条
            rlProgress.setVisibility(View.GONE);
            return;
        }
        if (bean.settle_status == 2 && bean.status == 4) {
            cpbProgress.setStatus(bean.status, getTime(bean));
            cpbProgress.setWFJJYWCValue();
        } else if (bean.settle_status == 2 && bean.status != 5) {
            String parfaTime = "";
            String wufachuli = "";
            if (bean.extra_date != null && bean.extra_date.size() > 0) {
                parfaTime = bean.extra_date.get(bean.extra_date.size() - 1).getSend_time();
            }
            if (bean.extra != null && bean.extra.size() > 0) {
                wufachuli = bean.extra.get(bean.extra.size() - 1).getOperate_time();
            }
            cpbProgress.setNoSolveValue(bean.add_time, parfaTime, wufachuli);
        } else {
            cpbProgress.setStatus(bean.status, getTime(bean));
        }
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
                    time.add(bean.extra_date.get(bean.extra_date.size() - 1).getSend_time());
                } else {
                    time.add("");
                }
                break;
            case 3:
                time.add(bean.add_time);
                if (bean.extra_date != null && bean.extra_date.size() > 0) {
                    time.add(bean.extra_date.get(bean.extra_date.size() - 1).getSend_time());
                } else {
                    time.add("");
                }
                if (bean.solve != null && bean.solve.size() > 0) {
                    time.add(bean.solve.get(bean.solve.size() - 1).getOperate_time());
                } else {
                    time.add("");
                }
                break;
            case 4:
                time.add(bean.add_time);
                if (bean.extra_date != null && bean.extra_date.size() > 0) {
                    time.add(bean.extra_date.get(bean.extra_date.size() - 1).getSend_time());
                } else {
                    time.add("");
                }
                if (bean.solve != null && bean.solve.size() > 0) {
                    time.add(bean.solve.get(bean.solve.size() - 1).getOperate_time());
                } else {
                    time.add("");
                }
                if (bean.completed != null && bean.completed.size() > 0) {
                    time.add(bean.completed.get(bean.completed.size() - 1).getOperate_time());
                } else {
                    time.add("");
                }
                break;
            case 5:
                time.add(bean.add_time);
                if (bean.extra_date != null && bean.extra_date.size() > 0) {
                    time.add(bean.extra_date.get(bean.extra_date.size() - 1).getSend_time());
                } else {
                    time.add("");
                }
                if (bean.solve != null && bean.solve.size() > 0) {
                    time.add(bean.solve.get(bean.solve.size() - 1).getOperate_time());
                } else {
                    time.add("");
                }
                if (bean.completed != null && bean.completed.size() > 0) {
                    time.add(bean.completed.get(bean.completed.size() - 1).getOperate_time());
                } else {
                    time.add("");
                }
                time.add("");
                break;
        }
        return time;
    }

    /******** 头部统一显示数据 **********/
    private void collectHeadData(EventReadIndexBean bean) {
        //图片加载
        bandImgAndVideo(bean.img, bean.video, recyclerView, mAdapter);
        tvContentTitle.setText(bean.title);
        tvTime.setText(Date2Util.handleTime(bean.add_time));
//        handlePatrol(bean.patrol_type);
//        tvCheckMethod.setText(bean.patrol_name);
        tvCheckMethod.setText("巡逻");
        tvHappen.setText(bean.place);
        tvReportPath.setText(bean.part_name);
        tvDescription.setText(new StringBuilder().append("事件简要描述：").append(bean.content));
        //定位补充描述
        if (TextUtils.isEmpty(bean.supplement)) {
            tvLocationDesc.setVisibility(View.GONE);
        } else {
            tvLocationDesc.setVisibility(View.VISIBLE);
            tvLocationDesc.setText("定位补充描述: " + bean.supplement);
        }
    }

    /*** 底部按钮显示隐藏********/
    private void showBottomButton(EventReadIndexBean bean) {

        if (bean.is_claim_auth == 1 && bean.status == 1) {
            btnCheckFaile.setTextColor(getResources().getColor(R.color.green));
            btnCheckFaile.setVisibility(View.VISIBLE);
            btnCheckFaile.setText("流转事件");
            btnCheckFaile.setBackgroundResource(R.drawable.btn_white_solid_bg);

            btnCheckSuccess.setTextColor(getResources().getColor(R.color.white));
            btnCheckSuccess.setVisibility(View.VISIBLE);
            btnCheckSuccess.setText("认领");
            btnCheckSuccess.setBackgroundResource(R.drawable.btn_green_solid_bg);
        } else {
            //1.是已经解决 2.未解决
            if (bean.is_finish == 1) {
                if (bean.is_claim == 1) {
                    btnCheckFaile.setVisibility(View.GONE);

                    btnCheckSuccess.setTextColor(getResources().getColor(R.color.white));
                    btnCheckSuccess.setVisibility(View.VISIBLE);
                    btnCheckSuccess.setText("解决");
                    btnCheckSuccess.setBackgroundResource(R.drawable.btn_green_solid_bg);
                    return;
                }
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
                if (bean.is_claim == 1) {  // 评价
                    btnCheckFaile.setVisibility(View.GONE);

                    btnCheckSuccess.setTextColor(getResources().getColor(R.color.white));
                    btnCheckSuccess.setVisibility(View.VISIBLE);
                    btnCheckSuccess.setText("评价");
                    btnCheckSuccess.setBackgroundResource(R.drawable.btn_green_solid_bg);
                    return;
                }
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
    }

    /***********  设置认领信息 *************/
    public void setRenlingData(EventReadIndexBean bean) {
        List<EventReadIndexBean.ExtraDateBean> claim_date = bean.claim_date;
        if (claim_date != null && claim_date.size() > 0) {
            EventReadIndexBean.ExtraDateBean item = claim_date.get(0);
            llRenlingLayout.setVisibility(View.VISIBLE);
            tvRenlingStatus.setVisibility(View.VISIBLE);
            tvRenlingStatus.setText("状态：" + getStatus(item.getStatus()));
            tvRenlingName.setVisibility(View.VISIBLE);
            tvRenlingName.setText("认领人：" + item.getName());
            tvRenlingTime.setVisibility(View.VISIBLE);
            tvRenlingTime.setText("认领时间：" + handleShortTime(item.getSend_time()));

            renlingLine.setVisibility((bean.circulation != null && bean.circulation.size() > 0) ? View.GONE : View.VISIBLE);
        } else {
            llRenlingLayout.setVisibility(View.GONE);
        }
    }

    /***********  设置认领评价信息 *************/
    private void setPingjiaData(EventReadIndexBean bean) {
        List<EventReadIndexBean.CompletedBean> data = bean.claim_completed;
        if (data != null && data.size() > 0) {
            EventReadIndexBean.CompletedBean item = data.get(0);
            llRenlingPingjia.setVisibility(View.VISIBLE);
            tvPingjiaStatus.setVisibility(View.VISIBLE);
            tvPingjiaStatus.setText("评价情况：" + item.getAppraiseStr());
            tvPingjiaDesc.setVisibility(View.VISIBLE);
            tvPingjiaDesc.setText("描述：" + item.getExtra());
        } else {
            llRenlingPingjia.setVisibility(View.GONE);
        }
    }

    /***  解决反馈*********/
    private void handleFinish(String finishTime1, String extra, String img, String video) {
        //由于后台会发送1000-01-01 00：00：00 所以 加入了 status 的判断
        if (!TextUtils.isEmpty(finishTime1)) {
            tvJiejueTime.setVisibility(View.VISIBLE);
            tvJiejueTime.setText("解决反馈时间：" + Date2Util.handleTime(finishTime1));
        } else {
            tvJiejueTime.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(extra)) {
            tvJiejueRemark.setVisibility(View.VISIBLE);
            tvJiejueRemark.setText("事件问题描述：" + extra);
        } else {
            tvJiejueRemark.setVisibility(View.GONE);
        }

        bandImgAndVideo(img, video, jiejue_recy, mFinishAdapter);
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
        tvCheckMethod.setText("巡逻");
    }

    private String getStatus(int status) {
        String strStatus = "";
        switch (status) {
            case 2:
                strStatus = "已认领";
                break;
            case 3:
                strStatus = "待评价";
                break;
            case 4:
                strStatus = "已完成";
                break;
            default:
                strStatus = "待认领";
                break;
        }
        return strStatus;
    }

    private void bandImgAndVideo(String img, String vedio, RecyclerView recyclerView, PatrolDetailImgAdapter adapter) {
        List<MediaBean> list = new ArrayList<>();
        if (!TextUtils.isEmpty(vedio)) {
            list.add(new MediaBean(vedio, 2));
        }

        if (!TextUtils.isEmpty(img)) {
            String[] split = img.split(",");
            for (int i = 0; i < split.length; i++) {
                list.add(new MediaBean(split[i], 1));
            }
        }
        if (list.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter.replaceData(list);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }

    private String handleShortTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
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
                if (eventStatus == 2 && isClaim == 2) {
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_SOLVE);
                } else if (eventStatus == 3 && isClaim == 2) {
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_CHACK_FAILE);
                } else if (eventStatus == 1 && isClaimAuth == 1) {  // 事件认领
                    mPresenter.failed(mEventId, "", 2);
                } else if (isFinish == 1 && isClaim == 1) {  // 认领解决
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_SOLVE);
                } else if (isModify == 1 && isClaim == 1) {  // 评价
                    if (solveEvaluateWindow == null) {
                        solveEvaluateWindow = new SolveEvaluateWindow(this, popwindow_bg);
                        solveEvaluateWindow.show(getLayout());
                        solveEvaluateWindow.setOnConfirmClick(new SolveEvaluateWindow.OnConfirmClick() {
                            @Override
                            public void onButtonClick(String str, int status) {
                                mPresenter.failed(mEventId, str, 4, status);
                                if (solveEvaluateWindow != null) {
                                    solveEvaluateWindow.dismiss();
                                }
                            }
                        });
                    } else {
                        solveEvaluateWindow.show(getLayout());
                    }
                }

                break;
            case R.id.btn_check_faile:    //左边的按钮点击事件
                //1.已经派发,确定的时候
//
                //2.已经反馈,然后弹出验收结果
                if (eventStatus == 2) {
                    if (erCheckResultWindow == null) {
                        erCheckResultWindow = new ERCheckResultWindow(this, popwindow_bg);
                        erCheckResultWindow.show(getLayout(), false);
                        erCheckResultWindow.setOnConfirmClick(this);
                    } else {
                        erCheckResultWindow.show(getLayout(), false);
                    }
                } else if (eventStatus == 3) {
                    skipSecondPush(EventReportDetailSecondActivity.TYPE_CHACK_SUCCSESS);
                } else if (eventStatus == 1 && isClaimAuth == 1) {   // 事件流转
                    Intent intent = new Intent(EventReportDetailActivity.this, EventMoveActivity.class);
                    intent.putExtra("eventId", mEventId);
                    startActivityForResult(intent, 321);
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
