package com.sdxxtop.openlive.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdxxtop.imagora.adapter.MessageAdapter;
import com.sdxxtop.imagora.model.MessageBean;
import com.sdxxtop.imagora.receiver.LoginIMReceiver;
import com.sdxxtop.imagora.rtmtutorial.AgoraIMConfig;
import com.sdxxtop.imagora.rtmtutorial.ChatManager;
import com.sdxxtop.imagora.utils.MessageUtil;
import com.sdxxtop.openlive.activities.presenter.im.AgoraIMLoginPresenter;
import com.sdxxtop.openlive.activities.presenter.im.IAgoraIMLoginView;
import com.sdxxtop.openlive.stats.LocalStatsData;
import com.sdxxtop.openlive.stats.RemoteStatsData;
import com.sdxxtop.openlive.stats.StatsData;
import com.sdxxtop.openlive.ui.VideoGridContainer;
import com.sdxxtop.sdkagora.AgoraSession;
import com.sdxxtop.sdkagora.R;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.video.VideoEncoderConfiguration;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannel;
import io.agora.rtm.RtmChannelAttribute;
import io.agora.rtm.RtmChannelListener;
import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.RtmStatusCode;

public class LiveActivity extends RtcBaseActivity implements IAgoraIMLoginView {
    public static final String MESSAGE_FINISH = "69b953e06abe399e018e2d0657bf26a4";
    public static final String MAIN_UID_TO_FINISH = "12345678";

    private static final String TAG = LiveActivity.class.getSimpleName();

    private VideoGridContainer mVideoGridContainer;
    private ImageView mMuteAudioBtn;

    private VideoEncoderConfiguration.VideoDimensions mVideoDimension;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mUserId = getIntent().getStringExtra(MessageUtil.INTENT_EXTRA_USER_ID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_room);

        AgoraSession.isLiving = true;

        initUI();
        initData();

        init();
    }

    private void initUI() {
        TextView roomName = findViewById(R.id.live_room_name);
        roomName.setText(config().getChannelName());
        roomName.setSelected(true);

        initUserIcon();

        int role = getIntent().getIntExtra(
                com.sdxxtop.openlive.Constants.KEY_CLIENT_ROLE,
                Constants.CLIENT_ROLE_AUDIENCE);
        boolean isBroadcaster = (role == Constants.CLIENT_ROLE_BROADCASTER);

        mMuteAudioBtn = findViewById(R.id.live_btn_mute_audio);
        mMuteAudioBtn.setActivated(isBroadcaster);

        mVideoGridContainer = findViewById(R.id.live_video_grid_layout);
        mVideoGridContainer.setStatsManager(statsManager());

        rtcEngine().setClientRole(role);
        if (isBroadcaster) startBroadcast();
    }

    private void initUserIcon() {
        Bitmap origin = BitmapFactory.decodeResource(getResources(), R.drawable.fake_user_icon);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), origin);
        drawable.setCircular(true);
        ImageView iconView = findViewById(R.id.live_name_board_icon);
        iconView.setImageDrawable(drawable);
    }

    private void initData() {
        mVideoDimension = com.sdxxtop.openlive.Constants.VIDEO_DIMENSIONS[
                config().getVideoDimenIndex()];
    }

    @Override
    protected void onGlobalLayoutCompleted() {
//        RelativeLayout topLayout = findViewById(R.id.live_room_top_layout);
//        FrameLayout.LayoutParams params =
//                (FrameLayout.LayoutParams) topLayout.getLayoutParams();
//        params.height = mStatusBarHeight + topLayout.getMeasuredHeight();
//        topLayout.setLayoutParams(params);
//        topLayout.setPadding(0, mStatusBarHeight, 0, 0);
    }

    private void startBroadcast() {
        rtcEngine().setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
        SurfaceView surface = prepareRtcVideo(0, true);
        mVideoGridContainer.addUserVideoSurface(0, surface, true);
        mMuteAudioBtn.setActivated(true);
    }

    private void stopBroadcast() {
        rtcEngine().setClientRole(Constants.CLIENT_ROLE_AUDIENCE);
        removeRtcVideo(0, true);
        mVideoGridContainer.removeUserVideo(0, true);
        mMuteAudioBtn.setActivated(false);
    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        // Do nothing at the moment
        Log.e(TAG, "uid : " + uid);
        this.uid = uid;
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {
        // Do nothing at the moment
    }

    @Override
    public void onUserOffline(final int uid, int reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (MAIN_UID_TO_FINISH.equals(uid + "")) {
                    finish();
                } else {
                    removeRemoteUser(uid);
                }
            }
        });
    }

    @Override
    public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                renderRemoteUser(uid);
            }
        });
    }

    private void renderRemoteUser(int uid) {
        SurfaceView surface = prepareRtcVideo(uid, false);
        mVideoGridContainer.addUserVideoSurface(uid, surface, false);
    }

    private void removeRemoteUser(int uid) {
        removeRtcVideo(uid, false);
        mVideoGridContainer.removeUserVideo(uid, false);
    }

    @Override
    public void onLocalVideoStats(IRtcEngineEventHandler.LocalVideoStats stats) {
        if (!statsManager().isEnabled()) return;

        LocalStatsData data = (LocalStatsData) statsManager().getStatsData(0);
        if (data == null) return;

        data.setWidth(mVideoDimension.width);
        data.setHeight(mVideoDimension.height);
        data.setFramerate(stats.sentFrameRate);
    }

    @Override
    public void onRtcStats(IRtcEngineEventHandler.RtcStats stats) {
        if (!statsManager().isEnabled()) return;

        LocalStatsData data = (LocalStatsData) statsManager().getStatsData(0);
        if (data == null) return;

        data.setLastMileDelay(stats.lastmileDelay);
        data.setVideoSendBitrate(stats.txVideoKBitRate);
        data.setVideoRecvBitrate(stats.rxVideoKBitRate);
        data.setAudioSendBitrate(stats.txAudioKBitRate);
        data.setAudioRecvBitrate(stats.rxAudioKBitRate);
        data.setCpuApp(stats.cpuAppUsage);
        data.setCpuTotal(stats.cpuAppUsage);
        data.setSendLoss(stats.txPacketLossRate);
        data.setRecvLoss(stats.rxPacketLossRate);
    }

    @Override
    public void onNetworkQuality(int uid, int txQuality, int rxQuality) {
        if (!statsManager().isEnabled()) return;

        StatsData data = statsManager().getStatsData(uid);
        if (data == null) return;

        data.setSendQuality(statsManager().qualityToString(txQuality));
        data.setRecvQuality(statsManager().qualityToString(rxQuality));
    }

    @Override
    public void onRemoteVideoStats(IRtcEngineEventHandler.RemoteVideoStats stats) {
        if (!statsManager().isEnabled()) return;

        RemoteStatsData data = (RemoteStatsData) statsManager().getStatsData(stats.uid);
        if (data == null) return;

        data.setWidth(stats.width);
        data.setHeight(stats.height);
        data.setFramerate(stats.rendererOutputFrameRate);
        data.setVideoDelay(stats.delay);
    }

    @Override
    public void onRemoteAudioStats(IRtcEngineEventHandler.RemoteAudioStats stats) {
        if (!statsManager().isEnabled()) return;

        RemoteStatsData data = (RemoteStatsData) statsManager().getStatsData(stats.uid);
        if (data == null) return;

        data.setAudioNetDelay(stats.networkTransportDelay);
        data.setAudioNetJitter(stats.jitterBufferDelay);
        data.setAudioLoss(stats.audioLossRate);
        data.setAudioQuality(statsManager().qualityToString(stats.quality));
    }

    @Override
    public void finish() {
        super.finish();
        statsManager().clearAllData();
    }

    public void onLeaveClicked(View view) {
        finish();
    }

    public void onSwitchCameraClicked(View view) {
        rtcEngine().switchCamera();
    }

    public void onBeautyClicked(View view) {
        view.setActivated(!view.isActivated());
        rtcEngine().setBeautyEffectOptions(view.isActivated(),
                com.sdxxtop.openlive.Constants.DEFAULT_BEAUTY_OPTIONS);
    }

    public void onMoreClicked(View view) {
        // Do nothing at the moment
    }

    public void onPushStreamClicked(View view) {
        // Do nothing at the moment
    }

    public void onMuteAudioClicked(View view) {
//        if (!mMuteVideoBtn.isActivated()) return;

        rtcEngine().muteLocalAudioStream(view.isActivated());
        view.setActivated(!view.isActivated());
    }

    public void onMuteVideoClicked(View view) {
        if (view.isActivated()) {
            stopBroadcast();
        } else {
            startBroadcast();
        }
        view.setActivated(!view.isActivated());
    }


    // 下面写发送消息逻辑，默认群聊

    private TextView mTitleTextView;
    private EditText mMsgEditText;
    private RecyclerView mRecyclerView;
    private List<MessageBean> mMessageBeanList = new ArrayList<>();
    private MessageAdapter mMessageAdapter;

    private boolean mIsPeerToPeerMode = true;
    private String mUserId = "";
    private String mPeerId = "";
    private String mChannelName = "";
    private int mChannelMemberCount = 1;

    private ChatManager mChatManager;
    private RtmClient mRtmClient;
    private RtmClientListener mClientListener;
    private RtmChannel mRtmChannel;

    AgoraIMLoginPresenter agoraIMLoginPresenter;

    private void init() {

        agoraIMLoginPresenter = new AgoraIMLoginPresenter(this);


        mChatManager = AgoraIMConfig.the().getChatManager();
        mRtmClient = mChatManager.getRtmClient();
        mClientListener = new MyRtmClientListener();
        mChatManager.registerListener(mClientListener);

        Intent intent = getIntent();
        mIsPeerToPeerMode = intent.getBooleanExtra(MessageUtil.INTENT_EXTRA_IS_PEER_MODE, true);

        String targetName = intent.getStringExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME);

        mChannelName = targetName;
        mChannelMemberCount = 1;
        createAndJoinChannel();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mMessageAdapter = new MessageAdapter(this, mMessageBeanList);
        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMessageAdapter);

        mMsgEditText = findViewById(R.id.et_message);
        mMsgEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    onClickSend();
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * API CALL: create and join channel
     */
    private void createAndJoinChannel() {
        // step 1: create a channel instance
        mRtmChannel = mRtmClient.createChannel(mChannelName, new MyChannelListener());
        if (mRtmChannel == null) {
            showToast(getString(R.string.join_channel_failed));
            finish();
            return;
        }

        Log.e("channel", mRtmChannel + "");

        // step 2: join the channel
        mRtmChannel.join(new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                Log.i(TAG, "join channel success");
                getChannelMemberList();
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.e(TAG, "join channel failed");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "join channel failed ---> " + errorInfo);
                        if (errorInfo != null) {
                            if (errorInfo.getErrorCode() == 2) {
                                getChannelMemberList();
                            }
                        } else {
                            showToast(getString(R.string.join_channel_failed) + "--" + errorInfo);
                        }
//                        finish();
                    }
                });
            }
        });
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }


    class MyChannelListener implements RtmChannelListener {
        @Override
        public void onMemberCountUpdated(int i) {

        }

        @Override
        public void onAttributesUpdated(List<RtmChannelAttribute> list) {

        }

        @Override
        public void onMessageReceived(final RtmMessage message, final RtmChannelMember fromMember) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String account = fromMember.getUserId();
                    String msg = message.getText();

                    if (msg.contains(MESSAGE_FINISH)) {
                        if ((MESSAGE_FINISH + uid).equals(msg) || MESSAGE_FINISH.equals(msg)) {
                            finish();
                            return;
                        }
                        return;
                    }

                    Log.e(TAG, "onMessageReceived account = " + account + " msg = " + msg);
                    MessageBean messageBean = new MessageBean(account, msg, false);
                    messageBean.setBackground(getMessageColor(account));
                    mMessageBeanList.add(messageBean);
                    mMessageAdapter.notifyItemRangeChanged(mMessageBeanList.size(), 1);
                    mRecyclerView.scrollToPosition(mMessageBeanList.size() - 1);
                }
            });
        }

        @Override
        public void onMemberJoined(RtmChannelMember member) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mChannelMemberCount++;
//                    refreshChannelTitle();
                }
            });
        }

        @Override
        public void onMemberLeft(RtmChannelMember member) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mChannelMemberCount--;
//                    refreshChannelTitle();
                }
            });
        }
    }


    private int getMessageColor(String account) {
        for (int i = 0; i < mMessageBeanList.size(); i++) {
            if (account.equals(mMessageBeanList.get(i).getAccount())) {
                return mMessageBeanList.get(i).getBackground();
            }
        }
        return MessageUtil.COLOR_ARRAY[MessageUtil.RANDOM.nextInt(MessageUtil.COLOR_ARRAY.length)];
    }


    /**
     * API CALL: get channel member list
     */
    private void getChannelMemberList() {
        mRtmChannel.getMembers(new ResultCallback<List<RtmChannelMember>>() {
            @Override
            public void onSuccess(final List<RtmChannelMember> responseInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChannelMemberCount = responseInfo.size();
//                        refreshChannelTitle();

                        for (RtmChannelMember rtmChannelMember : responseInfo) {
                            String userId = rtmChannelMember.getUserId();

                            Log.e(TAG, "getMembers : " + userId + "===" + rtmChannelMember.getChannelId());
                        }
                    }
                });
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.e(TAG, "failed to get channel members, err: " + errorInfo.getErrorCode());
            }
        });
    }


    /**
     * API CALLBACK: rtm event listener
     */
    class MyRtmClientListener implements RtmClientListener {

        @Override
        public void onConnectionStateChanged(final int state, int reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (state) {
                        case RtmStatusCode.ConnectionState.CONNECTION_STATE_RECONNECTING:
                            showToast(getString(R.string.reconnecting));
                            break;
                        case RtmStatusCode.ConnectionState.CONNECTION_STATE_ABORTED:
                            showToast(getString(R.string.account_offline));
                            setResult(MessageUtil.ACTIVITY_RESULT_CONN_ABORTED);
                            finish();
                            break;
                    }
                }
            });
        }

        @Override
        public void onMessageReceived(final RtmMessage message, final String peerId) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String content = message.getText();
                    Log.i(TAG, "onMessageReceived222 content = " + content);
                    if (peerId.equals(mPeerId)) {
                        MessageBean messageBean = new MessageBean(peerId, content, false);
                        messageBean.setBackground(getMessageColor(peerId));
                        mMessageBeanList.add(messageBean);
                        mMessageAdapter.notifyItemRangeChanged(mMessageBeanList.size(), 1);
                        mRecyclerView.scrollToPosition(mMessageBeanList.size() - 1);
                    } else {
                        MessageUtil.addMessageBean(peerId, content);
                    }
                }
            });
        }

        @Override
        public void onTokenExpired() {

        }
    }

    public void onClickSend() {
        String msg = mMsgEditText.getText().toString();
        if (!msg.equals("")) {
            MessageBean messageBean = new MessageBean(mUserId, msg, false);
            mMessageBeanList.add(messageBean);
            mMessageAdapter.notifyItemRangeChanged(mMessageBeanList.size(), 1);
            mRecyclerView.scrollToPosition(mMessageBeanList.size() - 1);
            sendChannelMessage(msg);
        }
        mMsgEditText.setText("");
    }

    /**
     * API CALL: send message to a channel
     */
    private void sendChannelMessage(String content) {
        // step 1: create a message
        RtmMessage message = mRtmClient.createMessage();
        message.setText(content);

        Log.e("channel", mRtmChannel + "");

        // step 2: send message to channel
        mRtmChannel.sendMessage(message, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                // refer to RtmStatusCode.ChannelMessageState for the message state
                final int errorCode = errorInfo.getErrorCode();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (errorCode) {
                            case RtmStatusCode.ChannelMessageError.CHANNEL_MESSAGE_ERR_TIMEOUT:
                            case RtmStatusCode.ChannelMessageError.CHANNEL_MESSAGE_ERR_FAILURE:
                                showToast(getString(R.string.send_msg_failed));
                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        leaveChat();

        Intent intent = new Intent();
        intent.setAction(LoginIMReceiver.ACTION_LOGIN_RECEIVER);
        sendBroadcast(intent);

        AgoraSession.isLiving = false;
    }

    private void leaveChat() {
        mRtmChannel.leave(new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        leaveAndReleaseChannel();
                        mChatManager.unregisterListener(mClientListener);

                        Log.e(TAG, "leave channel success ---> " + aVoid);
                    }
                });
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.e(TAG, "leave channel failed ---> " + errorInfo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        leaveAndReleaseChannel();
                        mChatManager.unregisterListener(mClientListener);
                    }
                });
            }
        });
    }

    /**
     * API CALL: leave and release channel
     */
    private void leaveAndReleaseChannel() {
        if (mRtmChannel != null) {
            mRtmChannel.leave(null);
            mRtmChannel.release();
            mRtmChannel = null;
        }
    }
}
