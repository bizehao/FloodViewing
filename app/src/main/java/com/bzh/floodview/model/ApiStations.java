package com.bzh.floodview.model;

import java.util.List;

/**
 * User: bizehao
 * Date: 2019-05-23
 * Time: 下午3:50
 * Description: 站点县域信息
 */
public class ApiStations {

    /**
     * stationList : [{"adnm":null,"stnm":"界河铺@","stcd":"30908800"},{"adnm":null,"stnm":"界河铺[忻定渠]@","stcd":"30908803"},{"adnm":null,"stnm":"界河铺[广济渠]@","stcd":"30908804"}]
     * adnm : 原平市
     */

    private String adnm;
    private List<StationListBean> stationList;

    public String getAdnm() {
        return adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public List<StationListBean> getStationList() {
        return stationList;
    }

    public void setStationList(List<StationListBean> stationList) {
        this.stationList = stationList;
    }

    public static class StationListBean {
        /**
         * stnm : 界河铺@
         * stcd : 30908800
         */

        private String stnm;
        private String stcd;
        private boolean whether;

        public String getStnm() {
            return stnm;
        }

        public void setStnm(String stnm) {
            this.stnm = stnm;
        }

        public String getStcd() {
            return stcd;
        }

        public void setStcd(String stcd) {
            this.stcd = stcd;
        }

        public boolean isWhether() {
            return whether;
        }

        public void setWhether(boolean whether) {
            this.whether = whether;
        }
    }

}
