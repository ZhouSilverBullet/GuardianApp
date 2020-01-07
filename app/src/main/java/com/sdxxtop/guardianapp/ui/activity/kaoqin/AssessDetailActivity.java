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

import java.util.ArrayList;
import java.util.List;

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

        List<AssessDetailAdapter.DetailBean> list = new ArrayList<>();
        list.add(new AssessDetailAdapter.DetailBean("迟到", "", "", "-0.2"));
        list.add(new AssessDetailAdapter.DetailBean("早退", "", "", "-0.3"));
        list.add(new AssessDetailAdapter.DetailBean("上报", "测试洛斯里克熟客舒克是", "其他类", "-0.9"));
        list.add(new AssessDetailAdapter.DetailBean("其他分类", "测试其他分类", "其他类", "-0.5"));
        list.add(new AssessDetailAdapter.DetailBean("在线时长", "", "", "-0.2"));
        list.add(new AssessDetailAdapter.DetailBean("巡逻距离", "", "", "-0.1"));
        list.add(new AssessDetailAdapter.DetailBean("事件验收", "测试事件验收", "验收类", "-0.1"));

        adapter.replaceData(list);
        msvView.setOnMonthChageListener((year, month) -> loadData(year, month));
    }

    @Override
    protected void initData() {
        loadData(msvView.year, msvView.month);
    }

    private void loadData(int year, int month) {
        Params params = new Params();
        params.put("de", Date2Util.dateToStamp(""+year + "-" + month));
        Observable<RequestBean<List<RecordInfoBean>>> observable = getEnvirApi().recordInfo(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<List<RecordInfoBean>>() {
            @Override
            public void onSuccess(List<RecordInfoBean> bean) {

            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
    }
}
