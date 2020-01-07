package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.model.bean.GztjMonthBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.GZTJ_Adapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MonthSelectView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static com.sdxxtop.guardianapp.model.http.net.RetrofitHelper.getEnvirApi;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc: GZTJ = 工作统计
 */
public class GZTJ_WorkFragment extends BaseFragment implements MonthSelectView.OnMonthChangeListener {

    @BindView(R.id.msv_view)
    MonthSelectView msvView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private GZTJ_Adapter adapter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_gztj;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new GZTJ_Adapter();
        recyclerView.setAdapter(adapter);
        msvView.setOnMonthChageListener(this);
    }

    @Override
    protected void initData() {
        loadData(msvView.year + "-" + msvView.month);
    }

    private void loadData(String date) {
        Params params = new Params();
        params.put("de", date);
        Observable<RequestBean<GztjMonthBean>> observable = getEnvirApi().gztjDayInfo(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<GztjMonthBean>() {
            @Override
            public void onSuccess(GztjMonthBean bean) {
                adapter.replaceData(bean.data);
            }

            @Override
            public void onFailure(int code, String error) {
            }
        });
    }


    /**
     * 切换月份回调
     *
     * @param year
     * @param month
     */
    @Override
    public void onMonthChanged(int year, int month) {
        loadData(year + "-" + month);
    }
}
