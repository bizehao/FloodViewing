package com.bzh.floodview.model.floodsituation;

/**
 * 河道站
 */
public class River {
    private String stcd;
    private String stnm;
    private String rvnm;
    private String tm;
    private String ttt;
    private double z;
    private double q;
    private double wrz;
    private String cjjsw;
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

    public String getTtt() {
        return ttt;
    }

    public void setTtt(String ttt) {
        this.ttt = ttt;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public double getWrz() {
        return wrz;
    }

    public void setWrz(double wrz) {
        this.wrz = wrz;
    }

    public String getCjjsw() {
        return cjjsw;
    }

    public void setCjjsw(String cjjsw) {
        this.cjjsw = cjjsw;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    @Override
    public String toString() {
        return "River{" +
                "stcd='" + stcd + '\'' +
                ", stnm='" + stnm + '\'' +
                ", rvnm='" + rvnm + '\'' +
                ", tm='" + tm + '\'' +
                ", ttt='" + ttt + '\'' +
                ", z=" + z +
                ", q=" + q +
                ", wrz=" + wrz +
                ", cjjsw=" + cjjsw +
                ", sts='" + sts + '\'' +
                '}';
    }
}
