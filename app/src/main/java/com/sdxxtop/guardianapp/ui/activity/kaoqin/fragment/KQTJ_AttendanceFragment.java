package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.model.bean.KqtjMonthBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.KQTJ_Adapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MonthSelectView;

import java.util.ArrayList;
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
 * Desc:  KQTJ = 考勤统计
 */
public class KQTJ_AttendanceFragment extends BaseFragment implements MonthSelectView.OnMonthChangeListener {
    @BindView(R.id.msv_view)
    MonthSelectView msvView;
    @BindView(R.id.attendance_statistical_recycler)
    RecyclerView recyclerView;

    private KQTJ_Adapter adapter;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_kqtj;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new KQTJ_Adapter();
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            dataList.add("" + i);
        }
        recyclerView.setAdapter(adapter);
        adapter.replaceData(dataList);
        msvView.setOnMonthChageListener(this);
    }

    @Override
    protected void initData() {
        loadMonthInfo(msvView.year, msvView.month);
    }

    private void loadMonthInfo(int year, int month) {
        Params params = new Params();
        params.put("yr", year);
        params.put("mh", month);
        Observable<RequestBean<KqtjMonthBean>> observable = getEnvirApi().kqtjDataInfo(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<KqtjMonthBean>() {
            @Override
            public void onSuccess(KqtjMonthBean bean) {
                dataList.clear();
                dataList.add(bean.absent_day);
                dataList.add(bean.absent_day);
                dataList.add(bean.late_num);
                dataList.add(bean.early_num);
                adapter.replaceData(dataList);
            }

            @Override
            public void onFailure(int code, String error) {
            }
        });
    }

    /**
     * 切换月份回调
     */
    @Override
    public void onMonthChanged(int year, int month) {
        loadMonthInfo(year,month);
    }
}
