package com.sdxxtop.openlive.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.sdxxtop.imagora.rtmtutorial.AgoraIMConfig;
import com.sdxxtop.imagora.utils.MessageUtil;
import com.sdxxtop.openlive.utils.JsonUtil;
import com.sdxxtop.sdkagora.AgoraSession;
import com.sdxxtop.sdkagora.R;
import com.sdxxtop.sdkagora.RtmCallEventCallback;

import io.agora.rtc.Constants;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.LocalInvitation;
import io.agora.rtm.RemoteInvitation;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;

public class CallIdleActivity extends BaseActivity {

    private static final String TAG = "CallIdleActivity";

    private String content;
    private RtmClient mRtmClient;

    private RtmCallEventCallback rtmCallEventCallback;
    private static volatile RemoteInvitation mRemoteInvitation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        setContentView(R.layout.activity_call_idle);

        mRtmClient = AgoraIMConfig.the().getChatManager().getRtmClient();
        content = getIntent().getStringExtra("content");

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
            public void onRemoteInvitationAccepted(final RemoteInvitation remoteInvitation) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "onRemoteInvitationAccepted remoteInvitation : " + remoteInvitation);
                    }
                });
            }

            @Override
            public void onLocalInvitationFailure(LocalInvitation localInvitation, int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("本地通话失败");
                        finish();
                    }
                });
            }

            @Override
            public void onRemoteInvitationFailure(RemoteInvitation remoteInvitation, int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("连接可能超时");
                        finish();
                    }
                });
            }
        };
        AgoraSession.addCallback(rtmCallEventCallback);
    }

    public void onAccepted(View view) {
        accepted();
    }

    public void onRefused(View view) {
        refused();
    }

    private void refused() {
        if (mRtmClient != null) {
            mRtmClient.getRtmCallManager().refuseRemoteInvitation(mRemoteInvitation, new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.e(TAG, "refused onSuccess : ");
                    finish();
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    Log.e(TAG, "refused errorInfo : " + errorInfo);
                    finish();
                }
            });
        }
    }

    private void accepted() {
        if (mRtmClient != null) {
            mRtmClient.getRtmCallManager().acceptRemoteInvitation(mRemoteInvitation, new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.e(TAG, "accepted onSuccess : ");

                    String client = JsonUtil.getClient(mRemoteInvitation.getContent());
                    gotoRoleActivity(client);
                    finish();
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    Log.e(TAG, "accepted errorInfo : " + errorInfo);

                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRemoteInvitation != null) {
            mRemoteInvitation = null;
        }
        AgoraSession.removeCallback(rtmCallEventCallback);
    }

    public static void startCallIdle(Context context, RemoteInvitation remoteInvitation) {

        mRemoteInvitation = remoteInvitation;

        Intent intent = new Intent(context, CallIdleActivity.class);
        intent.putExtra("content", remoteInvitation.getContent());
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        context.startActivity(intent);
    }

    public void gotoRoleActivity(String client) {
        Intent intent = new Intent(this, LiveActivity.class);
        //默认是主播
        intent.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, client);
        intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, "11");

        intent.putExtra(com.sdxxtop.openlive.Constants.KEY_CLIENT_ROLE, Constants.CLIENT_ROLE_BROADCASTER);
        config().setChannelName(client);
        startActivity(intent);
    }
}
