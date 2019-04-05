package com.sdxxtop.guardianapp.ui.activity;

import android.util.Log;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.ContactPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ContactContract;
import com.sdxxtop.guardianapp.ui.widget.SideIndexBar;

import butterknife.BindView;

public class ContactActivity extends BaseMvpActivity<ContactPresenter> implements ContactContract.IView {
    @BindView(R.id.index_bar)
    SideIndexBar mSideIndexBar;
    @BindView(R.id.text_dialog)
    TextView mText;

    @Override
    protected int getLayout() {
        return R.layout.activity_contact;
    }

    @Override
    protected void initView() {
        super.initView();

        mSideIndexBar.setLetters("#ABCDEFHIJKLMOPQSTUVXYZ");
        mSideIndexBar.setTextDialog(mText);
        mSideIndexBar.setOnLetterChangedListener(new SideIndexBar.OnLetterChangedListener() {
            @Override
            public void onChanged(String s, int position) {
                Log.e("SideIndexBar", s + " position:" + position);
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }
}
