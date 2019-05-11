package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.bumptech.glide.Glide;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.ui.activity.PatrolPathActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author :  lwb
 * Date: 2019/5/8
 * Desc:
 */
public class GridMarkerAdapter implements AMap.InfoWindowAdapter {

    private Context mContext;
    private LatLng latLng;
    private CircleImageView img;
    private TextView nameTV;
    private TextView tvJobs;
    private TextView tvCompany;


    private String snippet;
    private String agentName;
    private String url;
    private String company;
    private String job;
    private int reportType;

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
        nameTV = view.findViewById(R.id.name);
        tvJobs = view.findViewById(R.id.tv_jobs);
        tvCompany = view.findViewById(R.id.tv_company);
        img = view.findViewById(R.id.img);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PatrolPathActivity.class);
                intent.putExtra("id",agentName);
                intent.putExtra("reportType",reportType);
                mContext.startActivity(intent);
            }
        });

        nameTV.setText(agentName);
        tvJobs.setText(job);
        tvCompany.setText(company);
        Glide.with(mContext).load(url).into(img);
        return view;
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
                reportType = jsonObject.getInt("reportType");
                url = jsonObject.getString("url");
                agentName = jsonObject.getString("title");
                company = jsonObject.getString("company");
                job = jsonObject.getString("job");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "stu_name: 失败");
        }
    }
}
