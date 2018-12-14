package com.bzh.floodview.model.floodsituation;

import java.io.Serializable;

/**
 * 河道水情基本信息表
 */
public class RiverTwoLevelBase implements Serializable {
    private String stcd; //测站编号
    private String stnm; //测站名称
    private String adnm; //政区
    private String rvnm; //河流
    private String hnnm; //水系
    private String bsnm; //流域
    private String stlc; //站址
    private String atcunit; //隶属行业单位
    private String admauth; //信息管理单位
    private String tm; //时间
    private String z; //水位(米)
    private String q; //流量(立方米/秒)
    private String wptn; //水势
    private String state; //状态
    private String ewrz; //超警戒水位(米)
    private String ewrq; //超警戒流量
    private String egrz; //超保证水位(米)
    private String egrq; //超保证流量(立方米/秒)
    private String ldkel; //左堤高程(米)
    private String rdkel; //右堤高程(米)
    private String wrz; //警戒水位(米)
    private String wrq; //警戒流量(立方米/秒)
    private String grz; //保证水位(米)
    private String grq; //保证流量(立方米/秒)
    private String flpq; //平摊流量(立方米/秒)
    private String obhtz; //实测最高水位(米)
    private String obhtztm; //实测最高水位时间
    private String ivhz; //调查最高水位(米)
    private String ivhztm; //调查最高水位时间
    private String obmxq; //实测最大流量(立方米/秒)
    private String obmxqtm; //实测最大流量时间
    private String ivmxq; //调查最大流量(立方米/秒)
    private String ivmxqtm; //调查最大流量出现时间
    private String hmxs; //历史最大含沙量(千克/立方米)
    private String hmxstm; //历史最大含沙量时间
    private String hmxavv; //历史最大断面平均流速(米/秒)
    private String hmxavvtm; //历史最大断面平均流速时间
    private String hlz; //历史最低水位(米)
    private String hlztm; //历史最低水位时间
    private String hmnq; //历史最小流量(米)
    private String hmnqtm; //历史最小流量时间
    private String bspn; //提防起点桩号

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

    public String getRvnm() {
        return rvnm;
    }

    public void setRvnm(String rvnm) {
        this.rvnm = rvnm;
    }

    public String getHnnm() {
        return hnnm;
    }

    public void setHnnm(String hnnm) {
        this.hnnm = hnnm;
    }

    public String getBsnm() {
        return bsnm;
    }

    public void setBsnm(String bsnm) {
        this.bsnm = bsnm;
    }

    public String getStlc() {
        return stlc;
    }

    public void setStlc(String stlc) {
        this.stlc = stlc;
    }

    public String getAtcunit() {
        return atcunit;
    }

    public void setAtcunit(String atcunit) {
        this.atcunit = atcunit;
    }

    public String getAdmauth() {
        return admauth;
    }

    public void setAdmauth(String admauth) {
        this.admauth = admauth;
    }

    public String getTm() {

        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
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

    public String getWptn() {
        return wptn;
    }

    public void setWptn(String wptn) {
        this.wptn = wptn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEwrz() {
        return ewrz;
    }

    public void setEwrz(String ewrz) {
        this.ewrz = ewrz;
    }

    public String getEwrq() {
        return ewrq;
    }

    public void setEwrq(String ewrq) {
        this.ewrq = ewrq;
    }

    public String getEgrz() {
        return egrz;
    }

    public void setEgrz(String egrz) {
        this.egrz = egrz;
    }

    public String getEgrq() {
        return egrq;
    }

    public void setEgrq(String egrq) {
        this.egrq = egrq;
    }

    public String getLdkel() {
        return ldkel;
    }

    public void setLdkel(String ldkel) {
        this.ldkel = ldkel;
    }

    public String getRdkel() {
        return rdkel;
    }

    public void setRdkel(String rdkel) {
        this.rdkel = rdkel;
    }

    public String getWrz() {
        return wrz;
    }

    public void setWrz(String wrz) {
        this.wrz = wrz;
    }

    public String getWrq() {
        return wrq;
    }

    public void setWrq(String wrq) {
        this.wrq = wrq;
    }

    public String getGrz() {
        return grz;
    }

    public void setGrz(String grz) {
        this.grz = grz;
    }

    public String getGrq() {
        return grq;
    }

    public void setGrq(String grq) {
        this.grq = grq;
    }

    public String getFlpq() {
        return flpq;
    }

    public void setFlpq(String flpq) {
        this.flpq = flpq;
    }

    public String getObhtz() {
        return obhtz;
    }

    public void setObhtz(String obhtz) {
        this.obhtz = obhtz;
    }

    public String getObhtztm() {
        return obhtztm;
    }

    public void setObhtztm(String obhtztm) {
        this.obhtztm = obhtztm;
    }

    public String getIvhz() {
        return ivhz;
    }

    public void setIvhz(String ivhz) {
        this.ivhz = ivhz;
    }

    public String getIvhztm() {
        return ivhztm;
    }

    public void setIvhztm(String ivhztm) {
        this.ivhztm = ivhztm;
    }

    public String getObmxq() {
        return obmxq;
    }

    public void setObmxq(String obmxq) {
        this.obmxq = obmxq;
    }

    public String getObmxqtm() {
        return obmxqtm;
    }

    public void setObmxqtm(String obmxqtm) {
        this.obmxqtm = obmxqtm;
    }

    public String getIvmxq() {
        return ivmxq;
    }

    public void setIvmxq(String ivmxq) {
        this.ivmxq = ivmxq;
    }

    public String getIvmxqtm() {
        return ivmxqtm;
    }

    public void setIvmxqtm(String ivmxqtm) {
        this.ivmxqtm = ivmxqtm;
    }

    public String getHmxs() {
        return hmxs;
    }

    public void setHmxs(String hmxs) {
        this.hmxs = hmxs;
    }

    public String getHmxstm() {
        return hmxstm;
    }

    public void setHmxstm(String hmxstm) {
        this.hmxstm = hmxstm;
    }

    public String getHmxavv() {
        return hmxavv;
    }

    public void setHmxavv(String hmxavv) {
        this.hmxavv = hmxavv;
    }

    public String getHmxavvtm() {
        return hmxavvtm;
    }

    public void setHmxavvtm(String hmxavvtm) {
        this.hmxavvtm = hmxavvtm;
    }

    public String getHlz() {
        return hlz;
    }

    public void setHlz(String hlz) {
        this.hlz = hlz;
    }

    public String getHlztm() {
        return hlztm;
    }

    public void setHlztm(String hlztm) {
        this.hlztm = hlztm;
    }

    public String getHmnq() {
        return hmnq;
    }

    public void setHmnq(String hmnq) {
        this.hmnq = hmnq;
    }

    public String getHmnqtm() {
        return hmnqtm;
    }

    public void setHmnqtm(String hmnqtm) {
        this.hmnqtm = hmnqtm;
    }

    public String getBspn() {
        return bspn;
    }

    public void setBspn(String bspn) {
        this.bspn = bspn;
    }

    public RiverTwoLevelBase(String stcd, String stnm, String adnm, String rvnm, String hnnm,
                             String bsnm, String stlc, String atcunit, String admauth, String tm,
                             String z, String q, String wptn, String state, String ewrz, String ewrq,
                             String egrz, String egrq, String ldkel, String rdkel, String wrz,
                             String wrq, String grz, String grq, String flpq, String obhtz,
                             String obhtztm, String ivhz, String ivhztm, String obmxq, String obmxqtm,
                             String ivmxq, String ivmxqtm, String hmxs, String hmxstm, String hmxavv,
                             String hmxavvtm, String hlz, String hlztm, String hmnq, String hmnqtm, String bspn) {
        this.stcd = stcd;
        this.stnm = stnm;
        this.adnm = adnm;
        this.rvnm = rvnm;
        this.hnnm = hnnm;
        this.bsnm = bsnm;
        this.stlc = stlc;
        this.atcunit = atcunit;
        this.admauth = admauth;
        this.tm = tm;
        this.z = z;
        this.q = q;
        this.wptn = wptn;
        this.state = state;
        this.ewrz = ewrz;
        this.ewrq = ewrq;
        this.egrz = egrz;
        this.egrq = egrq;
        this.ldkel = ldkel;
        this.rdkel = rdkel;
        this.wrz = wrz;
        this.wrq = wrq;
        this.grz = grz;
        this.grq = grq;
        this.flpq = flpq;
        this.obhtz = obhtz;
        this.obhtztm = obhtztm;
        this.ivhz = ivhz;
        this.ivhztm = ivhztm;
        this.obmxq = obmxq;
        this.obmxqtm = obmxqtm;
        this.ivmxq = ivmxq;
        this.ivmxqtm = ivmxqtm;
        this.hmxs = hmxs;
        this.hmxstm = hmxstm;
        this.hmxavv = hmxavv;
        this.hmxavvtm = hmxavvtm;
        this.hlz = hlz;
        this.hlztm = hlztm;
        this.hmnq = hmnq;
        this.hmnqtm = hmnqtm;
        this.bspn = bspn;
    }
}
