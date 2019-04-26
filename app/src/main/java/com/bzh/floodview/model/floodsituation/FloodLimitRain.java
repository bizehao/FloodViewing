package com.bzh.floodview.model.floodsituation;

/**
 * 汛限水位中的降雨
 */
public class FloodLimitRain {
    private String magnitude; //量级
    private String sumofRainfall; //降雨总结
    private String kong;//空白

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getSumofRainfall() {
        return sumofRainfall;
    }

    public void setSumofRainfall(String sumofRainfall) {
        this.sumofRainfall = sumofRainfall;
    }

    public FloodLimitRain(String magnitude, String sumofRainfall, String kong) {
        this.magnitude = magnitude;
        this.sumofRainfall = sumofRainfall;
        this.kong = kong;
    }

    public String getKong() {
        return kong;
    }

    public void setKong(String kong) {
        this.kong = kong;
    }
}
