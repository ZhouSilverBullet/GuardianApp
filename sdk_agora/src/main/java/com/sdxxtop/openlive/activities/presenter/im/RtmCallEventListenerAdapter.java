package com.sdxxtop.openlive.activities.presenter.im;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sdxxtop.openlive.activities.CallIdleActivity;
import com.sdxxtop.sdkagora.AgoraSession;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.LocalInvitation;
import io.agora.rtm.RemoteInvitation;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmCallEventListener;
import io.agora.rtm.RtmClient;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-08 11:30
 * Version: 1.0
 * Description:
 */
public class RtmCallEventListenerAdapter implements RtmCallEventListener {
    private static final String TAG = "RtmCallEventListener";

    private RtmClient rtmClient;
    private Context mContext;

    public RtmCallEventListenerAdapter(Context context, RtmClient rtmClient) {
        this.mContext = context;
        this.rtmClient = rtmClient;
    }

    @Override
    public void onLocalInvitationReceivedByPeer(LocalInvitation localInvitation) {
        Log.e(TAG, "onLocalInvitationReceivedByPeer: ");

        AgoraSession.getCallEventManager().onLocalInvitationReceivedByPeer(localInvitation);
    }

    @Override
    public void onLocalInvitationAccepted(LocalInvitation localInvitation, String s) {
        Log.e(TAG, "onLocalInvitationAccepted: ");

        AgoraSession.getCallEventManager().onLocalInvitationAccepted(localInvitation, s);
    }

    @Override
    public void onLocalInvitationRefused(LocalInvitation localInvitation, String s) {
        Log.e(TAG, "onLocalInvitationRefused: ");

        AgoraSession.getCallEventManager().onLocalInvitationRefused(localInvitation, s);
    }

    @Override
    public void onLocalInvitationCanceled(LocalInvitation localInvitation) {
        Log.e(TAG, "onLocalInvitationCanceled: ");

        AgoraSession.getCallEventManager().onLocalInvitationCanceled(localInvitation);
    }

    @Override
    public void onLocalInvitationFailure(LocalInvitation localInvitation, int i) {
        Log.e(TAG, "onLocalInvitationFailure: ");

        AgoraSession.getCallEventManager().onLocalInvitationFailure(localInvitation, i);
    }

    @Override
    public void onRemoteInvitationReceived(RemoteInvitation remoteInvitation) {

        Log.e(TAG, "onRemoteInvitationReceived: "+ AgoraSession.isLiving);

//        Intent intent = new Intent(mContext, CallIdleActivity.class);
//        intent.putExtra("content", remoteInvitation.getContent());
//        if (!(mContext instanceof Activity)) {
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        }
//        mContext.startActivity(intent);

        if (AgoraSession.isLiving) {
            return;
        }
        CallIdleActivity.startCallIdle(mContext, remoteInvitation);

        AgoraSession.getCallEventManager().onRemoteInvitationReceived(remoteInvitation);
    }

    @Override
    public void onRemoteInvitationAccepted(RemoteInvitation remoteInvitation) {
        Log.e(TAG, "onRemoteInvitationAccepted: ");
        AgoraSession.getCallEventManager().onRemoteInvitationAccepted(remoteInvitation);
    }

    @Override
    public void onRemoteInvitationRefused(RemoteInvitation remoteInvitation) {
        Log.e(TAG, "onRemoteInvitationRefused: ");

        AgoraSession.getCallEventManager().onRemoteInvitationRefused(remoteInvitation);
    }

    @Override
    public void onRemoteInvitationCanceled(RemoteInvitation remoteInvitation) {
        Log.e(TAG, "onRemoteInvitationCanceled: ");

        AgoraSession.getCallEventManager().onRemoteInvitationCanceled(remoteInvitation);

    }

    @Override
    public void onRemoteInvitationFailure(RemoteInvitation remoteInvitation, int i) {
        Log.e(TAG, "onRemoteInvitationFailure: ");

        AgoraSession.getCallEventManager().onRemoteInvitationFailure(remoteInvitation, i);
    }
}
