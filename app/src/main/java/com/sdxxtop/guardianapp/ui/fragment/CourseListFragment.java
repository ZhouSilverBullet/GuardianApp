package com.sdxxtop.guardianapp.ui.fragment;

import android.os.Bundle;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.course.BaseCourseDataBean;
import com.sdxxtop.guardianapp.model.bean.course.CourseCellBean;
import com.sdxxtop.guardianapp.model.bean.course.CourseHeaderBean;
import com.sdxxtop.guardianapp.model.bean.course.CourseLineBean;
import com.sdxxtop.guardianapp.presenter.CourseListPresenter;
import com.sdxxtop.guardianapp.presenter.contract.CourseListContract;
import com.sdxxtop.guardianapp.ui.adapter.CourseListAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CourseListFragment extends BaseMvpFragment<CourseListPresenter> implements CourseListContract.IView {
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    private CourseListAdapter mAdapter;

    public static CourseListFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        CourseListFragment fragment = new CourseListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_course_list;
    }


    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CourseListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        ArrayList<BaseCourseDataBean> list = new ArrayList<>();
        list.add(new CourseHeaderBean());
        list.add(new CourseCellBean());
        list.add(new CourseLineBean());
        list.add(new CourseCellBean());

        list.add(new CourseHeaderBean());
        list.add(new CourseCellBean());
        list.add(new CourseLineBean());
        list.add(new CourseCellBean());

        list.add(new CourseHeaderBean());
        list.add(new CourseCellBean());
        list.add(new CourseLineBean());
        list.add(new CourseCellBean());
        mAdapter.addData(list);
    }

    @Override
    public void showError(String error) {

    }
}
