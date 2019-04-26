package com.bzh.floodview.model.floodsituation;

import java.util.Map;

/**
 *  给smartTable传值
 */
public class LoadTable<T> {
    private Map<String, String> map;
    private T oblist;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public T getOblist() {
        return oblist;
    }

    public void setOblist(T oblist) {
        this.oblist = oblist;
    }
}
