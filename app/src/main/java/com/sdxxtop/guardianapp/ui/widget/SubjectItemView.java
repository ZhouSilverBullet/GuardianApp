package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubjectItemView extends FrameLayout {

    public static final int TYPE_A = 0;
    public static final int TYPE_B = 1;
    public static final int TYPE_C = 2;
    public static final int TYPE_D = 3;

    @BindView(R.id.tv_select_value)
    TextView tvSelectValue;
    @BindView(R.id.tv_select_content)
    TextView tvSelectContent;
    @BindView(R.id.cb_select)
    CheckBox cbSelect;

    public SubjectItemView(@NonNull Context context) {
        this(context, null);
    }

    public SubjectItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubjectItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_subject_item, this, true);
        ButterKnife.bind(this);
        cbSelect.setClickable(false);

    }

    @OnClick(R.id.ll_root)
    public void onViewClicked() {
        boolean isCheck = !cbSelect.isChecked();
        if (mOnSubjectClickListener != null) {
            mOnSubjectClickListener.onSubjectClick(this, isCheck);
        }
        cbSelect.setChecked(isCheck);
    }


    /**
     * @param index   选项类型
     * @param content 内容
     */

    public void setSelectIndexContent(int index, String content) {
        String value = getSelectValue(index);
        tvSelectValue.setText(value);
        tvSelectContent.setText(content);
    }


    private String getSelectValue(int index) {
        String value;
        switch (index) {
            case TYPE_A:
                value = "A";
                break;
            case TYPE_B:
                value = "B";
                break;
            case TYPE_C:
                value = "C";
                break;
            case TYPE_D:
                value = "D";
                break;
            default:
                value = "A";
                break;
        }
        return value;
    }

    public boolean isCheck() {
        return cbSelect.isChecked();
    }

    public void setCheck(boolean isCheck) {
        cbSelect.setChecked(isCheck);
    }

    private OnSubjectClickListener mOnSubjectClickListener;

    public void setOnSubjectClickListener(OnSubjectClickListener onSubjectClickListener) {
        mOnSubjectClickListener = onSubjectClickListener;
    }

    public interface OnSubjectClickListener {
        void onSubjectClick(SubjectItemView subjectItemView, boolean isCheck);
    }
}
