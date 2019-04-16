package com.sdxxtop.guardianapp.model.http.util;

import android.text.TextUtils;

import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.exception.ApiException;
import com.sdxxtop.guardianapp.model.bean.RequestBean;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
                            return Observable.error(new ApiException(requestBean.getCode(), requestBean.getMsg()));
                        } else {
                            return Observable.error(new ApiException(-99, "获取数据为空"));
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


    public static <D> Disposable handleDataHttp(Observable<RequestBean<D>> observable, IRequestCallback<D> requestCallback) {
        return observable.compose(RxUtils.schedulers()).compose(RxUtils.handleResult())
                .subscribe(successData(requestCallback),  //成功
                        throwableConsumer(requestCallback)); //失败

    }

    private static <D> Consumer<D> successData(IRequestCallback<D> requestCallback) {
        return new Consumer<D>() {
            @Override
            public void accept(D d) throws Exception {
                if (requestCallback != null) {
                    if (d != null) {
                        requestCallback.onSuccess(d);
                    } else {
                        requestCallback.onFailure(-100, "数据源为空");
                    }
                }
            }
        };
    }

    public static Disposable handleHttp(Observable<RequestBean> observable, IRequestCallback<RequestBean> requestCallback) {
        return observable.compose(RxUtils.schedulers()).
                subscribe(successConsumer(requestCallback),  //成功
                        throwableConsumer(requestCallback)); //失败
    }

    private static <D extends RequestBean> Consumer<D> successConsumer(IRequestCallback<D> requestCallback) {
        return new Consumer<D>() {
            @Override
            public void accept(D requestBean) throws Exception {
                if (requestCallback != null) {
                    if (requestBean != null) {
                        if (requestBean.getCode() == 200) {
                            requestCallback.onSuccess(requestBean);
                        } else {
                            requestCallback.onFailure(requestBean.getCode(), requestBean.getMsg());
                        }
                    } else { //如果成功了，但是获取数据是空的
                        requestCallback.onFailure(-100, "请求数据为空");
                    }
                }
            }
        };
    }

    private static Consumer<Throwable> throwableConsumer(IRequestCallback requestCallback) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (requestCallback != null) {
                    if (throwable instanceof ApiException) {
                        requestCallback.onFailure(((ApiException) throwable).getCode(), throwable.getMessage());
                    } else {
                        requestCallback.onFailure(-100, NetUtil.getHttpExceptionMsg(throwable, ""));
                    }
                }
            }
        };
    }

    //    public static <D> Disposable handleHttp(Observable<RequestBean> observable, IRequestCallback<RequestBean> requestCallback) {
//        return observable.compose(RxUtils.schedulers()).subscribe(new Consumer<RequestBean>() {
//            @Override
//            public void accept(RequestBean requestBean) throws Exception {
//                if (requestCallback != null) {
//                    if (requestBean != null) {
//                        if (requestBean.getCode() == 200) {
//                            requestCallback.onSuccess(requestBean);
//                        } else {
//                            requestCallback.onFailure(requestBean.getCode(), requestBean.getMsg());
//                        }
//                    } else { //如果成功了，但是获取数据是空的
//                        requestCallback.onFailure(-100, "请求数据为空");
//                    }
//                }
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                if (requestCallback != null) {
//                    requestCallback.onFailure(-100, NetUtil.getHttpExceptionMsg(throwable, ""));
//                }
//            }
//        });
//    }
}
