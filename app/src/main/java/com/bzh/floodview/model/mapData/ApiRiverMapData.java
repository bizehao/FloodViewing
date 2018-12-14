package com.bzh.floodview.model.mapData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author 毕泽浩
 * @Description: 河道地图数据
 * @time 2018/11/20 9:28
 */
public class ApiRiverMapData {

    /**
     * state : 0
     * message : null
     * data : {"river":{"stcd":"30902270","z":29.6,"q":0,"chaochu":0,"wrz":0,"ymdhm":1533092400000},"rivertimeList":[{"ymdhm":1532836800000,"q":0,"zr":29.56}]}
     */

    private int state;
    private Object message;
    private DataBean data;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * river : {"stcd":"30902270","z":29.6,"q":0,"chaochu":0,"wrz":0,"ymdhm":1533092400000}
         * rivertimeList : [{"ymdhm":1532836800000,"q":0,"zr":29.56}]
         */

        private RiverBean river;
        private List<RivertimeListBean> rivertimeList;

        public RiverBean getRiver() {
            return river;
        }

        public void setRiver(RiverBean river) {
            this.river = river;
        }

        public List<RivertimeListBean> getRivertimeList() {
            return rivertimeList;
        }

        public void setRivertimeList(List<RivertimeListBean> rivertimeList) {
            this.rivertimeList = rivertimeList;
        }

        public static class RiverBean {
            /**
             * stcd : 30902270
             * z : 29.6
             * q : 0
             * chaochu : 0
             * wrz : 0
             * ymdhm : 1533092400000
             */

            private String stcd;
            private double z;
            private int q;
            private int chaochu;
            private int wrz;
            private long ymdhm;

            public String getStcd() {
                return stcd;
            }

            public void setStcd(String stcd) {
                this.stcd = stcd;
            }

            public double getZ() {
                return z;
            }

            public void setZ(double z) {
                this.z = z;
            }

            public int getQ() {
                return q;
            }

            public void setQ(int q) {
                this.q = q;
            }

            public int getChaochu() {
                return chaochu;
            }

            public void setChaochu(int chaochu) {
                this.chaochu = chaochu;
            }

            public int getWrz() {
                return wrz;
            }

            public void setWrz(int wrz) {
                this.wrz = wrz;
            }

            public String getYmdhm() {
                return dateFormat.format(new Date(ymdhm));
            }

            public void setYmdhm(long ymdhm) {
                this.ymdhm = ymdhm;
            }
        }

        public static class RivertimeListBean {
            /**
             * ymdhm : 1532836800000
             * q : 0
             * zr : 29.56
             */

            private long ymdhm; //时间
            private String q;//流量
            private String zr;//水位值

            public String getYmdhm() {
                return dateFormat.format(new Date(ymdhm));
            }

            public void setYmdhm(long ymdhm) {
                this.ymdhm = ymdhm;
            }

            public String getQ() {
                return q;
            }

            public void setQ(String q) {
                this.q = q;
            }

            public String getZr() {
                return zr;
            }

            public void setZr(String zr) {
                this.zr = zr;
            }
        }
    }
}
