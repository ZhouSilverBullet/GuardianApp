package com.sdxxtop.guardianapp.ui.widget.bottom_tab;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author :  lwb
 * Date: 2019/9/4
 * Desc:
 */
public class CustomBottomTab extends LinearLayout {

    @BindView(R.id.iv_menu_1)
    ImageView ivMenu1;
    @BindView(R.id.tv_menu_1)
    TextView tvMenu1;
    @BindView(R.id.ll_menu_1)
    LinearLayout llMenu1;
    @BindView(R.id.iv_menu_2)
    ImageView ivMenu2;
    @BindView(R.id.tv_menu_2)
    TextView tvMenu2;
    @BindView(R.id.ll_menu_2)
    public LinearLayout llMenu2;
    @BindView(R.id.iv_menu_4)
    ImageView ivMenu4;
    @BindView(R.id.tv_menu_4)
    TextView tvMenu4;
    @BindView(R.id.ll_menu_4)
    LinearLayout llMenu4;
    @BindView(R.id.iv_menu_5)
    ImageView ivMenu5;
    @BindView(R.id.tv_menu_5)
    TextView tvMenu5;
    @BindView(R.id.ll_menu_5)
    LinearLayout llMenu5;
    @BindView(R.id.iv_home)
    ImageView ivHome;

    private int mCurrentItem = 0;

    public CustomBottomTab(Context context) {
        this(context, null);
    }

    public CustomBottomTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBottomTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_bottom_tab, this, true);
        ButterKnife.bind(this);

        mCurrentItem = 0;
        tvMenu1.setTextColor(Color.parseColor("#999999"));
        tvMenu2.setTextColor(Color.parseColor("#999999"));
        tvMenu4.setTextColor(Color.parseColor("#999999"));
        tvMenu5.setTextColor(Color.parseColor("#999999"));
        ivMenu1.setImageResource(R.drawable.tab_bangong_default);   //icon_4_home
        ivMenu2.setImageResource(R.drawable.tab_yingyong_default);  // bottom_tab_select
        ivMenu4.setImageResource(R.drawable.tab_xuexi_default);   // icon_5_home
        ivMenu5.setImageResource(R.drawable.tab_my_default);   //  tab_my_default
        ivHome.setImageResource(R.drawable.tab_home_pre);
    }


    @OnClick({R.id.ll_menu_1, R.id.ll_menu_2, R.id.ll_menu_4, R.id.ll_menu_5, R.id.iv_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_menu_1:
                if (mListener != null && mCurrentItem != 1) {
                    boolean b = mListener.netResult(1);
                    mCurrentItem = 1;
                    if (!b) {
                        return;
                    }
                }
                tvMenu1.setTextColor(Color.parseColor("#2593E7"));
                tvMenu2.setTextColor(Color.parseColor("#999999"));
                tvMenu4.setTextColor(Color.parseColor("#999999"));
                tvMenu5.setTextColor(Color.parseColor("#999999"));
                ivMenu1.setImageResource(R.drawable.tab_bangong);   //icon_4_home
                ivMenu2.setImageResource(R.drawable.tab_yingyong_default);  // bottom_tab_select
                ivMenu4.setImageResource(R.drawable.tab_xuexi_default);   // icon_5_home
                ivMenu5.setImageResource(R.drawable.tab_my_default);   //  tab_my_default
                ivHome.setImageResource(R.drawable.tab_home_default);
                break;
            case R.id.ll_menu_2:
                if (mListener != null && mCurrentItem != 2) {
                    boolean b = mListener.netResult(2);
                    mCurrentItem = 2;
                    if (!b) {
                        return;
                    }
                }
                tvMenu1.setTextColor(Color.parseColor("#999999"));
                tvMenu2.setTextColor(Color.parseColor("#2593E7"));
                tvMenu4.setTextColor(Color.parseColor("#999999"));
                tvMenu5.setTextColor(Color.parseColor("#999999"));
                ivMenu1.setImageResource(R.drawable.tab_bangong_default);
                ivMenu2.setImageResource(R.drawable.tab_yingyong);
                ivMenu4.setImageResource(R.drawable.tab_xuexi_default);
                ivMenu5.setImageResource(R.drawable.tab_my_default);
                ivHome.setImageResource(R.drawable.tab_home_default);
                break;
            case R.id.ll_menu_4:
                if (mListener != null && mCurrentItem != 3) {
                    boolean b = mListener.netResult(3);
                    if (!b) {
                        return;
                    }
                }
                tvMenu1.setTextColor(Color.parseColor("#999999"));
                tvMenu2.setTextColor(Color.parseColor("#999999"));
                tvMenu4.setTextColor(Color.parseColor("#2593E7"));
                tvMenu5.setTextColor(Color.parseColor("#999999"));
                ivMenu1.setImageResource(R.drawable.tab_bangong_default);
                ivMenu2.setImageResource(R.drawable.tab_yingyong_default);
                ivMenu4.setImageResource(R.drawable.tab_xuexi);
                ivMenu5.setImageResource(R.drawable.tab_my_default);
                ivHome.setImageResource(R.drawable.tab_home_default);
                break;
            case R.id.ll_menu_5:
                if (mListener != null && mCurrentItem != 4) {
                    mCurrentItem = 4;
                    boolean b = mListener.netResult(4);
                    if (!b) {
                        return;
                    }
                }
                tvMenu1.setTextColor(Color.parseColor("#999999"));
                tvMenu2.setTextColor(Color.parseColor("#999999"));
                tvMenu4.setTextColor(Color.parseColor("#999999"));
                tvMenu5.setTextColor(Color.parseColor("#2593E7"));
                ivMenu1.setImageResource(R.drawable.tab_bangong_default);   //icon_4_home
                ivMenu2.setImageResource(R.drawable.tab_yingyong_default);  // bottom_tab_select
                ivMenu4.setImageResource(R.drawable.tab_xuexi_default);   // icon_5_home
                ivMenu5.setImageResource(R.drawable.tab_my);   //  tab_my_default
                ivHome.setImageResource(R.drawable.tab_home_default);
                break;
            case R.id.iv_home:
                if (mListener != null && mCurrentItem != 0) {
                    mCurrentItem = 0;
                    boolean b = mListener.netResult(0);
                    if (!b) {
                        return;
                    }
                }
                tvMenu1.setTextColor(Color.parseColor("#999999"));
                tvMenu2.setTextColor(Color.parseColor("#999999"));
                tvMenu4.setTextColor(Color.parseColor("#999999"));
                tvMenu5.setTextColor(Color.parseColor("#999999"));
                ivMenu1.setImageResource(R.drawable.tab_bangong_default);   //icon_4_home
                ivMenu2.setImageResource(R.drawable.tab_yingyong_default);  // bottom_tab_select
                ivMenu4.setImageResource(R.drawable.tab_xuexi_default);   // icon_5_home
                ivMenu5.setImageResource(R.drawable.tab_my_default);   //  tab_my_default
                ivHome.setImageResource(R.drawable.tab_home_pre);
                break;
        }
    }

    public void setCurrentItem(int index) {
        mCurrentItem = 3;
        tvMenu1.setTextColor(Color.parseColor("#999999"));
        tvMenu2.setTextColor(Color.parseColor("#999999"));
        tvMenu4.setTextColor(Color.parseColor("#2593E7"));
        tvMenu5.setTextColor(Color.parseColor("#999999"));
        ivMenu1.setImageResource(R.drawable.tab_bangong_default);   //icon_4_home
        ivMenu2.setImageResource(R.drawable.tab_yingyong_default);  // bottom_tab_select
        ivMenu4.setImageResource(R.drawable.tab_xuexi);   // icon_5_home
        ivMenu5.setImageResource(R.drawable.tab_my_default);   //  tab_my_default
        ivHome.setImageResource(R.drawable.tab_home_default);
    }

    public void setWurenjiClick() {
        mCurrentItem = 2;
        tvMenu1.setTextColor(Color.parseColor("#999999"));
        tvMenu2.setTextColor(Color.parseColor("#2593E7"));
        tvMenu4.setTextColor(Color.parseColor("#999999"));
        tvMenu5.setTextColor(Color.parseColor("#999999"));
        ivMenu1.setImageResource(R.drawable.tab_bangong_default);   //icon_4_home
        ivMenu2.setImageResource(R.drawable.tab_yingyong);  // bottom_tab_select
        ivMenu4.setImageResource(R.drawable.tab_xuexi_default);   // icon_5_home
        ivMenu5.setImageResource(R.drawable.tab_my_default);   //  tab_my_default
        ivHome.setImageResource(R.drawable.tab_home_default);
    }

    public interface OnMenuClickListener {
        boolean netResult(int menu);
    }

    private OnMenuClickListener mListener;

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        this.mListener = listener;
    }
}
