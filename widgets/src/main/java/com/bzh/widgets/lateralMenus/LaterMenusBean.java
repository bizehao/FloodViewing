package com.bzh.widgets.lateralMenus;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/11 10:14
 */
public class LaterMenusBean {
    private Object img_path;
    private String title;

    public LaterMenusBean(Object img_path, String title) {
        this.img_path = img_path;
        this.title = title;
    }

    public Object getImg_path() {
        return img_path;
    }

    public void setImg_path(Object img_path) {
        this.img_path = img_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
