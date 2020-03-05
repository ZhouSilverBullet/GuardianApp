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
import com.sdxxtop.guardianapp.model.bean.EventStreamDetailBean;
import com.sdxxtop.guardianapp.model.bean.MediaBean;
import com.sdxxtop.guardianapp.presenter.EventReportDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportDetailContract;
import com.sdxxtop.guardianapp.ui.activity.custom_event.Custom2TextView;
import com.sdxxtop.guardianapp.ui.adapter.PatrolDetailImgAdapter;
import com.sdxxtop.guardianapp.ui.pop.ERCheckResultWindow;
import com.sdxxtop.guardianapp.ui.pop.SelectMapPopView;
import com.sdxxtop.guardianapp.ui.pop.SolveEvaluateWindow;
import com.sdxxtop.guardianapp.ui.widget.BottomStyleDialog;
import com.sdxxtop.guardianapp.ui.widget.CustomProgressBar;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.Date2Util;
import com.sdxxtop.guardianapp.utils.SkipMapUtils;

import java.util.ArrayList;
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_check_faile)
    Button btnCheckFaile;
    @BindView(R.id.btn_check_success)
    Button btnCheckSuccess;
    @BindView(R.id.popwindow_bg)
    View popwindow_bg;
    @BindView(R.id.tv_title)
    TitleView tvTitle;

    /************ 统一的头部 ***************/
    @BindView(R.id.c2Tx_title)
    Custom2TextView c2TxTitle;
    @BindView(R.id.c2Tx_report_name)
    Custom2TextView c2TxReportName;
    @BindView(R.id.c2Tx_report_phone)
    Custom2TextView c2TxReportPhone;
    @BindView(R.id.c2Tx_report_part)
    Custom2TextView c2TxReportPart;
    @BindView(R.id.c2Tx_report_time)
    Custom2TextView c2TxReportTime;
    @BindView(R.id.c2Tx_report_find_type)
    Custom2TextView c2TxReportFindType;
    @BindView(R.id.c2Tx_report_find_place)
    Custom2TextView c2TxReportFindPlace;
    @BindView(R.id.c2Tx_report_duty_part)
    Custom2TextView c2TxReportDutyPart;
    @BindView(R.id.c2Tx_report_event_desc)
    Custom2TextView c2TxReportEventDesc;
    @BindView(R.id.c2Tx_report_position_desc)
    Custom2TextView c2TxReportPositionDesc;
    @BindView(R.id.c2Tx_report_event_category)
    Custom2TextView c2TxReportEventCategory;

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
    @BindView(R.id.c2Tx_lz_caozuoren)
    Custom2TextView c2TxLzCaozuoren;
    @BindView(R.id.c2Tx_lz_keshi)
    Custom2TextView c2TxLzKeshi;
    @BindView(R.id.c2Tx_lz_phone)
    Custom2TextView c2TxLzPhone;


    /***派发***/
    @BindView(R.id.col_pf)
    ConstraintLayout colPf;
    @BindView(R.id.tv_pf_time)
    TextView tvPfTime;
    @BindView(R.id.tv_pf_name)
    TextView tvPfName;
    @BindView(R.id.tv_pf_part)
    TextView tvPfPart;
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

    /***操作按钮***/
    @BindView(R.id.tvBtnClick)
    TextView tvBtnClick;
    @BindView(R.id.tvFuCha2)
    TextView tvFuCha2;
    @BindView(R.id.tvFuCha1)
    TextView tvFuCha1;

    /***镇区呼应***/
    @BindView(R.id.col_zq)
    ConstraintLayout colZq;
    @BindView(R.id.c2Tx_zq_caozuoren)
    Custom2TextView c2TxZqCaozuoren;
    @BindView(R.id.c2Tx_zq_heshiren)
    Custom2TextView c2TxZqHeshiren;
    @BindView(R.id.c2Tx_zq_keshi)
    Custom2TextView c2TxZqKeshi;
    @BindView(R.id.c2Tx_zq_phone)
    Custom2TextView c2TxZqPhone;
    @BindView(R.id.tv_zq_reason)
    TextView tvZqReason;


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
    /******弹框数据*******/
    private BottomStyleDialog dialog;  // 操作弹框
    private ArrayList<String> list = new ArrayList<>();

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
            Intent intent = new Intent(EventReportDetailActivity_new.this, RecordLogActivity.class);
            intent.putExtra("eventId", mEventId);
            startActivity(intent);
        });

        dialog = new BottomStyleDialog(EventReportDetailActivity_new.this, mEventId, popwindow_bg);
        tvBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null) {
                    dialog = new BottomStyleDialog(EventReportDetailActivity_new.this, mEventId, popwindow_bg);
                }
                dialog.show();
            }
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
    public void readNewData(EventStreamDetailBean bean) {
        address = bean.place;
        longitude = "";
        eventStatus = bean.status;
        isClaim = bean.is_claim_auth;
        isClaimAuth = bean.is_claim_auth;
//        isFinish = bean.is_finish;
        isModify = bean.is_modify;
        //操作权限
        dialog.setData(bean);


        if (!isPartEvent) {
            showBottomButton(bean);
        }
        collectHeadData(bean);   // 统一数据管理

        /********* 流转 **********/
        if (bean.turn != null) {
            EventStreamDetailBean.TurnBean turn = bean.turn;
            setTurnData(turn);
        }

        /********* 联办 **********/
        if (bean.union != null) {
            EventStreamDetailBean.UnionBean union = bean.union;
            setUnionData(union);
        }

        /********* 认领 **********/
        if (bean.send != null) {
            EventStreamDetailBean.ClaimBean renling = bean.claim;
            setRlData(renling);
        }

        /********* 派发 **********/
        if (bean.send != null) {
            EventStreamDetailBean.SendBean pfItem = bean.send;
            setPfData(pfItem);
        }

        /********* 结果 **********/
        if (bean.solve != null) {
            EventStreamDetailBean.SolveBean solve = bean.solve;
            setSolveData(solve);
        }

        /********* 验收 **********/
        if (bean.check != null) {
            EventStreamDetailBean.CheckBean checkBean = bean.check;
            setCompleted(checkBean);
        }

        /********* 验收 **********/
        if (bean.log != null) {
            EventStreamDetailBean.LogBean logBean = bean.log;
            setLogInfo(logBean);
        }

        /**********  认领状态下不显示进度条  ************/
        if (bean.is_claim == 1) {
            rlProgress.setVisibility(View.GONE);
            return;
        } else {
            rlProgress.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置流转信息
     *
     * @param data
     */
    private void setTurnData(EventStreamDetailBean.TurnBean data) {
        boolean a = isTextViewShow(tvLzTime, data.operate_time, "流转时间：");
        boolean b = isTextViewShow(tvLzPart, data.part_name, "流转部门：");
        boolean c = isTextViewShow(tvLzCause, data.extra, "流转原因：");
        c2TxLzCaozuoren.getTvRight().setText(data.verify_person);  //事件核实人
        c2TxLzKeshi.getTvRight().setText(data.department);  //所在科室
        c2TxLzPhone.getTvRight().setText(data.telephone);  //联系电话

        if (a || b || c) {
            colLz.setVisibility(View.VISIBLE);
//            tvLzTime.setText("流转时间：" + data.operate_time);
//            tvLzPart.setText("流转部门：" + data.part_name);
//            tvLzCause.setText("流转原因：" + data.extra);
        } else {
            colLz.setVisibility(View.GONE);
        }
    }

    /**
     * 联办
     *
     * @param data
     */
    private void setUnionData(EventStreamDetailBean.UnionBean data) {
        boolean a = isTextViewShow(tvLbTime, "", "联办时间：");
        boolean b = isTextViewShow(tvLbPart, data.part_name, "联办部门：");
        boolean c = isTextViewShow(tvLbTitle, data.extra, "联办原因：");
        if (a || b || c) {
            colLb.setVisibility(View.VISIBLE);
//            tvLbTime.setText("联办时间：" + data.add_time);
//            tvLbPart.setText("联办部门：" + data.part_name);
//            tvLbTitle.setText("联办原因：" + data.extra);
        } else {
            colLb.setVisibility(View.GONE);
        }
    }

    /**
     * 验收
     *
     * @param data
     */
    private void setCompleted(EventStreamDetailBean.CheckBean data) {
        boolean a = isTextViewShow(tvYsTime, data.check_time, "验收时间：");
        tvFuCha1.setVisibility(a ? View.VISIBLE : View.GONE);
        boolean b = isTextViewShow(tvYsResult, eventStatus == 4 ? "验收通过" : "验收不通过", "验收结果：");
        boolean c = isTextViewShow(tvYsBeizhu, data.extra, "备注：");
        boolean d = isTextViewShow(tvRlPingjiaStatus, data.appraise, "评价情况：");
        boolean e = isTextViewShow(tvRlPingjiaDesc, data.appExtra, "评价描述：");

        if (a || c || !TextUtils.isEmpty(data.img) || !TextUtils.isEmpty(data.video) || d || e) {
            colYs.setVisibility(View.VISIBLE);
//            tvYsTime.setText("验收时间：" + data.operate_time);
//            tvYsResult.setText("验收结果：" + (data.status == 4 ? "验收通过" : "验收不通过"));
//            tvYsBeizhu.setText("备注：" + data.extra);
            bandImgAndVideo(data.img, data.video, recyclerYanshou, yanshouAdapter);
        } else {
            colYs.setVisibility(View.GONE);
        }
    }

    /**
     * 设置解决的信息
     *
     * @param data
     */
    private void setSolveData(EventStreamDetailBean.SolveBean data) {
        boolean a = isTextViewShow(tvJjTime, data.settle_time, "解决反馈时间：");
        boolean b = isTextViewShow(tvJjDesc, data.settle_reason, "事件问题描述：");
        if (a || b || !TextUtils.isEmpty(data.finish_img) || !TextUtils.isEmpty(data.finish_video)) {
            colJj.setVisibility(View.VISIBLE);
//            tvJjTime.setText("解决反馈时间：" + data.operate_time);
//            tvJjDesc.setText("事件问题描述：" + data.extra);
            bandImgAndVideo(data.finish_img, data.finish_video, rvJjRv, rvJjAdapter);
        } else {
            colJj.setVisibility(View.GONE);
        }
    }

    /**
     * 设置认领信息
     *
     * @param data
     */
    private void setRlData(EventStreamDetailBean.ClaimBean data) {
        boolean a = isTextViewShow(tvRlStatus, getStatus(data.status), "状态：");
        boolean b = isTextViewShow(tvRlName, data.send_name, "认领人：");
        boolean c = isTextViewShow(tvRlTime, data.send_time, "认领时间：");
        if (b || c) {
            colRl.setVisibility(View.VISIBLE);
//            tvRlStatus.setText("状态：" + getStatus(data.status));
//            tvRlName.setText("认领人：" + data.name);
//            tvRlTime.setText("认领时间：" + data.send_time);
        } else {
            colRl.setVisibility(View.GONE);
        }
    }

    /**
     * 设置派发数据
     *
     * @param data
     */
    private void setPfData(EventStreamDetailBean.SendBean data) {
        boolean a = isTextViewShow(tvPfTime, data.send_time, "派发时间：");
        boolean c = isTextViewShow(tvPfName, TextUtils.isEmpty(data.send_name) ? "指挥中心" : data.send_name, "派发人：");
        boolean b = isTextViewShow(tvPfEndTime, data.end_date, "截止解决日期：");
        boolean d = isTextViewShow(tvPfImportance, data.important_type, "事件重要性：");
        boolean e = isTextViewShow(tvPfPart, data.send_part_name, "派发部门：");
        if (a || b || d || e) {
            colPf.setVisibility(View.VISIBLE);
//            tvPfTime.setText("派发时间：" + data.send_time);
//            tvPfEndTime.setText("截止解决日期：" + data.operate_date);
//            tvPfName.setText("派发人：指挥中心");
//            tvPfImportance.setText("事件重要性：" + getImportanceStr(data.important_type));
        } else {
            colPf.setVisibility(View.GONE);
        }
    }

    /**
     * 设置镇区呼应信息
     *
     * @param data
     */
    private void setLogInfo(EventStreamDetailBean.LogBean data) {
        checkTextView(c2TxZqCaozuoren.getTvRight(), data.name, c2TxZqCaozuoren);
        checkTextView(c2TxZqHeshiren.getTvRight(), data.verify_person, c2TxZqHeshiren);
        checkTextView(c2TxZqKeshi.getTvRight(), data.department, c2TxZqKeshi);
        checkTextView(c2TxZqPhone.getTvRight(), data.telephone, c2TxZqPhone);
        isTextViewShow(tvZqReason, data.extra, "流转原因：");
        if (data.name.isEmpty() || data.verify_person.isEmpty() || data.department.isEmpty() || data.telephone.isEmpty()) {
            colZq.setVisibility(View.GONE);
        } else {
            colZq.setVisibility(View.VISIBLE);
        }
    }

    private List<String> getTime(EventStreamDetailBean bean) {
        List<String> time = new ArrayList<>();
        switch (bean.status) {
            case 1:
                time.add(bean.add_time);
                break;
            case 2:
                time.add(bean.add_time);
                if (bean.send != null) {
                    time.add((TextUtils.isEmpty(bean.send.send_time) && bean.claim.status == 4) ? bean.claim.send_time : bean.send.send_time);
                } else {
                    time.add("");
                }
                break;
            case 3:
                time.add(bean.add_time);
                if (bean.send != null) {
                    time.add((TextUtils.isEmpty(bean.send.send_time) && bean.claim.status == 4) ? bean.claim.send_time : bean.send.send_time);
                } else {
                    time.add("");
                }
                if (bean.solve != null) {
                    time.add(bean.solve.settle_time);
                } else {
                    time.add("");
                }
                break;
            case 4:
                time.add(bean.add_time);
                if (bean.send != null) {
                    time.add((TextUtils.isEmpty(bean.send.send_time) && bean.claim.status == 4) ? bean.claim.send_time : bean.send.send_time);
                } else {
                    time.add("");
                }
                if (bean.solve != null) {
                    time.add(bean.solve.settle_time);
                } else {
                    time.add("");
                }
                if (bean.check != null) {
                    time.add(bean.check.check_time);
                } else {
                    time.add("");
                }
                break;
            case 5:
                time.add(bean.add_time);
                if (bean.send != null) {
                    time.add((TextUtils.isEmpty(bean.send.send_time) && bean.claim.status == 4) ? bean.claim.send_time : bean.send.send_time);
                } else {
                    time.add("");
                }
                if (bean.solve != null) {
                    time.add(bean.solve.settle_time);
                } else {
                    time.add("");
                }
                if (bean.check != null) {
                    time.add(bean.check.check_time);
                } else {
                    time.add("");
                }
                time.add("");
                break;
        }
        return time;
    }


    /******** 事件流 头部统一显示数据 **********/
    private void collectHeadData(EventStreamDetailBean bean) {
        bandImgAndVideo(bean.img, bean.video, recyclerView, mAdapter);
        checkTextView(c2TxTitle.getTvRight(), bean.title, c2TxTitle);
        checkTextView(c2TxReportName.getTvRight(), bean.username, c2TxReportName);
        checkTextView(c2TxReportPhone.getTvRight(), bean.mobile, c2TxReportPhone);
        checkTextView(c2TxReportPart.getTvRight(), bean.part_name, c2TxReportPart);
        checkTextView(c2TxReportTime.getTvRight(), Date2Util.handleTime(bean.add_time), c2TxReportTime);
        checkTextView(c2TxReportFindType.getTvRight(), bean.patrol_type, c2TxReportFindType);
        checkTextView(c2TxReportFindPlace.getTvRight(), bean.place, c2TxReportFindPlace);
        checkTextView(c2TxReportDutyPart.getTvRight(), bean.path_name, c2TxReportDutyPart);
        checkTextView(c2TxReportEventDesc.getTvLeft(), TextUtils.isEmpty(bean.content) ? "" :
                new StringBuilder().append("事件简要描述：").append(bean.content).toString(), c2TxReportEventDesc);
        checkTextView(c2TxReportPositionDesc.getTvLeft(), TextUtils.isEmpty(bean.supplement) ? "" :
                new StringBuilder().append("定位补充描述：").append(bean.supplement).toString(), c2TxReportPositionDesc);
        checkTextView(c2TxReportEventCategory.getTvRight(), bean.category_name, c2TxReportEventCategory);
        tvTitle.getTvRight().setVisibility(bean.basicOperation == 1 ? View.VISIBLE : View.GONE);

        getTime(bean);
        cpbProgress.setStatus(bean.status, getTime(bean));
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

    /**
     * 判断 图片/视频 资源是否有
     *
     * @param img
     * @param vedio
     * @param recyclerView
     * @param adapter
     */
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

    public void renling() {
        mPresenter.failed(mEventId, "", 2);
    }

    @OnClick({R.id.btn_check_success, R.id.c2Tx_report_find_place, R.id.btn_check_faile})
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
            case R.id.c2Tx_report_find_place:
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

    /**
     * 检查是否隐藏布局
     *
     * @param tx  TextView
     * @param str 数据
     */
    protected void checkTextView(TextView tx, String str, Custom2TextView c2tv) {
        if (TextUtils.isEmpty(str)) {
            c2tv.setVisibility(View.GONE);
        } else {
            tx.setText(str);
            c2tv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 检查TextView是否显示
     *
     * @param tx
     * @param str
     * @return
     */
    protected boolean isTextViewShow(TextView tx, String str, String startAppend) {
        if (TextUtils.isEmpty(str)) {
            tx.setVisibility(View.GONE);
            return false;
        } else {
            tx.setText(startAppend + str);
            tx.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public void showBottomButton(EventStreamDetailBean bean) {
        list.clear();
        if (bean.is_claim_auth == 1) list.add("认领");
        if (bean.is_Operate_auth == 1) list.add("流转");
        if (bean.unable_solve == 1) list.add("无法解决");
        if (bean.is_solve == 1) list.add("解决");
        if (bean.is_modify == 1) list.add("验收通过");
        if (bean.unable_check == 1) list.add("验收不通过");
        dialog.setData(list);
        tvBtnClick.setVisibility(list.size() == 0 ? View.GONE : View.VISIBLE);
    }
}
