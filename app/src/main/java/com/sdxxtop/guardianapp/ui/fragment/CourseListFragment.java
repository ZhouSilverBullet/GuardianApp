package com.sdxxtop.guardianapp.ui.fragment;

import android.os.Bundle;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.course.BaseCourseDataBean;
import com.sdxxtop.guardianapp.model.bean.course.CourseCellBean;
import com.sdxxtop.guardianapp.model.bean.course.CourseHeaderBean;
import com.sdxxtop.guardianapp.model.bean.course.CourseLineBean;
import com.sdxxtop.guardianapp.model.bean.course.ExamCellBean;
import com.sdxxtop.guardianapp.presenter.CourseListPresenter;
import com.sdxxtop.guardianapp.presenter.contract.CourseListContract;
import com.sdxxtop.guardianapp.ui.adapter.CourseListAdapter;
import com.sdxxtop.guardianapp.utils.GuardianUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CourseListFragment extends BaseMvpFragment<CourseListPresenter> implements CourseListContract.IView {
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    private CourseListAdapter mAdapter;
    private int mType;
    //是否是课堂
    private boolean isCourse;

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

        if (getArguments() != null) {
            mType = getArguments().getInt("type");

            isCourse = mType == 1;
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CourseListAdapter();
        mAdapter.setCourse(isCourse);
        mRecyclerView.setAdapter(mAdapter);

//        ArrayList<BaseCourseDataBean> list = new ArrayList<>();
//        list.add(new CourseHeaderBean());
//        list.add(new CourseCellBean());
//        list.add(new CourseLineBean());
//        list.add(new CourseCellBean());
//
//        list.add(new CourseHeaderBean());
//        list.add(new CourseCellBean());
//        list.add(new CourseLineBean());
//        list.add(new CourseCellBean());
//
//        list.add(new CourseHeaderBean());
//        list.add(new CourseCellBean());
//        list.add(new CourseLineBean());
//        list.add(new CourseCellBean());
//        mAdapter.addData(list);
    }

    @Override
    protected void initData() {
        super.initData();
//        String namePath = isCourse ? "course" : "exam";
//        mPresenter.loadData(namePath);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            String namePath = isCourse ? "course" : "exam";
            mPresenter.loadData(namePath);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        String namePath = isCourse ? "course" : "exam";
        mPresenter.loadData(namePath);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showList(List<CourseCellBean> courseBeanList) {
        //分类
        Map<String, List<CourseCellBean>> map = new LinkedHashMap<>();
        for (CourseCellBean courseBean : courseBeanList) {
            String type = courseBean.getClassify_name();
            List<CourseCellBean> beanList = map.get(type);
            if (beanList == null) {
                beanList = new ArrayList<>();
                beanList.add(courseBean);
                map.put(type, beanList);
            } else {
                beanList.add(courseBean);
            }
        }

        //重新转载数据
        List<BaseCourseDataBean> list = new ArrayList<>();
        int size = 0;
        for (String integer : map.keySet()) {
            List<CourseCellBean> tempList = map.get(integer);

            if (tempList == null) {  //取出来数据为空
                continue;
            }

            //重新转载数据
            for (int i = 0; i < tempList.size(); i++) {
                CourseCellBean courseCellBean = tempList.get(i);
                if (i == 0) { //设置头
                    CourseHeaderBean e = new CourseHeaderBean();
                    e.strHeader = courseCellBean.getClassify_name();
                    e.color = GuardianUtils.getColor(size);
                    size++;
                    list.add(e);
                }
                if (isCourse) {
                    list.add(courseCellBean);
                } else {
                    //转载考试的数据
                    ExamCellBean examCellBean = new ExamCellBean();
                    examCellBean.setClassify_id(courseCellBean.getClassify_id());
                    examCellBean.setClassify_name(courseCellBean.getClassify_name());
                    examCellBean.setEnd_time(courseCellBean.getEnd_time());
                    examCellBean.setExam_id(courseCellBean.getExam_id());
                    examCellBean.setNum(courseCellBean.getNum());
                    examCellBean.setTitle(courseCellBean.getTitle());
                    examCellBean.setType(courseCellBean.getType());
                    examCellBean.setExam_time(courseCellBean.getExam_time());
                    list.add(examCellBean);
                }
                list.add(new CourseLineBean());
            }
        }
        mAdapter.replaceData(list);
    }

    /**
     * 回头再抽取吧
     *
     * @param examCellBeans
     */
    @Override
    public void showExam(List<ExamCellBean> examCellBeans) {
        //分类
        Map<String, List<ExamCellBean>> map = new LinkedHashMap<>();
        for (ExamCellBean courseBean : examCellBeans) {
            String type = courseBean.getClassify_name();
            List<ExamCellBean> beanList = map.get(type);
            if (beanList == null) {
                beanList = new ArrayList<>();
                beanList.add(courseBean);
                map.put(type, beanList);
            } else {
                beanList.add(courseBean);
            }
        }

        //重新转载数据
        List<BaseCourseDataBean> list = new ArrayList<>();
        int size = 0;
        for (String integer : map.keySet()) {
            List<ExamCellBean> tempList = map.get(integer);

            if (tempList == null) {  //取出来数据为空
                continue;
            }

            //重新转载数据
            for (int i = 0; i < tempList.size(); i++) {
                ExamCellBean courseCellBean = tempList.get(i);
                if (i == 0) { //设置头
                    CourseHeaderBean e = new CourseHeaderBean();
                    e.strHeader = courseCellBean.getClassify_name();
                    e.color = GuardianUtils.getColor(size);
                    size++;
                    list.add(e);
                }

                list.add(courseCellBean);
                list.add(new CourseLineBean());
            }
        }
        mAdapter.replaceData(list);
    }

//    private void handleData(List<BaseCourseDataBean> list,) {
//        list.add(new CourseHeaderBean());
//        list.add(new CourseCellBean());
//        list.add(new CourseLineBean());
//        list.add(new CourseCellBean());
//    }
}
