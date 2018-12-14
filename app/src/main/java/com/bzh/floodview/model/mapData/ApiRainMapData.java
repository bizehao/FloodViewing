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
     * state : 0
     * message : null
     * data : [{"tm":1541959200000,"drp":"0.0"},{"tm":1541962800000,"drp":"0.0"},{"tm":1541966400000,"drp":"0.0"},{"tm":1541970000000,"drp":"0.0"},{"tm":1541973600000,"drp":"0.0"},{"tm":1542214800000,"drp":"--"},{"tm":1542218400000,"drp":"--"},{"tm":1542222000000,"drp":"--"},{"tm":1542225600000,"drp":"--"},{"tm":1542229200000,"drp":"--"},{"tm":1542232800000,"drp":"--"},{"tm":1542236400000,"drp":"--"}]
     */

    private int state;
    private Object message;
    private List<DataBean> data;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tm : 1541959200000
         * drp : 0.0
         */

        private long tm;
        private String drp;

        public String getTm() {
            if(tm != 0){
                return dateFormat.format(new Date(tm));
            }else {
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "tm=" + getTm() +
                    ", drp='" + drp + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ApiRainMapData{" +
                "state=" + state +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
