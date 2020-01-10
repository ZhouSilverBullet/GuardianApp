package com.sdxxtop.guardianapp.ui.activity.kaoqin

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource
import com.amap.api.maps.model.*
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.base.BaseActivity
import com.sdxxtop.guardianapp.model.bean.CheckTrajectoryBean
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback
import com.sdxxtop.guardianapp.model.http.net.Params
import com.sdxxtop.guardianapp.model.http.net.RetrofitHelper
import com.sdxxtop.guardianapp.model.http.util.RxUtils
import com.sdxxtop.guardianapp.ui.adapter.PatrolMapAdapter
import kotlinx.android.synthetic.main.activity_show_xljl.*
import java.util.*

class ShowXljlActivity : BaseActivity(), LocationSource, AMap.OnMapLoadedListener, AMapLocationListener {
    private var date = ""
    private var aMap: AMap? = null
    private var mListener: LocationSource.OnLocationChangedListener? = null
    private var mlocationClient: AMapLocationClient? = null
    //定位获取到的临时经纬度
    private var curLatlng: LatLng? = null
    private val polylines: MutableList<Polyline> = LinkedList()

    override fun getLayout() = R.layout.activity_show_xljl

    override fun initVariables() {
        if (intent != null) {
            date = intent.getStringExtra("date") ?: ""
        }
    }

    override fun initView() {
        aMap = mapView.map
        setUpMap()
    }

    /**
     * 设置一些amap的属性
     */
    private fun setUpMap() {
        aMap?.uiSettings?.isZoomControlsEnabled = false
        // 设置定位监听
        aMap?.setLocationSource(this)
        //定位中心点移动
        mapView.post { aMap?.setPointToCenter(mapView.measuredWidth / 2, 250) }
        aMap?.setOnMapLoadedListener(this)
        aMap?.setOnCameraChangeListener(object : AMap.OnCameraChangeListener {
            override fun onCameraChange(cameraPosition: CameraPosition) {}
            override fun onCameraChangeFinish(cameraPosition: CameraPosition) {
//                if (!isMoveCamera) {
//                    isMoveCamera = true
//                    AsyncTask.THREAD_POOL_EXECUTOR.execute { moveToPath() }
//                }
            }
        })
        // 设置默认定位按钮是否显示
        aMap?.uiSettings?.isMyLocationButtonEnabled = false
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap?.isMyLocationEnabled = true
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.showMyLocation(false)
        myLocationStyle.interval(2000)
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(resources, R.drawable.map_1)))
        myLocationStyle.strokeColor(Color.TRANSPARENT)
        myLocationStyle.radiusFillColor(Color.TRANSPARENT)
        aMap?.myLocationStyle = myLocationStyle
        aMap?.setMyLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        aMap?.setInfoWindowAdapter(PatrolMapAdapter())
    }


    override fun activate(onLocationChangedListener: LocationSource.OnLocationChangedListener?) {
        mListener = onLocationChangedListener
        if (mlocationClient == null && mContext != null) {
            mlocationClient = AMapLocationClient(mContext)
            val mLocationOption = AMapLocationClientOption()
            //设置定位监听
            mlocationClient?.setLocationListener(this)
            //设置为高精度定位模式
            mLocationOption.isOnceLocation = true
            mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            //设置定位参数
            mlocationClient?.setLocationOption(mLocationOption)
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient?.startLocation()
        }
    }

    override fun onMapLoaded() {
    }

    /**
     * 定位发生改变的回调
     */
    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.errorCode == 0) {
                mListener!!.onLocationChanged(aMapLocation)
                curLatlng = LatLng(aMapLocation.latitude, aMapLocation.longitude)
                //此时数据已经回来了，且为空就定位开始
                moveToCamera()
            }
        }
    }

    override fun initData() {
        val params = Params()
        params.put("de", date)
        val observable = RetrofitHelper.getEnvirApi().checkTrajectory(params.data)
        val disposable = RxUtils.handleDataHttp(observable, object : IRequestCallback<CheckTrajectoryBean?> {
            override fun onSuccess(bean: CheckTrajectoryBean?) {
                drawMapTrack(bean?.longitude)
            }

            override fun onFailure(code: Int, error: String) {}
        })
    }

    fun drawMapTrack(trackList: MutableList<CheckTrajectoryBean.LongitudeBean>?) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute {
            if (trackList != null && trackList.isNotEmpty()) {
                drawTrackOnMap(trackList)
            }
        }
    }

    /**
     * 将绘制轨迹
     */
    private fun drawTrackOnMap(points: List<CheckTrajectoryBean.LongitudeBean>) {
        if (points.size < 2) return
        val boundsBuilder = LatLngBounds.Builder()
        val polylineOptions = PolylineOptions()
        polylineOptions.color(Color.BLUE).width(10f)
        if (points.isNotEmpty()) { // 起点
            val pointBean = points[0]
            val markerOptions = MarkerOptions()
                    .position(pointBean.getLatLng())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            mapView.map.addMarker(markerOptions)
        }
        if (points.size > 1) { // 终点
            val pointBean = points[points.size - 1]
            val markerOptions = MarkerOptions()
                    .position(pointBean.getLatLng())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            mapView.map.addMarker(markerOptions)
        }
        for (pointBean in points) {
            polylineOptions.add(pointBean.getLatLng())
            boundsBuilder.include(pointBean.getLatLng())
        }
        val polyline = mapView.map.addPolyline(polylineOptions)
        polylines.add(polyline)
        //            mapView.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 30));
        if (points.isNotEmpty()) {
            aMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(points[0].getLatLng(), 14f))
        }
    }

    /**
     * 当没有数据的时候，定位到当前位置
     */
    @Synchronized
    private fun moveToCamera() {
        aMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        //mapview 修改一下加载时间试试
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        if (mapView != null) {
            mapView.onDestroy()
        }
        super.onDestroy()
    }

    override fun deactivate() {
    }
}
