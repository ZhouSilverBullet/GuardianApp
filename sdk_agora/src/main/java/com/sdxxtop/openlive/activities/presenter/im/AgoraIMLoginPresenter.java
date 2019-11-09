package com.sdxxtop.openlive.activities.presenter.im;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.sdxxtop.imagora.rtmtutorial.AgoraIMConfig;
import com.sdxxtop.imagora.rtmtutorial.ChatManager;
import com.sdxxtop.imagora.utils.MessageUtil;
import com.sdxxtop.openlive.activities.CallActivity;
import com.sdxxtop.sdkagora.R;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-08 10:49
 * Version: 1.0
 * Description:
 */
public class AgoraIMLoginPresenter implements Handler.Callback {

    public static final int LOGIN_SUCCESS = 100;
    public static final int LOGIN_FAILURE = 101;
    public static final int LOGOUT = 102;

    private static final String TAG = "AgoraIMLoginPresenter";

    private IAgoraIMLoginView mView;
    private Handler mHandler;

    private ChatManager mChatManager;
    private RtmClient mRtmClient;

    /**
     * 是否登录成功
     */
    private boolean mIsInChat = false;

    public AgoraIMLoginPresenter(IAgoraIMLoginView view) {
        mView = view;

        mChatManager = AgoraIMConfig.the().getChatManager();
        mRtmClient = mChatManager.getRtmClient();


        mHandler = new Handler(Looper.getMainLooper(), this);
    }

    /**
     * 登录通信
     */
    public void doLogin(String userId) {
        if (TextUtils.isEmpty(userId)) {
            showToast("登录不能为空");
            return;
        }

        mRtmClient.login(null, userId, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                Log.i(TAG, "login success");

                if (mHandler != null) {
                    mHandler.sendEmptyMessage(LOGIN_SUCCESS);
                }
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.i(TAG, "login failed: " + errorInfo.getErrorCode());

                if (mHandler != null) {
                    mHandler.sendEmptyMessage(LOGIN_FAILURE);
                }
            }
        });
    }

    /**
     * 登出
     */
    public void doLogout() {
        mRtmClient.logout(null);

        if (mHandler != null) {
            mHandler.sendEmptyMessage(LOGOUT);
        }

        MessageUtil.cleanMessageListBeanList();
    }

    public void doCall(Activity activity, String userId) {
        Intent intent = new Intent(activity, CallActivity.class);
        intent.putExtra("userId", userId);
        activity.startActivity(intent);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case LOGIN_SUCCESS:
                mIsInChat = true;
                Toast.makeText(mView.getContext(), "login success", Toast.LENGTH_SHORT).show();
                break;
            case LOGIN_FAILURE:
                mIsInChat = false;
                Toast.makeText(mView.getContext(), mView.getContext().getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                break;
            case LOGOUT:
                mIsInChat = false;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 是否登录通信成功
     *
     * @return
     */
    public boolean isInChat() {
        return mIsInChat;
    }

    public void setInChat(boolean mIsInChat) {
        this.mIsInChat = mIsInChat;
    }

    public void showToast(String text) {
        Toast.makeText(mView.getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
