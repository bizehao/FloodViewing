package com.bzh.floodview.model;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/1 11:37
 */
public class ApiRainInfo {

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
         * stcd : 30933875
         * stnm : 南大郭
         * drp : 157.6
         * adcd : 130501
         * adnm : 邢台市
         */

        private String stcd;
        private String stnm;
        private String drp;
        private String adcd;
        private String adnm;

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

        public String getDrp() {
            return drp;
        }

        public void setDrp(String drp) {
            this.drp = drp;
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
    }
}
