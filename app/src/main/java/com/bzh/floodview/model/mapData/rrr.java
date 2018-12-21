package com.bzh.floodview.model.mapData;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/12/21 15:39
 */
public class rrr {

    /**
     * code : 200
     * data : [{"stcd":"30902260","stnm":"香城固","rvnm":"老沙河","q":0,"z":0},{"stcd":"30904102","stnm":"艾辛庄(滏)(闸下二)","rvnm":"滏阳河","q":0,"z":0},{"stcd":"30956793","stnm":"北大丰","rvnm":"李阳河","q":0,"z":0},{"stcd":"30956795","stnm":"高望","rvnm":"汦河","q":0,"z":0.71}]
     * message : null
     */

    private String code;
    private Object message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * stcd : 30902260
         * stnm : 香城固
         * rvnm : 老沙河
         * q : 0
         * z : 0
         */

        private String stcd;
        private String stnm;
        private String rvnm;
        private int q;
        private int z;

        public String getStcd() {
            return stcd;
        }

        public void setStcd(String stcd) {
            this.stcd = stcd;
        }

        public String getStnm() {
            return stnm;
        }

        public void setStnm(String stnm) {
            this.stnm = stnm;
        }

        public String getRvnm() {
            return rvnm;
        }

        public void setRvnm(String rvnm) {
            this.rvnm = rvnm;
        }

        public int getQ() {
            return q;
        }

        public void setQ(int q) {
            this.q = q;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }
    }
}
