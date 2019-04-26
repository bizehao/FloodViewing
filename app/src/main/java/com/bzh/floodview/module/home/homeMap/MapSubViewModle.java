package com.bzh.floodview.module.home.homeMap;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.model.mapData.ApiRainTable;
import com.bzh.floodview.model.mapData.ApiRiverTable;
import com.bzh.floodview.model.mapData.ApiRsvrTable;
import java.util.List;
import io.reactivex.Observable;


/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/22 9:15
 */
public class MapSubViewModle {

    RetrofitHelper retrofitHelper;

    public MapSubViewModle(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }

    private List<ApiRainTable> rainInfoAB; //雨情
    private List<ApiRiverTable> riverInfoAB; //河道
    private List<ApiRsvrTable> rsvrInfoAB;//水库

    private MutableLiveData<List<ApiRainTable>> rainInfo = new MutableLiveData<>(); //雨情
    private MutableLiveData<List<ApiRiverTable>> riverInfo = new MutableLiveData<>(); //河道
    private MutableLiveData<List<ApiRsvrTable>> rsvrInfo = new MutableLiveData<>();//水库

    private MutableLiveData<String> stcd = new MutableLiveData<>();//列表点击

    private MutableLiveData<Integer> loading = new MutableLiveData<>();//正在加载

    public LiveData<List<ApiRainTable>> getRainInfo() {
        return rainInfo;
    }

    public void setRainInfo(List<ApiRainTable> rainInfos) {
        rainInfo.setValue(rainInfos);
    }

    public LiveData<List<ApiRiverTable>> getRiverInfo() {
        return riverInfo;
    }

    public void setRiverInfo(List<ApiRiverTable> riverInfos) {
        riverInfo.setValue(riverInfos);
    }

    public LiveData<List<ApiRsvrTable>> getRsvrInfo() {
        return rsvrInfo;
    }

    public void setRsvrInfo(List<ApiRsvrTable> rsvrInfos) {
        rsvrInfo.setValue(rsvrInfos);
    }

    //获取表格信息
    public void getTableInfo(String stTime, String enTime, Context context) {
        Observable<BaseApi<List<ApiRsvrTable>>> observableRsvr = retrofitHelper.getServer().getRsvrTable(stTime, enTime);
        retrofitHelper.requestHandler(observableRsvr,context, new RetrofitHelper.callHandler<BaseApi<List<ApiRsvrTable>>>() {

            @Override
            public void run(BaseApi<List<ApiRsvrTable>> listBaseApi) {
                rsvrInfoAB = listBaseApi.getData();
                setRsvrInfo(rsvrInfoAB);
                setLoading(getLoading().getValue()+1);
            }
        });
        Observable<BaseApi<List<ApiRiverTable>>> observableRiver = retrofitHelper.getServer().getRiverTable(stTime, enTime);
        retrofitHelper.requestHandler(observableRiver,context, new RetrofitHelper.callHandler<BaseApi<List<ApiRiverTable>>>() {
            @Override
            public void run(BaseApi<List<ApiRiverTable>> apiRiverTableBaseApi) {
                riverInfoAB = apiRiverTableBaseApi.getData();
                setRiverInfo(riverInfoAB);
                setLoading(getLoading().getValue()+1);
            }
        });
        Observable<BaseApi<List<ApiRainTable>>> observableRain = retrofitHelper.getServer().getRainTable(stTime, enTime);
        retrofitHelper.requestHandler(observableRain,context, new RetrofitHelper.callHandler<BaseApi<List<ApiRainTable>>>() {
            @Override
            public void run(BaseApi<List<ApiRainTable>> listBaseApi) {
                rainInfoAB = listBaseApi.getData();
                setRainInfo(rainInfoAB);
                setLoading(getLoading().getValue()+1);
            }
        });
    }


    public List<ApiRainTable> getRainInfoAB() {
        return rainInfoAB;
    }

    public List<ApiRiverTable> getRiverInfoAB() {
        return riverInfoAB;
    }

    public List<ApiRsvrTable> getRsvrInfoAB() {
        return rsvrInfoAB;
    }

    public LiveData<String> getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd.setValue(stcd);
    }

    public LiveData<Integer> getLoading() {
        return loading;
    }

    public void setLoading(Integer loading) {
        this.loading.setValue(loading);
    }
}
