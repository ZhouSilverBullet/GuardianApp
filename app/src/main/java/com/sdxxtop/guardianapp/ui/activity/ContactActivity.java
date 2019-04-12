package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.ContactIndexBean;
import com.sdxxtop.guardianapp.presenter.ContactPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ContactContract;
import com.sdxxtop.guardianapp.ui.ContactSearchActivity;
import com.sdxxtop.guardianapp.ui.adapter.ContactAdapter;
import com.sdxxtop.guardianapp.ui.widget.SearchView;
import com.sdxxtop.guardianapp.ui.widget.SideIndexBar;
import com.sdxxtop.guardianapp.utils.ItemDivider;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ContactActivity extends BaseMvpActivity<ContactPresenter> implements ContactContract.IView {
    @BindView(R.id.index_bar)
    SideIndexBar mSideIndexBar;
    @BindView(R.id.text_dialog)
    TextView mText;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.sv_search)
    SearchView mSearchView;
    private ContactAdapter mAdapter;

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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new ItemDivider());

        mAdapter = new ContactAdapter(R.layout.item_contact_recycler);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContactSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showList(List<ContactIndexBean.ContactBean> contactBean) {
        mAdapter.addData(contactBean);
    }
}
