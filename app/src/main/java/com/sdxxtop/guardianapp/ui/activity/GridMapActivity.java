package com.sdxxtop.guardianapp.ui.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.permissions.RxPermissions;
import com.orhanobut.logger.Logger;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.MainMapBean;
import com.sdxxtop.guardianapp.presenter.GridMapPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GridMapContract;
import com.sdxxtop.guardianapp.ui.adapter.GridMapAdapter;
import com.sdxxtop.guardianapp.utils.GuardianUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class GridMapActivity extends BaseMvpActivity<GridMapPresenter> implements AMap.OnMyLocationChangeListener, AMap.OnMapClickListener,
        GridMapContract.IView, AMap.OnMarkerClickListener {
    private static final String TAG = "GridMapActivity";

    @BindView(R.id.gmap)
    MapView mMapView;
    private AMap aMap;
    private RxPermissions mRxPermissions;

    private boolean isFirst;
    private GridMapAdapter mAdapter;
    private Polygon polygon;

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
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(false); //设置默认定位按钮是否显示，非必需设置。
//        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
//        aMap.setOnMyLocationChangeListener(this);

        aMap.setOnMapClickListener(this);

        mAdapter = new GridMapAdapter();
        aMap.setInfoWindowAdapter(mAdapter);
        aMap.setOnMarkerClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.postMap();
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

    public void showPolygon(int index, MainMapBean.UserBean userBean, String lanLan) {
        String middle = userBean.getMiddle();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(mContext).load(userBean.getImg()).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (!TextUtils.isEmpty(middle)) {
                            if (aMap==null)return;
                            String[] split = middle.split(",");
                            if (split.length > 1) {
                                LatLng latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                                MarkerOptions markerOptions = new MarkerOptions();
                                Bitmap bitmap = drawable2Bitmap(resource);

//                                Bitmap bm = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
//                                Log.i("GitMap", "压缩后图片的大小" + (bm.getByteCount() / 1024) + "KB宽度为"
//                                        + bm.getWidth() + "高度为" + bm.getHeight());

                                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(drawable2Bitmap(getResources().getDrawable(R.drawable.grid_map_icon))));
                                markerOptions.position(latLng).title("" + lanLan).snippet(index + ",:," + userBean.getName()+",:,"+userBean.getImg());
                                aMap.addMarker(markerOptions);
                                if (index == 0) {
                                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
                                }
//                                mAdapter.notifyAll();
                            }
                        }
                    }
                });
            }
        });


    }

    Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
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
        if (tempMarker != null && tempMarker.isInfoWindowShown()) {
            tempMarker.hideInfoWindow();
        }
    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private Marker tempMarker;

    @Override
    public boolean onMarkerClick(Marker marker) {
        tempMarker = marker;
        if (polygon!=null){
            polygon.remove();
        }
        MarkerOptions options = marker.getOptions();
        String snippet = options.getSnippet();
        int markerIndex = Integer.parseInt(snippet.split(",:,")[0]);

        setPolygont(markerIndex, options.getTitle());

        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        return true;
    }

    /*********************  地图优化  ************************/
    public void setPolygont(int index, String lat) {
        PolygonOptions  polygonOptions = new PolygonOptions();
        polygonOptions.addAll(parseData(lat));
        int parseColor = Color.parseColor(GuardianUtils.getHalfColor(index));
        polygonOptions.strokeWidth(5) // 多边形的边框
                .strokeColor(parseColor) // 边框颜色
                .fillColor(parseColor);   // 多边形的填充色
        polygon = aMap.addPolygon(polygonOptions);
    }

    @Override
    public void showMap(MainMapBean mainMapBean) {
        if (mainMapBean.getUser() != null && mainMapBean.getUser().size() > 0) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(
                    new MapRunnable(mainMapBean.getUser()));
        } else {
            showToast("暂无数据");
        }
    }

    public class MapRunnable implements Runnable {
        List<MainMapBean.UserBean> user;

        public MapRunnable(List<MainMapBean.UserBean> user) {
            this.user = user;
        }

        @Override
        public void run() {
            for (MainMapBean.UserBean userBean : user) {
                String lonLon = userBean.getLonLon();
                int i = user.indexOf(userBean);
                if (!TextUtils.isEmpty(lonLon)) {
                    showPolygon(i, userBean, lonLon);
                }
            }
        }
    }

    private List<LatLng> parseData(String lanLan) {
        List<LatLng> list = new ArrayList<>();
        if (TextUtils.isEmpty(lanLan)) {
            return list;
        }
        String[] lat = lanLan.split(";");
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
        return list;
    }
}
