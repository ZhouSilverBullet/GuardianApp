package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.ContactIndexBean;
import com.sdxxtop.guardianapp.presenter.ContactPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ContactContract;
import com.sdxxtop.guardianapp.ui.activity.ContactSearchActivity;
import com.sdxxtop.guardianapp.ui.adapter.ContactAdapter;
import com.sdxxtop.guardianapp.ui.widget.SearchView;
import com.sdxxtop.guardianapp.ui.widget.SideIndexBar;
import com.sdxxtop.guardianapp.utils.ItemDivider;
import com.sdxxtop.guardianapp.utils.pinyin.CharacterParser;
import com.sdxxtop.guardianapp.utils.pinyin.PinyinComparator;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ContactActivity extends BaseMvpActivity<ContactPresenter> implements ContactContract.IView, TextWatcher {
    @BindView(R.id.index_bar)
    SideIndexBar mSideIndexBar;
    @BindView(R.id.text_dialog)
    TextView mText;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
//    @BindView(R.id.sv_search)
//    SearchView mSearchView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    private ContactAdapter mAdapter;

    CharacterParser characterParser = CharacterParser.getInstance();
    PinyinComparator pinyinComparator = new PinyinComparator();
    List<ContactIndexBean.ContactBean> beanList = new ArrayList<>();


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
                int posi = mAdapter.getPositionForSection(s.charAt(0));
                if (posi != -1 && mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(posi, 0);
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new ItemDivider());

        mAdapter = new ContactAdapter(R.layout.item_contact_recycler);
        mRecyclerView.setAdapter(mAdapter);

        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter); //绑定之前的adapter

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        mRecyclerView.addItemDecoration(headersDecor);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
//        mSearchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ContactSearchActivity.class);
//                startActivity(intent);
//            }
//        });

        etSearch.addTextChangedListener(this);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除
                etSearch.setText("");
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
    public void showList(List<ContactIndexBean.ContactBean> contactBeanList) {
        handleData(contactBeanList, true);
    }

    @Override
    public void showSearchList(List<ContactIndexBean.ContactBean> contactBean) {
        handleData(contactBean, false);
    }

    private void handleData(List<ContactIndexBean.ContactBean> contactBeanList, boolean isShowBar) {
        if (beanList != null && beanList.size() > 0) {
            beanList.clear();
        }

        for (ContactIndexBean.ContactBean contactBean : contactBeanList) {
            String pinyin = characterParser.getSelling(contactBean.getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                contactBean.sortLetters = sortString.toUpperCase();
            } else {
                contactBean.sortLetters = "#";
            }
            beanList.add(contactBean);
        }


        Collections.sort(beanList, pinyinComparator);

        //计算mSideIndexBar的个数
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (ContactIndexBean.ContactBean contactBean : beanList) {
            set.add(contactBean.sortLetters);
        }
        StringBuilder sb = new StringBuilder();
        for (String s : set) {
            sb.append(s);
        }
        mSideIndexBar.setLetters(sb.toString());

        if (isShowBar) {
            mSideIndexBar.setVisibility(View.VISIBLE);
            mAdapter.addData(beanList);
        } else {
            mAdapter.replaceData(beanList);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
            mPresenter.searchData(s.toString());
            tvCancel.setVisibility(View.VISIBLE);
            mSideIndexBar.setVisibility(View.GONE);
        } else {
            mPresenter.loadData();
//            mAdapter.replaceData(new ArrayList<>());
            tvCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
