package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.course.BaseCourseDataBean;
import com.sdxxtop.guardianapp.model.bean.course.CourseCellBean;
import com.sdxxtop.guardianapp.model.bean.course.CourseHeaderBean;
import com.sdxxtop.guardianapp.model.bean.course.ExamCellBean;
import com.sdxxtop.guardianapp.ui.activity.ExamineActivity;
import com.sdxxtop.guardianapp.ui.activity.NewsDetailsActivity;
import com.sdxxtop.guardianapp.ui.activity.VideoPlayActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CourseListAdapter extends BaseMultiItemQuickAdapter<BaseCourseDataBean, BaseViewHolder> {

    private boolean mIsCourse;

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
                if (item instanceof CourseHeaderBean) {
                    TextView tvHeader = helper.getView(R.id.tv_header);
                    tvHeader.setText(((CourseHeaderBean) item).strHeader);
                }
                break;
            case BaseCourseDataBean.TYPE_CELL:
                TextView tvTitle = helper.getView(R.id.tv_title);
                TextView tvRightDec = helper.getView(R.id.tv_right_dec);
                TextView tvBtn = helper.getView(R.id.tv_btn);
                TextView tvDec = helper.getView(R.id.tv_dec);
                TextView tvLastTime = helper.getView(R.id.tv_last_time);

                if (item instanceof CourseCellBean) {

                    CourseCellBean bean = (CourseCellBean) item;
                    //1.文章 2.视频

                    tvTitle.setText(bean.getTitle());
                    //共26课时，预计用时13小时
                    tvDec.setText("共" + bean.getClass_time() + "课时，预计用时" + bean.getStudy_time() + "小时");
//                    tvLastTime.setText("共" + bean.getClass_time() + "课时，预计用时" + bean.getStudy_time() + "小时");
                    tvLastTime.setText("课程考试日期：" + handleTime(bean.getExam_time()));

                    tvBtn.setText("去学习");
                    int type = bean.getType();
                    if (type == 1) {
                        tvRightDec.setText("文章");
                    } else {
                        tvRightDec.setText("视频");
                    }

                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (type == 1) {
                                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                                intent.putExtra("article_path", ((CourseCellBean) item).getUrl());
                                mContext.startActivity(intent);
                            } else {
                                Intent intent = new Intent(mContext, VideoPlayActivity.class);
                                intent.putExtra("video_path",((CourseCellBean) item).getUrl());
                                intent.putExtra("title",((CourseCellBean) item).getTitle());
                                mContext.startActivity(intent);
                            }
                        }
                    });
                }


                if (item instanceof ExamCellBean) {
                    ExamCellBean bean = (ExamCellBean) item;
                    tvTitle.setText(bean.getTitle());

                    tvDec.setText("考试形式：选择题    人脸识别方法作弊");

                    String endTime = bean.getEnd_time();
                    tvLastTime.setText("课程最晚考试时间: " + handleTime(endTime));

                    tvBtn.setText("去学习");
                    int type = bean.getType();
                    if (type == 1) {
                        tvRightDec.setText("文章");
                    } else {
                        tvRightDec.setText("PPT");
                    }

                    //1.参加了，2.没参加
                    int is_exam = bean.getIs_exam();
                    if (is_exam == 1) {
                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    } else {
                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ExamineActivity.class);
                                intent.putExtra("examId", ((ExamCellBean) item).getExam_id());
                                mContext.startActivity(intent);
                            }
                        });
                    }


                }
                break;

            case BaseCourseDataBean.TYPE_LINE:

                break;
        }
    }

    private String handleTime(String examTime) {
        if (TextUtils.isEmpty(examTime)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM.dd日");
        try {
            Date date = sdf.parse(examTime);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void setCourse(boolean isCourse) {
        mIsCourse = isCourse;
    }
}
