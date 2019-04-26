package com.bzh.floodview.model.floodsituation;

/**
 * 降雨信息
 */
public class RainInfo {
    private String adcd;
    private String adnm;
    private String stcd;
    private String stnm;
    private double drp;
    private String sts;

    public RainInfo() {
    }

    public RainInfo(String adcd, String adnm, String stcd, String stnm, double drp, String sts) {
        this.adcd = adcd;
        this.adnm = adnm;
        this.stcd = stcd;
        this.stnm = stnm;
        this.drp = drp;
        this.sts = sts;
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

    public double getDrp() {
        return drp;
    }

    public void setDrp(double drp) {
        this.drp = drp;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }
}
