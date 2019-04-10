package com.sdxxtop.guardianapp.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.ExaminePresenter;
import com.sdxxtop.guardianapp.presenter.contract.ExamineContract;
import com.sdxxtop.guardianapp.ui.widget.SubjectItemView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ExamineActivity extends BaseMvpActivity<ExaminePresenter> implements ExamineContract.IView, SubjectItemView.OnSubjectClickListener {
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
    @BindView(R.id.siv_a)
    SubjectItemView selectA;
    @BindView(R.id.siv_b)
    SubjectItemView selectB;
    @BindView(R.id.siv_c)
    SubjectItemView selectC;
    @BindView(R.id.siv_d)
    SubjectItemView selectD;
    @BindView(R.id.tv_up)
    TextView tvUp;
    @BindView(R.id.tv_push)
    TextView tvPush;
    @BindView(R.id.tv_down)
    TextView tvDown;

    private boolean isSingle = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_examine;
    }

    @Override
    protected void initView() {
        super.initView();

        statusBar(false);
        showStatusBar();

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        selectA.setOnSubjectClickListener(this);
        selectB.setOnSubjectClickListener(this);
        selectC.setOnSubjectClickListener(this);
        selectD.setOnSubjectClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        //加载数据
        mPresenter.loadData();
    }

    @OnClick({R.id.tv_up, R.id.tv_push, R.id.tv_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_up: //上一题
                break;
            case R.id.tv_push: //确定
                break;
            case R.id.tv_down: //下一题
                break;
        }
    }

    public void resetSelectStatus() {
        selectA.setCheck(false);
        selectB.setCheck(false);
        selectC.setCheck(false);
        selectD.setCheck(false);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showData(String data) {
        selectA.setSelectIndexContent(SubjectItemView.TYPE_A, "safasfasasfda");
        selectB.setSelectIndexContent(SubjectItemView.TYPE_B, "afdasdf");
        selectC.setSelectIndexContent(SubjectItemView.TYPE_C, "asfasdgasdgasdgsadgsd");
        selectD.setSelectIndexContent(SubjectItemView.TYPE_D, "afdasfasdfasdfasdfasdfasdfasfasfasf");
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
}
