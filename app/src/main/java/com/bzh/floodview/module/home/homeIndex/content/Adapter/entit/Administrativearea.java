package com.bzh.floodview.module.home.homeIndex.content.Adapter.entit;

public class Administrativearea {
    private String areaId;//县域号
    private String areaName;//区域名称
    private boolean areaState; //状态，政区是否被选中

    public Administrativearea() {
    }

    public Administrativearea(String areaName, boolean areaState) {
        this.areaName = areaName;
        this.areaState = areaState;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public boolean isAreaState() {
        return areaState;
    }

    public void setAreaState(boolean areaState) {
        this.areaState = areaState;
    }

    @Override
    public String toString() {
        return "Administrativearea{" +
                "areaName='" + areaName + '\'' +
                ", areaState=" + areaState +
                '}';
    }
}
