package com.sdxxtop.guardianapp.ui.activity;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.UnreadNewslistBean;
import com.sdxxtop.guardianapp.presenter.CenterMessage2Presenter;
import com.sdxxtop.guardianapp.presenter.contract.CenterMessage2Contract;
import com.sdxxtop.guardianapp.ui.adapter.CenterMessageListAdapter;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CenterMessage2Activity extends BaseMvpActivity<CenterMessage2Presenter> implements CenterMessage2Contract.IView {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private String title;
    private int type;
    private CenterMessageListAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_center_message2;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        super.initView();
        title = getIntent().getStringExtra("name");
        type = getIntent().getIntExtra("type", 0);
        titleView.setTitleValue(title);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CenterMessageListAdapter(R.layout.item_message_info);
        mRecyclerView.setAdapter(mAdapter);
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter); //绑定之前的adapter
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if (headersDecor != null) {
                    headersDecor.invalidateHeaders();
                }
            }
        });
        mRecyclerView.addItemDecoration(headersDecor);

//        adapter = new MessageInfoAdapter(R.layout.item_message_info, null);
//        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.unreadNewslist(type);
    }

    @Override
    public void showData(UnreadNewslistBean bean) {
        List<UnreadNewslistBean.EventItemBean> list = new ArrayList<>();

        List<UnreadNewslistBean.EventItemBean> overdue_event = bean.getOverdue_event();
        if (overdue_event != null && bean.getOverdue_event().size() > 0) {
            if (type==3){
                for (UnreadNewslistBean.EventItemBean eventItemBean : overdue_event) {
                    eventItemBean.setClassify("自行处理事件");
                }
            }else if (type==1){
                for (UnreadNewslistBean.EventItemBean eventItemBean : overdue_event) {
                    eventItemBean.setClassify("派发事件");
                }
            }
            list.addAll(overdue_event);
        }

        if (bean.getEvent_expire() != null && bean.getEvent_expire().size() > 0) {
            list.addAll(bean.getEvent_expire());
        }
        if (bean.getReject_data() != null && bean.getReject_data().size() > 0) {
            list.addAll(bean.getReject_data());
        }
        if (bean.getWhole_event() != null && bean.getWhole_event().size() > 0) {
            list.addAll(bean.getWhole_event());
        }

        mAdapter.setmType(bean.getEvent_type());
        mAdapter.replaceData(list);
    }
}
