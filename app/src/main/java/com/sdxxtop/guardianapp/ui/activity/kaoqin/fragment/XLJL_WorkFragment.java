package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.XljlDateBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.DashLineView;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.XLJLAverAdapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MyAssessCalendarView;
import com.sdxxtop.guardianapp.utils.Date2Util;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static com.sdxxtop.guardianapp.model.http.net.RetrofitHelper.getEnvirApi;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc: XLJL = 巡逻距离
 */
public class XLJL_WorkFragment extends BaseFragment implements MyAssessCalendarView.OnDataChooseListener {
    @BindView(R.id.average_recycler)
    RecyclerView averageRecycler;
    @BindView(R.id.average_weight_view)
    View weightView;
    @BindView(R.id.average_text)
    TextView averageText;
    @BindView(R.id.mcv_view)
    MyAssessCalendarView mcvView;

    private XLJLAverAdapter adapter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_xljl;
    }

    @Override
    protected void initView() {
        adapter = new XLJLAverAdapter();
        averageRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        averageRecycler.setAdapter(adapter);

        mcvView.setOnDataChoose(this);
    }

    @Override
    protected void initData() {
        loadData(mcvView.currentDate, mcvView.currentDate);
    }

    private void loadData(String start_time, String endTime) {
        Params params = new Params();
        params.put("st", start_time);
        params.put("et", endTime);
        Observable<RequestBean<XljlDateBean>> observable = getEnvirApi().xljlDayInfo(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<XljlDateBean>() {
            @Override
            public void onSuccess(XljlDateBean bean) {
                bandData(bean);
            }

            @Override
            public void onFailure(int code, String error) {
            }
        });
    }

    private static final String TAG = "XLJL_WorkFragment";

    private void bandData(XljlDateBean bean) { // 26
        float weight = ((bean.avg < 1 ? 1 : bean.avg) / bean.max) * 26;
        if (weight > 26) {
            weight = 26;
        }
        if (bean.avg == 0) {
            weight = 0;
        }
        weightView.setLayoutParams(new LinearLayout.LayoutParams(0, DashLineView.dp2px(getContext(), 30), weight));
        averageText.setText("平均" + bean.avg);
        adapter.setListData(bean.distance, bean.max);
    }

    /**
     * 选择日期的回调
     *
     * @param data
     */
    @Override
    public void selected(List<Calendar> data) {
        if (data != null && data.size() > 0) {
            loadData(Date2Util.getCalendarDate(data.get(0)), Date2Util.getCalendarDate(data.get(data.size() - 1)));
        }
    }
}
