package com.sdxxtop.guardianapp.ui.activity;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.luck.picture.lib.permissions.RxPermissions;
import com.orhanobut.logger.Logger;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.GridMapPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GridMapContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class GridMapActivity extends BaseMvpActivity<GridMapPresenter> implements AMap.OnMyLocationChangeListener, AMap.OnMapClickListener, GridMapContract.IView {
    private static final String TAG = "GridMapActivity";

    @BindView(R.id.gmap)
    MapView mMapView;
    private AMap aMap;
    private RxPermissions mRxPermissions;

    private boolean isFirst;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_grid_map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        showStatusBar();

    }

    @Override
    protected void initView() {
        super.initView();

        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    initMap();
                }
            }
        });
    }

    private void initMap() {
        aMap = mMapView.getMap();
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true); //设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.setOnMyLocationChangeListener(this);

        search("朝阳区");
        search("海淀区");

        aMap.setOnMapClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMyLocationChange(Location location) {
        String s = "location " + location.getLongitude() + " + " + location.getLatitude();
        Logger.e(TAG, s);
    }

    @Override
    public void showPolygon(List<LatLng> list) {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(list);
        polygonOptions.strokeWidth(15) // 多边形的边框
                .strokeColor(getResources().getColor(R.color.color_32B16C)) // 边框颜色
                .fillColor(getResources().getColor(R.color.color_32B16C));   // 多边形的填充色

        aMap.addPolygon(polygonOptions);
    }

    private void search(String keywords) {
        DistrictSearch search = new DistrictSearch(this);
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords(keywords);//传入关键字
        query.setShowBoundary(true);//是否返回边界值
        search.setQuery(query);
        search.setOnDistrictSearchListener(new DistrictSearch.OnDistrictSearchListener() {
            @Override
            public void onDistrictSearched(DistrictResult result) {
                handleDistrict(result);
            }
        });
        search.searchDistrictAsyn();

    }

    private void handleDistrict(DistrictResult result) {
        ArrayList<DistrictItem> district = result.getDistrict();
        for (DistrictItem districtItem : district) {
            String adcode = districtItem.getAdcode();
            LatLonPoint center = districtItem.getCenter();
            String citycode = districtItem.getCitycode();
            String level = districtItem.getLevel();
            String name = districtItem.getName();
            List<DistrictItem> subDistrict = districtItem.getSubDistrict();
//            for (DistrictItem item : subDistrict) {
            new Thread() {
                @Override
                public void run() {
                    parseData(districtItem);

                }
            }.start();

//            }
        }
    }

    private void parseData(DistrictItem districtItem) {
        String[] polyStr = districtItem.districtBoundary();
        if (polyStr == null || polyStr.length == 0) {
            return;
        }

        List<LatLng> list;

        for (String str : polyStr) {
            list = new ArrayList<>();
            String[] lat = str.split(";");
            boolean isFirst = true;
            LatLng firstLatLng = null;
            for (String latstr : lat) {
                String[] lats = latstr.split(",");
                if (isFirst) {
                    isFirst = false;
                    firstLatLng = new LatLng(Double
                            .parseDouble(lats[1]), Double
                            .parseDouble(lats[0]));
                }
                list.add(new LatLng(Double
                        .parseDouble(lats[1]), Double
                        .parseDouble(lats[0])));

            }
            if (firstLatLng != null) {
                list.add(firstLatLng);
            }
            show(list);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        aMap.clear();
//        double latitude=latLng.latitude;
//        double longtitude=latLng.longitude;
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_1));
//        markerOptions.position(latLng);
//        aMap.addMarker(markerOptions);
//        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
    }

    @Override
    public void showError(String error) {

    }
}
