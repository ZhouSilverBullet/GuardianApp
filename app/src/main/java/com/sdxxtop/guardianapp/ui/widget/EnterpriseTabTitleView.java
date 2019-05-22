package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.compar.EnterpriseCompanyCompar;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :  lwb
 * Date: 2019/5/11
 * Desc:
 */
public class EnterpriseTabTitleView extends LinearLayout implements View.OnClickListener {
    @BindView(R.id.tv_text_1)
    TextView tvText1;
    @BindView(R.id.tv_text_2)
    TextView tvText2;
    @BindView(R.id.tv_text_3)
    TextView tvText3;
    @BindView(R.id.tv_text_4)
    TextView tvText4;
    @BindView(R.id.tv_text_5)
    TextView tvText5;

    private boolean isOrder;
    private int currentItemClick = 0;

    public EnterpriseTabTitleView(Context context) {
        this(context, null);
    }

    public EnterpriseTabTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnterpriseTabTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_enterprise_title, this, true);
        ButterKnife.bind(this);
        tvText1.setText("企业名称");
        tvText2.setText("所属单位");
        tvText3.setText("安全管理\n" + "员人数");
        tvText4.setText("学习培训\n" + "总次数");
        tvText5.setText("上报自查\n" + "总次数");
        tvText2.setOnClickListener(this);
        tvText3.setOnClickListener(this);
        tvText4.setOnClickListener(this);
        tvText5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_text_2:
                if (currentItemClick==EnterpriseCompanyCompar.COMPANY_CHOOSE_AREA){
                    isOrder = !isOrder;
                }else{
                    isOrder = false;
                    currentItemClick = EnterpriseCompanyCompar.COMPANY_CHOOSE_AREA;
                }
                listener.onTextClick(currentItemClick,isOrder);
                break;
            case R.id.tv_text_3:
                if (currentItemClick==EnterpriseCompanyCompar.COMPANY_CHOOSE_SEU_COUNT){
                    isOrder = !isOrder;
                }else{
                    isOrder = false;
                    currentItemClick = EnterpriseCompanyCompar.COMPANY_CHOOSE_SEU_COUNT;
                }
                listener.onTextClick(currentItemClick,isOrder);
                break;
            case R.id.tv_text_4:
                if (currentItemClick==EnterpriseCompanyCompar.COMPANY_CHOOSE_TRAIN_COUNT){
                    isOrder = !isOrder;
                }else{
                    isOrder = false;
                    currentItemClick = EnterpriseCompanyCompar.COMPANY_CHOOSE_TRAIN_COUNT;
                }
                listener.onTextClick(currentItemClick,isOrder);
                break;
            case R.id.tv_text_5:
                if (currentItemClick==EnterpriseCompanyCompar.COMPANY_CHOOSE_REPORT_INFO){
                    isOrder = !isOrder;
                }else{
                    isOrder = false;
                    currentItemClick = EnterpriseCompanyCompar.COMPANY_CHOOSE_REPORT_INFO;
                }
                listener.onTextClick(currentItemClick,isOrder);
                break;
        }
    }

    private OnTextClickListener listener;

    public interface OnTextClickListener {
        void onTextClick(int num,boolean isOrder);
    }

    public void setOnTextClickListener(OnTextClickListener listener) {
        this.listener = listener;
    }
}
