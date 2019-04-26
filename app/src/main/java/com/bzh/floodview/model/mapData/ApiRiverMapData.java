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

    private RiverBean river;
    private List<RivertimeListBean> rivertimeList;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);

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
        private double q;
        private double chaochu;
        private double wrz;
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

        public double getQ() {
            return q;
        }

        public void setQ(double q) {
            this.q = q;
        }

        public double getChaochu() {
            return chaochu;
        }

        public void setChaochu(double chaochu) {
            this.chaochu = chaochu;
        }

        public double getWrz() {
            return wrz;
        }

        public void setWrz(double wrz) {
            this.wrz = wrz;
        }

        public String getYmdhm() {
            if (ymdhm != 0) {
                return dateFormat.format(new Date(ymdhm));
            }
            return "";
        }

        public void setYmdhm(long ymdhm) {
            this.ymdhm = ymdhm;
        }

        @Override
        public String toString() {
            return "RiverBean{" +
                    "stcd='" + stcd + '\'' +
                    ", z=" + z +
                    ", q=" + q +
                    ", chaochu=" + chaochu +
                    ", wrz=" + wrz +
                    ", ymdhm=" + ymdhm +
                    '}';
        }
    }

    public static class RivertimeListBean {
        /**
         * ymdhm : 1532836800000
         * q : 0
         * zr : 29.56
         */

        private long ymdhm; //时间
        private double q;//流量
        private double zr;//水位值
        private String subscripttime;//统计图时间

        public String getSubscripttime() {
            return subscripttime;
        }

        public void setSubscripttime(String subscripttime) {
            this.subscripttime = subscripttime;
        }

        public String getYmdhm() {
            return dateFormat.format(new Date(ymdhm));
        }

        public void setYmdhm(long ymdhm) {
            this.ymdhm = ymdhm;
        }

        public double getQ() {
            return q;
        }

        public void setQ(double q) {
            this.q = q;
        }

        public double getZr() {
            return zr;
        }

        public void setZr(double zr) {
            this.zr = zr;
        }

        @Override
        public String toString() {
            return "RivertimeListBean{" +
                    "ymdhm=" + ymdhm +
                    ", q='" + q + '\'' +
                    ", zr='" + zr + '\'' +
                    ", subscripttime='" + subscripttime + '\'' +
                    '}';
        }
    }
}
