package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.model.bean.KqstDayBean;
import com.sdxxtop.guardianapp.model.bean.RecordDetailBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.UcenterIndexBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.ui.activity.MyFaceLivenessActivity;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.KQST_Adapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.utils.AMapFindLocation2;
import com.sdxxtop.guardianapp.utils.GpsUtils;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static com.sdxxtop.guardianapp.model.http.net.RetrofitHelper.getEnvirApi;

public class ClockInActivity extends BaseActivity {

    @BindView(R.id.civ_header)
    CircleImageView civHeader;
    @BindView(R.id.tvPosition)
    TextView tvPosition;
    @BindView(R.id.tvPart)
    TextView tvPart;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private boolean isClock;  // 是否有打卡权限
    private boolean isFace;   // 是否绑定人脸
    private KQST_Adapter adapter;
    private List<KqstDayBean.SignLogBean> list = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_clock_in;
    }

    @Override
    protected void initVariables() {
        if (getIntent() != null) {
            isClock = getIntent().getIntExtra("isClock", 0) == 1;
            isFace = getIntent().getIntExtra("isFace", 0) == 1;
        }
    }

    @OnClick({R.id.tvDate, R.id.textClock})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.textClock:
                toFaceDaka(this);
                break;
            case R.id.tvDate:
                startActivity(new Intent(this, MineAttendanceActivity.class));
                break;
        }
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new KQST_Adapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        Params params = new Params();
        Observable<RequestBean<UcenterIndexBean>> observable = getEnvirApi().postUcenterIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<UcenterIndexBean>() {
            @Override
            public void onSuccess(UcenterIndexBean bean) {
                String img = bean.img;
                if (!TextUtils.isEmpty(img)) {
                    SpUtil.putString(Constants.USER_IMG, img);
                    Glide.with(ClockInActivity.this).load(img).into(civHeader);
                }
                tvPosition.setText(bean.name);
                tvPart.setText(new StringBuilder().append(bean.part_name).append(" ").append(bean.getStringPosition()));
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        Params params = new Params();
        Observable<RequestBean<RecordDetailBean>> observable = getEnvirApi().getRecordDetail(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<RecordDetailBean>() {
            @Override
            public void onSuccess(RecordDetailBean bean) {
                isClock = bean.is_clock == 1;
                isFace = bean.is_face == 1;
                list.clear();
                for (RecordDetailBean.ListsBean item : bean.lists) {
                    KqstDayBean.SignLogBean itemBean = new KqstDayBean.SignLogBean();
                    itemBean.sign_name = item.sign_name;
                    itemBean.sys_date = item.sign_time;
                    if (item.sign_info != null) {
                        itemBean.status = item.sign_info.status;
                        itemBean.address = item.sign_info.address;
                        itemBean.sign_time = item.sign_info.sign_time;
                    }
                    list.add(itemBean);
                }
                adapter.replaceData(list);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
    }

    /**
     * 去打卡
     *
     * @param context
     */
    private void toFaceDaka(Context context) {
        //判断一次打卡，gps是否打开
        if (isClock) {
            if (isFace) {
                showLoadingDialog();
                if (GpsUtils.isOPen(context)) {
                    AMapFindLocation2 instance = AMapFindLocation2.getInstance();
                    instance.location();
                    instance.setLocationCompanyListener(new AMapFindLocation2.LocationCompanyListener() {
                        @Override
                        public void onAddress(AMapLocation aMapLocation) {
                            hideLoadingDialog();

                            String address = aMapLocation.getAddress();
                            if (TextUtils.isEmpty(address)) {
                                UIUtils.showToast("定位获取位置失败,请稍后重试");
                            } else {
                                instance.stopLocation();
                                Intent intent = new Intent(context, MyFaceLivenessActivity.class);
                                intent.putExtra("isFace", true);
                                intent.putExtra("address", address);
                                context.startActivity(intent);
                            }
                        }
                    });
                } else {
                    hideLoadingDialog();
                    GpsUtils.showCode332ErrorDialog(context);
                }
            } else {
                toFace(context);
            }
        } else {
            showToast("暂无权限");
        }
    }

    /**
     * 人脸的弹窗
     *
     * @param context
     */
    private void toFace(Context context) {
        new IosAlertDialog(context)
                .builder()
                .setTitle("提示")
                .setMsg("您还没注册人脸信息，是否去录入")
                .setPositiveButton("录入", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MyFaceLivenessActivity.class);
                        intent.putExtra("isFace", false);
                        startActivityForResult(intent, 100);
                    }
                })
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }
}
