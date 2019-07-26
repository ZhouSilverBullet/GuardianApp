package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.ui.activity.EventDiscretionListActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportListActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolAddDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.SectionEventActivity;
import com.sdxxtop.guardianapp.ui.activity.TaskAgentsActivity;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author :  lwb
 * Date: 2019/5/28
 * Desc:
 */
public class ImgAndTextLinearView extends LinearLayout {
    @BindView(R.id.tv_task_title)
    TextView tvTaskTitle;
    @BindView(R.id.tv_more)
    public TextView tvMore;
    @BindView(R.id.iv_icon_1)
    ImageView ivIcon1;
    @BindView(R.id.tv_title_1)
    TextView tvTitle1;
    @BindView(R.id.tv_desc_1)
    TextView tvDesc1;
    @BindView(R.id.iv_icon_2)
    ImageView ivIcon2;
    @BindView(R.id.tv_title_2)
    TextView tvTitle2;
    @BindView(R.id.tv_desc_2)
    TextView tvDesc2;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_task_layout_1)
    LinearLayout llTaskLayout1;
    @BindView(R.id.ll_task_layout_2)
    LinearLayout llTaskLayout2;

    private String taskTitle, taskPrefix;
    private Drawable ivIcon;
    private int mItemClick;
    private List<TagEventBean> mData;

    public ImgAndTextLinearView(Context context) {
        this(context, null);
    }

    public ImgAndTextLinearView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImgAndTextLinearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImgAndTextLinearView, defStyleAttr, 0);
        taskTitle = typedArray.getString(R.styleable.ImgAndTextLinearView_tv_task_title);
        taskPrefix = typedArray.getString(R.styleable.ImgAndTextLinearView_tv_task_prefix);
        ivIcon = typedArray.getDrawable(R.styleable.ImgAndTextLinearView_iv_icon);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_img_text_layout, this, true);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(taskTitle)) {
            tvTaskTitle.setText(taskTitle);
        }
        if (ivIcon != null) {
            ivIcon1.setImageDrawable(ivIcon);
            ivIcon2.setImageDrawable(ivIcon);
        }
    }


    public void setData(List<TagEventBean> data) {
        this.mData = data;
        tvNoData.setVisibility(View.GONE);
        if (data.size() == 1) {
            llTaskLayout1.setVisibility(View.VISIBLE);
            llTaskLayout2.setVisibility(View.GONE);
            tvTitle1.setText(data.get(0).getTitle());
            tvDesc1.setVisibility(TextUtils.isEmpty(data.get(0).getEndTime()) ? View.GONE : View.VISIBLE);
            tvDesc1.setText(data.get(0).getEndTime());
        } else if (data.size() == 2) {
            llTaskLayout1.setVisibility(View.VISIBLE);
            llTaskLayout2.setVisibility(View.VISIBLE);
            tvTitle1.setText(data.get(0).getTitle());
            tvDesc1.setText(data.get(0).getEndTime());
            tvTitle2.setText(data.get(1).getTitle());
            tvDesc2.setText(data.get(1).getEndTime());

            tvDesc1.setVisibility(TextUtils.isEmpty(data.get(0).getEndTime()) ? View.GONE : View.VISIBLE);
            tvDesc1.setText(data.get(0).getEndTime());
            tvDesc2.setVisibility(TextUtils.isEmpty(data.get(1).getEndTime()) ? View.GONE : View.VISIBLE);
            tvDesc2.setText(data.get(1).getEndTime());

        } else {
            setNoDate();
        }
    }

    public void setNoDate() {
        llTaskLayout1.setVisibility(View.INVISIBLE);
        llTaskLayout2.setVisibility(View.INVISIBLE);
        tvNoData.setVisibility(View.VISIBLE);
    }

    public void setOnClick(int i) {
        this.mItemClick = i;
    }

    @OnClick({R.id.tv_more, R.id.ll_task_layout_1, R.id.ll_task_layout_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                switch (mItemClick) {
                    case 1:
                        getContext().startActivity(new Intent(getContext(), TaskAgentsActivity.class));
                        break;
                    case 2:
                        getContext().startActivity(new Intent(getContext(), EventReportListActivity.class));
                        break;
                    case 3:
                        getContext().startActivity(new Intent(getContext(), EventDiscretionListActivity.class));
                        break;
                    case 4:
                        getContext().startActivity(new Intent(getContext(), SectionEventActivity.class));
                        break;
                }
                break;
            case R.id.ll_task_layout_1:
                if (mData != null && mData.size() > 0) {
                    skip(0);
                }
                break;
            case R.id.ll_task_layout_2:
                if (mData != null && mData.size() > 1) {
                    skip(1);
                }
                break;
        }
    }

    private void skip(int position) {
        switch (mItemClick) {
            case 1:   // 上报详情
            case 2:
                Intent intent = new Intent(getContext(), EventReportDetailActivity.class);
                intent.putExtra("eventId", String.valueOf(mData.get(position).getEventId()));
                getContext().startActivity(intent);
                break;
            case 3:
                Intent intentPatrol = new Intent(getContext(), PatrolAddDetailActivity.class);
                intentPatrol.putExtra("patrol_id", mData.get(position).getEventId());
                getContext().startActivity(intentPatrol);
                break;
        }
    }

    public static class TagEventBean {
        private int eventId;
        private String title;
        private String endTime;
        private String status;

        public TagEventBean(int eventId, String title, String endTime,String status) {
            this.eventId = eventId;
            this.title = title;
            this.endTime = endTime;
            this.status = status;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public String getTitle() {
            return status+" : "+title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEndTime() {
            return (TextUtils.isEmpty(endTime) || "1000-01-01".equals(endTime)) ? "" : "截止日期：" + endTime.replace("-","/");
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
