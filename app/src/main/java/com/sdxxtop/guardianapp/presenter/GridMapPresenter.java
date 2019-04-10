package com.sdxxtop.guardianapp.presenter;


import android.content.Context;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GridMapContract;
import com.sdxxtop.guardianapp.presenter.contract.HomeContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.ColorRes;

public class GridMapPresenter extends RxPresenter<GridMapContract.IView> implements GridMapContract.IPresenter, DistrictSearch.OnDistrictSearchListener {
    @Inject
    public GridMapPresenter() {
    }


    public void search(Context context, List<GridPeople> gridPeopleList) {

        for (GridPeople gridPeople : gridPeopleList) {
            search(context, gridPeople.address);
        }
    }


    private void search(Context context,String keywords) {
        DistrictSearch search = new DistrictSearch(context);
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords(keywords);//传入关键字
        query.setShowBoundary(true);//是否返回边界值
        search.setQuery(query);
        search.setOnDistrictSearchListener(this);
        search.searchDistrictAsyn();

    }

    @Override
    public void onDistrictSearched(DistrictResult districtResult) {
        ArrayList<DistrictItem> district = districtResult.getDistrict();
        for (DistrictItem districtItem : district) {
            String adcode = districtItem.getAdcode();
            LatLonPoint center = districtItem.getCenter();
            String citycode = districtItem.getCitycode();
            String level = districtItem.getLevel();
            String name = districtItem.getName();
            List<DistrictItem> subDistrict = districtItem.getSubDistrict();
            new Thread() {
                @Override
                public void run() {
                    parseData(districtItem);

                }
            }.start();

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
            mView.showPolygon(list);
        }
    }


    static class GridPeople {
        public String name;
        public String title;
        public String address;
        public String msg;
        public @ColorRes int color;
    }

}
