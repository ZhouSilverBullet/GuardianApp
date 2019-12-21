package com.sdxxtop.guardianapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.permissions.RxPermissions;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.model.bean.LocationBean;
import com.sdxxtop.guardianapp.utils.FileUtil;
import com.sdxxtop.guardianapp.utils.ItemDivider;
import com.sdxxtop.guardianapp.utils.PictureUtil;
import com.sdxxtop.guardianapp.utils.SpUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class AmapPoiActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener, LocationSource, AMapLocationListener,
        GeocodeSearch.OnGeocodeSearchListener {
    @BindView(R.id.dialog_search_back)
    ImageButton backBtn;
    @BindView(R.id.dialog_serach_btn_search)
    View searchBtn;
    @BindView(R.id.search_maps_bar)
    RelativeLayout searchMapsRl;
    @BindView(R.id.dialog_search_et)
    EditText searchEdit;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.activity_amap_my_location)
    View loactionBtn;
    @BindView(R.id.dialog_search_recyclerview)
    RecyclerView searchRecycler;
    @BindView(R.id.current_search_recyclerview)
    RecyclerView currentRecycler;
    @BindView(R.id.dialog_search_recyclerview_linear)
    LinearLayout recyclerLinearLayout;
    @BindView(R.id.item_amap_search_text1)
    TextView searchText1;
    @BindView(R.id.item_amap_search_title1)
    TextView searchTitle1;
    @BindView(R.id.item_amap_search_root1)
    View searchRoot1;
    @BindView(R.id.amap_img_detele)
    View edImgDelete;

    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private ArrayList<LocationBean> datas;
    private AMap aMap;

    private AMapLocationClient mlocationClient;
    private OnLocationChangedListener mListener;
    private LatLng curLatlng;
    private SearchAdapter searchAdapter;
    private boolean isItemClickAction = true;
    private LatLonPoint searchLatlonPoint;
    private GeocodeSearch geocoderSearch;

    private boolean isRecyclerItemClick;
    private SearchAdapter searchAdapter2;
    private Marker locationMarker;
    private PoiItem firstItem;
    private String skipActivityName;
    private double locationLatitude;
    private double locationLongitude;
    private RxPermissions rxPermissions;

    @Override
    protected int getLayout() {
        return R.layout.activity_amap_poi;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {
        if (getIntent() != null) {
            skipActivityName = getIntent().getStringExtra("activity");
            locationLatitude = getIntent().getDoubleExtra("location_latitude", -1);
            locationLongitude = getIntent().getDoubleExtra("location_longitude", -1);
        }
    }

    @Override
    protected void initView() {
        super.initView();

        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    toInitView();
                } else {
                    showToast("请开启定位权限再进行知点的定位功能");
                    finish(false);
                }
            }
        });
    }

    private void toInitView() {
        searchAdapter = new SearchAdapter(R.layout.item_amap_search_layout);
        searchRecycler.setLayoutManager(new LinearLayoutManager(this));
        searchRecycler.addItemDecoration(new ItemDivider().setDividerWidth(UIUtils.dip2px(1)));
        searchRecycler.setAdapter(searchAdapter);

        searchAdapter2 = new SearchAdapter(R.layout.item_amap_search_layout);
        currentRecycler.setLayoutManager(new LinearLayoutManager(this));
        currentRecycler.addItemDecoration(new ItemDivider().setDividerWidth(UIUtils.dip2px(1)));
        currentRecycler.setAdapter(searchAdapter2);
        init();
    }

    private boolean isFirst;

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if (cameraPosition != null) {

                }
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
//                if (isItemClickAction) {

                if (locationLatitude != -1 && locationLongitude != -1 && !isFirst) {
                    searchLatlonPoint = new LatLonPoint(locationLatitude, locationLongitude);
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationLatitude, locationLongitude), 16f));
                } else {
                    searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                }
                geoAddress();
                startJumpAnimation();
                isItemClickAction = false;
//                }
            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
//                if (locationLatitude != -1 && locationLongitude != -1) {
//                    LatLng locationLatLng = new LatLng(locationLatitude, locationLongitude);
//                    addMarkerInScreenCenter(locationLatLng);
//                } else {
                addMarkerInScreenCenter(null);
//                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLocationPoi();
            }
        });

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);


//        findViewById(R.id.demo_click).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
//                    @Override
//                    public void onMapScreenShot(Bitmap bitmap) {
//
//                    }
//
//                    @Override
//                    public void onMapScreenShot(Bitmap bitmap, int i) {
//                        saveMapScreenShot(bitmap);
//                    }
//                });
//            }
//        });
    }

    /**
     * 响应逆地理编码
     */
    public void geoAddress() {
        if (searchLatlonPoint != null) {
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(searchLatlonPoint, 1000, GeocodeSearch.AMAP);
            geocoderSearch.getFromLocationAsyn(query);
        }
    }

    /**
     * 标志柱子
     *
     * @param locationLatLng
     */
    private void addMarkerInScreenCenter(LatLng locationLatLng) {
        if (locationLatLng == null) {
            locationLatLng = aMap.getCameraPosition().target;
        }
        Point screenPosition = aMap.getProjection().toScreenLocation(locationLatLng);
        locationMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        locationMarker.setZIndex(1);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.getUiSettings().setZoomControlsEnabled(false);
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {

        if (locationMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = locationMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= UIUtils.dip2px(125);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            locationMarker.setAnimation(animation);
            //开始动画
            locationMarker.startAnimation();

            locationMarker.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart() {

                }

                @Override
                public void onAnimationEnd() {
                    isFirst = true;
                }
            });

        } else {
            Log.e("ama", "screenMarker is null");
        }
    }


    /**
     * 用于返回的,或者结束的时候,提示上个页面loadingDialog hide
     *
     * @param isBackData 是否 有返回intent 这个值结束的
     */
    public void finish(boolean isBackData) {
        if (!isBackData) {
            setResult(100);
        }
        super.finish();
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textChangeSearch(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setSearchRecyclerVisible();

                if (TextUtils.isEmpty(editable)) {
                    edImgDelete.setVisibility(View.GONE);
                } else {
                    if (searchEdit.isFocused()) {
                        edImgDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        edImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEdit.setText("");
            }
        });

        loactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(false);
            }
        });

    }

    /**
     * 移动图标到对应的经纬度
     */
    private void location() {
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(curLatlng));
    }

    private void searchLocationPoi() {
        String searchValue = searchEdit.getText().toString().trim();
        if (TextUtils.isEmpty(searchValue)) {
            showToast("内容为空!");
        } else {
            query = new PoiSearch.Query(searchValue, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
            query.setPageSize(50);// 设置每页最多返回多少条poiitem
            query.setPageNum(0);// 设置查第一页
            query.setCityLimit(true);
            poiSearch = new PoiSearch(this, query);
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 1000, true));
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn();
        }

        geoAddress();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && recyclerLinearLayout.getVisibility() == View.VISIBLE) {
            recyclerLinearLayout.setVisibility(View.GONE);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 监听edittext内容的变化,去搜索
     */
    private void textChangeSearch(CharSequence charSequence) {
        String content = charSequence.toString().trim();//获取自动提示输入框的内容
        InputtipsQuery inputtipsQuery = new InputtipsQuery(content, "");//初始化一个输入提示搜索对象，并传入参数
        inputtipsQuery.setCityLimit(true);
        Inputtips inputtips = new Inputtips(this, inputtipsQuery);//定义一个输入提示对象，传入当前上下文和搜索对象
        inputtips.setInputtipsListener(new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> list, int errcode) {
                if (errcode == AMapException.CODE_AMAP_SUCCESS && list != null && !isRecyclerItemClick) {
                    datas = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        LocationBean locationBean = new LocationBean();
                        Tip tip = list.get(i);
                        if (tip.getPoiID() != null && tip.getPoint() != null) {
                            locationBean.latitude = tip.getPoint().getLatitude();
                            locationBean.longitude = tip.getPoint().getLongitude();
                            locationBean.snippet = tip.getName();
                            locationBean.title = tip.getDistrict();
                            locationBean.address = tip.getAddress();
                            datas.add(locationBean);
                        }
                    }

                    if (datas.size() == 0) {
                        showToast("无搜索结果");
                    }
//                    searchCarAdapter.setNewData(datas);
                    searchAdapter.replaceData(datas);
//                    searchAdapter2.replaceData(datas);

                    setSearchRecyclerVisible();
                }
                hideLoadingDialog();
                isRecyclerItemClick = false;
            }
        });//设置输入提示查询的监听，实现输入提示的监听方法onGetInputtips()
        inputtips.requestInputtipsAsyn();//输入查询提示的异步接口实现
    }

    private void setSearchRecyclerVisible() {
        if (TextUtils.isEmpty(searchEdit.getText().toString())) {
            recyclerLinearLayout.setVisibility(View.GONE);
            return;
        }
        if (searchAdapter.getData().size() != 0) {
            recyclerLinearLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerLinearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int errCode) {
        String tempSnippet = "";
        if (tempLocationBean != null) {
            tempSnippet = tempLocationBean.snippet;
        }
        if (errCode == 1000 && !isRecyclerItemClick) {
            poiResult.getSearchSuggestionKeywords();
            poiResult.getSearchSuggestionCitys();
            datas = new ArrayList<>();
            ArrayList<PoiItem> pois = poiResult.getPois();
            for (int i = 0; i < pois.size(); i++) {
                LocationBean locationBean = new LocationBean();
                PoiItem poiItem = pois.get(i);
                locationBean.title = poiItem.getTitle();
                locationBean.snippet = poiItem.getSnippet();
                locationBean.address = poiItem.getAdName();
                if (poiItem.getLatLonPoint() != null && !tempSnippet.equals(locationBean.snippet)) {
                    locationBean.latitude = poiItem.getLatLonPoint().getLatitude();
                    locationBean.longitude = poiItem.getLatLonPoint().getLongitude();
                    if (datas.size() == 0 && tempLocationBean == null) {
                        tempLocationBean = locationBean;
                    } else {
                        datas.add(locationBean);
                    }
                }
            }
//            handleFirstItem(tempLocationBean);
//            searchAdapter.replaceData(new ArrayList<LocationBean>());
//            searchAdapter2.replaceData(datas);
            if (!isDoSearchQuery) {
                setSearchRecyclerVisible();
            }
            isDoSearchQuery = false;
        }
        hideLoadingDialog();
        isRecyclerItemClick = false;
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int errCode) {
        Log.e("onPoiItemSearched", "go");
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
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
//            searchResultAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void deactivate() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
                curLatlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                searchLatlonPoint = new LatLonPoint(curLatlng.latitude, curLatlng.longitude);
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
//                isInputKeySearch = false;
//                searchText.setText("");
//                searchResultAdapter.notifyDataSetChanged();
            } else {
                UIUtils.showToast("定位失败");
                hideLoadingDialog();
            }
        }
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            RegeocodeAddress regeocodeAddress = result.getRegeocodeAddress();
            if (result != null && regeocodeAddress != null
                    && regeocodeAddress.getFormatAddress() != null) {
                String address = regeocodeAddress.getProvince() + regeocodeAddress.getCity() + regeocodeAddress.getDistrict() + regeocodeAddress.getTownship();
                firstItem = new PoiItem("regeo", searchLatlonPoint, address, address);
                LocationBean bean = new LocationBean();
                bean.latitude = firstItem.getLatLonPoint().getLatitude();
                bean.longitude = firstItem.getLatLonPoint().getLongitude();


                String formatAddress = regeocodeAddress.getFormatAddress();
                String[] range;
                if (formatAddress.contains("区")) {
                    range = formatAddress.split("区");
                    range[0] = range[0] + "区";
                } else if (formatAddress.contains("市")) {
                    range = formatAddress.split("市");
                    range[0] = range[0] + "市";
                } else if (formatAddress.contains("省")) {
                    range = formatAddress.split("省");
                    range[0] = range[0] + "省";
                } else {
                    range = formatAddress.split("");
                }
//                if (reocode.formattedAddress containsString:@"区") {
//                    range = [reocode.formattedAddress rangeOfString:@"区"];
//                } else if ([reocode.formattedAddress containsString:@"市"]) {
//                    range = [reocode.formattedAddress rangeOfString:@"市"];
//                } else if ([reocode.formattedAddress containsString:@"省"]) {
//                    range = [reocode.formattedAddress rangeOfString:@"省"];
//                } else {
//                    range = NSMakeRange(0, 3);
//                }

                String tempAddress = "";
                if (range.length >= 2) {
                    bean.snippet = range[1];
                    tempAddress = range[0];
                } else {
                    bean.snippet = range[0];
                    tempAddress = range[0];
                }
                bean.title = tempAddress;
                handleFirstItem(bean);

                List<PoiItem> pois = regeocodeAddress.getPois();
                datas = new ArrayList<>();
                for (int i = 0; i < pois.size(); i++) {
                    LocationBean locationBean = new LocationBean();
                    PoiItem poiItem = pois.get(i);
                    locationBean.title = poiItem.getTitle();
                    locationBean.snippet = poiItem.getSnippet();
                    locationBean.address = tempAddress;
                    if (poiItem.getLatLonPoint() != null) {
                        locationBean.latitude = poiItem.getLatLonPoint().getLatitude();
                        locationBean.longitude = poiItem.getLatLonPoint().getLongitude();
                        datas.add(locationBean);
                    }
                }
                handleFirstItem(tempLocationBean);
                searchAdapter.replaceData(new ArrayList<LocationBean>());
                searchAdapter2.replaceData(datas);
                hideLoadingDialog();
//                //第一次加载这个firstItem
//                if (firstItem.getLatLonPoint() != null && TextUtils.isEmpty(searchTitle1.getText().toString())) {
//                    bean.latitude = firstItem.getLatLonPoint().getLatitude();
//                    bean.longitude = firstItem.getLatLonPoint().getLongitude();
//                    bean.title = result.getRegeocodeAddress().getTownship();
//                    bean.snippet = firstItem.getSnippet();
//                }

//                doSearchQuery();
            }
        } else {
            UIUtils.showToast("error code is " + rCode);
        }
    }

    private LocationBean tempLocationBean;

    public void handleFirstItem(LocationBean bean) {
        if (bean != null) {
            searchTitle1.setText(bean.snippet);
            tempLocationBean = bean;
        }

        searchRoot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempLocationBean == null) {
                    return;
                }

                if (TextUtils.isEmpty(skipActivityName)) {
                    Intent intentFirst = new Intent();
                    intentFirst.putExtra("ar", tempLocationBean.title);
                    intentFirst.putExtra("ad", tempLocationBean.snippet);
                    double longitude = tempLocationBean.longitude;
                    double latitude = tempLocationBean.latitude;
                    String value = longitude + "," + latitude;
                    intentFirst.putExtra("lt", value);
                    intentFirst.putExtra("longitude", longitude);
                    intentFirst.putExtra("latitude", latitude);
                    SpUtil.putString(Constants.COMPANY_JIN_WEIDU, value);
                    setResult(10087, intentFirst);
                    finish(true);

                } else {
                    showLoadingDialog();
                    aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
                        @Override
                        public void onMapScreenShot(Bitmap bitmap) {

                        }

                        @Override
                        public void onMapScreenShot(Bitmap bitmap, int i) {
                            saveMapScreenShot(bitmap);
                        }
                    });
                }
            }
        });
    }

    private void saveMapScreenShot(Bitmap bitmap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            // 保存在SD卡根目录下，图片为png格式。
            Bitmap cropBitmap = PictureUtil.centerSquareScaleBitmap(bitmap, UIUtils.dip2px(300));
            String filename = "zhidian_" + sdf.format(new Date()) + ".png";
            String file = FileUtil.createFile(cropBitmap, filename);
            if (TextUtils.isEmpty(file)) {
//                Toast.makeText(this, "截屏失败", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(this, "截屏成功", Toast.LENGTH_LONG).show();
            }
            hideLoadingDialog();

            Intent intentFirst = new Intent();
            intentFirst.putExtra("ar", tempLocationBean.title);
            intentFirst.putExtra("ad", tempLocationBean.snippet);
            double longitude = tempLocationBean.longitude;
            double latitude = tempLocationBean.latitude;
            String value = longitude + "," + latitude;
            intentFirst.putExtra("lt", value);
            intentFirst.putExtra("longitude", longitude);
            intentFirst.putExtra("latitude", latitude);

            String cacheFilePath = FileUtil.getCacheFilePath(filename);

            intentFirst.putExtra("image_url", cacheFilePath);
            SpUtil.putString(Constants.COMPANY_JIN_WEIDU, value);
            setResult(10087, intentFirst);
            finish(true);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            hideLoadingDialog();
        }
    }

    /**
     * 开始进行poi搜索
     */
    int currentPage;
    boolean isDoSearchQuery;

    protected void doSearchQuery() {
        currentPage = 0;
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query("", "", "");
        query.setCityLimit(true);
        query.setPageSize(40);
        query.setPageNum(currentPage);

        if (searchLatlonPoint != null) {
            isDoSearchQuery = true;
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 1000, true));
            poiSearch.searchPOIAsyn();
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    class SearchAdapter extends BaseQuickAdapter<LocationBean, BaseViewHolder> {

        double latitudeTemp;
        double longitudeTemp;

        public SearchAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final LocationBean item) {
            String snippet = item.snippet;
            String title = item.title;
            TextView searchText = helper.getView(R.id.item_amap_search_text);
            TextView searchTitle = helper.getView(R.id.item_amap_search_title);
            searchText.setText(snippet);
            searchTitle.setText(title);

            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double latitude = item.latitude;
                    double longitude = item.longitude;
                    if (latitudeTemp == latitude && longitudeTemp == longitude) {
                        showToast("已定位到选中位置");
                        return;
                    } else {
                        showLoadingDialog();
                        latitudeTemp = latitude;
                        longitudeTemp = longitude;
                    }


                    searchLatlonPoint = new LatLonPoint(latitude, longitude);
                    LatLng curLatlng = new LatLng(latitude, longitude);
                    isItemClickAction = true;
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
//                    searchEdit.setText(item.snippet);

//                    handleFirstItem(item);
//                    isRecyclerItemClick = true;

                    Intent intentFirst = new Intent();
                    intentFirst.putExtra("ar", item.title);
                    intentFirst.putExtra("ad", item.snippet);
                    double longitudeRes = item.longitude;
                    double latitudeRes = item.latitude;
                    String value = longitudeRes + "," + latitudeRes;
                    intentFirst.putExtra("lt", value);
                    intentFirst.putExtra("longitude", longitude);
                    intentFirst.putExtra("latitude", latitude);
                    SpUtil.putString(Constants.COMPANY_JIN_WEIDU, value);
                    setResult(10087, intentFirst);
                    finish(true);

                    recyclerLinearLayout.setVisibility(View.GONE);
                }
            });
        }
    }
}

