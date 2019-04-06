package com.sdxxtop.guardianapp.ui.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.ui.widget.decorator.CalendarSelectorDecorator;
import com.sdxxtop.guardianapp.ui.widget.decorator.MaxTodayDecorator;
import com.sdxxtop.guardianapp.ui.widget.decorator.TodayDecorator;
import com.sdxxtop.guardianapp.utils.Date2Util;
import com.sdxxtop.guardianapp.utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StatSelectionDateWindow extends PopupWindow {

    private boolean isStat;
    private LayoutInflater inflater;
    private Activity mContext;

    private MaterialCalendarView materialCalendarView;
    private TextView tvTitle;

    private boolean isShowBottom;
    public String stringDate;
    private String currentDate;
    private RecyclerView dateRecycler;
    private View dateBottomLayout;
    private LinearLayout imageRight;
    private LinearLayout imageLeft;
    private View include;

    /**
     * @param activity
     * @param isShowBottom
     * @param isStat       统计的时候头部只显示 xxxx年xx日
     */
    public StatSelectionDateWindow(Activity activity, boolean isShowBottom, boolean isStat) {
        this.mContext = activity;
        this.isShowBottom = isShowBottom;
        this.isStat = isStat;
        init();
        defaultStyle(activity);
    }

    private void defaultStyle(Context context) {
        int weight = context.getResources().getDisplayMetrics().widthPixels;
//        int height = ViewUtil.dp2px(mContext, 420);
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setWidth(weight);
        setHeight(height);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.ActionSheetDialogAnimation);
    }

    private void init() {
        inflater = LayoutInflater.from(mContext);
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.item_selection_date_window, null);
        this.setContentView(view);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.material_calendar);
        imageRight = (LinearLayout) view.findViewById(R.id.image_right);
        imageLeft = (LinearLayout) view.findViewById(R.id.image_left);
        include = view.findViewById(R.id.include);
        imageLeft.setVisibility(View.VISIBLE);
        imageRight.setVisibility(View.VISIBLE);

        Calendar instance = Calendar.getInstance();

        materialCalendarView.state().edit().setMaximumDate(getLastDayOfMonth()).commit();
        materialCalendarView.setTopbarVisible(false);
        tvTitle = (TextView) view.findViewById(R.id.tv_date);

        materialCalendarView.setSelectedDate(instance);

        if (isStat) {
            tvTitle.setText(DateUtil.getChineseYearAndMonthDate(materialCalendarView.getSelectedDate()));
        } else {
            String date = Date2Util.getDate();
            tvTitle.setText(date);
        }
//        currentDate = cDate[0] + "-" + cDate[1] + "-" + cDate[2];

        stringDate = DateUtil.getSelectorDate(materialCalendarView.getSelectedDate());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (compareToday2(date)) {
//                    ToastUtil.show("没有更早的内容");
                    materialCalendarView.setDateSelected(date, false);
                    return;
                }
                materialCalendarView.invalidateDecorators();
                if (isStat) {
                    tvTitle.setText(DateUtil.getChineseYearAndMonthDate(date));
                } else {
                    tvTitle.setText(DateUtil.getChineseSelectorDate(date));
                }
                stringDate = DateUtil.getSelectorDate(date);
                if (listener != null) {
                    listener.onSelector(stringDate, date);
                }
            }
        });


        view.findViewById(R.id.image_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialCalendarView.goToPrevious();
            }
        });

        view.findViewById(R.id.image_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialCalendarView.goToNext();
            }
        });

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                tvTitle.setText(DateUtil.getChineseYearAndMonthDate(date));
            }
        });

        materialCalendarView.addDecorators(new CalendarSelectorDecorator(mContext), new TodayDecorator(mContext), new MaxTodayDecorator(mContext));

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setShowBackground(1.0f);
            }
        });
    }

    private SelectorDateListener listener;

    public void setSelectorDateListener(SelectorDateListener listener) {
        this.listener = listener;
    }

    public interface SelectorDateListener {
        void onSelector(String date, CalendarDay selectorCalendarDay);
    }

    public boolean compareToday2(CalendarDay date) {
        int year = Calendar.getInstance().get(Calendar.YEAR); //年
        int month = Calendar.getInstance().get(Calendar.MONTH); //月
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); //日
        int year1 = date.getYear();
        int month1 = date.getMonth();
        int day1 = date.getDay();
        if (year1 > year) {
            return true;
        } else if (year1 == year && month1 > month) {
            return true;
        } else if (year1 == year && month1 == month && day1 > day) {
            return true;
        }
        return false;
    }

    /**
     * 获取本月的最后一天
     *
     * @return
     */
    public Date getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth;
    }

    /**
     * 设置当前选中的时间
     *
     * @param date
     */
    public void setChooseDate(String date) {
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = dft.parse(date);
            instance.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        materialCalendarView.setSelectedDate(instance);
    }

    public void setTopTitleVisibility() {
        include.setVisibility(View.VISIBLE);
    }

    public void setTvTitle() {
        if (isStat) {
            tvTitle.setText(DateUtil.getChineseYearAndMonthDate(materialCalendarView.getCurrentDate()));
        } else {
            String date = Date2Util.getDate();
            tvTitle.setText(date);
        }
    }

    public void setCurrentDate(String date) {
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = dft.parse(date);
            instance.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (materialCalendarView != null) {
            materialCalendarView.setCurrentDate(instance);
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        setShowBackground(0.5f);
        super.showAtLocation(parent, gravity, x, y);
    }

    private void setShowBackground(float alpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = alpha;
        mContext.getWindow().setAttributes(lp);
    }
}
