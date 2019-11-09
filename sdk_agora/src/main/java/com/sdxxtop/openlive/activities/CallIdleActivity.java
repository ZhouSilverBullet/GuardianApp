package com.sdxxtop.openlive.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.sdxxtop.imagora.rtmtutorial.AgoraIMConfig;
import com.sdxxtop.imagora.utils.MessageUtil;
import com.sdxxtop.sdkagora.AgoraSession;
import com.sdxxtop.sdkagora.R;
import com.sdxxtop.sdkagora.RtmCallEventCallback;

import io.agora.rtc.Constants;
import io.agora.rtm.LocalInvitation;
import io.agora.rtm.RemoteInvitation;
import io.agora.rtm.RtmClient;

public class CallIdleActivity extends RtcBaseActivity {

    private String content;
    private RtmClient mRtmClient;

    private static CallIdleCallback sCallIdleCallback;
    private RtmCallEventCallback rtmCallEventCallback;

    public interface CallIdleCallback {
        void onAccepted();

        void onRefused();
    }

    public interface CallIdleCancelCallback {
        void onCancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        setContentView(R.layout.activity_call_idle);

        mRtmClient = AgoraIMConfig.the().getChatManager().getRtmClient();
        content =  getIntent().getStringExtra("content");

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        rtmCallEventCallback = new RtmCallEventCallback() {
            @Override
            public void onRemoteInvitationRefused(RemoteInvitation remoteInvitation) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AgoraSession.getLiveConfig().getApplicationContext(), "对方取消了通信", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });
            }

            @Override
            public void onRemoteInvitationCanceled(RemoteInvitation remoteInvitation) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AgoraSession.getLiveConfig().getApplicationContext(), "Remote取消了通信", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });
            }

            @Override
            public void onLocalInvitationAccepted(LocalInvitation localInvitation, String s) {

            }

            @Override
            public void onRemoteInvitationAccepted(RemoteInvitation remoteInvitation) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gotoRoleActivity();
//                        finish();
                    }
                });
            }
        };
        AgoraSession.addCallback(rtmCallEventCallback);
    }

    public void onAccepted(View view) {
//        if (sCallIdleCallback != null) {
//            sCallIdleCallback.onAccepted();
//        }

        finish();
    }

    public void onRefused(View view) {
//        if (sCallIdleCallback != null) {
//            sCallIdleCallback.onRefused();
//        }

        finish();
    }

    private void onAccepted() {

    }

    private void onRefused() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (rtmCallEventCallback != null) {
            AgoraSession.removeCallback(rtmCallEventCallback);
        }
    }

    public static void startCallIdle(Context context, RemoteInvitation remoteInvitation) {
        Intent intent = new Intent(context, CallIdleActivity.class);
        intent.putExtra("content", remoteInvitation.getContent());
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public void gotoRoleActivity() {
//        final String userLogin = etLogin.getText().toString();

        Intent intent = new Intent(this, LiveActivity.class);
//        String room = etLive.getText().toString();
        //默认是主播
        intent.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, "11");
        intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, "11");

        intent.putExtra(com.sdxxtop.openlive.Constants.KEY_CLIENT_ROLE, Constants.CLIENT_ROLE_BROADCASTER);
        config().setChannelName("11");
        startActivity(intent);
    }
}
