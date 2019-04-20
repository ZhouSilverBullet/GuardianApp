package com.sdxxtop.guardianapp.ui.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.ExamineFinishBean;
import com.sdxxtop.guardianapp.model.bean.StudyCheckBean;
import com.sdxxtop.guardianapp.model.bean.StudyQuestionBean;
import com.sdxxtop.guardianapp.presenter.ExaminePresenter;
import com.sdxxtop.guardianapp.presenter.contract.ExamineContract;
import com.sdxxtop.guardianapp.ui.control.ExamineTimeCountdown;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.SubjectItemView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.GuardianUtils;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ExamineActivity extends BaseMvpActivity<ExaminePresenter> implements ExamineContract.IView, SubjectItemView.OnSubjectClickListener, ExamineTimeCountdown.CompleteListener {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.tv_face)
    TextView tvFace;
    @BindView(R.id.tv_face_status)
    TextView tvFaceStatus;
    @BindView(R.id.tv_subject)
    TextView tvSubject;
    @BindView(R.id.tv_show_time)
    TextView tvShowTime;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.tv_subject_type)
    TextView tvSubjectType;
    @BindView(R.id.tv_subject_content)
    TextView tvSubjectContent;
    @BindView(R.id.civ_header)
    CircleImageView civHeader;

    @BindView(R.id.ll_subject)
    LinearLayout llSubject;

    @BindView(R.id.tv_up)
    TextView tvUp;
    @BindView(R.id.tv_push)
    TextView tvPush;
    @BindView(R.id.tv_down)
    TextView tvDown;

    private boolean isSingle = true;
    private int mExamId;
    private volatile String mAttendId;
    private boolean mIsLast;
    private volatile int mNumber;
    private volatile int mQuestionId;
    private volatile int mQuestionNum;
    private String mExamTime;
    private ExamineTimeCountdown mTimeCountdown;
    private String mScore;
    private String mTitle;
    private String mAnwer;

    @Override
    protected int getLayout() {
        return R.layout.activity_examine;
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        mExamId = getIntent().getIntExtra("examId", 0);
        mExamTime = getIntent().getStringExtra("examTime");
        mTitle = getIntent().getStringExtra("title");
    }

    @Override
    protected void initView() {
        super.initView();

        statusBar(false);
        showStatusBar();
        if (!TextUtils.isEmpty(mTitle)) {
            mTitleView.setTitleValue(mTitle);
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
//        selectA.setOnSubjectClickListener(this);
//        selectB.setOnSubjectClickListener(this);
//        selectC.setOnSubjectClickListener(this);
//        selectD.setOnSubjectClickListener(this);

        mTitleView.resetBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFinishDialog();
            }
        });
    }

    private void showFinishDialog() {
        new IosAlertDialog(this)
                .builder()
                .setTitle("提示")
                .setMsg("是否退出这次考试")
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showFinishDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initData() {
        super.initData();
        //加载数据

        loadData(mExamId, 1, "0");

        //开始倒计时
        mTimeCountdown = new ExamineTimeCountdown(tvShowTime, this);
        mTimeCountdown.start(mExamTime);

        //头像显示
        String img = SpUtil.getString(Constants.USER_IMG);
        if (!TextUtils.isEmpty(img)) {
            Glide.with(this).load(img).into(civHeader);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeCountdown != null) {
            mTimeCountdown.destroy();
            mTimeCountdown = null;
        }
    }

    private void loadData(int examId, int number, String attendId) {
        mPresenter.loadData(examId, number, attendId);
    }

    @OnClick({R.id.tv_up, R.id.tv_push, R.id.tv_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_up: //上一题
                int up = mNumber - 1;
                if (up <= 0) {
                    UIUtils.showToast("已经是第一题了");
                    return;
                }
                loadData(mExamId, up, mAttendId);
                break;
            case R.id.tv_push: //确定
                pushQuestion();
                break;
            case R.id.tv_down: //下一题
                int down = mNumber + 1;
                if (down > mQuestionNum) {
                    UIUtils.showToast("已经是最后一题了");
                    return;
                }
                loadData(mExamId, down, mAttendId);
                break;
        }
    }

    private void pushQuestion() {
        String answer = "";

        for (int i = 0; i < llSubject.getChildCount(); i++) {
            SubjectItemView childAt = (SubjectItemView) llSubject.getChildAt(i);
            if (childAt.isCheck()) {
                answer += childAt.getSelectValue() + ",";
            }
        }

        if (answer.length() > 0) {
            answer = answer.substring(0, answer.length() - 1);
        }

        if (TextUtils.isEmpty(answer)) {
            showToast("请选择答案提交");
            return;
        }

        mPresenter.pushSelectQuestion(mExamId, mQuestionId, answer, mNumber, mAttendId);
    }

    public void resetSelectStatus() {
        for (int i = 0; i < llSubject.getChildCount(); i++) {
            View childAt = llSubject.getChildAt(i);
            if (childAt instanceof SubjectItemView) {
                ((SubjectItemView) childAt).setCheck(false);
            }
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onSubjectClick(SubjectItemView subjectItemView, boolean isCheck) {
        if (isSingle) {
            resetSelectStatus();
        }
    }

    @Override
    public void showData(StudyQuestionBean bean) {
        //刷新一下选择的状态
//        resetSelectStatus();

        List<String> question = bean.getQuestion();
        int type = bean.getType();
        isSingle = type == 1;
        String anwer = bean.getAnwer();

        handleSelect(anwer, question);

        mAttendId = bean.getAttend_id();
        mNumber = bean.getNumber();
        mQuestionId = bean.getQuestion_id();

        mQuestionNum = bean.getQuestion_num();
        tvSubject.setText("第" + mNumber + "/" + mQuestionNum + "题");
//        tvShowTime.setText("");
        tvSubjectType.setText(isSingle ? "单选题目" : "多选题目");
        tvSubjectContent.setText(bean.getTitle());
        mAnwer = bean.getAnwer();
        if (TextUtils.isEmpty(mAnwer)) {
            tvPush.setVisibility(View.VISIBLE);
        } else {
            tvPush.setVisibility(View.INVISIBLE);
        }
        //1.是 2.否
        mIsLast = bean.getIs_last() == 1;
//        mScore = bean.getScore();
    }

    private void handleSelect(String anwer, List<String> question) {
        llSubject.removeAllViews();
        if (question == null) {
            return;
        }

        boolean isClick = TextUtils.isEmpty(anwer);
        for (int i = 0; i < question.size(); i++) {
            SubjectItemView subjectItemView = new SubjectItemView(this);
            subjectItemView.setOnSubjectClickListener(this);
            subjectItemView.setSelectIndexContent(i, question.get(i));
            subjectItemView.setItemViewClickable(isClick);
            llSubject.addView(subjectItemView);
        }

        int childCount = llSubject.getChildCount();
        //计算是否选中
        if (!TextUtils.isEmpty(anwer)) {
            if (anwer.startsWith(",") && anwer.length() > 1) {
                anwer = anwer.substring(1, anwer.length());
            }
            String[] split = anwer.split(",");
            for (String strIndex : split) {
                int index = GuardianUtils.getQuestionIndex(strIndex);
                if (index < childCount && index >= 0) {
                    ((SubjectItemView) llSubject.getChildAt(index)).setCheck(true);
                }
            }
        }

    }

    @Override
    public void pushQuestionSuccess(StudyCheckBean studyCheckBean) {
        if (mIsLast) { //最后一题了
//            showLastDialog();
            finishLoad();
        } else { //不是最后一题继续答题
            loadData(mExamId, mNumber + 1, mAttendId);
        }
    }

    private void showLastDialog() {
        new IosAlertDialog(this)
                .builder()
                .setMsg("本次考试得分")
                .setMsg2(mScore)
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setDismissListener(new IosAlertDialog.DialogDismissListener() {
                    @Override
                    public void dismiss() {
                        finish();
                    }
                })
                .show();
    }

    private void finishLoad() {
        mPresenter.finishData(mExamId, mAttendId);
    }

    @Override
    public void finishSuccess(ExamineFinishBean finishBean) {
        mScore = finishBean.getScore();
        showLastDialog();
    }

    @Override
    public void finishFailure() {
        finish();
    }

    @Override
    public void onComplete() {
        //完成考试了
        finishLoad();
    }
}
