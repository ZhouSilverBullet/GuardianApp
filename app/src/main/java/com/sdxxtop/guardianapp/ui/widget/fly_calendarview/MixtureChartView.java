package com.sdxxtop.guardianapp.ui.widget.fly_calendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.DeviceDataBean;
import com.sdxxtop.guardianapp.ui.widget.autoscroll.AutoScrollRecyclerView;
import com.sdxxtop.guardianapp.ui.widget.autoscroll.NoticeRecyclerViewAdapter;
import com.sdxxtop.guardianapp.ui.widget.chart.CustomBarChartView;
import com.sdxxtop.guardianapp.ui.widget.chart.CustomLineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author :  lwb
 * Date: 2019/9/2
 * Desc:
 */
public class MixtureChartView extends LinearLayout {
    @BindView(R.id.cbcv_bar_view)
    CustomBarChartView cbcvBarView;
    @BindView(R.id.clcv_line_view)
    CustomLineChartView clcvLineView;
    @BindView(R.id.text_layout)
    LinearLayout text_layout;
    @BindView(R.id.show_text)
    TextView showText;
    @BindView(R.id.show_bar)
    TextView showBar;
    @BindView(R.id.show_line)
    TextView showLine;
    @BindView(R.id.recyclerView)
    AutoScrollRecyclerView recyclerView;

    public MixtureChartView(Context context) {
        this(context, null);
    }

    public MixtureChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MixtureChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.mixture_chart_view, this, true);
        ButterKnife.bind(this);
        showText.setBackgroundResource(R.drawable.shape_text_select);
        showBar.setBackgroundResource(R.drawable.shape_text_select_normal);
        showLine.setBackgroundResource(R.drawable.shape_text_select_normal);
        showText.setTextColor(getResources().getColor(R.color.white));
        showBar.setTextColor(getResources().getColor(R.color.black));
        showLine.setTextColor(getResources().getColor(R.color.black));

        List<DeviceDataBean.DustDataBean> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new DeviceDataBean.DustDataBean("", 40, 60, "2019-01-02 05:2" + i + ":22"));
        }
        clcvLineView.initData(data);
        cbcvBarView.initData(data);

        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("测试数据" + i);
        }
        NoticeRecyclerViewAdapter adapter = new NoticeRecyclerViewAdapter(dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        //item滚动步骤1：自定义LinearSmoothScroller，重写方法，滚动item至顶部，控制滚动速度
        mScroller = new LinearSmoothScroller(this.getContext()) {

            //将移动的置顶显示
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            //控制速度，这里注意当速度过慢的时候可能会形成流式的效果，因为这里是代表移动像素的速度，
            // 当定时器中每隔的2秒之内正好或者还未移动一个item的高度的时候会出现，前一个还没移动完成又继续移动下一个了，就形成了流滚动的效果了
            // 这个问题后续可通过重写另外一个方法来进行控制，暂时就先这样了
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 3f / displayMetrics.density;
            }
        };
        //item滚动步骤3：开始滚动
        startAuto();
        //流式滚动效果
//        recyclerView.start();
    }

    @OnClick({R.id.show_text, R.id.show_bar, R.id.show_line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_text:
                text_layout.setVisibility(View.VISIBLE);
                cbcvBarView.setVisibility(View.GONE);
                clcvLineView.setVisibility(View.GONE);
                showText.setBackgroundResource(R.drawable.shape_text_select);
                showBar.setBackgroundResource(R.drawable.shape_text_select_normal);
                showLine.setBackgroundResource(R.drawable.shape_text_select_normal);
                showText.setTextColor(getResources().getColor(R.color.white));
                showBar.setTextColor(getResources().getColor(R.color.black));
                showLine.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.show_bar:
                text_layout.setVisibility(View.GONE);
                cbcvBarView.setVisibility(View.VISIBLE);
                clcvLineView.setVisibility(View.GONE);
                showText.setBackgroundResource(R.drawable.shape_text_select_normal);
                showBar.setBackgroundResource(R.drawable.shape_text_select);
                showLine.setBackgroundResource(R.drawable.shape_text_select_normal);
                showText.setTextColor(getResources().getColor(R.color.black));
                showBar.setTextColor(getResources().getColor(R.color.white));
                showLine.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.show_line:
                text_layout.setVisibility(View.GONE);
                cbcvBarView.setVisibility(View.GONE);
                clcvLineView.setVisibility(View.VISIBLE);
                showText.setBackgroundResource(R.drawable.shape_text_select_normal);
                showBar.setBackgroundResource(R.drawable.shape_text_select_normal);
                showLine.setBackgroundResource(R.drawable.shape_text_select);
                showText.setTextColor(getResources().getColor(R.color.black));
                showBar.setTextColor(getResources().getColor(R.color.black));
                showLine.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    private Disposable mAutoTask;
    private LinearSmoothScroller mScroller;

    //item滚动步骤2：设置定时器自动滚动
    public void startAuto() {
        if (mAutoTask!= null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
        }
        mAutoTask = Observable.interval(1, 2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                //滚动到指定item
                mScroller.setTargetPosition(aLong.intValue());
                recyclerView.getLayoutManager().startSmoothScroll(mScroller);
            }
        });
    }

    private void stopAuto() {
        if (mAutoTask!= null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
            mAutoTask = null;
        }
    }

}
