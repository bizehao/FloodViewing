package com.bzh.floodview.module.home.homeIndex.content.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.bzh.floodview.R;
import com.bzh.floodview.databinding.FragmentRightRainBinding;
import com.bzh.floodview.databinding.FragmentRightRiverBinding;
import com.bzh.floodview.databinding.FragmentRightRsvrBinding;
import com.bzh.floodview.model.floodsituation.RainTwoLevelBase;
import com.bzh.floodview.model.floodsituation.RiverTwoLevelBase;
import com.bzh.floodview.model.floodsituation.RsvrTwoLevelBase;
import com.bzh.floodview.utils.HttpUtil;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.bzh.floodview.file.property.Route;

/**
 * 二级右
 */
public class RightFragment extends Fragment {

    private View view;
    private TableLayout tableLayout;
    private TextView textView;
    private String address;
    private String stcd;
    private FragmentRightRainBinding rainBinding; //雨情
    private FragmentRightRiverBinding riverBinding; //河道
    private FragmentRightRsvrBinding rsvrBinding; //水库


    public static RightFragment newInstance(String stcd, String address){
        System.out.println("详细信息");
        System.out.println(stcd);
        System.out.println(address);
        RightFragment rightFragment = new RightFragment();
        Bundle bundle = new Bundle();
        bundle.putString("itemStcd",stcd);
        bundle.putString("itemAddress",address);
        rightFragment.setArguments(bundle);
        return rightFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        stcd = bundle.getString("itemStcd");
        address = bundle.getString("itemAddress");

        if(address.equals(Route+"waterInfo/river_detailed")){ //河道详情信息
            view = inflater.inflate(R.layout.fragment_right_river,container,false);
            riverBinding = DataBindingUtil.bind(view);
        }
        if(address.equals(Route+"waterInfo/reservoir_detailed")){ //水库详情信息
            view = inflater.inflate(R.layout.fragment_right_rsvr,container,false);
            rsvrBinding = DataBindingUtil.bind(view);
        }
        if(address.equals(Route+"rainInfo/rain_detailed")){ //降雨详细信息
            view = inflater.inflate(R.layout.fragment_right_rain,container,false);
            rainBinding = DataBindingUtil.bind(view);
        }
        initViews();
        return view;
    }

    private void initViews() {
        //tableLayout = view.findViewById(R.id.show_list);
        getHttpsDatas(stcd,address);
        //tableLayout.addView(makeTableRow());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    //动态添加控件
    public TableRow makeTableRow(){
        TableRow tableRow = new TableRow(getContext());
        TableLayout.LayoutParams tl = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(tl);
        tableRow.setPadding(1,1,1,1);

        TextView textView = new TextView(getContext());
        //这里的Textview的父layout是TableRow，所以要用TableRow.LayoutParams
        TableRow.LayoutParams tt = new TableRow.LayoutParams(0, 80, 1.0f);
        textView.setLayoutParams(tt);
        textView.setText("测站名称");
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.parseColor("#ffffffff"));
        tableRow.addView(textView);
        return tableRow;
    }

    //网络请求
    private void getHttpsDatas(String stcd, String address){
        final RequestBody requestBody = new FormBody.Builder()
                .add("stcd", stcd)
                .build();
        HttpUtil.sendPostOKHttpRequest(address, requestBody, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("二级查询", "失败了2222222");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    int state = jsonObject.getInt("state");
                    Message message = new Message();
                    if (state == 0) {
                        String data = jsonObject.getString("data");
                        message.what = 1;
                        message.obj = data;
                        handler.sendMessage(message);
                    } else {
                        message.what = 0;
                        //handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Gson gson = new Gson();
                    String data = (String) msg.obj;
                    if(address.equals(Route+"waterInfo/river_detailed")){
                        RiverTwoLevelBase riverTwoLevelBase = gson.fromJson(data,RiverTwoLevelBase.class);
                        double ewrz = Double.parseDouble(riverTwoLevelBase.getZ())-Double.parseDouble(riverTwoLevelBase.getWrz());
                        double ewrq = Double.parseDouble(riverTwoLevelBase.getQ())-Double.parseDouble(riverTwoLevelBase.getWrq());
                        double egrz = Double.parseDouble(riverTwoLevelBase.getZ())-Double.parseDouble(riverTwoLevelBase.getGrz());
                        double egrq = Double.parseDouble(riverTwoLevelBase.getQ())-Double.parseDouble(riverTwoLevelBase.getGrq());
                        if(ewrz<=0&&ewrq<=0&&egrz<=0&&egrq<=0){
                            riverTwoLevelBase.setState("正常");
                        }else{
                            riverTwoLevelBase.setState("不正常");
                        }
                        riverTwoLevelBase.setEwrz(String.valueOf(ewrz));
                        riverTwoLevelBase.setEwrq(String.valueOf(ewrq));
                        riverTwoLevelBase.setEgrz(String.valueOf(egrz));
                        riverTwoLevelBase.setEgrq(String.valueOf(egrq));
                        riverBinding.setRiverTwoLevelBase(riverTwoLevelBase);
                    }
                    if(address.equals(Route+"waterInfo/reservoir_detailed")){
                        RsvrTwoLevelBase rsvrTwoLevelBase = gson.fromJson(data,RsvrTwoLevelBase.class);
                        System.out.println(rsvrTwoLevelBase);
                        double efsltdz = Double.parseDouble(rsvrTwoLevelBase.getRz())-Double.parseDouble(rsvrTwoLevelBase.getFsltdz());
                        double efsltdw = Double.parseDouble(rsvrTwoLevelBase.getW())-Double.parseDouble(rsvrTwoLevelBase.getFsltdw());
                        double enormz = Double.parseDouble(rsvrTwoLevelBase.getRz())-Double.parseDouble(rsvrTwoLevelBase.getNormz());
                        rsvrTwoLevelBase.setEfsltdz(String.valueOf(efsltdz));
                        rsvrTwoLevelBase.setEfsltdw(String.valueOf(efsltdw));
                        rsvrTwoLevelBase.setEnormz(String.valueOf(enormz));
                        rsvrBinding.setRsvrTwoLevelBase(rsvrTwoLevelBase);
                    }
                    if(address.equals(Route+"rainInfo/rain_detailed")){
                        RainTwoLevelBase rainTwoLevelBase = gson.fromJson(data,RainTwoLevelBase.class);
                        rainBinding.setRainTwoLevelBase(rainTwoLevelBase);
                    }
                    break;
            }
        }
    };

}
