package com.sdxxtop.sdkagora;

import java.util.ArrayList;

import io.agora.rtm.LocalInvitation;
import io.agora.rtm.RemoteInvitation;
import io.agora.rtm.RtmCallEventListener;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-08 14:03
 * Version: 1.0
 * Description:
 */
public final class RtmCallEventManager implements RtmCallEventListener {

    private ArrayList<RtmCallEventCallback> mList = new ArrayList<>();

    public void addCallback(RtmCallEventCallback callback) {
        mList.add(callback);
    }

    public void removeCallback(RtmCallEventCallback callback) {
        mList.remove(callback);
    }

    @Override
    public void onLocalInvitationReceivedByPeer(LocalInvitation localInvitation) {
        for (RtmCallEventCallback rtmCallEventCallback : mList) {
            if (rtmCallEventCallback != null) {
                rtmCallEventCallback.onLocalInvitationReceivedByPeer(localInvitation);
            }
        }
    }

    @Override
    public void onLocalInvitationAccepted(LocalInvitation localInvitation, String s) {
        for (RtmCallEventCallback rtmCallEventCallback : mList) {
            if (rtmCallEventCallback != null) {
                rtmCallEventCallback.onLocalInvitationAccepted(localInvitation, s);
            }
        }
    }

    @Override
    public void onLocalInvitationRefused(LocalInvitation localInvitation, String s) {
        for (RtmCallEventCallback rtmCallEventCallback : mList) {
            if (rtmCallEventCallback != null) {
                rtmCallEventCallback.onLocalInvitationRefused(localInvitation, s);
            }
        }
    }

    @Override
    public void onLocalInvitationCanceled(LocalInvitation localInvitation) {
        for (RtmCallEventCallback rtmCallEventCallback : mList) {
            if (rtmCallEventCallback != null) {
                rtmCallEventCallback.onLocalInvitationCanceled(localInvitation);
            }
        }
    }

    @Override
    public void onLocalInvitationFailure(LocalInvitation localInvitation, int i) {
        for (RtmCallEventCallback rtmCallEventCallback : mList) {
            if (rtmCallEventCallback != null) {
                rtmCallEventCallback.onLocalInvitationFailure(localInvitation, i);
            }
        }
    }

    @Override
    public void onRemoteInvitationReceived(RemoteInvitation remoteInvitation) {
        for (RtmCallEventCallback rtmCallEventCallback : mList) {
            if (rtmCallEventCallback != null) {
                rtmCallEventCallback.onRemoteInvitationReceived(remoteInvitation);
            }
        }
    }

    @Override
    public void onRemoteInvitationAccepted(RemoteInvitation remoteInvitation) {
        for (RtmCallEventCallback rtmCallEventCallback : mList) {
            if (rtmCallEventCallback != null) {
                rtmCallEventCallback.onRemoteInvitationAccepted(remoteInvitation);
            }
        }
    }

    @Override
    public void onRemoteInvitationRefused(RemoteInvitation remoteInvitation) {
        for (RtmCallEventCallback rtmCallEventCallback : mList) {
            if (rtmCallEventCallback != null) {
                rtmCallEventCallback.onRemoteInvitationRefused(remoteInvitation);
            }
        }
    }

    @Override
    public void onRemoteInvitationCanceled(RemoteInvitation remoteInvitation) {
        for (RtmCallEventCallback rtmCallEventCallback : mList) {
            if (rtmCallEventCallback != null) {
                rtmCallEventCallback.onRemoteInvitationCanceled(remoteInvitation);
            }
        }
    }

    @Override
    public void onRemoteInvitationFailure(RemoteInvitation remoteInvitation, int i) {
        for (RtmCallEventCallback rtmCallEventCallback : mList) {
            if (rtmCallEventCallback != null) {
                rtmCallEventCallback.onRemoteInvitationFailure(remoteInvitation, i);
            }
        }
    }
}
