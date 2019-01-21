package com.bzh.floodview.model;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 河道信息表
 * @time 2018/11/7 15:05
 */
public class ApiRiverInfo {

    private String code;
    private String RequestMessage;
    private List<DataBean> data;

    private String subscripttime;//统计图时间

    public String getSubscripttime() {
        return subscripttime;
    }

    public void setSubscripttime(String subscripttime) {
        this.subscripttime = subscripttime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRequestMessage() {
        return RequestMessage;
    }

    public void setRequestMessage(String RequestMessage) {
        this.RequestMessage = RequestMessage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * stcd : 30908010
         * stnm : 柏乡城关
         * rvnm : 午河
         * tm : 1535126400000
         * ttt : 08月25日 00:00
         * z : 32.27
         * q : 0
         * wrz : 0
         * cjjsw : 32.27
         */

        private String stcd;
        private String stnm;
        private String rvnm;
        private String tm;
        private String ttt;
        private String z;
        private String q;
        private String wrz;
        private String cjjsw;

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

        public String getTm() {
            return tm;
        }

        public void setTm(String tm) {
            this.tm = tm;
        }

        public String getTtt() {
            return ttt;
        }

        public void setTtt(String ttt) {
            this.ttt = ttt;
        }

        public String getZ() {
            return z;
        }

        public void setZ(String z) {
            this.z = z;
        }

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getWrz() {
            return wrz;
        }

        public void setWrz(String wrz) {
            this.wrz = wrz;
        }

        public String getCjjsw() {
            return cjjsw;
        }

        public void setCjjsw(String cjjsw) {
            this.cjjsw = cjjsw;
        }
    }
}
