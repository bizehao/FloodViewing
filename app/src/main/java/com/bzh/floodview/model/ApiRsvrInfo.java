package com.bzh.floodview.model;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 水库信息
 * @time 2018/11/7 15:21
 */
public class ApiRsvrInfo {

    private String code;
    private String RequestMessage;
    private List<DataBean> data;

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
         * stcd : 30956641
         * stnm : 东川口
         * adcd : null
         * adnm : 邢台县
         * tm : 1535126400000
         * ttt : 08月25日 00:00
         * rz : 223.59
         * fsltdz : 0
         * cxxsw : 223.59
         * sts : null
         */

        private String stcd;
        private String stnm;
        private String adcd;
        private String adnm;
        private String tm;
        private String ttt;
        private String rz;
        private String fsltdz;
        private String cxxsw;
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

        public String getAdcd() {
            return adcd;
        }

        public void setAdcd(String adcd) {
            this.adcd = adcd;
        }

        public String getAdnm() {
            return adnm;
        }

        public void setAdnm(String adnm) {
            this.adnm = adnm;
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

        public String getRz() {
            return rz;
        }

        public void setRz(String rz) {
            this.rz = rz;
        }

        public String getFsltdz() {
            return fsltdz;
        }

        public void setFsltdz(String fsltdz) {
            this.fsltdz = fsltdz;
        }

        public String getCxxsw() {
            return cxxsw;
        }

        public void setCxxsw(String cxxsw) {
            this.cxxsw = cxxsw;
        }

        public String getSts() {
            return sts;
        }

        public void setSts(String sts) {
            this.sts = sts;
        }
    }
}
