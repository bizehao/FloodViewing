package com.bzh.floodview.module.home.homeIndex.content;

import android.util.Log;

import com.bzh.floodview.base.view.BaseView;
import com.bzh.floodview.utils.HttpUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class McontentPresenter implements McontentContract.Present {

    private final McontentContract.View mView;

    public McontentPresenter(McontentContract.View mView){
        this.mView = mView;
    }


    @Override
    public void getSecondlevelRiverInfo(String stcd, String start_time, String end_time) {

    }

    public void getHttpResponse(String stcd, String start_time, String end_time, String address){
        final RequestBody requestBody = new FormBody.Builder()
                .add("stm", start_time)
                .add("etm", end_time)
                .build();
        HttpUtil.sendPostOKHttpRequest(address, requestBody, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("McontentPresenter", "失败了");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    int state = jsonObject.getInt("state");
                    if (state == 0) {
                        String data = jsonObject.getString("data");
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void takeView(BaseView view) {

    }

    @Override
    public void dropView() {

    }
}
