package com.bzh.floodview.model.mapData;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 河道表
 * @time 2018/11/22 11:30
 */
public class ApiRiverTable {

    /**
     * state : 0
     * message : null
     * data : [{"stcd":"30902260","stnm":"香城固","rvnm":"老沙河","q":0,"z":0}]
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

    public static class DataBean {
        /**
         * stcd : 30902260
         * stnm : 香城固
         * rvnm : 老沙河
         * q : 0
         * z : 0
         */

        private String stcd;
        private String stnm;
        private String rvnm;
        private double q;
        private double z;

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

        public double getQ() {
            return q;
        }

        public void setQ(double q) {
            this.q = q;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }
    }
}
