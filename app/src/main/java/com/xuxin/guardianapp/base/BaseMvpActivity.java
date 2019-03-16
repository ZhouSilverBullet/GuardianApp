package com.xuxin.guardianapp.base;

import android.os.Bundle;

import com.xuxin.guardianapp.app.App;
import com.xuxin.guardianapp.di.component.ActivityComponent;
import com.xuxin.guardianapp.di.component.DaggerActivityComponent;
import com.xuxin.guardianapp.di.module.ActivityModule;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public abstract class BaseMvpActivity<T extends RxPresenter> extends BaseActivity implements BaseView {
    @Inject
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInject();
        if (presenter != null) {
            presenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
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
