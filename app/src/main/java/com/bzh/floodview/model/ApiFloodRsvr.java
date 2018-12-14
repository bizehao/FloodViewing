package com.bzh.floodview.model;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 汛情摘要 水库
 * @time 2018/11/9 15:47
 */
public class ApiFloodRsvr {

    /**
     * state : 0
     * message : null
     * data : [{"stcd":"30956782","stnm":"北李庄水库","adcd":null,"adnm":"内丘县","tm":1507687200000,"ttt":null,"rz":0.79,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956791","stnm":"北岭水库","adcd":null,"adnm":"内丘县","tm":1531756800000,"ttt":null,"rz":7.45,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956740","stnm":"北赛水库","adcd":null,"adnm":"内丘县","tm":1499500800000,"ttt":null,"rz":0.88,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956641","stnm":"东川口","adcd":null,"adnm":"邢台县","tm":1535126400000,"ttt":null,"rz":223.59,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956818","stnm":"东菅等","adcd":null,"adnm":"临城县","tm":1535126400000,"ttt":null,"rz":105.525,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956507","stnm":"东石岭水库(山)","adcd":null,"adnm":"沙河市","tm":1535126400000,"ttt":null,"rz":374.86,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956668","stnm":"丰来峪1号","adcd":null,"adnm":"邢台县","tm":1534719600000,"ttt":null,"rz":90.65,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956844","stnm":"丰盈","adcd":null,"adnm":"临城县","tm":1535126400000,"ttt":null,"rz":96.42,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956778","stnm":"韩家庄1号水库","adcd":null,"adnm":"内丘县","tm":1533405600000,"ttt":null,"rz":7.12,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"3090780b","stnm":"临城水库二级水位","adcd":null,"adnm":"临城县","tm":1534093200000,"ttt":null,"rz":123.27,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"3090780c","stnm":"临城水库一级水位","adcd":null,"adnm":"临城县","tm":1535126400000,"ttt":null,"rz":121.57,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"3090780d","stnm":"临城水库自收揽水位","adcd":null,"adnm":"临城县","tm":1535126400000,"ttt":null,"rz":122.17,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30907750","stnm":"乱木水库","adcd":null,"adnm":"临城县","tm":1526889600000,"ttt":null,"rz":4095,"fsltdz":112,"cxxsw":0,"sts":null},{"stcd":"30956794","stnm":"落凹水库","adcd":null,"adnm":"内丘县","tm":1523332800000,"ttt":null,"rz":1.47,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956548","stnm":"马峪水库","adcd":null,"adnm":"沙河市","tm":1534914000000,"ttt":null,"rz":35.12,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956546","stnm":"盆水水库","adcd":null,"adnm":"沙河市","tm":1535126400000,"ttt":null,"rz":40.48,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956792","stnm":"山凹水库","adcd":null,"adnm":"内丘县","tm":1535126400000,"ttt":null,"rz":2.08,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956812","stnm":"石窝铺","adcd":null,"adnm":"临城县","tm":1533139200000,"ttt":null,"rz":132.559,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956785","stnm":"西庞1号水库","adcd":null,"adnm":"内丘县","tm":1531090800000,"ttt":null,"rz":21.47,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956842","stnm":"西双井","adcd":null,"adnm":"临城县","tm":1535126400000,"ttt":null,"rz":138.1,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956541","stnm":"峡沟水库","adcd":null,"adnm":"沙河市","tm":1535126400000,"ttt":null,"rz":148.66,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956789","stnm":"新城水库","adcd":null,"adnm":"内丘县","tm":1535126400000,"ttt":null,"rz":1.36,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30907201","stnm":"朱庄水库","adcd":null,"adnm":"沙河市","tm":1519844400000,"ttt":null,"rz":4095,"fsltdz":239,"cxxsw":0,"sts":null},{"stcd":"3090720b","stnm":"朱庄水库二级水位","adcd":null,"adnm":"沙河市","tm":1535126400000,"ttt":null,"rz":242.5,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"3090720a","stnm":"朱庄水库一级水位","adcd":null,"adnm":"沙河市","tm":1535126400000,"ttt":null,"rz":241.54,"fsltdz":0,"cxxsw":0,"sts":null},{"stcd":"30956582","stnm":"朱庄小水库","adcd":null,"adnm":"沙河市","tm":1510250400000,"ttt":null,"rz":27.58,"fsltdz":0,"cxxsw":0,"sts":null}]
     */

    private int state;
    private Object message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * stcd : 30956782
         * stnm : 北李庄水库
         * adcd : null
         * adnm : 内丘县
         * tm : 1507687200000
         * ttt : null
         * rz : 0.79
         * fsltdz : 0
         * cxxsw : 0
         * sts : null
         */

        private String stcd;
        private String stnm;
        private Object adcd;
        private String adnm;
        private long tm;
        private Object ttt;
        private double rz;
        private int fsltdz;
        private int cxxsw;
        private Object sts;

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

        public Object getAdcd() {
            return adcd;
        }

        public void setAdcd(Object adcd) {
            this.adcd = adcd;
        }

        public String getAdnm() {
            return adnm;
        }

        public void setAdnm(String adnm) {
            this.adnm = adnm;
        }

        public long getTm() {
            return tm;
        }

        public void setTm(long tm) {
            this.tm = tm;
        }

        public Object getTtt() {
            return ttt;
        }

        public void setTtt(Object ttt) {
            this.ttt = ttt;
        }

        public double getRz() {
            return rz;
        }

        public void setRz(double rz) {
            this.rz = rz;
        }

        public int getFsltdz() {
            return fsltdz;
        }

        public void setFsltdz(int fsltdz) {
            this.fsltdz = fsltdz;
        }

        public int getCxxsw() {
            return cxxsw;
        }

        public void setCxxsw(int cxxsw) {
            this.cxxsw = cxxsw;
        }

        public Object getSts() {
            return sts;
        }

        public void setSts(Object sts) {
            this.sts = sts;
        }
    }
}
