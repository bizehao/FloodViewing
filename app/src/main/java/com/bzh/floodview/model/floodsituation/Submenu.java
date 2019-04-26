package com.bzh.floodview.model.floodsituation;

import java.util.LinkedHashMap;

//子项菜单
public class Submenu {

    private String itemName;

    private int imgId;

    private int sign;// 1进入二级菜单  2进入一级菜单  3尚未开发完成

    private LinkedHashMap<String,Integer> subMap;

    public Submenu() {
    }

    public Submenu(String itemName, int imgId) {
        this.itemName = itemName;
        this.imgId = imgId;
    }

    public Submenu(String itemName, int imgId, int sign, LinkedHashMap<String,Integer> subMap) {
        this.itemName = itemName;
        this.imgId = imgId;
        this.sign = sign;
        this.subMap = subMap;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public LinkedHashMap<String, Integer> getSubMap() {
        return subMap;
    }

    public void setSubMap(LinkedHashMap<String, Integer> subMap) {
        this.subMap = subMap;
    }
}
