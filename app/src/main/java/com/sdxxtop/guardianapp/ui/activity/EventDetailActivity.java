package com.sdxxtop.guardianapp.ui.activity;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.EventDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventDetailContract;

public class EventDetailActivity extends BaseMvpActivity<EventDetailPresenter> implements EventDetailContract.IView {

    @Override
    protected int getLayout() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void showError(String error) {

    }
}
