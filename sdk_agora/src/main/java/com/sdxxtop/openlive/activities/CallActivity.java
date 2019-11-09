package com.sdxxtop.openlive.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sdxxtop.imagora.rtmtutorial.AgoraIMConfig;
import com.sdxxtop.sdkagora.AgoraSession;
import com.sdxxtop.sdkagora.R;
import com.sdxxtop.sdkagora.RtmCallEventCallback;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.LocalInvitation;
import io.agora.rtm.RemoteInvitation;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;

public class CallActivity extends AppCompatActivity {

    private String userId;
    private RtmClient mRtmClient;

    private LocalInvitation localInvitation;
    private RtmCallEventCallback rtmCallEventCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mRtmClient = AgoraIMConfig.the().getChatManager().getRtmClient();
        userId = getIntent().getStringExtra("userId");

        localInvitation = mRtmClient.getRtmCallManager().createLocalInvitation(userId);
        localInvitation.setContent("1111111111111111111");
        mRtmClient.getRtmCallManager().sendLocalInvitation(localInvitation, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Toast.makeText(CallActivity.this, "呼叫失败" + errorInfo.toString(), Toast.LENGTH_SHORT).show();
            }
        });


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
            public void onLocalInvitationRefused(LocalInvitation localInvitation, String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AgoraSession.getLiveConfig().getApplicationContext(), "Local取消了通信", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });
            }
        };
        AgoraSession.addCallback(rtmCallEventCallback);
    }

    /**
     * 取消拨号
     */
    public void onCancel(View view) {
        mRtmClient.getRtmCallManager().cancelLocalInvitation(localInvitation, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });

            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AgoraSession.getLiveConfig().getApplicationContext(), "取消失败", Toast.LENGTH_SHORT).show();

//                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AgoraSession.removeCallback(rtmCallEventCallback);
    }
}
