package com.sdxxtop.guardianapp.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.course.BaseCourseDataBean;
import com.sdxxtop.guardianapp.ui.fragment.CourseListFragment;

import java.util.ArrayList;

public class CourseListAdapter extends BaseMultiItemQuickAdapter<BaseCourseDataBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public CourseListAdapter() {
        super(new ArrayList<>());

        addItemType(BaseCourseDataBean.TYPE_HEADER, R.layout.item_course_list_header_recycler);
        addItemType(BaseCourseDataBean.TYPE_CELL, R.layout.item_course_list_cell_recycler);
        addItemType(BaseCourseDataBean.TYPE_LINE, R.layout.item_course_list_line_recycler);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseCourseDataBean item) {
        switch (item.getItemType()) {
            case BaseCourseDataBean.TYPE_HEADER:

                break;
            case BaseCourseDataBean.TYPE_CELL:

                break;

            case BaseCourseDataBean.TYPE_LINE:

                break;
        }
    }
}
