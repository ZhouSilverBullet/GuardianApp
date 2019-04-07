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
        cbSelect.setChecked(!cbSelect.isChecked());
    }


    /**
     *
     *
     *
     * @param index 选项
     * @param content 内容
     */

    public void setSelectIndexContent(String index, String content) {
        tvSelectValue.setText(index);
        tvSelectContent.setText(content);
    }
}
