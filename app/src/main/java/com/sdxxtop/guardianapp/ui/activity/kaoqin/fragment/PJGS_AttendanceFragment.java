package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.model.bean.PjgsDateBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.XljlDateBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.DashLineView;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.AverAdapter;
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
 * Desc:  PJGS = 平均工时
 */
public class PJGS_AttendanceFragment extends BaseFragment {

    @BindView(R.id.average_recycler)
    RecyclerView averageRecycler;
    @BindView(R.id.average_weight_view)
    View weightView;
    @BindView(R.id.average_text)
    TextView averageText;
    @BindView(R.id.msv_view)
    MonthSelectView msvView;

    private AverAdapter adapter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_pjgs;
    }

    @Override
    protected void initView() {
        handleDayAve();
    }

    private void handleDayAve() {
        adapter = new AverAdapter();
        averageRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        averageRecycler.setAdapter(adapter);

        msvView.setOnMonthChageListener((year, month) -> loadData(year, month));
    }

    @Override
    protected void initData() {
        loadData(msvView.year, msvView.month);
    }

    private void loadData(int year, int month) {
        Params params = new Params();
        params.put("yr", year);
        params.put("mh", month);
        Observable<RequestBean<PjgsDateBean>> observable = getEnvirApi().pjgsDayInfo(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<PjgsDateBean>() {
            @Override
            public void onSuccess(PjgsDateBean bean) {
                weightView.setLayoutParams(new LinearLayout.LayoutParams(0, DashLineView.dp2px(getContext(), 30), bean.treno_date));
                averageText.setText("平均" + bean.treno_date + "小时");
                List<XljlDateBean> list = new ArrayList<>();
                for (PjgsDateBean.SignBean item : bean.sign) {
                    XljlDateBean itemBean = new XljlDateBean();
                    itemBean.sign_date = item.sign_date;
                    itemBean.total_distance = item.treno_time;
                    list.add(itemBean);
                }
                adapter.replaceData(list);
            }

            @Override
            public void onFailure(int code, String error) {
            }
        });
    }
}
