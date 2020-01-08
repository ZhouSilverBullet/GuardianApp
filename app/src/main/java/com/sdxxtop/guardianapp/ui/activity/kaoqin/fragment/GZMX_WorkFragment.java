package com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment;

import android.view.View;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseFragment;
import com.sdxxtop.guardianapp.model.bean.CategoryStatusBean;
import com.sdxxtop.guardianapp.model.bean.GzmxDateBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.GZMX_Adapter;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MyAssessCalendarView;
import com.sdxxtop.guardianapp.ui.widget.SingleStyleView;
import com.sdxxtop.guardianapp.utils.Date2Util;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static com.sdxxtop.guardianapp.model.http.net.RetrofitHelper.getEnvirApi;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc: GZMX = 工作明细
 */
public class GZMX_WorkFragment extends BaseFragment implements MyAssessCalendarView.OnDataChooseListener {
    @BindView(R.id.mcv_view)
    MyAssessCalendarView mcvView;
    @BindView(R.id.tvEventTypeSelect)
    TextView tvEventTypeSelect;
    @BindView(R.id.tvEventStatusSelect)
    TextView tvEventStatusSelect;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private SingleStyleView categorySelect;
    private SingleStyleView eventStatusSelect;
    private GZMX_Adapter adapter;

    private String startTime, endTime;
    private int categoryId, status;
    private List<SingleStyleView.ListDataBean> categoryData = new ArrayList<>();
    private List<SingleStyleView.ListDataBean> statusData = new ArrayList<>();

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_gzmx;
    }

    @Override
    protected void initView() {
        adapter = new GZMX_Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //创建分类,状态选择框
        categoryData.add(new SingleStyleView.ListDataBean(0, "全部"));
        categorySelect = new SingleStyleView(getActivity(), categoryData);
        eventStatusSelect = new SingleStyleView(getActivity(), statusData);

        categorySelect.setOnItemSelectLintener((id, result) -> {
            tvEventTypeSelect.setText(result);
            categoryId = id;
            loadData(0);
        });
        eventStatusSelect.setOnItemSelectLintener((id, result) -> {
            tvEventStatusSelect.setText(result);
            status = id;
            loadData(0);
        });

        mcvView.setOnDataChoose(this);

        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (adapter != null) {
                    loadData(adapter.getData().size());
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData(0);
            }
        });
    }

    @OnClick({R.id.tvEventTypeSelect, R.id.tvEventStatusSelect})
    public void onViewClicked(View view) {
        if (statusData.size() == 0) {
            loadCategoryStatus();
            return;
        }
        switch (view.getId()) {
            case R.id.tvEventTypeSelect:
                if (categorySelect != null) {
                    categorySelect.show();
                }
                break;
            case R.id.tvEventStatusSelect:
                if (eventStatusSelect != null) {
                    eventStatusSelect.show();
                }
                break;
        }
    }


    @Override
    protected void initData() {
        startTime = mcvView.currentDate;
        endTime = mcvView.currentDate;
        loadData(0);
        loadCategoryStatus();
    }

    /**
     * 加载分类/状态数据
     */
    private void loadCategoryStatus() {
        Params params = new Params();
        Observable<RequestBean<CategoryStatusBean>> observable = getEnvirApi().getCategoryStatus(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<CategoryStatusBean>() {
            @Override
            public void onSuccess(CategoryStatusBean bean) {
                categoryData.clear();
                statusData.clear();
                categoryData.add(new SingleStyleView.ListDataBean(0, "全部"));

                for (CategoryStatusBean.CategoryBean item : bean.category) {
                    categoryData.add(new SingleStyleView.ListDataBean(item.category_id, item.category_name));
                }
                for (CategoryStatusBean.StatusBean item : bean.status) {
                    statusData.add(new SingleStyleView.ListDataBean(item.status_id, item.status_name));
                }

                categorySelect.replaceData(categoryData);
                eventStatusSelect.replaceData(statusData);
            }

            @Override
            public void onFailure(int code, String error) {
            }
        });
    }


    /**
     * 加载列表数据
     *
     * @param page
     */
    private void loadData(int page) {
        Params params = new Params();
        params.put("st", startTime);
        params.put("et", endTime);
        params.put("ct", categoryId);
        params.put("su", status);
        params.put("sp", page);
        Observable<RequestBean<GzmxDateBean>> observable = getEnvirApi().gzmxDayInfo(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<GzmxDateBean>() {
            @Override
            public void onSuccess(GzmxDateBean bean) {
                smartRefresh.finishLoadMore();
                smartRefresh.finishRefresh();
                if (page == 0) {
                    adapter.replaceData(bean.event);
                } else {
                    adapter.addData(bean.event);
                }
            }

            @Override
            public void onFailure(int code, String error) {
            }
        });
    }

    /**
     * 选择日期后的回调
     *
     * @param data
     */
    @Override
    public void selected(List<Calendar> data) {
        if (data.size() > 0) {
            startTime = Date2Util.getCalendarDate(data.get(0));
            endTime = Date2Util.getCalendarDate(data.get(data.size() - 1));
        }
        loadData(0);
    }
}
