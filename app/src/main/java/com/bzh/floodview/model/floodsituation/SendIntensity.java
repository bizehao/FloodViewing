package com.bzh.floodview.model.floodsituation;

import java.util.List;

public class SendIntensity {
    private List<IntensityOfRain> oneIntensity;
    private List<IntensityOfRain> twoIntensity;
    private List<IntensityOfRain> threeIntensity;

    public SendIntensity() {
    }

    public SendIntensity(List<IntensityOfRain> oneIntensity, List<IntensityOfRain> twoIntensity, List<IntensityOfRain> threeIntensity) {
        this.oneIntensity = oneIntensity;
        this.twoIntensity = twoIntensity;
        this.threeIntensity = threeIntensity;
    }

    public List<IntensityOfRain> getOneIntensity() {
        return oneIntensity;
    }

    public void setOneIntensity(List<IntensityOfRain> oneIntensity) {
        this.oneIntensity = oneIntensity;
    }

    public List<IntensityOfRain> getTwoIntensity() {
        return twoIntensity;
    }

    public void setTwoIntensity(List<IntensityOfRain> twoIntensity) {
        this.twoIntensity = twoIntensity;
    }

    public List<IntensityOfRain> getThreeIntensity() {
        return threeIntensity;
    }

    public void setThreeIntensity(List<IntensityOfRain> threeIntensity) {
        this.threeIntensity = threeIntensity;
    }
}
