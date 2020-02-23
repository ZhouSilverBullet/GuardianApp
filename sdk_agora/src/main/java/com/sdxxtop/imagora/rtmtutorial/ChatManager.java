package com.sdxxtop.imagora.rtmtutorial;

import android.content.Context;
import android.util.Log;

import com.sdxxtop.openlive.activities.presenter.im.RtmCallEventListenerAdapter;
import com.sdxxtop.sdkagora.AgoraSession;
import com.sdxxtop.sdkagora.BuildConfig;
import com.sdxxtop.sdkagora.R;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.SendMessageOptions;

public class ChatManager {
    private static final String TAG = ChatManager.class.getSimpleName();

    private Context mContext;
    private RtmClient mRtmClient;
    private SendMessageOptions mSendMsgOptions;
    private List<RtmClientListener> mListenerList = new ArrayList<>();
    private RtmMessagePool mMessagePool = new RtmMessagePool();

    public ChatManager(Context context) {
        mContext = context;
    }

    public void init() {
//        String appID = mContext.getString(R.string.private_app_id);
        String appID = "10202661a1f64b1a9c207ff6539e3950";
        Log.e(TAG, "init ---AppID-- " + appID);

        try {
            mRtmClient = RtmClient.createInstance(mContext, appID, new RtmClientListener() {
                @Override
                public void onConnectionStateChanged(int state, int reason) {
                    Log.e(TAG, "onConnectionStateChanged --- " + state+"---- reason -- " +reason);
                    for (RtmClientListener listener : mListenerList) {
                        listener.onConnectionStateChanged(state, reason);
                    }
                }

                @Override
                public void onMessageReceived(RtmMessage rtmMessage, String peerId) {
                    Log.e(TAG, "onMessageReceived --- " + peerId);
                    if (mListenerList.isEmpty()) {
                        // If currently there is no callback to handle this
                        // message, this message is unread yet. Here we also
                        // take it as an offline message.
                        mMessagePool.insertOfflineMessage(rtmMessage, peerId);
                    } else {
                        for (RtmClientListener listener : mListenerList) {
                            listener.onMessageReceived(rtmMessage, peerId);
                        }
                    }
                }

                @Override
                public void onTokenExpired() {
                    Log.e(TAG, "onTokenExpired --- ");
                }
            });

            if (BuildConfig.DEBUG) {
                mRtmClient.setParameters("{\"rtm.log_filter\": 65535}");
            }
//            mRtmClient.setParameters("{\"che.audio.live_for_comm\":true}");
        } catch (Exception e) {
            Log.e(TAG, "init --- exception");
            Log.e(TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtm sdk init fatal error\n" + Log.getStackTraceString(e));
        }

        // Global option, mainly used to determine whether
        // to support offline messages now.
        mSendMsgOptions = new SendMessageOptions();

        RtmCallEventListenerAdapter rtmCallEventListener = new RtmCallEventListenerAdapter(mContext, mRtmClient);
        mRtmClient.getRtmCallManager().setEventListener(rtmCallEventListener);

    }

    public RtmClient getRtmClient() {
        return mRtmClient;
    }

    public void registerListener(RtmClientListener listener) {
        mListenerList.add(listener);
    }

    public void unregisterListener(RtmClientListener listener) {
        mListenerList.remove(listener);
    }

    public void enableOfflineMessage(boolean enabled) {
        mSendMsgOptions.enableOfflineMessaging = enabled;
    }

    public boolean isOfflineMessageEnabled() {
        return mSendMsgOptions.enableOfflineMessaging;
    }

    public SendMessageOptions getSendMessageOptions() {
        return mSendMsgOptions;
    }

    public List<RtmMessage> getAllOfflineMessages(String peerId) {
        return mMessagePool.getAllOfflineMessages(peerId);
    }

    public void removeAllOfflineMessages(String peerId) {
        mMessagePool.removeAllOfflineMessages(peerId);
    }
}
