package com.xuxin.guardianapp.model.http.util;

import android.text.TextUtils;

import com.xuxin.guardianapp.model.bean.RequestBean;
import com.xuxin.guardianapp.model.http.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {
    public static <T> ObservableTransformer<T, T> schedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<RequestBean<T>, T> handleResult() {
        return new ObservableTransformer<RequestBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<RequestBean<T>> upstream) {
                return upstream.flatMap(new Function<RequestBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(RequestBean<T> requestBean) throws Exception {
                        if (requestBean.getCode() == 200) {
                            return createData(requestBean);
                        } else if (!TextUtils.isEmpty(requestBean.getMsg())) {
                            return Observable.error(new ApiException(requestBean.getMsg()));
                        } else {
                            return Observable.error(new ApiException("error"));
                        }
                    }
                });
            }
        };
    }

    private static <T> ObservableSource<T> createData(RequestBean<T> requestBean) {
        return new ObservableSource<T>() {
            @Override
            public void subscribe(Observer<? super T> observer) {
                try {
                    observer.onNext(requestBean.getData());
                    observer.onComplete();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

}
