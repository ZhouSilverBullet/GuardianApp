package com.sdxxtop.guardianapp.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventStreamBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.ui.activity.EventDiscretionListActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportListActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantCompanyReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantEventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantGridReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GridEventActivity;
import com.sdxxtop.guardianapp.ui.activity.HomeActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolRecordActivity;
import com.sdxxtop.guardianapp.ui.activity.SectionEventActivity;
import com.sdxxtop.guardianapp.ui.activity.custom_event.CustomHeightBottomSheetDialog;
import com.sdxxtop.guardianapp.ui.assignevent.AssignEventActivity;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static com.sdxxtop.guardianapp.model.http.net.RetrofitHelper.getEnvirApi;

/**
 * @author :  lwb
 * Date: 2019/9/5
 * Desc:
 */
public class WorkTabAdapter extends BaseAdapter {

    private int[] imgRes = {R.drawable.icon_shijian_work, R.drawable.icon_shuju_work, R.drawable.icon_qiye_work,
            R.drawable.icon_bumen_work, R.drawable.icon_shangbao_work, R.drawable.icon_xuncha_work, R.drawable.icon_jiaoban_work};
    //    private int[] imgRes = {R.drawable.icon_shijian_work, R.drawable.icon_shuju_work, R.drawable.icon_qiye_work,
//            R.drawable.icon_bumen_work, R.drawable.icon_shangbao_work, R.drawable.icon_xuncha_work, R.drawable.icon_bumen_work};
    private String[] titleRes = {"事件统计", "工作数据", "企业数据", "部门事件", "我的上报", "我的巡查", "交办事件"};
//    private String[] titleRes = {"事件统计", "工作数据", "企业数据", "部门事件", "我的上报", "我的巡查", "网格事件"};

    private List<Integer> imgResValue = new ArrayList<>();
    private List<String> titleResValue = new ArrayList<>();
    private Activity context;
    private CustomHeightBottomSheetDialog dialog;

    public WorkTabAdapter(Activity mContext) {
        context = mContext;
        for (int imgRe : imgRes) {
            imgResValue.add(imgRe);
        }
        for (String titleRe : titleRes) {
            titleResValue.add(titleRe);
        }
    }


    @Override
    public int getCount() {
        return titleResValue.size();
    }

    @Override
    public Object getItem(int position) {
        return titleResValue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // 第一次加载创建View，其余复用 View
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.gridview_item_work, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.iv_icon);
            holder.textView = convertView.findViewById(R.id.tv_title);
            holder.llLayout = convertView.findViewById(R.id.ll_layout);
            // 打标签
            convertView.setTag(holder);

        } else {
            // 从标签中获取数据
            holder = (ViewHolder) convertView.getTag();
        }

        // 根据key值设置不同数据内容
        holder.imageView.setImageResource(imgResValue.get(position));
        holder.textView.setText(titleResValue.get(position));
        holder.llLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(position, context);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout llLayout;
    }

    private void startActivity(int index, Activity context) {
        if (mBean == null) {
            showToast(context);
            return;
        }
        Intent intent = null;
        switch (titleResValue.get(index)) {
            case "事件统计":   // 事件统计
                if (mBean.is_statistics == 1) {
                    intent = new Intent(context, GrantEventReportActivity.class);
                }
                break;
            case "工作数据":   // 工作数据
                if (mBean.is_working == 1) {
                    intent = new Intent(context, GrantGridReportActivity.class);
                }
                break;
            case "企业数据":   // 企业数据
                if (mBean.is_enterprise == 1) {
                    intent = new Intent(context, GrantCompanyReportActivity.class);
                }
                break;
            case "部门事件":   // 部门事件
                if (mBean.is_sectoral == 1) {
                    intent = new Intent(context, SectionEventActivity.class);
                }
                break;
            case "我的上报":   // 我的上报
                if (mBean.is_report == 1) {
                    intent = new Intent(context, EventReportListActivity.class);
                }
                break;
            case "我的巡查":   // 我的巡查
                if (mBean.is_patrol == 1) {
                    intent = new Intent(context, EventDiscretionListActivity.class);
                }
                break;
            case "网格事件":   // 网格事件
                if (mBean.is_grid_event == 1) {
                    intent = new Intent(context, GridEventActivity.class);
                }
                break;
            case "工作轨迹":   // 工作轨迹
                intent = new Intent(context, PatrolRecordActivity.class);
                break;
            case "交办事件":   // 交办事件
                if (mBean.is_assignment==1) {
                    intent = new Intent(context, AssignEventActivity.class);
                }
                break;
            case "自定义事件":   // 自定义事件
                if (dialog == null) {
                    initDialog();
                }
                initData(0);
                if (true)
                    return;
                break;
        }
        if (intent == null) {
            showToast(context);
            return;
        }
        context.startActivity(intent);
    }

    private void initData(int page) {
        if (context instanceof BaseMvpActivity) {
            ((HomeActivity) context).showLoadingDialog();
        }
        Params params = new Params();
        params.put("sp", page);
        Observable<RequestBean<EventStreamBean>> observable = getEnvirApi().postEventStreamList(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventStreamBean>() {
            @Override
            public void onSuccess(EventStreamBean bean) {
                if (context instanceof BaseMvpActivity) {
                    ((HomeActivity) context).hideLoadingDialog();
                }
                if (bean != null) {
                    if (dialog != null && dialog.adapter != null) {
                        dialog.setData(bean.serrings, page == 0);
                        if (dialog.adapter.getData().size() > 0) {
                            dialog.setPeekHeight(dp2px(bean.serrings.size() * 44));
                            dialog.show();
                        } else {
                            UIUtils.showToast("当前没有事件流");
                        }
                    }
                }
            }

            @Override
            public void onFailure(int code, String error) {
                if (context instanceof BaseMvpActivity) {
                    ((HomeActivity) context).hideLoadingDialog();
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initDialog() {
        dialog = new CustomHeightBottomSheetDialog(context, R.layout.custom_event_list);
        dialog.setRefreshListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (dialog != null && dialog.adapter != null) {
                    initData(dialog.adapter.getData().size());
                }
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData(0);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    private WorkIndexBean mBean;

    public void setLimits(WorkIndexBean bean) {
        imgResValue.clear();
        titleResValue.clear();
        for (int imgRe : imgRes) {
            imgResValue.add(imgRe);
        }
        for (String titleRe : titleRes) {
            titleResValue.add(titleRe);
        }
        if (bean.is_serrings == 1) {
            imgResValue.add(R.drawable.icon_cus_evnet_work);
            titleResValue.add("自定义事件");
        }

        long aLong = SpUtil.getLong(Constants.IS_TRACK, 2);
        if (aLong == 1) {
            imgResValue.add(R.drawable.icon_bumen_work);
            titleResValue.add("工作轨迹");
        }

//        imgResValue.add(R.drawable.icon_jiaoban_work);
//        titleResValue.add("交办事件");

        notifyDataSetChanged();
        this.mBean = bean;
    }

    public void showToast(Context context) {
        UIUtils.showToast("暂无权限");
    }

    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
