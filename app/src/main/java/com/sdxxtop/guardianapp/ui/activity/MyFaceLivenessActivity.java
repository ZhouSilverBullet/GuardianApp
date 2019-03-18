package com.sdxxtop.guardianapp.ui.activity;

import android.Manifest;
import android.os.Bundle;

import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;

import io.reactivex.functions.Consumer;

public class MyFaceLivenessActivity extends FaceLivenessActivity {

    private RxPermissions mRxPermissions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    init();
                }
            }
        });
    }
}
