package com.sdxxtop.guardianapp.ui.pop;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/10
 * Desc:
 */
public class AreaSelectPopWindow extends PopupWindow {


    private LayoutInflater inflater;
    private View viewLayout;
    private TextView tagTextView;
    private TextView tvBg;
    private Activity activity;
    private List<PopWindowDataBean> mData;
    private OnPopItemClickListener mListener;

    private int selectPartId = -1;

    public AreaSelectPopWindow(Activity activity, View viewLayout, List<PopWindowDataBean> data, TextView textView) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.viewLayout = viewLayout;
        this.mData = data;
        this.tagTextView = textView;
        initView();
    }

    public AreaSelectPopWindow(Activity activity, View viewLayout, List<PopWindowDataBean> data, TextView textView, TextView textView2) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.viewLayout = viewLayout;
        this.mData = data;
        this.tagTextView = textView;
        this.tvBg = textView2;
        initView();
    }

    private void initView() {
        if (mData!=null&&mData.size()>0){
            for (PopWindowDataBean mDatum : mData) {
                if (tagTextView.getText().equals(mDatum.getPartName())){
                    selectPartId = mDatum.getPartId();
                }
            }
        }

        setTouchable(true);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());
        setWidth(viewLayout.getWidth());
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //添加弹出、弹入的动画
        setAnimationStyle(R.style.Popupwindow);

        View view = inflater.inflate(R.layout.pop_area_select, null);
        setContentView(view);

        ListView listView = view.findViewById(R.id.listView);
        ListAdapter adapter = new ListAdapter();
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        showAsDropDown(viewLayout, 0, 0);
//        toWindowBackground(activity, 0.6f);
        if (tvBg != null) {
            tvBg.setVisibility(View.VISIBLE);
        }

        if (tvBg != null) {
            tvBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvBg.setVisibility(View.GONE);
                }
            });
        }

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
//                setShowBackground(1.f);
                if (tvBg != null) {
                    tvBg.setVisibility(View.GONE);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    PopWindowDataBean item = mData.get(position);
                    mListener.onPopItemClick(item.getPartId(), item.getPartName());
//                    tagTextView.setText(completeInfo.getPart_name());
                    selectPartId = item.getPartId();
                }
                dismiss();
            }
        });
    }


    private void setShowBackground(float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }

    public void toWindowBackground(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }


    public void setOnPopItemClickListener(OnPopItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnPopItemClickListener {
        void onPopItemClick(int partTypeid, String partName);
    }


    /************************** 适配器 ********************************************/
    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public PopWindowDataBean getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.from(activity).inflate(R.layout.item_text, null);
                viewHolder.tvArea = convertView.findViewById(R.id.tv_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            PopWindowDataBean item = getItem(position);
            if (selectPartId != -1 && selectPartId == item.getPartId()) {
                viewHolder.tvArea.setTextColor(Color.parseColor("#32B16C"));
            }else{
                viewHolder.tvArea.setTextColor(Color.parseColor("#292929"));
            }
            viewHolder.tvArea.setText(item.getPartName());
            return convertView;
        }
    }

    class ViewHolder {
        private TextView tvArea;
    }

    /************************** 区域选择器数据bean ********************************************/
    public static class PopWindowDataBean {
        private int partId;
        private String partName;

        public PopWindowDataBean(int partId, String partName) {
            this.partId = partId;
            this.partName = partName;
        }

        public int getPartId() {
            return partId;
        }

        public void setPartId(int partId) {
            this.partId = partId;
        }

        public String getPartName() {
            return partName;
        }

        public void setPartName(String partName) {
            this.partName = partName;
        }
    }

}
