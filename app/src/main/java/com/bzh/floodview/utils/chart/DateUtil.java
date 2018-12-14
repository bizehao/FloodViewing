package com.bzh.floodview.utils.chart;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by xhu_ww on 2018/6/1.
 * description:
 */
public class DateUtil {

    public static String formatDateToMD(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sf3 = new SimpleDateFormat("MM-dd HH:mm");//
        String formatStr = "";
        try {
            formatStr = sf3.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    public static String formatDateToYMD(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }
}
