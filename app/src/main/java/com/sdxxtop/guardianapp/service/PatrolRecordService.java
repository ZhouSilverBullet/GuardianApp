package com.sdxxtop.guardianapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.orhanobut.logger.Logger;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.net.RetrofitHelper;
import com.sdxxtop.guardianapp.utils.AMapFindLocation;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: zhousaito
 * @Date: 2019-04-24 11:32
 * @Version 1.0
 * @UserWhat what
 */
public class PatrolRecordService extends Service implements Handler.Callback {
    public static final int TYPE_PUSH = 250;

    private Handler mHandler;
    private Disposable mDisposable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mHandler == null) {
            mHandler = new Handler(this);
        }
        mHandler.sendEmptyMessage(TYPE_PUSH);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean handleMessage(Message msg) {
        pushData();
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(TYPE_PUSH, 10000);
        }
        return true;
    }

    private void pushData() {
        AMapFindLocation instance = AMapFindLocation.getInstance();
        instance.location();
        instance.setLocationCompanyListener(new AMapFindLocation.LocationCompanyListener() {
            @Override
            public void onAddress(AMapLocation address) {
                double longitude = address.getLongitude();
                double latitude = address.getLatitude();
                String address1 = address.getAddress();
                if (TextUtils.isEmpty(address1)) {
                    address1 = "无名路";
                }
                if (longitude == 0.0 || latitude == 0.0) {
                    Logger.e("获取经纬度为0或者地址不对，就放弃这次访问");
                    return;
                }

                String value = longitude + "," + latitude;
                face(value, address1);
            }
        });
    }


    public void face(String lanLan, String address) {
        //释放资源
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        Params params = new Params();
        params.put("slt", lanLan);
        params.put("ad", address);
        params.put("st", "2");
        Observable<RequestBean> observable = RetrofitHelper.getEnvirApi().postMainSign(params.getData());
        //直接跑子线程中干活就行了
        mDisposable = observable.subscribeOn(Schedulers.io()).subscribe(new Consumer<RequestBean>() {
            @Override
            public void accept(RequestBean requestBean) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(TYPE_PUSH);
            mHandler = null;
        }
    }
}
