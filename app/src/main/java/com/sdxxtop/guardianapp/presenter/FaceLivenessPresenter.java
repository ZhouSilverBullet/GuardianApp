package com.sdxxtop.guardianapp.presenter;


import android.graphics.Bitmap;

import com.amap.api.location.AMapLocation;
import com.sdxxtop.guardianapp.app.App;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.ImageParams;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.FaceLivenessContract;
import com.sdxxtop.guardianapp.utils.AMapFindLocation;
import com.sdxxtop.guardianapp.utils.UIUtils;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 用来copy使用的
 */
public class FaceLivenessPresenter extends RxPresenter<FaceLivenessContract.IView> implements FaceLivenessContract.IPresenter {

    public FaceLivenessPresenter() {
    }

    public void face(String lanLan, String address) {
        Params params = new Params();
        params.put("slt", lanLan);
        params.put("ad", address);
        Observable<RequestBean> observable = getEnvirApi().postMainSign(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                mView.faceSuccess(address);
            }

            @Override
            public void onFailure(int code, String error) {
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }

    public void rigesterFace(Bitmap bitmap) {

        Disposable subscribe = Flowable.just(bitmap).map(new Function<Bitmap, File>() {
            @Override
            public File apply(Bitmap bitmap) throws Exception {
                String pathImg = Constants.PATH_IMG;
                File file = new File(pathImg + File.separator + "face_img.png");
                if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                    return null;
                }

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
                return file;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {

                    @Override
                    public void accept(File s) throws Exception {
                        rigister(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        UIUtils.showToast("注册失败");
                    }
                });

    }

    private void rigister(File imgFile) {

        ImageParams params = new ImageParams();
        params.addCompressImagePath("img",imgFile, App.getInstance().getCacheDir() + "/img.png", 80);
        Observable<RequestBean> observable = getEnvirApi().postFaceReg(params.getImgData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                mView.faceRegisterSuccess("");
            }

            @Override
            public void onFailure(int code, String error) {
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }

    public void loginFace(Bitmap bitmap) {
        Disposable subscribe = Flowable.just(bitmap).map(new Function<Bitmap, File>() {
            @Override
            public File apply(Bitmap bitmap) throws Exception {
                String pathImg = Constants.PATH_IMG;
                File file = new File(pathImg + File.separator + "face_img.png");
                if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                    return null;
                }

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
                return file;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {

                    @Override
                    public void accept(File s) throws Exception {
                        loginFace(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        UIUtils.showToast("登录失败");
                    }
                });
    }

    private void loginFace(File imgFile) {

        ImageParams params = new ImageParams();
        params.addCompressImagePath("img",imgFile, App.getInstance().getCacheDir() + "/img.png", 80);
        Observable<RequestBean> observable = getEnvirApi().postFaceVerify(params.getImgData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
//                mView.faceRegisterSuccess("");
                AMapFindLocation instance = AMapFindLocation.getInstance();
                instance.location();
                instance.setLocationCompanyListener(new AMapFindLocation.LocationCompanyListener() {
                    @Override
                    public void onAddress(AMapLocation address) {
                        double longitude = address.getLongitude();
                        double latitude = address.getLatitude();
                        String value = longitude + "," + latitude;
                        String address1 = address.getAddress();

                        face(value, address1);
                    }
                });
            }

            @Override
            public void onFailure(int code, String error) {
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }
}
