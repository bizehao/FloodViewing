package com.bzh.floodview.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapIntent implements Serializable {
    private LinkedHashMap mMap;

    public MapIntent(Map mMap){
        this.mMap = (LinkedHashMap) mMap;
    }

    public void setMap(LinkedHashMap map) {
        mMap = map;
    }

    public LinkedHashMap getMap() {
        return mMap;

    }
}
