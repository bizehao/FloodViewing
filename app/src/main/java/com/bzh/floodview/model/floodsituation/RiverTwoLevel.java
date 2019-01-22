package com.bzh.floodview.model.floodsituation;

/**
 * 河道二级查询的类
 */
public class RiverTwoLevel {
    private String stcd;
    private String stnm;
    private String rvnm;
    private String tm;
    private String z;
    private String q;
    private String ttt;
    private String minz;
    private String maxz;
    private String nowz;
    private String wrz;
    private String grz;
    private String subscripttime;//统计图时间

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

    public String getTtt() {
        return ttt;
    }

    public void setTtt(String ttt) {
        this.ttt = ttt;
    }

    public String getMinz() {
        return minz;
    }

    public void setMinz(String minz) {
        this.minz = minz;
    }

    public String getMaxz() {
        return maxz;
    }

    public void setMaxz(String maxz) {
        this.maxz = maxz;
    }

    public String getNowz() {
        return nowz;
    }

    public void setNowz(String nowz) {
        this.nowz = nowz;
    }

    public String getWrz() {
        return wrz;
    }

    public void setWrz(String wrz) {
        this.wrz = wrz;
    }

    public String getGrz() {
        return grz;
    }

    public void setGrz(String grz) {
        this.grz = grz;
    }

    public String getSubscripttime() {
        return subscripttime;
    }

    public void setSubscripttime(String subscripttime) {
        this.subscripttime = subscripttime;
    }

    @Override
    public String toString() {
        return "RiverTwoLevel{" +
                "stcd='" + stcd + '\'' +
                ", stnm='" + stnm + '\'' +
                ", rvnm='" + rvnm + '\'' +
                ", tm=" + tm +
                ", z='" + z + '\'' +
                ", q='" + q + '\'' +
                ", ttt='" + ttt + '\'' +
                ", minz='" + minz + '\'' +
                ", maxz='" + maxz + '\'' +
                ", nowz='" + nowz + '\'' +
                '}';
    }
}
