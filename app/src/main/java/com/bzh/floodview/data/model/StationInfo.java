package com.bzh.floodview.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author 毕泽浩
 * @Description: 站点信息数据
 * @time 2018/11/29 9:45
 */
@Entity
public class StationInfo {
    /**
     * stcd : 3030254
     * stlc : 河北省邢台县野沟门水库水文站
     * sttp : PR
     * lttd : 37.195475
     * lgtd : 114.101849
     * stnm : 野沟门水库(水位)
     */

    @PrimaryKey
    private Long id; //id
    private String stcd;
    private String stlc;
    private String sttp;
    private String lttd;
    private String lgtd;
    private String stnm;

    public StationInfo(Long id, String stcd, String stlc, String sttp, String lttd, String lgtd, String stnm) {
        this.id = id;
        this.stcd = stcd;
        this.stlc = stlc;
        this.sttp = sttp;
        this.lttd = lttd;
        this.lgtd = lgtd;
        this.stnm = stnm;
    }

    @Ignore
    public StationInfo(String stcd, String stlc, String sttp, String lttd, String lgtd, String stnm) {
        this.stcd = stcd;
        this.stlc = stlc;
        this.sttp = sttp;
        this.lttd = lttd;
        this.lgtd = lgtd;
        this.stnm = stnm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
