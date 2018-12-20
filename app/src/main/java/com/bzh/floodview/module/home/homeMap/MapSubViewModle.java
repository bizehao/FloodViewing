package com.bzh.floodview.module.home.homeMap;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.bzh.floodview.api.RetrofitHelper;
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

    private List<ApiRainTable.DataBean> rainInfoAB; //雨情
    private List<ApiRiverTable.DataBean> riverInfoAB; //河道
    private List<ApiRsvrTable.DataBean> rsvrInfoAB;//水库

    private MutableLiveData<List<ApiRainTable.DataBean>> rainInfo = new MutableLiveData<>(); //雨情
    private MutableLiveData<List<ApiRiverTable.DataBean>> riverInfo = new MutableLiveData<>(); //河道
    private MutableLiveData<List<ApiRsvrTable.DataBean>> rsvrInfo = new MutableLiveData<>();//水库

    private MutableLiveData<String> stcd = new MutableLiveData<>();//列表点击

    private MutableLiveData<Integer> loading = new MutableLiveData<>();//正在加载

    public LiveData<List<ApiRainTable.DataBean>> getRainInfo() {
        return rainInfo;
    }

    public void setRainInfo(List<ApiRainTable.DataBean> rainInfos) {
        rainInfo.setValue(rainInfos);
    }

    public LiveData<List<ApiRiverTable.DataBean>> getRiverInfo() {
        return riverInfo;
    }

    public void setRiverInfo(List<ApiRiverTable.DataBean> riverInfos) {
        riverInfo.setValue(riverInfos);
    }

    public LiveData<List<ApiRsvrTable.DataBean>> getRsvrInfo() {
        return rsvrInfo;
    }

    public void setRsvrInfo(List<ApiRsvrTable.DataBean> rsvrInfos) {
        rsvrInfo.setValue(rsvrInfos);
    }

    //获取表格信息
    public void getTableInfo(String stTime, String enTime) {
        Observable<ApiRsvrTable> observableRsvr = retrofitHelper.getServer().getRsvrTable(stTime, enTime);
        retrofitHelper.successHandler(observableRsvr, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRsvrTable rsvrTable = (ApiRsvrTable) t;
                rsvrInfoAB = rsvrTable.getData();
                setRsvrInfo(rsvrTable.getData());
                setLoading(getLoading().getValue()+1);
            }
        });
        Observable<ApiRiverTable> observableRiver = retrofitHelper.getServer().getRiverTable(stTime, enTime);
        retrofitHelper.successHandler(observableRiver, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRiverTable riverTable = (ApiRiverTable) t;
                riverInfoAB = riverTable.getData();
                setRiverInfo(riverTable.getData());
                setLoading(getLoading().getValue()+1);
            }
        });
        Observable<ApiRainTable> observableRain = retrofitHelper.getServer().getRainTable(stTime, enTime);
        retrofitHelper.successHandler(observableRain, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRainTable rainTable = (ApiRainTable) t;
                rainInfoAB = rainTable.getData();
                setRainInfo(rainTable.getData());
                setLoading(getLoading().getValue()+1);
            }
        });
    }


    public List<ApiRainTable.DataBean> getRainInfoAB() {
        return rainInfoAB;
    }

    public List<ApiRiverTable.DataBean> getRiverInfoAB() {
        return riverInfoAB;
    }

    public List<ApiRsvrTable.DataBean> getRsvrInfoAB() {
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
