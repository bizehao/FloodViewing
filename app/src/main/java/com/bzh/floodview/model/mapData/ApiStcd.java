package com.bzh.floodview.model.mapData;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/1 14:55
 */
public class ApiStcd {

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
         * stcd : 3030254
         * stlc : 河北省邢台县野沟门水库水文站
         * sttp : PR
         * lttd : 37.195475
         * lgtd : 114.101849
         * stnm : 野沟门水库(水位)
         */

        private String stcd;
        private String stlc;
        private String sttp;
        private String lttd;
        private String lgtd;
        private String stnm;

        public DataBean() {
        }

        public DataBean(String stcd, String stlc, String sttp, String lttd, String lgtd, String stnm) {
            this.stcd = stcd;
            this.stlc = stlc;
            this.sttp = sttp;
            this.lttd = lttd;
            this.lgtd = lgtd;
            this.stnm = stnm;
        }

        public String getStcd() {
            return stcd;
        }

        public void setStcd(String stcd) {
            this.stcd = stcd;
        }

        public String getStlc() {
            return stlc;
        }

        public void setStlc(String stlc) {
            this.stlc = stlc;
        }

        public String getSttp() {
            return sttp;
        }

        public void setSttp(String sttp) {
            this.sttp = sttp;
        }

        public String getLttd() {
            return lttd;
        }

        public void setLttd(String lttd) {
            this.lttd = lttd;
        }

        public String getLgtd() {
            return lgtd;
        }

        public void setLgtd(String lgtd) {
            this.lgtd = lgtd;
        }

        public String getStnm() {
            return stnm;
        }

        public void setStnm(String stnm) {
            this.stnm = stnm;
        }
    }
}
