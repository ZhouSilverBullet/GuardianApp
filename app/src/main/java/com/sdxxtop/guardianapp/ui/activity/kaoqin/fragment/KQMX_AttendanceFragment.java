package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;


import com.haibin.calendarview.Calendar;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.model.bean.KqmxMonthBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.KQMX_Adapter;
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
 * Date: 2019/12/31
 * Desc:
 */
public class KQMX_AttendanceFragment extends BaseFragment {

    @BindView(R.id.mcv_view)
    MyAssessCalendarView mcvView;
    @BindView(R.id.attendance_detail_recycler)
    RecyclerView recyclerView;

    private KQMX_Adapter adapter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_kqmx;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new KQMX_Adapter();
        recyclerView.setAdapter(adapter);

        mcvView.setOnDataChoose(new MyAssessCalendarView.OnDataChooseListener() {
            @Override
            public void selected(List<Calendar> data) {
                if (data.size() > 0) {
                    loadData(Date2Util.getCalendarDate(data.get(0)),Date2Util.getCalendarDate(data.get(data.size()-1)));
                }
            }
        });
    }

    @Override
    protected void initData() {
        loadData(mcvView.currentDate, mcvView.currentDate);
    }

    private void loadData(String startTime, String endTime) {
        Params params = new Params();
        params.put("sd", startTime);
        params.put("ed", endTime);
        Observable<RequestBean<KqmxMonthBean>> observable = getEnvirApi().kqmxDayInfo(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<KqmxMonthBean>() {
            @Override
            public void onSuccess(KqmxMonthBean bean) {
                adapter.replaceData(bean.sign_log);
            }

            @Override
            public void onFailure(int code, String error) {
            }
        });
    }

}
