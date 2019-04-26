package com.bzh.floodview.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author 毕泽浩
 * @Description: 汛情摘要 降雨
 * @time 2018/11/9 15:45
 */
public class ApiFloodRain {

    /**
     * state : 0
     * message : null
     * data : {"0 ~ 10":11,"10 ~ 25":23,"25 ~ 50":104,"50 ~ 100":126,"100 ~ 250":3,"250 ~ ":0}
     */

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
        @SerializedName("0 ~ 10")
        private int _$_0107; // FIXME check this code
        @SerializedName("10 ~ 25")
        private int _$_1025292; // FIXME check this code
        @SerializedName("25 ~ 50")
        private int _$_255061; // FIXME check this code
        @SerializedName("50 ~ 100")
        private int _$_501009; // FIXME check this code
        @SerializedName("100 ~ 250")
        private int _$_100250136; // FIXME check this code
        @SerializedName("250 ~ ")
        private int _$_250309; // FIXME check this code

        public int get_$_0107() {
            return _$_0107;
        }

        public void set_$_0107(int _$_0107) {
            this._$_0107 = _$_0107;
        }

        public int get_$_1025292() {
            return _$_1025292;
        }

        public void set_$_1025292(int _$_1025292) {
            this._$_1025292 = _$_1025292;
        }

        public int get_$_255061() {
            return _$_255061;
        }

        public void set_$_255061(int _$_255061) {
            this._$_255061 = _$_255061;
        }

        public int get_$_501009() {
            return _$_501009;
        }

        public void set_$_501009(int _$_501009) {
            this._$_501009 = _$_501009;
        }

        public int get_$_100250136() {
            return _$_100250136;
        }

        public void set_$_100250136(int _$_100250136) {
            this._$_100250136 = _$_100250136;
        }

        public int get_$_250309() {
            return _$_250309;
        }

        public void set_$_250309(int _$_250309) {
            this._$_250309 = _$_250309;
        }
    }
}
