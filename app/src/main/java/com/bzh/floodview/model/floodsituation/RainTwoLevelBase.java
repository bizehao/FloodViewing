package com.bzh.floodview.model.floodsituation;

import java.io.Serializable;

/**
 * 降水量基本信息表
 */
public class RainTwoLevelBase implements Serializable {
    private String stcd; //测站编号
    private String stnm; //测站名称
    private String sttp; //站类
    private String adnm; //政区
    private String rvnm; //河流
    private String hnnm; //水系
    private String bsnm; //流域
    private String stlc; //站址
    private String atcunit; //隶属行业单位
    private String admauth; //信息管理单位

    public String getStcd() {
        return stcd == null ? "" : stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getStnm() {
        return stnm == null ? "" : stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getSttp() {
        return sttp == null ? "" : sttp;
    }

    public void setSttp(String sttp) {
        this.sttp = sttp;
    }

    public String getAdnm() {
        return adnm == null ? "" : adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public String getRvnm() {
        return rvnm == null ? "" : rvnm;
    }

    public void setRvnm(String rvnm) {
        this.rvnm = rvnm;
    }

    public String getHnnm() {
        return hnnm == null ? "" : hnnm;
    }

    public void setHnnm(String hnnm) {
        this.hnnm = hnnm;
    }

    public String getBsnm() {
        return bsnm == null ? "" : bsnm;
    }

    public void setBsnm(String bsnm) {
        this.bsnm = bsnm;
    }

    public String getStlc() {
        return stlc == null ? "" : stlc;
    }

    public void setStlc(String stlc) {
        this.stlc = stlc;
    }

    public String getAtcunit() {
        return atcunit == null ? "" : atcunit;
    }

    public void setAtcunit(String atcunit) {
        this.atcunit = atcunit;
    }

    public String getAdmauth() {
        return admauth == null ? "" : admauth;
    }

    public void setAdmauth(String admauth) {
        this.admauth = admauth;
    }

    @Override
    public String toString() {
        return "RainTwoLevelBase{" +
                "stcd='" + stcd + '\'' +
                ", stnm='" + stnm + '\'' +
                ", sttp='" + sttp + '\'' +
                ", adnm='" + adnm + '\'' +
                ", rvnm='" + rvnm + '\'' +
                ", hnnm='" + hnnm + '\'' +
                ", bsnm='" + bsnm + '\'' +
                ", stlc='" + stlc + '\'' +
                ", atcunit='" + atcunit + '\'' +
                ", admauth='" + admauth + '\'' +
                '}';
    }
}
