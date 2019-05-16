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
public class GridreportPatrolTabTitleView extends LinearLayout implements View.OnClickListener {
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
    @BindView(R.id.ll_containor_2)
    LinearLayout ll_containor_2;
    @BindView(R.id.ll_containor_3)
    LinearLayout ll_containor_3;
    @BindView(R.id.ll_containor_4)
    LinearLayout ll_containor_4;
    @BindView(R.id.ll_containor_5)
    LinearLayout ll_containor_5;

    private boolean isOrder;
    private int currentItemClick = 0;

    public GridreportPatrolTabTitleView(Context context) {
        this(context, null);
    }

    public GridreportPatrolTabTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridreportPatrolTabTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_enterprise_title, this, true);
        ButterKnife.bind(this);
        ll_containor_5.setVisibility(View.GONE);
        tvText1.setText("姓名");
        tvText2.setText("所属区域");
        tvText3.setText("巡逻天数");
        tvText4.setText("巡逻距离");
        ll_containor_2.setOnClickListener(this);
        ll_containor_3.setOnClickListener(this);
        ll_containor_4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_containor_2:
                if (currentItemClick==EnterpriseCompanyCompar.COMPANY_CHOOSE_AREA){
                    isOrder = !isOrder;
                }else{
                    isOrder = false;
                    currentItemClick = EnterpriseCompanyCompar.COMPANY_CHOOSE_AREA;
                }
                listener.onTextClick(currentItemClick,isOrder);
                break;
            case R.id.ll_containor_3:
                if (currentItemClick==EnterpriseCompanyCompar.COMPANY_CHOOSE_SEU_COUNT){
                    isOrder = !isOrder;
                }else{
                    isOrder = false;
                    currentItemClick = EnterpriseCompanyCompar.COMPANY_CHOOSE_SEU_COUNT;
                }
                listener.onTextClick(currentItemClick,isOrder);
                break;
            case R.id.ll_containor_4:
                if (currentItemClick==EnterpriseCompanyCompar.COMPANY_CHOOSE_TRAIN_COUNT){
                    isOrder = !isOrder;
                }else{
                    isOrder = false;
                    currentItemClick = EnterpriseCompanyCompar.COMPANY_CHOOSE_TRAIN_COUNT;
                }
                listener.onTextClick(currentItemClick,isOrder);
                break;
        }
    }

    private OnTextClickListener listener;

    public interface OnTextClickListener {
        void onTextClick(int num, boolean isOrder);
    }

    public void setOnTextClickListener(OnTextClickListener listener) {
        this.listener = listener;
    }
}
