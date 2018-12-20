package com.bzh.floodview.module.home.homeNews;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/12/19 13:01
 */
public abstract class MapStatusChangeListener implements BaiduMap.OnMapStatusChangeListener {
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public abstract void onMapStatusChangeFinish(MapStatus mapStatus);
}
