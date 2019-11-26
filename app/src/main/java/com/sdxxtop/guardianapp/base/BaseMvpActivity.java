package com.sdxxtop.guardianapp.base;

import android.os.Bundle;

import com.sdxxtop.guardianapp.app.App;
import com.sdxxtop.guardianapp.di.component.ActivityComponent;
import com.sdxxtop.guardianapp.di.component.DaggerActivityComponent;
import com.sdxxtop.guardianapp.di.module.ActivityModule;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public abstract class BaseMvpActivity<T extends RxPresenter> extends BaseActivity implements BaseView {
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent
                .builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract void initInject();
}
