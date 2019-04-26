package com.bzh.floodview.model.mapData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.icu.text.DateFormat.getDateInstance;
import static android.icu.text.DateFormat.getDateTimeInstance;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/26 10:36
 */
public class ApiRainMapData {

    /**
     * tm : 1541959200000
     * drp : 0.0
     */

    private long tm;
    private String drp;
    private String subscripttime;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);

    public String getTm() {
        if (tm != 0) {
            return dateFormat.format(new Date(tm));
        } else {
            return null;
        }
    }

    public void setTm(long tm) {
        this.tm = tm;
    }

    public String getDrp() {
        return drp;
    }

    public void setDrp(String drp) {
        this.drp = drp;
    }

    public String getSubscripttime() {
        return subscripttime;
    }

    public void setSubscripttime(String subscripttime) {
        this.subscripttime = subscripttime;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "tm=" + getTm() +
                ", drp='" + drp + '\'' +
                '}';
    }
}
