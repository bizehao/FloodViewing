package com.bzh.floodview.module.home.homeIndex.content;

import android.content.Context;
import android.os.Handler;

import com.bzh.floodview.base.presenter.BasePresenter;
import com.bzh.floodview.base.view.BaseView;
import com.bzh.floodview.model.ApiRainInfo;
import com.bzh.floodview.model.ApiRainStInfo;
import com.bzh.floodview.model.ApiRiverInfo;
import com.bzh.floodview.model.ApiRsvrInfo;

import java.util.List;
import java.util.Map;

public interface ContentContract {

    //视图层
    interface View extends BaseView {
        //降雨
        void setRainTable(Map<String, String> map,List<ApiRainInfo.DataBean> apiRainInfo);
        //雨强
        void setRainStTable(ApiRainStInfo.DataBean apiRainSsInfo);
        //河道
        void setRiverStTable(Map<String,String> map, List<ApiRiverInfo.DataBean> apiRiverInfo);
        //水库
        void setRsvrStTable(Map<String,String> map, List<ApiRsvrInfo.DataBean> apiRsvrInfo);
    }

    //中间主持层
    interface Present extends BasePresenter<View> {
        //降雨信息
        void getRainfallInfo(String start_time, String end_time);
        //汛情降雨二级
        void getRainfallInfoXun(String rainLJ, String start_time, String end_time, Handler handler);
        //雨强信息
        void getIntensityOfRainInfo(String start_time, String end_time);

        //河道站
        void getRiverInfo(String start_time, String end_time);
        //水库站
        void getRsvrInfo(String start_time, String end_time, Handler handler);

        //汛情摘要
        void getFloodSituation(String start_time, String end_time, Handler handler);
    }
}
