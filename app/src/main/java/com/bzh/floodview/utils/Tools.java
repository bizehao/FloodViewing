package com.bzh.floodview.utils;

import android.view.View;
import com.bzh.floodview.model.floodsituation.RainInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//杂项的工具类
public class Tools {
    //处理时间
    public static String handleTime(Date date){
        Calendar tm = Calendar.getInstance();
        tm.setTime(date);
        int year = tm.get(Calendar.YEAR);
        String month = handleNum(tm.get(Calendar.MONTH)+1);
        String day = handleNum(tm.get(Calendar.DATE));
        String hour = handleNum(tm.get(Calendar.HOUR_OF_DAY));
        String minute = handleNum(tm.get(Calendar.MINUTE));
        String time = year+"-"+month+"-"+day+" "+hour+":"+minute;
        return time;
    }
    public static String handleNum(int num){
        String number = String.valueOf(num);
        if(number.length() == 1){
            return 0+number;
        }else {
            return number;
        }
    }
    //降雨信息
    public List<List<String>> gsonRain(String data){
        Gson gson = new Gson();
        List<RainInfo> rainInfoList = gson.fromJson(data, new TypeToken<List<RainInfo>>(){}.getType());
        List<List<String>> rListOne = new ArrayList<>();
        RainInfo rainInfo = null;
        List<String> rListTwo = new ArrayList<>();
        rListTwo.add("站名");
        rListTwo.add("雨量(mm)");
        rListTwo.add("政区");
        rListOne.add(rListTwo);
        for (int i=0; i<rainInfoList.size(); i++){
            rainInfo = rainInfoList.get(i);
            rListTwo = new ArrayList<>();
            rListTwo.add(rainInfo.getStnm());
            rListTwo.add(String.valueOf(rainInfo.getDrp()));
            rListTwo.add(rainInfo.getAdnm());
            rListOne.add(rListTwo);
        }
        return rListOne;
    }

    //控件的显示
    public static void setViewVisible(View view){
        if(view.getVisibility() == View.GONE){
            view.setVisibility(View.VISIBLE);
        }
    }

    //控件隐藏
    public static void setViewInVisible(View view){
        if(view.getVisibility() == View.VISIBLE){
            view.setVisibility(View.GONE);
        }
    }

}
