package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.model.bean.RecordInfoBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.AssessDetailAdapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MonthSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.Date2Util;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static com.sdxxtop.guardianapp.model.http.net.RetrofitHelper.getEnvirApi;

public class AssessDetailActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.msv_view)
    MonthSelectView msvView;

    private AssessDetailAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_assess_detail;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssessDetailAdapter();
        recyclerView.setAdapter(adapter);

        msvView.setOnMonthChageListener((year, month) -> loadData(year, month));
    }

    @Override
    protected void initData() {
        loadData(msvView.year, msvView.month);
    }

    private void loadData(int year, int month) {
        Params params = new Params();
        params.put("de", Date2Util.dateToStamp(""+year + "-" + month));
        Observable<RequestBean<RecordInfoBean>> observable = getEnvirApi().recordInfo(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<RecordInfoBean>() {
            @Override
            public void onSuccess(RecordInfoBean bean) {
                adapter.replaceData(bean.lists);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
    }
}

