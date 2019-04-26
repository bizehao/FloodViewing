package com.bzh.floodview.utils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 存储数据
 */
public class DataHolder {

    private static final DataHolder holder = new DataHolder();

    public  static DataHolder getInstance(){
        return holder;
    }

    private static final Map<String, WeakReference<Object>> data = new HashMap<String, WeakReference<Object>>();

    /**
     * 存数据
     * @param id
     * @param object
     */
    public void save(String id, Object object){
        data.put(id,new WeakReference<Object>(object));
    }

    /**
     * 取数据
     * @param id
     * @return
     */
    public Object retrieve(String id){
        WeakReference<Object> objectWeakReference = data.get(id);
        return objectWeakReference.get();
    }
}
