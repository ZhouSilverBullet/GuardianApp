package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.bumptech.glide.Glide;
import com.sdxxtop.guardianapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author :  lwb
 * Date: 2019/5/8
 * Desc:
 */
public class GridMarkerAdapter implements AMap.InfoWindowAdapter, View.OnClickListener {

    private Context mContext;
    private LatLng latLng;
    private LinearLayout call;
    private LinearLayout navigation;
    private CircleImageView img;
    private TextView nameTV;
    private TextView addrTV;
    private String snippet;
    private String agentName;
    private String url;

    public GridMarkerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        initData(marker);
        View view = initView();
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initData(Marker marker) {
        latLng = marker.getPosition();
        snippet = marker.getSnippet();
        parseJSONWithJSONObject(marker.getTitle());
    }

    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_infowindow, null);
        navigation = view.findViewById(R.id.navigation_LL);
        call = view.findViewById(R.id.call_LL);
        nameTV = view.findViewById(R.id.name);
        addrTV = view.findViewById(R.id.addr);
        img = view.findViewById(R.id.img);
        navigation.setOnClickListener(this);
        call.setOnClickListener(this);

        nameTV.setText(agentName);
        addrTV.setText(String.format("地址：%1$s", snippet));

        Glide.with(mContext).load(url).into(img);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.navigation_LL:  //点击导航
                break;

            case R.id.call_LL:  //点击打电话
                break;
        }
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            //将json字符串jsonData装入JSON数组，即JSONArray
            //jsonData可以是从文件中读取，也可以从服务器端获得
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //循环遍历，依次取出JSONObject对象
                //用getInt和getString方法取出对应键值
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                url = jsonObject.getString("url");
                agentName = jsonObject.getString("title");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "stu_name: 失败");
        }
    }
}
