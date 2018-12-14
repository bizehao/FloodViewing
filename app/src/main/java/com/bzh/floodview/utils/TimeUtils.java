package com.bzh.floodview.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 * Created by yangle on 2016/12/2.
 */
public class TimeUtils {
    public static String dateFormat_day = "HH:mm";
    public static String dateFormat_month = "MM-dd";

    /**
     * 时间转换成字符串,默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @param time 时间
     */
    public static String dateToString(long time) {
        return dateToString(time, "yyyy.MM.dd HH:mm");
    }

    /**
     * 时间转换成字符串,指定格式
     *
     * @param time   时间
     * @param format 时间格式
     */
    public static String dateToString(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 根据毫秒返回时分秒
     * @param time
     * @return
     */
    public static String getFormatHMS(long time){
        time=time/1000;//总秒数
        int s= (int) (time%60);//秒
        int m= (int) (time/60);//分
        int h=(int) (time/3600);//秒
        return String.format("%02d:%02d:%02d",h,m,s);
    }

    public static String fromDataTime(String dataTime)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dataTime);
            Calendar t = Calendar.getInstance();
            t.setTime(date);
            return t.get(Calendar.YEAR)+"年"+(t.get(Calendar.MONTH)+1)+"月"+t.get(Calendar.DATE)+"日"
                    +t.get(Calendar.HOUR_OF_DAY)+"时"+t.get(Calendar.MINUTE)+"分";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
