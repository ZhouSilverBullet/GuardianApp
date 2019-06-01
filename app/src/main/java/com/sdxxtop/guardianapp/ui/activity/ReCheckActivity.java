package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.presenter.ReCheckPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ReCheckContract;
import com.sdxxtop.guardianapp.ui.widget.CustomVideoImgSelectView;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class ReCheckActivity extends BaseMvpActivity<ReCheckPresenter> implements ReCheckContract.IView{

    @BindView(R.id.cvisv_view)
    CustomVideoImgSelectView cvisvView;
    @BindView(R.id.net_content)
    NumberEditTextView netContent;
    @BindView(R.id.btn_push)
    Button btnPush;


    private int patrolId;

    @Override
    protected int getLayout() {
        return R.layout.activity_re_check;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        super.initView();

        patrolId = getIntent().getIntExtra("patrol_id", 0);

        netContent.setEditHint("");
        netContent.setMaxLength(200);
    }

    @OnClick(R.id.btn_push)
    public void onViewClicked() {
        List<File> imagePushPath = cvisvView.getImageOrVideoPushPath(1);
        List<File> videoPushPath = cvisvView.getImageOrVideoPushPath(2);
        if (imagePushPath.size() == 0 && videoPushPath.size() == 0) {
            showToast("请选择图片或者视频");
            return;
        }

        String editValue = netContent.getEditValue();
        if (TextUtils.isEmpty(editValue)) {
            showToast("请填写事件描述内容");
            return;
        }
        mPresenter.patrolHandle(editValue,imagePushPath, videoPushPath,patrolId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hideLoadingDialog();
        if (resultCode == RESULT_OK) {
            cvisvView.callActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void showMsg(RequestBean bean) {
        UIUtils.showToast(bean.getMsg());
        finish();
    }
}
