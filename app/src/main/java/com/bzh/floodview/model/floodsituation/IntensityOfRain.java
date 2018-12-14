package com.bzh.floodview.model.floodsituation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 雨强信息
 */
public class IntensityOfRain implements Parcelable {
    private String stcd;
    private String stnm;
    private String tm;
    private double drp;
    private String adnm;
    private String ttt;

    protected IntensityOfRain(Parcel in) {
        stcd = in.readString();
        stnm = in.readString();
        tm = in.readString();
        drp = in.readDouble();
        adnm = in.readString();
        ttt = in.readString();
    }

    public static final Creator<IntensityOfRain> CREATOR = new Creator<IntensityOfRain>() {
        @Override
        public IntensityOfRain createFromParcel(Parcel source) {
            IntensityOfRain intensityOfRain = new IntensityOfRain();
            intensityOfRain.setStcd(source.readString());
            intensityOfRain.setStnm(source.readString());
            intensityOfRain.setTm(source.readString());
            intensityOfRain.setDrp(source.readDouble());
            intensityOfRain.setAdnm(source.readString());
            intensityOfRain.setTtt(source.readString());
            return intensityOfRain;
        }

        @Override
        public IntensityOfRain[] newArray(int size) {
            return new IntensityOfRain[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // 1.必须按成员变量声明的顺序封装数据，不然会出现获取数据出错
        // 2.序列化对象
        parcel.writeString(stcd);
        parcel.writeString(stnm);
        parcel.writeString(tm);
        parcel.writeDouble(drp);
        parcel.writeString(adnm);
        parcel.writeString(ttt);
    }

    public IntensityOfRain() {
    }

    public IntensityOfRain(String stcd, String stnm, String tm, double drp, String adnm, String ttt) {
        this.stcd = stcd;
        this.stnm = stnm;
        this.tm = tm;
        this.drp = drp;
        this.adnm = adnm;
        this.ttt = ttt;
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

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public double getDrp() {
        return drp;
    }

    public void setDrp(double drp) {
        this.drp = drp;
    }

    public String getAdnm() {
        return adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public String getTtt() {
        return ttt;
    }

    public void setTtt(String ttt) {
        this.ttt = ttt;
    }
}
