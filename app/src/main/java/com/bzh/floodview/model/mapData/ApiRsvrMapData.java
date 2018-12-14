package com.bzh.floodview.model.mapData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/26 10:48
 */
public class ApiRsvrMapData {

    /**
     * state : 0
     * message : null
     * data : {"reservoir":{"stcd":"3090720a","tm":1533100320000,"rz":238.76,"inq":null,"w":0,"otq":null},"reservoirtimeList":[{"tm":1532844000000,"rz":238.32,"w":0}]}
     */
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);

    private int state;
    private Object message;
    private DataBean data;

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
         * reservoir : {"stcd":"3090720a","tm":1533100320000,"rz":238.76,"inq":null,"w":0,"otq":null}
         * reservoirtimeList : [{"tm":1532844000000,"rz":238.32,"w":0}]
         */

        private ReservoirBean reservoir;
        private List<ReservoirtimeListBean> reservoirtimeList;

        public ReservoirBean getReservoir() {
            return reservoir;
        }

        public void setReservoir(ReservoirBean reservoir) {
            this.reservoir = reservoir;
        }

        public List<ReservoirtimeListBean> getReservoirtimeList() {
            return reservoirtimeList;
        }

        public void setReservoirtimeList(List<ReservoirtimeListBean> reservoirtimeList) {
            this.reservoirtimeList = reservoirtimeList;
        }

        public static class ReservoirBean {
            /**
             * stcd : 3090720a
             * tm : 1533100320000
             * rz : 238.76
             * inq : null
             * w : 0
             * otq : null
             */

            private String stcd;
            private long tm;
            private double rz;
            private Object inq;
            private int w;
            private Object otq;

            public String getStcd() {
                return stcd;
            }

            public void setStcd(String stcd) {
                this.stcd = stcd;
            }

            public String getTm() {
                return dateFormat.format(new Date(tm));
            }

            public void setTm(long tm) {
                this.tm = tm;
            }

            public double getRz() {
                return rz;
            }

            public void setRz(double rz) {
                this.rz = rz;
            }

            public Object getInq() {
                return inq;
            }

            public void setInq(Object inq) {
                this.inq = inq;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

            public Object getOtq() {
                return otq;
            }

            public void setOtq(Object otq) {
                this.otq = otq;
            }
        }

        public static class ReservoirtimeListBean {
            /**
             * tm : 1532844000000
             * rz : 238.32
             * w : 0
             */

            private long tm; //时间
            private String rz; //库上水位
            private String w; //蓄水量

            public String getTm() {
                return  dateFormat.format(new Date(tm));
            }

            public void setTm(long tm) {
                this.tm = tm;
            }

            public String getRz() {
                return rz;
            }

            public void setRz(String rz) {
                this.rz = rz;
            }

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }
        }
    }
}
