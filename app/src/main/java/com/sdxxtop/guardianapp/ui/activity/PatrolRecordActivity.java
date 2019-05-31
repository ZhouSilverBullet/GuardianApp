package com.sdxxtop.guardianapp.ui.activity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.SignLogBean;
import com.sdxxtop.guardianapp.presenter.PatrolPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PatrolContract;
import com.sdxxtop.guardianapp.ui.adapter.PatrolMapAdapter;
import com.sdxxtop.guardianapp.ui.pop.StatSelectionDateWindow;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;
import com.sdxxtop.guardianapp.ui.widget.calendarSelect.BottomDialogView;
import com.sdxxtop.guardianapp.utils.Date2Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;

public class PatrolRecordActivity extends BaseMvpActivity<PatrolPresenter> implements PatrolContract.IView, AMapLocationListener, LocationSource, AMap
        .OnMapLoadedListener {

    @BindView(R.id.tatv_date)
    TextAndTextView tatvDate;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.v_half_bg)
    View vHalfBg;

    private AMap aMap;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private List<SignLogBean.SignBean> curLatlngList = new ArrayList<>();
    private boolean isMoveCamera;
    //如果 map 已经加载完成，这样的话就可以画地图了
    private boolean isMapLoad;
    //网络加载完成的数据标识
    private boolean isInternetDataLoad;
    private Polyline polyline;
    private String todayDate;

    //是否定位
    private boolean isPositionLoadFinish;
    //下载数据为空
    private boolean isLoadEmpty;
    private BottomDialogView dialogView;

    @Override
    protected int getLayout() {
        return R.layout.activity_patrol_record;
    }

    @Override
    protected void initView() {
        super.initView();

        aMap = mapView.getMap();
        setUpMap();

        tatvDate.setTextRightImage2Show(true);
        tatvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogView!=null){
                    dialogView.show();
                }else {
                    dialogView = new BottomDialogView(PatrolRecordActivity.this);
                    dialogView.setConfirmClickListener(new BottomDialogView.onConfirmClick() {
                        @Override
                        public void onClick(String time) {
                            tatvDate.getTextRightText().setText(time);
                            mPresenter.loadData(time);
                        }
                    });
                    dialogView.show();
                }
            }
        });
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.getUiSettings().setZoomControlsEnabled(false);
        // 设置定位监听
        aMap.setLocationSource(this);
        //定位中心点移动
        mapView.post(new Runnable() {
            @Override
            public void run() {
                aMap.setPointToCenter(mapView.getMeasuredWidth() / 2, 250);
            }
        });
        aMap.setOnMapLoadedListener(this);
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (!isMoveCamera) {
                    isMoveCamera = true;
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                        @Override
                        public void run() {
                            moveToPath();
                        }
                    });
                }
            }
        });
        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(false);
        myLocationStyle.interval(2000);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.map_1)));
        myLocationStyle.strokeColor(Color.TRANSPARENT);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);

        aMap.setInfoWindowAdapter(new PatrolMapAdapter());
    }

    private void moveToPath() {
        Map<String,SignLogBean.SignBean> tempList = new HashMap<>();

        if (curLatlngList != null && curLatlngList.size() > 0) {
            for (SignLogBean.SignBean signBean : curLatlngList) {
                tempList.put(signBean.getAddress(), signBean);
            }

            double size = tempList.size();
            double latitude = 0;
            double longitude = 0;
            for (String s : tempList.keySet()) {
                SignLogBean.SignBean signBean = tempList.get(s);
                LatLng lng = signBean.getLatLng();
                latitude += lng.latitude;
                longitude += lng.longitude;
//                if (i == curLatlngList.size() - 1) {
                getadress(lng, signBean.getAddress());
            }

            final double finalLatitude = latitude;
            final double finalLongitude = longitude;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    LatLng centerLatLng = new LatLng(finalLatitude / size, finalLongitude / size);

                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLatLng, 16f));
                }
            });

        }
    }

    //解析指定坐标的地址
    public void getadress(LatLng latLng, String address) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(mContext);//地址查询器

        //设置查询参数,
        //三个参数依次为坐标，范围多少米，坐标系
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 200, GeocodeSearch.AMAP);

        //设置查询结果监听
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            //根据坐标获取地址信息调用
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                String s = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                double latitude = regeocodeResult.getRegeocodeQuery().getPoint().getLatitude();
                double longitude = regeocodeResult.getRegeocodeQuery().getPoint().getLongitude();
                makepoint(latitude, longitude, address);
            }

            //根据地址获取坐标信息是调用
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
            }
        });

        geocodeSearch.getFromLocationAsyn(regeocodeQuery);//发起异步查询请求
    }

    //https://blog.csdn.net/qq_34536167/article/details/79328861 自定义气泡
    //https://www.jianshu.com/p/f6e2cd079a9b
    //做标记
    private void makepoint(double latitude, double longitude, String s) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(latitude, longitude)).title("").snippet(s);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.map_1)));//最后一个点设置成小蓝点
        aMap.addMarker(markerOptions);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null && mContext != null) {
            mlocationClient = new AMapLocationClient(mContext);
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {

    }

    //定位获取到的临时经纬度
    private LatLng curLatlng;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);

                curLatlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                //加载完毕定位
                isPositionLoadFinish = true;
                //此时数据已经回来了，且为空就定位开始
                if (isLoadEmpty) {
                    moveToCamera();
                }

//                moveToPath();
//                drawMapLine();
            } else {
//                showToast("定位失败");
            }
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void onMapLoaded() {
        if (isInternetDataLoad) {
            drawMapLine();
        }
        isMapLoad = true;
    }

    //地图上面画线
    private void drawMapLine() {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                if (curLatlngList != null && curLatlngList.size() > 0) {
                    // 获取轨迹坐标点
                    PolylineOptions polt = new PolylineOptions();
                    for (int i = 0; i < curLatlngList.size(); i++) {
                        polt.add(curLatlngList.get(i).getLatLng());
                    }
                    polt.width(10).geodesic(false).color(Color.parseColor("#EB6100"));
                    polyline = aMap.addPolyline(polt);
                }
                moveToPath();
            }
        });

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showData(SignLogBean signLogBean) {
//        ExerciseBean.DataBean data = bean.getData();

        String distance = signLogBean.getDistance();

//        int num = signLogBean.getNum();
//        tvPosition.setText("共" + distance + "米 打卡" + num + "次");
        tvPosition.setText("共" + distance + "米");

        //每次请求刷新一次
        if (curLatlngList.size() > 0) {
            curLatlngList.clear();
        }

        List<SignLogBean.SignBean> signBeanList = signLogBean.getSign();
        if (signBeanList != null) {
            curLatlngList.addAll(signBeanList);

            if (signBeanList.size() == 0) {
                isLoadEmpty = true;
                if (isPositionLoadFinish) {
                    moveToCamera();
                }
            }
        }

        aMap.clear();

        isInternetDataLoad = true;
        if (isMapLoad) {
            drawMapLine();
        }


//        if (data != null) {
//            addressList = data.getAddress();
//            if (addressList != null && addressList.size() > 0) {
//                sportAddressAdapter.replaceData(addressList);
//            }
//            int step_num = data.getStep_num();
//            tvStepNum.setText(step_num + "");
//            String distance = data.getDistance();
//            String cal = data.getCal();
//            tvDistance.setText(cal + "");
//            tvCal.setText(distance + "");
//
//            List<String> longitude = data.getLongitude();
//            if (longitude.size() > 0) {
//                if (curLatlngList.size() > 0) {
//                    curLatlngList.clear();
//                }
//
//                for (String longitData : longitude) {
//                    if (!TextUtils.isEmpty(longitData)) {
//                        String[] split = longitData.split(",");
//                        if (split.length > 1) {
//                            try {
//                                curLatlngList.add(new LatLng(Double.parseDouble(split[1]),
//                                        Double.parseDouble(split[0])));
//                            } catch (Exception e) {
//
//                            }
//                        }
//                    }
//                }
//
//                if (isMapLoad) {
//                    drawMapLine();
//                }
//            }
//        }
    }

    /**
     * 当没有数据的时候，定位到当前位置
     */
    private synchronized void moveToCamera() {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
        isLoadEmpty = false;
        //load只会加载一次，所以不改变状态
//        isPositionLoadFinish = false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //mapview 修改一下加载时间试试
        mapView.onResume();
        mPresenter.loadData(todayDate);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroy();
    }


    private StatSelectionDateWindow selectionDateWindow;
    private String chooseDay;

    /**
     * 选择日期的pop
     */
    private void showSelectDateWindow() {
        if (selectionDateWindow == null) {
            selectionDateWindow = new StatSelectionDateWindow(this, false, false, true);

            selectionDateWindow.setSelectorDateListener(new StatSelectionDateWindow.SelectorDateListener() {
                @Override
                public void onSelector(String date, CalendarDay calendarDay) {
                    // 请求网络
                    chooseDay = date;
                    tatvDate.getTextRightText().setText(getFormatDate(chooseDay));
//                    mPresenter.kaoqinMore(getUserID(), studentID, date);
                    mPresenter.loadData(date);
                    selectionDateWindow.dismiss();
                }
            });

            selectionDateWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    vHalfBg.setVisibility(View.GONE);
                }
            });
        }
        vHalfBg.setVisibility(View.VISIBLE);
        selectionDateWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_patrol_record, null), Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void initData() {
        super.initData();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = simpleDateFormat.format(date);
        tatvDate.getTextRightText().setText("今日");
    }

    public String getFormatDate(String sDate) {
        if (sDate.equals(Date2Util.getDate())) {
            return "今日";
        }
        String formatDate = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            formatDate = sdf2.format(sdf1.parse(sDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate;
    }
}
