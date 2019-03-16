package com.xuxin.guardianapp.base;

import android.os.Bundle;

import com.xuxin.guardianapp.app.App;
import com.xuxin.guardianapp.di.component.DaggerFragmentComponent;
import com.xuxin.guardianapp.di.component.FragmentComponent;
import com.xuxin.guardianapp.di.module.FragmentModule;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public abstract class BaseMvpFragment<T extends RxPresenter> extends BaseFragment implements BaseView {
    @Inject
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    protected abstract void initInject();

    public FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }
}
