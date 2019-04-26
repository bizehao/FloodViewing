package com.bzh.floodview.model.floodsituation;

/**
 * 水库二级查询的类
 */
public class RsvrTwoLevel {
    private String stcd;
    private String stnm;
    private String ttt;
    private String rz;//水位
    private String inq;//入库
    private String otq;//出库
    private String minz;//最低
    private String maxz;//最高
    private String nowz;//当前
    private String fsltdz;//汛限水位
    private String subscripttime; //统计图时间

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

    public String getTtt() {
        return ttt;
    }

    public void setTtt(String ttt) {
        this.ttt = ttt;
    }

    public String getRz() {
        return rz;
    }

    public void setRz(String rz) {
        this.rz = rz;
    }

    public String getInq() {
        return inq;
    }

    public void setInq(String inq) {
        this.inq = inq;
    }

    public String getOtq() {
        return otq;
    }

    public void setOtq(String otq) {
        this.otq = otq;
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

    public String getFsltdz() {
        return fsltdz;
    }

    public void setFsltdz(String fsltdz) {
        this.fsltdz = fsltdz;
    }
}
