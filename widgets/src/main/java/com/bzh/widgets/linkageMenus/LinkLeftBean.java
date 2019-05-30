package com.bzh.widgets.linkageMenus;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/11 18:48
 */
public class LinkLeftBean {
    private String typeName;
    private boolean clickState;

    public LinkLeftBean(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean getClickState() {
        return clickState;
    }

    public void setClickState(boolean clickState) {
        this.clickState = clickState;
    }
}
