package com.bzh.floodview.module.home.homeIndex.content;

import android.os.Handler;
import android.os.Message;

import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.view.BaseView;
import com.bzh.floodview.file.property;
import com.bzh.floodview.model.ApiFloodRain;
import com.bzh.floodview.model.ApiRainInfo;
import com.bzh.floodview.model.ApiRainStInfo;
import com.bzh.floodview.model.ApiRiverInfo;
import com.bzh.floodview.model.ApiRsvrInfo;
import com.bzh.floodview.model.floodsituation.IntensityOfRain;
import com.bzh.floodview.model.floodsituation.LoadTable;
import com.bzh.floodview.model.floodsituation.RainInfo;
import com.bzh.floodview.model.floodsituation.River;
import com.bzh.floodview.model.floodsituation.Rsvr;
import com.bzh.floodview.model.floodsituation.SendIntensity;
import com.bzh.floodview.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

public class ContentPresenter implements ContentContract.Present {

    @Inject
    RetrofitHelper retrofitHelper;

    private ContentContract.View mView;

    @Inject
    public ContentPresenter() {
    }

    static int signs = 0;
    static Object[] objects = new Object[3];

    public void getHttpResponse(String start_time, String end_time, final Handler handler, String address, final int sign, String... miscellaneous) {
        final Handler handl = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Message message = new Message();
                String data = (String) msg.obj;
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()//格式化输出
                        .setDateFormat("yyyy-MM-dd HH:mm:ss") //时间转化为特定格式
                        .disableHtmlEscaping() //防止特殊字符出现乱码// ;
                        .create();
                Map<String, String> map = null;
                switch (msg.what) {
                    case 51:
                        signs++;
                        Map<String, String> map1 = gson.fromJson(data, new TypeToken<Map<String, String>>() {
                        }.getType());
                        objects[0] = map1;
                        break;
                    case 52:
                        signs++;
                        List<River> flootRiverLists = gson.fromJson(data, new TypeToken<List<River>>() {
                        }.getType());
                        objects[1] = flootRiverLists;
                        break;
                    case 53:
                        signs++;
                        List<Rsvr> flootRsvrLists = gson.fromJson(data, new TypeToken<List<Rsvr>>() {
                        }.getType());
                        objects[2] = flootRsvrLists;
                        break;
                }
                if (signs == 3) {
                    signs = 0;
                    message.what = 3;
                    message.obj = objects;
                    handler.sendMessage(message);
                }
            }
        };
        RequestBody requestBody;
        if (miscellaneous.length != 0) {
            System.out.println(miscellaneous[0]);
            requestBody = new FormBody.Builder()
                    .add("nums", miscellaneous[0])
                    .add("stm", start_time)
                    .add("etm", end_time)
                    .build();
        } else {
            requestBody = new FormBody.Builder()
                    .add("stm", start_time)
                    .add("etm", end_time)
                    .build();
        }

        HttpUtil.sendPostOKHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String da = response.body().string();
                    System.out.println(da);
                    if (da.length() > 0) {
                        JSONObject jsonObject = new JSONObject(da);
                        int state = jsonObject.getInt("state");
                        Message message = new Message();
                        if (state == 0) {
                            String data = jsonObject.getString("data");
                            message.what = sign;
                            message.obj = data;
                            handl.sendMessage(message);
                        } else {
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //降雨信息
    @Override
    public void getRainfallInfo(String start_time, String end_time, String adcd) {
        Observable<ApiRainInfo> observable = retrofitHelper.getServer().getRainfallInfo(start_time, end_time, adcd);
        retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRainInfo rainInfos = (ApiRainInfo) t;
                Map<String, String> map = new LinkedHashMap<>();
                map.put("站号", "stcd");
                map.put("站名", "stnm");
                map.put("雨量(mm)", "drp");
                map.put("政区", "adnm");
                mView.setRainTable(map, rainInfos.getData());
            }
        });
    }

    //汛情降雨信息二级
    @Override
    public void getRainfallInfoXun(String rainLJ, String start_time, String end_time, Handler handler, String adcd) {
        Observable<ApiRainInfo> observable = retrofitHelper.getServer().getFloodRainInfo(start_time, end_time, rainLJ, adcd);
        retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRainInfo rainInfos = (ApiRainInfo) t;
                Map<String, String> map = new LinkedHashMap<>();
                map.put("站号", "stcd");
                map.put("站名", "stnm");
                map.put("雨量(mm)", "drp");
                map.put("政区", "adnm");
                mView.setRainTable(map, rainInfos.getData());
            }
        });

    }

    //雨强信息
    @Override
    public void getIntensityOfRainInfo(String start_time, String end_time, String adcd) {
        Observable<ApiRainStInfo> observable = retrofitHelper.getServer().getIntensityOfRainInfo(start_time, adcd);
        retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRainStInfo rainInfos = (ApiRainStInfo) t;
                Timber.e(rainInfos.toString());
                mView.setRainStTable(rainInfos.getData());
            }
        });
    }

    //河道站
    @Override
    public void getRiverInfo(String start_time, String end_time, String adcd) {
        Observable<ApiRiverInfo> observable = retrofitHelper.getServer().getRiverInfo(start_time, end_time, adcd);
        retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRiverInfo riverInfo = (ApiRiverInfo) t;
                Map<String, String> map = new LinkedHashMap<>();
                map.put("站号", "stcd");
                map.put("站名", "stnm");
                map.put("河流", "rvnm");
                map.put("时间", "ttt");
                map.put("水位(m)", "z");
                map.put("超警戒水位", "cjjsw");
                mView.setRiverStTable(map, riverInfo.getData());
            }
        });
    }

    //水库站
    @Override
    public void getRsvrInfo(String start_time, String end_time, Handler handler, String adcd) {
        Observable<ApiRsvrInfo> observable = retrofitHelper.getServer().getRsvrInfo(start_time, end_time, adcd);
        retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRsvrInfo rsvrInfo = (ApiRsvrInfo) t;
                Map<String, String> map = new LinkedHashMap<>();
                map.put("站号", "stcd");
                map.put("站名", "stnm");
                map.put("政区", "adnm");
                map.put("时间", "ttt");
                map.put("库水位(m)", "rz");
                map.put("超汛限水位", "cxxsw");
                mView.setRsvrStTable(map, rsvrInfo.getData());
            }
        });
    }

    //汛情摘要
    @Override
    public void getFloodSituation(String start_time, String end_time, Handler handler) {
        final String address1 = property.Route + "floodAbstract/floodAbstract_rain";
        final String address2 = property.Route + "floodAbstract/floodAbstract_river";
        final String address3 = property.Route + "floodAbstract/floodAbstract_reservoir";
        getHttpResponse(start_time, end_time, handler, address1, 51);
        getHttpResponse(start_time, end_time, handler, address2, 52);
        getHttpResponse(start_time, end_time, handler, address3, 53);
    }

    @Override
    public void takeView(ContentContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
