package com.sdxxtop.guardianapp.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.luck.picture.lib.permissions.RxPermissions;
import com.sdxxtop.guardianapp.presenter.FaceLivenessPresenter;
import com.sdxxtop.guardianapp.presenter.contract.FaceLivenessContract;
import com.sdxxtop.guardianapp.utils.AMapFindLocation;
import com.sdxxtop.guardianapp.utils.DialogUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import io.reactivex.functions.Consumer;

public class MyFaceLivenessActivity extends FaceLivenessActivity implements FaceLivenessContract.IView {

    private RxPermissions mRxPermissions;

    protected FaceLivenessPresenter mPresenter;

    private DialogUtil mDialogUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mPresenter == null) {
            mPresenter = new FaceLivenessPresenter();
            mPresenter.attachView(this);
        }

        super.onCreate(savedInstanceState);
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    init();
                }
            }
        });

    }

    public void showLoadingDialog() {
        if (mDialogUtil == null) {
            mDialogUtil = new DialogUtil();
        }
        mDialogUtil.showLoadingDialog(this);
    }

    public void hideLoadingDialog() {
        if (mDialogUtil != null) {
            mDialogUtil.closeLoadingDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void showFaceSuccess() {
        showLoadingDialog();

        AMapFindLocation instance = AMapFindLocation.getInstance();
        instance.location();
        instance.setLocationCompanyListener(new AMapFindLocation.LocationCompanyListener() {
            @Override
            public void onAddress(AMapLocation address) {
                double longitude = address.getLongitude();
                double latitude = address.getLatitude();
                String value = longitude + "," + latitude;
                String address1 = address.getAddress();

                mPresenter.face(value, address1);
            }
        });
    }

    @Override
    public void faceSuccess(String address) {
        hideLoadingDialog();
        UIUtils.showToast("打卡成功");
        tvLocation.setText(address);
        btnCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        hideLoadingDialog();
    }
}
