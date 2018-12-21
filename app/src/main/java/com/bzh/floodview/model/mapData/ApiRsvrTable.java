package com.bzh.floodview.model.mapData;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 水库表
 * @time 2018/11/22 11:30
 */
public class ApiRsvrTable {
    /**
     * stcd : 3090720a
     * stnm : 朱庄水库一级水位
     * adnm : 沙河市
     * rz : 0
     * inq : 0
     * otq : 0
     */

    private String stcd;
    private String stnm;
    private String adnm;
    private double rz;
    private double inq;
    private double otq;

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

    public double getRz() {
        return rz;
    }

    public void setRz(double rz) {
        this.rz = rz;
    }

    public double getInq() {
        return inq;
    }

    public void setInq(double inq) {
        this.inq = inq;
    }

    public double getOtq() {
        return otq;
    }

    public void setOtq(double otq) {
        this.otq = otq;
    }
}
