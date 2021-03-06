package com.bzh.floodview.model.mapData;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:降雨表格
 * @time 2018/11/22 11:30
 */
public class ApiRainTable {

    /**
     * stcd : 30933875
     * stnm : 南大郭
     * adnm : 邢台市
     * dyp : 0
     */

    private String stcd;
    private String stnm;
    private String adnm;
    private double dyp;

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

    public String getAdnm() {
        return adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public double getDyp() {
        return dyp;
    }

    public void setDyp(double dyp) {
        this.dyp = dyp;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "stcd='" + stcd + '\'' +
                ", stnm='" + stnm + '\'' +
                ", adnm='" + adnm + '\'' +
                ", dyp=" + dyp +
                '}';
    }

}
