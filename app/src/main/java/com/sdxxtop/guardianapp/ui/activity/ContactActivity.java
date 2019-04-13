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
    public void showList(List<ContactIndexBean.ContactBean> contactBeanList) {
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

        mAdapter.addData(beanList);
    }
}
