package com.bzh.floodview.model;

import java.util.Date;
import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 汛情摘要 河道
 * @time 2018/11/9 15:47
 */
public class ApiFloodRiver {

    /**
     * state : 0
     * message : null
     * data : []
     */

    private int state;
    private Object message;
    private List<DataBean> data;

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

    public static class DataBean{
        private String stcd;
        private String stnm;
        private String rvnm;
        private Date tm;
        private String ttt;
        private double z;
        private double q;
        private double wrz;
        private double cjjsw;//超警戒水位=q-wrz
        private String sts;

        public String getStcd() {
            return stcd;
        }

        public void setStcd(String stcd) {
            this.stcd = stcd;
        }

        public String getStnm() {
            return stnm;
        }

        public void setStnm(String stnm) {
            this.stnm = stnm;
        }

        public String getRvnm() {
            return rvnm;
        }

        public void setRvnm(String rvnm) {
            this.rvnm = rvnm;
        }

        public Date getTm() {
            return tm;
        }

        public void setTm(Date tm) {
            this.tm = tm;
        }

        public String getTtt() {
            return ttt;
        }

        public void setTtt(String ttt) {
            this.ttt = ttt;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }

        public double getQ() {
            return q;
        }

        public void setQ(double q) {
            this.q = q;
        }

        public double getWrz() {
            return wrz;
        }

        public void setWrz(double wrz) {
            this.wrz = wrz;
        }

        public double getCjjsw() {
            return cjjsw;
        }

        public void setCjjsw(double cjjsw) {
            this.cjjsw = cjjsw;
        }

        public String getSts() {
            return sts;
        }

        public void setSts(String sts) {
            this.sts = sts;
        }
    }
}
