package com.sdxxtop.openlive.activities;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sdxxtop.imagora.rtmtutorial.AgoraIMConfig;
import com.sdxxtop.imagora.rtmtutorial.ChatManager;
import com.sdxxtop.imagora.utils.MessageUtil;
import com.sdxxtop.sdkagora.R;

import io.agora.rtc.Constants;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.LocalInvitation;
import io.agora.rtm.RemoteInvitation;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmCallEventListener;
import io.agora.rtm.RtmClient;

public class MainAgoraActivity extends BaseActivity {
    private static final String TAG = MainAgoraActivity.class.getSimpleName();
    private static final int MIN_INPUT_METHOD_HEIGHT = 200;
    private static final int ANIM_DURATION = 200;

    // Permission request code of any integer value
    private static final int PERMISSION_REQ_CODE = 1 << 4;

    private String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private Rect mVisibleRect = new Rect();
    private int mLastVisibleHeight = 0;
    private RelativeLayout mBodyLayout;
    private int mBodyDefaultMarginTop;
    private EditText mTopicEdit;
    private TextView mStartBtn;
    private ImageView mLogo;

    private EditText etUserLogin;

    private Animator.AnimatorListener mLogoAnimListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {
            // Do nothing
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            mLogo.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationCancel(Animator animator) {
            mLogo.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
            // Do nothing
        }
    };

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
        }

        @Override
        public void afterTextChanged(Editable editable) {
            mStartBtn.setEnabled(!TextUtils.isEmpty(editable));
        }
    };

    private ViewTreeObserver.OnGlobalLayoutListener mLayoutObserverListener =
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    checkInputMethodWindowState();
                }
            };

    private void checkInputMethodWindowState() {
        getWindow().getDecorView().getRootView().getWindowVisibleDisplayFrame(mVisibleRect);
        int visibleHeight = mVisibleRect.bottom - mVisibleRect.top;
        if (visibleHeight == mLastVisibleHeight) return;

        boolean inputShown = mDisplayMetrics.heightPixels - visibleHeight > MIN_INPUT_METHOD_HEIGHT;
        mLastVisibleHeight = visibleHeight;

        // Log.i(TAG, "onGlobalLayout:" + inputShown +
        //        "|" + getWindow().getDecorView().getRootView().getViewTreeObserver());

        // There is no official way to determine whether the
        // input method dialog has already shown.
        // This is a workaround, and if the visible content
        // height is significantly less than the screen height,
        // we should know that the input method dialog takes
        // up some screen space.
        if (inputShown) {
            if (mLogo.getVisibility() == View.VISIBLE) {
                mBodyLayout.animate().translationYBy(-mLogo.getMeasuredHeight())
                        .setDuration(ANIM_DURATION).setListener(null).start();
                mLogo.setVisibility(View.INVISIBLE);
            }
        } else if (mLogo.getVisibility() != View.VISIBLE) {
            mBodyLayout.animate().translationYBy(mLogo.getMeasuredHeight())
                    .setDuration(ANIM_DURATION).setListener(mLogoAnimListener).start();
        }
    }


    private ChatManager mChatManager;
    private RtmClient mRtmClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agora_main);
        initUI();

        mChatManager = AgoraIMConfig.the().getChatManager();
        mRtmClient = mChatManager.getRtmClient();

        mRtmClient.getRtmCallManager().setEventListener(new RtmCallEventListener() {
            @Override
            public void onLocalInvitationReceivedByPeer(LocalInvitation localInvitation) {
                Log.e(TAG, "-------onLocalInvitationReceivedByPeer-------");
            }

            @Override
            public void onLocalInvitationAccepted(LocalInvitation localInvitation, String s) {
                Log.e(TAG, "-------onLocalInvitationAccepted-------");
            }

            @Override
            public void onLocalInvitationRefused(LocalInvitation localInvitation, String s) {
                Log.e(TAG, "-------onLocalInvitationRefused-------");
            }

            @Override
            public void onLocalInvitationCanceled(LocalInvitation localInvitation) {
                Log.e(TAG, "-------onLocalInvitationCanceled-------");
            }

            @Override
            public void onLocalInvitationFailure(LocalInvitation localInvitation, int i) {
                Log.e(TAG, "-------onLocalInvitationFailure-------");
            }

            @Override
            public void onRemoteInvitationReceived(final RemoteInvitation remoteInvitation) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String content = remoteInvitation.getContent();
                        Toast.makeText(MainAgoraActivity.this, "呼叫来了 onRemoteInvitationReceived 内容：" + content, Toast.LENGTH_SHORT).show();

                        mRtmClient.getRtmCallManager().acceptRemoteInvitation(remoteInvitation, new ResultCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }

                            @Override
                            public void onFailure(ErrorInfo errorInfo) {

                            }
                        });
                    }
                });

                Log.e(TAG, "-------onRemoteInvitationReceived-------");
            }

            @Override
            public void onRemoteInvitationAccepted(RemoteInvitation remoteInvitation) {
                Log.e(TAG, "-------onRemoteInvitationAccepted-------");

            }

            @Override
            public void onRemoteInvitationRefused(RemoteInvitation remoteInvitation) {
                Log.e(TAG, "-------onRemoteInvitationRefused-------");
            }

            @Override
            public void onRemoteInvitationCanceled(RemoteInvitation remoteInvitation) {
                Log.e(TAG, "-------onRemoteInvitationCanceled-------");
            }

            @Override
            public void onRemoteInvitationFailure(RemoteInvitation remoteInvitation, int i) {
                Log.e(TAG, "-------onRemoteInvitationFailure`-------");
            }
        });

        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogout();
            }
        });


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCall();
            }
        });
    }

    private void initUI() {
        mBodyLayout = findViewById(R.id.middle_layout);
        mLogo = findViewById(R.id.main_logo);
        etUserLogin = findViewById(R.id.et_user_login);

        mTopicEdit = findViewById(R.id.topic_edit);
        mTopicEdit.addTextChangedListener(mTextWatcher);

        mStartBtn = findViewById(R.id.start_broadcast_button);
        if (TextUtils.isEmpty(mTopicEdit.getText())) mStartBtn.setEnabled(false);
    }

    @Override
    protected void onGlobalLayoutCompleted() {
        adjustViewPositions();
    }

    private void adjustViewPositions() {
        // Setting btn move downward away the status bar
        ImageView settingBtn = findViewById(R.id.setting_button);
        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) settingBtn.getLayoutParams();
        param.topMargin += mStatusBarHeight;
        settingBtn.setLayoutParams(param);

        // Logo is 0.48 times the screen width
        // ImageView logo = findViewById(R.id.main_logo);
        param = (RelativeLayout.LayoutParams) mLogo.getLayoutParams();
        int size = (int) (mDisplayMetrics.widthPixels * 0.48);
        param.width = size;
        param.height = size;
        mLogo.setLayoutParams(param);

        // Bottom margin of the main body should be two times it's top margin.
        param = (RelativeLayout.LayoutParams) mBodyLayout.getLayoutParams();
        param.topMargin = (mDisplayMetrics.heightPixels -
                mBodyLayout.getMeasuredHeight() - mStatusBarHeight) / 3;
        mBodyLayout.setLayoutParams(param);
        mBodyDefaultMarginTop = param.topMargin;

        // The width of the start button is roughly 0.72
        // times the width of the screen
        mStartBtn = findViewById(R.id.start_broadcast_button);
        param = (RelativeLayout.LayoutParams) mStartBtn.getLayoutParams();
        param.width = (int) (mDisplayMetrics.widthPixels * 0.72);
        mStartBtn.setLayoutParams(param);
    }

    public void onSettingClicked(View view) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void onStartBroadcastClicked(View view) {
        checkPermission();
    }

    private void checkPermission() {
        boolean granted = true;
        for (String per : PERMISSIONS) {
            if (!permissionGranted(per)) {
                granted = false;
                break;
            }
        }

        if (granted) {
            resetLayoutAndForward();
        } else {
            requestPermissions();
        }
    }

    private boolean permissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(
                this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQ_CODE) {
            boolean granted = true;
            for (int result : grantResults) {
                granted = (result == PackageManager.PERMISSION_GRANTED);
                if (!granted) break;
            }

            if (granted) {
                resetLayoutAndForward();
            } else {
                toastNeedPermissions();
            }
        }
    }

    private void resetLayoutAndForward() {
        closeImeDialogIfNeeded();
        gotoRoleActivity();
    }

    private void closeImeDialogIfNeeded() {
        InputMethodManager manager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mTopicEdit.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void gotoRoleActivity() {

//        doLogin();

        final String userLogin = etUserLogin.getText().toString();


        Intent intent = new Intent(MainAgoraActivity.this, LiveActivity.class);
        String room = mTopicEdit.getText().toString();
        //默认是主播
        intent.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, room);
        intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, userLogin);

        intent.putExtra(com.sdxxtop.openlive.Constants.KEY_CLIENT_ROLE, Constants.CLIENT_ROLE_BROADCASTER);
        config().setChannelName(room);
        startActivity(intent);
    }

    private void toastNeedPermissions() {
        Toast.makeText(this, R.string.need_necessary_permissions, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetUI();
        registerLayoutObserverForSoftKeyboard();
    }

    private void resetUI() {
        resetLogo();
        closeImeDialogIfNeeded();
    }

    private void resetLogo() {
        mLogo.setVisibility(View.VISIBLE);
        mBodyLayout.setY(mBodyDefaultMarginTop);
    }

    private void registerLayoutObserverForSoftKeyboard() {
        View view = getWindow().getDecorView().getRootView();
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(mLayoutObserverListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeLayoutObserverForSoftKeyboard();
    }

    private void removeLayoutObserverForSoftKeyboard() {
        View view = getWindow().getDecorView().getRootView();
        view.getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutObserverListener);
    }

    /**
     * API CALL: login RTM server
     */
    private void doLogin() {
//        mIsInChat = true;
        final String userLogin = etUserLogin.getText().toString();
        mRtmClient.login(null, userLogin, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                Log.i(TAG, "login success");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        showToast("登出成功！！！");
//                        Intent intent = new Intent(LoginActivity.this, SelectionActivity.class);
//                        intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, mUserId);
//                        startActivity(intent);

//                        Intent intent = new Intent(MainAgoraActivity.this, LiveActivity.class);
//                        String room = mTopicEdit.getText().toString();
//                        //默认是主播
//                        intent.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, room);
//                        intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, userLogin);
//
//                        intent.putExtra(com.sdxxtop.openlive.Constants.KEY_CLIENT_ROLE, Constants.CLIENT_ROLE_BROADCASTER);
//                        config().setChannelName(room);
//                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.i(TAG, "login failed: " + errorInfo.getErrorCode());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        mLoginBtn.setEnabled(true);
//                        mIsInChat = false;
                        showToast(getString(R.string.login_failed));
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        doLogout();
    }


    /**
     * API CALL: logout from RTM server
     */
    private void doLogout() {
        mRtmClient.logout(null);
        MessageUtil.cleanMessageListBeanList();
    }

    private void doCall() {
        EditText etCall = findViewById(R.id.et_call);
        LocalInvitation localInvitation = mRtmClient.getRtmCallManager().createLocalInvitation(etCall.getText().toString().trim());
        localInvitation.setContent("我我是造的假数据！！！，我是假的数据，我是假数据 ");
        mRtmClient.getRtmCallManager().sendLocalInvitation(localInvitation, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Toast.makeText(MainAgoraActivity.this, "呼叫失败" + errorInfo.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
