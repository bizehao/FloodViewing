package com.bzh.widgets.linkageMenus;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/11 18:48
 */
public class LinkBean {
    private String typeName;

    private List<InnerType> innerTypes;

    public LinkBean(String typeName, List<InnerType> innerTypes) {
        this.typeName = typeName;
        this.innerTypes = innerTypes;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<InnerType> getInnerTypes() {
        return innerTypes;
    }

    public void setInnerTypes(List<InnerType> innerTypes) {
        this.innerTypes = innerTypes;
    }

    public static class InnerType{
        private String innerTypeName; //站名
        private String innerTypeId; //站名
        private boolean status; //用户是否已选择
        private boolean isShow = false; //是否显示

        public InnerType(String innerTypeName, String innerTypeId, boolean status) {
            this.innerTypeName = innerTypeName;
            this.innerTypeId = innerTypeId;
            this.status = status;
        }

        public String getInnerTypeName() {
            return innerTypeName;
        }

        public void setInnerTypeName(String innerTypeName) {
            this.innerTypeName = innerTypeName;
        }

        public String getInnerTypeId() {
            return innerTypeId;
        }

        public void setInnerTypeId(String innerTypeId) {
            this.innerTypeId = innerTypeId;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }
    }

    @Override
    public String toString() {
        return "LinkBean{" +
                "typeName='" + typeName + '\'' +
                ", innerTypes=" + innerTypes +
                '}';
    }
}
