package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.ui.widget.timerselect.BottomDialogView;
import com.sdxxtop.guardianapp.utils.UIUtils;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author :  lwb
 * Date: 2019/5/9
 * Desc:
 */
public class GERTimeSelectView extends LinearLayout {
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    public GERTimeSelectView(Context context) {
        this(context, null);
    }

    public GERTimeSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GERTimeSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_time_select, this, true);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                BottomDialogView bottomDialogView = new BottomDialogView(getContext(), new BottomDialogView.onConfirmClick() {
                    @Override
                    public void onClick(String time) {
                        UIUtils.showToast(time);
                    }
                });
                bottomDialogView.show();
                break;
            case R.id.tv_end_time:
                BottomDialogView bottomDialogView1 = new BottomDialogView(getContext(), new BottomDialogView.onConfirmClick() {
                    @Override
                    public void onClick(String time) {
                        UIUtils.showToast(time);
                    }
                });
                bottomDialogView1.show();
                break;
        }
    }

}
