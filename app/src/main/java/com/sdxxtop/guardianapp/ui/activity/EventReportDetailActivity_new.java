package com.sdxxtop.guardianapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventReadIndexBean;
import com.sdxxtop.guardianapp.model.bean.EventReadIndexBean_new;
import com.sdxxtop.guardianapp.model.bean.MediaBean;
import com.sdxxtop.guardianapp.presenter.EventReportDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.PatrolDetailImgAdapter;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventReportDetailActivity_new extends BaseMvpActivity<EventReportDetailPresenter> implements EventReportDetailContract.IView,
        ERCheckResultWindow.OnConfirmClick {


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
    @BindView(R.id.tv_location_desc)
    TextView tvLocationDesc;
    @BindView(R.id.tv_report_path)
    TextView tvReportPath;
    @BindView(R.id.rl_report_path)
    RelativeLayout rlReportPath;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.btn_check_faile)
    Button btnCheckFaile;
    @BindView(R.id.btn_check_success)
    Button btnCheckSuccess;
    @BindView(R.id.popwindow_bg)
    View popwindow_bg;
    @BindView(R.id.tv_title)
    TitleView tvTitle;

    /************ 事件反馈 ***************/
    /***联办***/
    @BindView(R.id.col_lb)
    ConstraintLayout colLb;
    @BindView(R.id.tv_lb_title)
    TextView tvLbTitle;
    @BindView(R.id.tv_lb_part)
    TextView tvLbPart;
    @BindView(R.id.tv_lb_time)
    TextView tvLbTime;

    /***流转***/
    @BindView(R.id.col_lz)
    ConstraintLayout colLz;
    @BindView(R.id.tv_lz_part)
    TextView tvLzPart;
    @BindView(R.id.tv_lz_time)
    TextView tvLzTime;
    @BindView(R.id.tv_lz_cause)
    TextView tvLzCause;

    /***派发***/
    @BindView(R.id.col_pf)
    ConstraintLayout colPf;
    @BindView(R.id.tv_pf_time)
    TextView tvPfTime;
    @BindView(R.id.tv_pf_name)
    TextView tvPfName;
    @BindView(R.id.tv_pf_importance)
    TextView tvPfImportance;
    @BindView(R.id.tv_pf_end_time)
    TextView tvPfEndTime;

    /***解决***/
    @BindView(R.id.col_jj)
    ConstraintLayout colJj;
    @BindView(R.id.tv_jj_time)
    TextView tvJjTime;
    @BindView(R.id.tv_jj_desc)
    TextView tvJjDesc;
    @BindView(R.id.rv_jj_rv)
    RecyclerView rvJjRv;

    /***验收***/
    @BindView(R.id.col_ys)
    ConstraintLayout colYs;
    @BindView(R.id.tv_ys_time)
    TextView tvYsTime;
    @BindView(R.id.tv_ys_result)
    TextView tvYsResult;
    @BindView(R.id.tv_ys_beizhu)
    TextView tvYsBeizhu;
    @BindView(R.id.recycler_yanshou)
    RecyclerView recyclerYanshou;

    /***认领***/
    @BindView(R.id.col_rl)
    ConstraintLayout colRl;
    @BindView(R.id.tv_rl_status)
    TextView tvRlStatus;
    @BindView(R.id.tv_rl_name)
    TextView tvRlName;
    @BindView(R.id.tv_rl_time)
    TextView tvRlTime;

    /***认领结果***/
    @BindView(R.id.col_rl_complete)
    ConstraintLayout colRlComplete;
    @BindView(R.id.tv_rl_pingjia_status)
    TextView tvRlPingjiaStatus;
    @BindView(R.id.tv_rl_pingjia_desc)
    TextView tvRlPingjiaDesc;

    /***驳回***/
    @BindView(R.id.col_bh)
    ConstraintLayout colBh;
    @BindView(R.id.tv_bh_time)
    TextView tvBhTime;
    @BindView(R.id.tv_bh_result)
    TextView tvBhResult;


    //用于提交
    private int eventStatus;
    private int isClaim;  // 流转/认领
    private int isClaimAuth;  // 流转/认领
    private int isFinish;  // 是否有解决权限
    private int isModify;  // 是否有评价权限

    private String mEventId;
    private Boolean isPartEvent; // 部门事件跳转到详情默认隐藏按钮
    private PatrolDetailImgAdapter mAdapter;
    private PatrolDetailImgAdapter rvJjAdapter;
    private PatrolDetailImgAdapter yanshouAdapter;
    //先反馈问题
    private ERCheckResultWindow erCheckResultWindow;
    private SolveEvaluateWindow solveEvaluateWindow;

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
            isPartEvent = intent.getBooleanExtra("isPartEvent", false);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        /**** 上报的图片和视频 ****/
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mAdapter = new PatrolDetailImgAdapter(R.layout.gv_filter_image, new ArrayList<>());
        recyclerView.setAdapter(mAdapter);

        rvJjRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvJjAdapter = new PatrolDetailImgAdapter(R.layout.gv_filter_image, new ArrayList<>());
        rvJjRv.setAdapter(rvJjAdapter);

        recyclerYanshou.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        yanshouAdapter = new PatrolDetailImgAdapter(R.layout.gv_filter_image, new ArrayList<>());
        recyclerYanshou.setAdapter(yanshouAdapter);


        tvTitle.getTvRight().setOnClickListener(v -> {
            Intent intent = new Intent(EventReportDetailActivity_new.this,RecordLogActivity.class);
            intent.putExtra("eventId",mEventId);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadNewData(mEventId);
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
                mPresenter.loadNewData(mEventId);
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
        mPresenter.loadNewData(mEventId);
    }

    @Override
    public void readData(EventReadIndexBean bean) {

    }

    @Override
    public void readNewData(EventReadIndexBean_new bean) {
        address = bean.place;
        longitude = bean.longitude;
        eventStatus = bean.status;
        isClaim = bean.is_claim;
        isClaimAuth = bean.is_claim_auth;
        isFinish = bean.is_finish;
        isModify = bean.is_modify;
        //显示进度条状态

        if (!isPartEvent) {
            showBottomButton(bean);  // 底部按钮显示隐藏
        }
        collectHeadData(bean);   // 统一数据管理

        /********* 流转 **********/
        if (bean.turn != null) {
            EventReadIndexBean_new.TurnBean turn = bean.turn;
            setTurnData(turn);
        }

        /********* 联办 **********/
        if (bean.union != null) {
            EventReadIndexBean_new.UnionBean union = bean.union;
            setUnionData(union);
        }

        /********* 认领 **********/
        if (bean.send != null) {
            EventReadIndexBean_new.ExtraDateBean pfItem = bean.extra_date;
            setRlData(pfItem);
        }

        /********* 评价 *********/
        if (bean.claim_completed != null) {
            EventReadIndexBean_new.ClaimCompletedBean rlPingjia = bean.claim_completed;
            setRlEvaluate(rlPingjia);
        }

        /********* 派发 **********/
        if (bean.send != null) {
            EventReadIndexBean_new.SendBean pfItem = bean.send;
            setPfData(pfItem);
        }

        /********* 结果 **********/
        if (bean.solve != null) {
            EventReadIndexBean_new.SolveBean solve = bean.solve;
            setSolveData(solve);
        }

        /********* 验收 **********/
        if (bean.completed != null) {
            EventReadIndexBean_new.CompletedBean completed = bean.completed;
            setCompleted(completed);
        }

        /********* 驳回 **********/
        if (bean.rifiuta != null) {
            EventReadIndexBean_new.RifiutaBean rifiuta = bean.rifiuta;
            if (!TextUtils.isEmpty(rifiuta.operate_time)) {
                colBh.setVisibility(View.VISIBLE);
                tvBhTime.setText("驳回时间：" + rifiuta.operate_time);
                tvBhResult.setText("驳回原因：" + rifiuta.extra);
            } else {
                colBh.setVisibility(View.GONE);
            }
        }

        /*********** 验收通过 **********/

        /********* 解决结果 **********/

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
            if (bean.send != null) {
                parfaTime = bean.send.send_time;
            }
            if (bean.solve != null) {
                wufachuli = bean.solve.operate_time;
            }
            if (bean.turn != null && bean.union != null) {

            }
            cpbProgress.setNoSolveValue2(bean.add_time, parfaTime, wufachuli);
        } else {
            cpbProgress.setStatus(bean.status, getTime(bean));
        }
    }

    /**
     * 设置流转信息
     *
     * @param data
     */
    private void setTurnData(EventReadIndexBean_new.TurnBean data) {
        if (!TextUtils.isEmpty(data.operate_time)) {
            colLz.setVisibility(View.VISIBLE);
            tvLzTime.setText("流转时间：" + data.operate_time);
            tvLzPart.setText("流传部门：" + data.part_name);
            tvLzCause.setText("流传部门：" + data.extra);
        } else {
            colLz.setVisibility(View.GONE);
        }
    }

    /**
     * 联办
     *
     * @param data
     */
    private void setUnionData(EventReadIndexBean_new.UnionBean data) {
        if (!TextUtils.isEmpty(data.add_time)) {
            colLb.setVisibility(View.VISIBLE);
            tvLbTime.setText("联办时间：" + data.add_time);
            tvLbPart.setText("联办部门：" + data.part_name);
            tvLbTitle.setText("联办原因：" + data.extra);
        } else {
            colLb.setVisibility(View.GONE);
        }
    }

    /**
     * 验收
     *
     * @param data
     */
    private void setCompleted(EventReadIndexBean_new.CompletedBean data) {
        if (!TextUtils.isEmpty(data.operate_time)) {
            colYs.setVisibility(View.VISIBLE);
            tvYsTime.setText("验收时间：" + data.operate_time);
            tvYsResult.setText("验收结果：" + (data.status == 4 ? "验收通过" : "验收不通过"));
            tvYsBeizhu.setText("备注：" + data.extra);
            bandImgAndVideo(data.img, data.video, recyclerYanshou, yanshouAdapter);
        } else {
            colYs.setVisibility(View.GONE);
        }
    }

    /**
     * 认领评价
     *
     * @param data
     */
    private void setRlEvaluate(EventReadIndexBean_new.ClaimCompletedBean data) {
        if (!TextUtils.isEmpty(data.operate_time)) {
            colRlComplete.setVisibility(View.VISIBLE);
            tvRlPingjiaStatus.setText("评价详情：" + data.getAppraiseStr());
            tvRlPingjiaDesc.setText("评价详情：" + data.extra);
        } else {
            colRlComplete.setVisibility(View.GONE);
        }
    }

    /**
     * 设置解决的信息
     *
     * @param data
     */
    private void setSolveData(EventReadIndexBean_new.SolveBean data) {
        if (!TextUtils.isEmpty(data.operate_time)) {
            colJj.setVisibility(View.VISIBLE);
            tvJjTime.setText("解决反馈时间：" + data.operate_time);
            tvJjDesc.setText("事件问题描述：" + data.extra);
            bandImgAndVideo(data.img, data.video, rvJjRv, rvJjAdapter);
        } else {
            colJj.setVisibility(View.GONE);
        }
    }

    /**
     * 设置认领信息
     *
     * @param data
     */
    private void setRlData(EventReadIndexBean_new.ExtraDateBean data) {
        if (!TextUtils.isEmpty(data.send_time)) {
            colRl.setVisibility(View.VISIBLE);
            tvRlStatus.setText("状态：" + getStatus(data.status));
            tvRlName.setText("认领人：" + data.name);
            tvRlTime.setText("认领时间：" + data.send_time);
        } else {
            colRl.setVisibility(View.GONE);
        }
    }

    /**
     * 设置派发数据
     *
     * @param pfItem
     */
    private void setPfData(EventReadIndexBean_new.SendBean pfItem) {
        if (!TextUtils.isEmpty(pfItem.send_time)) {
            colPf.setVisibility(View.VISIBLE);
            tvPfTime.setText("派发时间：" + pfItem.send_time);
            tvPfName.setText("派发人：指挥中心");
            tvPfEndTime.setText("截止解决日期：" + pfItem.operate_date);
            tvPfImportance.setText("事件重要性：" + getImportanceStr(pfItem.important_type));
        } else {
            colPf.setVisibility(View.GONE);
        }
    }

    private List<String> getTime(EventReadIndexBean_new bean) {
        List<String> time = new ArrayList<>();
        switch (bean.status) {
            case 1:
                time.add(bean.add_time);
                break;
            case 2:
                time.add(bean.add_time);
                if (bean.send != null) {
                    time.add(bean.send.send_time);
                } else {
                    time.add("");
                }
                break;
            case 3:
                time.add(bean.add_time);
                if (bean.send != null) {
                    time.add(bean.send.send_time);
                } else {
                    time.add("");
                }
                if (bean.solve != null) {
                    time.add(bean.solve.operate_time);
                } else {
                    time.add("");
                }
                break;
            case 4:
                time.add(bean.add_time);
                if (bean.send != null) {
                    time.add(bean.send.send_time);
                } else {
                    time.add("");
                }
                if (bean.solve != null) {
                    time.add(bean.solve.operate_time);
                } else {
                    time.add("");
                }
                if (bean.completed != null) {
                    time.add(bean.completed.operate_time);
                } else {
                    time.add("");
                }
                break;
            case 5:
                time.add(bean.add_time);
                if (bean.send != null) {
                    time.add(bean.send.send_time);
                } else {
                    time.add("");
                }
                if (bean.solve != null) {
                    time.add(bean.solve.operate_time);
                } else {
                    time.add("");
                }
                if (bean.completed != null) {
                    time.add(bean.completed.operate_time);
                } else {
                    time.add("");
                }
                time.add("");
                break;
        }
        return time;
    }


    /******** 头部统一显示数据 **********/
    private void collectHeadData(EventReadIndexBean_new bean) {
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
    private void showBottomButton(EventReadIndexBean_new bean) {
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

    /**
     * 获取重要性描述
     * 1:低 2:中 3:高
     *
     * @param status
     * @return
     */
    private String getImportanceStr(int status) {
        String str = "";
        switch (status) {
            case 1:
                str = "低";
                break;
            case 2:
                str = "中";
                break;
            case 3:
                str = "高";
                break;
            default:
                str = "低";
                break;
        }

        return str;
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
        Intent intent = new Intent(context, EventReportDetailActivity_new.class);
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
                    Intent intent = new Intent(EventReportDetailActivity_new.this, EventMoveActivity.class);
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
