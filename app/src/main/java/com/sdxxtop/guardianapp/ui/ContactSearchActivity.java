package com.sdxxtop.guardianapp.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.ContactIndexBean;
import com.sdxxtop.guardianapp.presenter.ContactSearchPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ContactSearchContract;
import com.sdxxtop.guardianapp.ui.adapter.ContactAdapter;
import com.sdxxtop.guardianapp.utils.ItemDivider;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ContactSearchActivity extends BaseMvpActivity<ContactSearchPresenter> implements ContactSearchContract.IView, TextWatcher {
    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_contact_search;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new ItemDivider());
        mAdapter = new ContactAdapter(R.layout.item_contact_recycler);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        etSearch.addTextChangedListener(this);
    }

    @Override
    public void showList(List<ContactIndexBean.ContactBean> contactBean) {
        mAdapter.replaceData(contactBean);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
            mPresenter.loadData(s.toString());
        } else {
            mAdapter.addData(new ArrayList<>());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
