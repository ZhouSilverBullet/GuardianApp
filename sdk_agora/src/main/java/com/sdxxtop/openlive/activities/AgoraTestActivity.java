package com.sdxxtop.openlive.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.sdxxtop.imagora.utils.MessageUtil;
import com.sdxxtop.openlive.activities.presenter.im.AgoraIMLoginPresenter;
import com.sdxxtop.openlive.activities.presenter.im.IAgoraIMLoginView;
import com.sdxxtop.openlive.activities.presenter.live.AgoraLivePresenter;
import com.sdxxtop.openlive.activities.presenter.live.IAgoraLiveView;
import com.sdxxtop.sdkagora.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.agora.rtc.Constants;

public class AgoraTestActivity extends BaseActivity implements IAgoraIMLoginView, IAgoraLiveView {

    private String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private EditText etLogin;
    private EditText etLive;
    private EditText etCall;
    private AgoraIMLoginPresenter agoraIMLoginPresenter;
    private AgoraLivePresenter agoraLivePresenter;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agora_test);

        etLogin = findViewById(R.id.et_login);
        etLive = findViewById(R.id.et_live);
        etCall = findViewById(R.id.et_call);

        rxPermissions = new RxPermissions(this);

        agoraIMLoginPresenter = new AgoraIMLoginPresenter(this);
        agoraLivePresenter = new AgoraLivePresenter(this);
    }

    /**
     * 登录 通信
     * 用于 获取拨号消息的
     * @param view
     */
    public void onLoginClick(View view) {
        String userId = etLogin.getText().toString().trim();
        if (agoraIMLoginPresenter != null) {
//            agoraIMLoginPresenter.doLogin(userId);
        }
    }

    /**
     * 登出 通信
     * @param view
     */
    public void onLogoutClick(View view) {
        if (agoraIMLoginPresenter != null) {
            agoraIMLoginPresenter.doLogout();
        }
    }

    public void onCallClick(View view) {
        String userId = etCall.getText().toString().trim();
        if (agoraIMLoginPresenter != null) {
            agoraIMLoginPresenter.doCall(this, userId);
        }
    }

    /**
     * 加入直播
     * @param view
     */
    public void onLiveClick(View view) {
        if (agoraIMLoginPresenter == null) {
            return;
        }

        //登录成功的状态
        if (agoraIMLoginPresenter.isInChat()) {
        }
        handleLive();
    }

    private void handleLive() {
        rxPermissions.request(PERMISSIONS).subscribe(granted -> {
            if (granted) {
                gotoRoleActivity();
            }
        });
    }


    public void gotoRoleActivity() {
        final String userLogin = etLogin.getText().toString();

        Intent intent = new Intent(this, LiveActivity.class);
        String room = etLive.getText().toString();
        //默认是主播
        intent.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, room);
        intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, userLogin);

        intent.putExtra(com.sdxxtop.openlive.Constants.KEY_CLIENT_ROLE, Constants.CLIENT_ROLE_BROADCASTER);
        config().setChannelName(room);
        startActivity(intent);
    }


    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
