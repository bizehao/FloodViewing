package com.bzh.floodview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 雨强信息
 * @time 2018/11/7 10:38
 */
public class ApiRainStInfo {


    /**
     * state : 0
     * message : null
     * data : {"1hours":[{"stcd":"3030392 ","stnm":"摩天岭","tm":1534316400000,"drp":4.4,"adcd":"130523","adnm":"内丘县","ttt":"14时-15时"},{"stcd":"30956738","stnm":"桃园","tm":1534330800000,"drp":2,"adcd":"130523","adnm":"内丘县","ttt":"18时-19时"},{"stcd":"30956545","stnm":"锁会","tm":1534334400000,"drp":1.5,"adcd":"130582","adnm":"沙河市","ttt":"19时-20时"},{"stcd":"30956556","stnm":"后井","tm":1534334400000,"drp":1.5,"adcd":"130582","adnm":"沙河市","ttt":"19时-20时"},{"stcd":"30956807","stnm":"戎家庄","tm":1534330800000,"drp":1.5,"adcd":"130522","adnm":"临城县","ttt":"18时-19时"},{"stcd":"30932150","stnm":"槲树滩","tm":1534334400000,"drp":1.4,"adcd":"130523","adnm":"内丘县","ttt":"19时-20时"},{"stcd":"30934250","stnm":"任庄","tm":1534320000000,"drp":1.4,"adcd":"130523","adnm":"内丘县","ttt":"15时-16时"},{"stcd":"30932250","stnm":"崇水峪","tm":1534334400000,"drp":1.2,"adcd":"130521","adnm":"邢台县","ttt":"19时-20时"},{"stcd":"30931850","stnm":"册井","tm":1534334400000,"drp":1,"adcd":"130582","adnm":"沙河市","ttt":"19时-20时"},{"stcd":"30932350","stnm":"冀家村","tm":1534334400000,"drp":1,"adcd":"130521","adnm":"邢台县","ttt":"19时-20时"},{"stcd":"30935580","stnm":"双石铺","tm":1534266000000,"drp":1,"adcd":"130522","adnm":"临城县","ttt":"00时-01时"},{"stcd":"30956604","stnm":"大河","tm":1534334400000,"drp":1,"adcd":"130521","adnm":"邢台县","ttt":"19时-20时"},{"stcd":"30956632","stnm":"武家庄","tm":1534334400000,"drp":1,"adcd":"130521","adnm":"邢台县","ttt":"19时-20时"},{"stcd":"30956736","stnm":"大恶石","tm":1534334400000,"drp":1,"adcd":"130523","adnm":"内丘县","ttt":"19时-20时"},{"stcd":"30932150","stnm":"槲树滩","tm":1534330800000,"drp":0.8,"adcd":"130523","adnm":"内丘县","ttt":"18时-19时"},{"stcd":"30932650","stnm":"西枣园","tm":1534334400000,"drp":0.8,"adcd":"130521","adnm":"邢台县","ttt":"19时-20时"},{"stcd":"30956518","stnm":"北沟","tm":1534334400000,"drp":0.5,"adcd":"130582","adnm":"沙河市","ttt":"19时-20时"},{"stcd":"30956541","stnm":"峡沟水库","tm":1534302000000,"drp":0.5,"adcd":"130582","adnm":"沙河市","ttt":"10时-11时"},{"stcd":"30956544","stnm":"册井西北街","tm":1534334400000,"drp":0.5,"adcd":"130582","adnm":"沙河市","ttt":"19时-20时"},{"stcd":"30956552","stnm":"康川","tm":1534334400000,"drp":0.5,"adcd":"130582","adnm":"沙河市","ttt":"19时-20时"},{"stcd":"30956584","stnm":"朱庄村","tm":1534330800000,"drp":0.5,"adcd":"130582","adnm":"沙河市","ttt":"18时-19时"},{"stcd":"30956586","stnm":"孔庄村","tm":1534334400000,"drp":0.5,"adcd":"130582","adnm":"沙河市","ttt":"19时-20时"},{"stcd":"30956608","stnm":"前补透","tm":1534334400000,"drp":0.5,"adcd":"130521","adnm":"邢台县","ttt":"19时-20时"},{"stcd":"30956614","stnm":"塔沟","tm":1534334400000,"drp":0.5,"adcd":"130521","adnm":"邢台县","ttt":"19时-20时"},{"stcd":"30956620","stnm":"李家沟","tm":1534334400000,"drp":0.5,"adcd":"130521","adnm":"邢台县","ttt":"19时-20时"},{"stcd":"30956644","stnm":"龙华","tm":1534330800000,"drp":0.5,"adcd":"130521","adnm":"邢台县","ttt":"18时-19时"},{"stcd":"30956706","stnm":"白鹿角","tm":1534334400000,"drp":0.5,"adcd":"130523","adnm":"内丘县","ttt":"19时-20时"},{"stcd":"30956706","stnm":"白鹿角","tm":1534338000000,"drp":0.5,"adcd":"130523","adnm":"内丘县","ttt":"20时-21时"},{"stcd":"30956730","stnm":"杏峪","tm":1534276800000,"drp":0.5,"adcd":"130523","adnm":"内丘县","ttt":"03时-04时"},{"stcd":"30956807","stnm":"戎家庄","tm":1534316400000,"drp":0.5,"adcd":"130522","adnm":"临城县","ttt":"14时-15时"},{"stcd":"30932175","stnm":"孟家坪","tm":1534302000000,"drp":0.4,"adcd":"130523","adnm":"内丘县","ttt":"10时-11时"},{"stcd":"30932350","stnm":"冀家村","tm":1534320000000,"drp":0.4,"adcd":"130521","adnm":"邢台县","ttt":"15时-16时"},{"stcd":"30935580","stnm":"双石铺","tm":1534334400000,"drp":0.4,"adcd":"130522","adnm":"临城县","ttt":"19时-20时"},{"stcd":"30941090","stnm":"牛庄","tm":1534323600000,"drp":0.4,"adcd":"130535","adnm":"临西县","ttt":"16时-17时"},{"stcd":"30941100","stnm":"清河","tm":1534323600000,"drp":0.4,"adcd":"130534","adnm":"清河县","ttt":"16时-17时"},{"stcd":"3030384 ","stnm":"宋家庄","tm":1534330800000,"drp":0.2,"adcd":"130521","adnm":"邢台县","ttt":"18时-19时"},{"stcd":"3030384 ","stnm":"宋家庄","tm":1534334400000,"drp":0.2,"adcd":"130521","adnm":"邢台县","ttt":"19时-20时"},{"stcd":"30932175","stnm":"孟家坪","tm":1534338000000,"drp":0.2,"adcd":"130523","adnm":"内丘县","ttt":"20时-21时"},{"stcd":"30936580","stnm":"北鱼台","tm":1534312800000,"drp":0.2,"adcd":"130528","adnm":"宁晋县","ttt":"13时-14时"},{"stcd":"30939130","stnm":"堤村","tm":1534323600000,"drp":0.2,"adcd":"130531","adnm":"广宗县","ttt":"16时-17时"},{"stcd":"30939160","stnm":"阎疃","tm":1534309200000,"drp":0.2,"adcd":"130529","adnm":"巨鹿县","ttt":"12时-13时"},{"stcd":"30939180","stnm":"官厅","tm":1534312800000,"drp":0.2,"adcd":"130529","adnm":"巨鹿县","ttt":"13时-14时"},{"stcd":"30939360","stnm":"大村","tm":1534309200000,"drp":0.2,"adcd":"130581","adnm":"南宫市","ttt":"12时-13时"},{"stcd":"30941070","stnm":"老官寨","tm":1534305600000,"drp":0.2,"adcd":"130535","adnm":"临西县","ttt":"11时-12时"}],"3hours":[{"stcd":"30956807","stnm":"戎家庄","tm":null,"drp":1.5,"adcd":"130522","adnm":"临城县","ttt":"18时-21时"},{"stcd":"30956807","stnm":"戎家庄","tm":null,"drp":1.5,"adcd":"130522","adnm":"临城县","ttt":"17时-20时"},{"stcd":"30932150","stnm":"槲树滩","tm":null,"drp":1.4,"adcd":"130523","adnm":"内丘县","ttt":"19时-22时"},{"stcd":"30932350","stnm":"冀家村","tm":null,"drp":1,"adcd":"130521","adnm":"邢台县","ttt":"19时-22时"},{"stcd":"30932350","stnm":"冀家村","tm":null,"drp":1,"adcd":"130521","adnm":"邢台县","ttt":"18时-21时"},{"stcd":"30956706","stnm":"白鹿角","tm":null,"drp":0.5,"adcd":"130523","adnm":"内丘县","ttt":"20时-23时"},{"stcd":"30935580","stnm":"双石铺","tm":null,"drp":0.4,"adcd":"130522","adnm":"临城县","ttt":"19时-22时"},{"stcd":"30935580","stnm":"双石铺","tm":null,"drp":0.4,"adcd":"130522","adnm":"临城县","ttt":"18时-21时"},{"stcd":"3030384 ","stnm":"宋家庄","tm":null,"drp":0.2,"adcd":"130521","adnm":"邢台县","ttt":"19时-22时"},{"stcd":"30932175","stnm":"孟家坪","tm":null,"drp":0.2,"adcd":"130523","adnm":"内丘县","ttt":"20时-23时"},{"stcd":"30932175","stnm":"孟家坪","tm":null,"drp":0.2,"adcd":"130523","adnm":"内丘县","ttt":"19时-22时"}],"6hours":[{"stcd":"30956807","stnm":"戎家庄","tm":null,"drp":1.5,"adcd":"130522","adnm":"临城县","ttt":"15时-21时"},{"stcd":"30956807","stnm":"戎家庄","tm":null,"drp":1.5,"adcd":"130522","adnm":"临城县","ttt":"16时-22时"},{"stcd":"30956807","stnm":"戎家庄","tm":null,"drp":1.5,"adcd":"130522","adnm":"临城县","ttt":"17时-23时"},{"stcd":"30956807","stnm":"戎家庄","tm":null,"drp":1.5,"adcd":"130522","adnm":"临城县","ttt":"18时-00时"},{"stcd":"30932150","stnm":"槲树滩","tm":null,"drp":1.4,"adcd":"130523","adnm":"内丘县","ttt":"19时-01时"},{"stcd":"30932350","stnm":"冀家村","tm":null,"drp":1,"adcd":"130521","adnm":"邢台县","ttt":"16时-22时"},{"stcd":"30932350","stnm":"冀家村","tm":null,"drp":1,"adcd":"130521","adnm":"邢台县","ttt":"17时-23时"},{"stcd":"30932350","stnm":"冀家村","tm":null,"drp":1,"adcd":"130521","adnm":"邢台县","ttt":"18时-00时"},{"stcd":"30932350","stnm":"冀家村","tm":null,"drp":1,"adcd":"130521","adnm":"邢台县","ttt":"19时-01时"},{"stcd":"30956706","stnm":"白鹿角","tm":null,"drp":0.5,"adcd":"130523","adnm":"内丘县","ttt":"20时-02时"},{"stcd":"30935580","stnm":"双石铺","tm":null,"drp":0.4,"adcd":"130522","adnm":"临城县","ttt":"19时-01时"},{"stcd":"30935580","stnm":"双石铺","tm":null,"drp":0.4,"adcd":"130522","adnm":"临城县","ttt":"15时-21时"},{"stcd":"30935580","stnm":"双石铺","tm":null,"drp":0.4,"adcd":"130522","adnm":"临城县","ttt":"16时-22时"},{"stcd":"30935580","stnm":"双石铺","tm":null,"drp":0.4,"adcd":"130522","adnm":"临城县","ttt":"17时-23时"},{"stcd":"30935580","stnm":"双石铺","tm":null,"drp":0.4,"adcd":"130522","adnm":"临城县","ttt":"18时-00时"},{"stcd":"3030384 ","stnm":"宋家庄","tm":null,"drp":0.2,"adcd":"130521","adnm":"邢台县","ttt":"19时-01时"},{"stcd":"30932175","stnm":"孟家坪","tm":null,"drp":0.2,"adcd":"130523","adnm":"内丘县","ttt":"17时-23时"},{"stcd":"30932175","stnm":"孟家坪","tm":null,"drp":0.2,"adcd":"130523","adnm":"内丘县","ttt":"18时-00时"},{"stcd":"30932175","stnm":"孟家坪","tm":null,"drp":0.2,"adcd":"130523","adnm":"内丘县","ttt":"19时-01时"},{"stcd":"30932175","stnm":"孟家坪","tm":null,"drp":0.2,"adcd":"130523","adnm":"内丘县","ttt":"20时-02时"},{"stcd":"30932175","stnm":"孟家坪","tm":null,"drp":0.2,"adcd":"130523","adnm":"内丘县","ttt":"16时-22时"}]}
     */

    private int state;
    private Object message;
    private DataBean data;

    private String subscripttime;//统计图时间

    public String getSubscripttime() {
        return subscripttime;
    }

    public void setSubscripttime(String subscripttime) {
        this.subscripttime = subscripttime;
    }

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
        @SerializedName("1hours")
        private List<_$1hoursBean> _$1hours;
        @SerializedName("3hours")
        private List<_$3hoursBean> _$3hours;
        @SerializedName("6hours")
        private List<_$6hoursBean> _$6hours;

        public List<_$1hoursBean> get_$1hours() {
            return _$1hours;
        }

        public void set_$1hours(List<_$1hoursBean> _$1hours) {
            this._$1hours = _$1hours;
        }

        public List<_$3hoursBean> get_$3hours() {
            return _$3hours;
        }

        public void set_$3hours(List<_$3hoursBean> _$3hours) {
            this._$3hours = _$3hours;
        }

        public List<_$6hoursBean> get_$6hours() {
            return _$6hours;
        }

        public void set_$6hours(List<_$6hoursBean> _$6hours) {
            this._$6hours = _$6hours;
        }

        public static class _$1hoursBean {
            /**
             * stcd : 3030392
             * stnm : 摩天岭
             * tm : 1534316400000
             * drp : 4.4
             * adcd : 130523
             * adnm : 内丘县
             * ttt : 14时-15时
             */

            private String stcd;
            private String stnm;
            private String tm;
            private String drp;
            private String adcd;
            private String adnm;
            private String ttt;

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

            public String getTm() {
                return tm;
            }

            public void setTm(String tm) {
                this.tm = tm;
            }

            public String getDrp() {
                return drp;
            }

            public void setDrp(String drp) {
                this.drp = drp;
            }

            public String getAdcd() {
                return adcd;
            }

            public void setAdcd(String adcd) {
                this.adcd = adcd;
            }

            public String getAdnm() {
                return adnm;
            }

            public void setAdnm(String adnm) {
                this.adnm = adnm;
            }

            public String getTtt() {
                return ttt;
            }

            public void setTtt(String ttt) {
                this.ttt = ttt;
            }
        }

        public static class _$3hoursBean {
            /**
             * stcd : 30956807
             * stnm : 戎家庄
             * tm : null
             * drp : 1.5
             * adcd : 130522
             * adnm : 临城县
             * ttt : 18时-21时
             */

            private String stcd;
            private String stnm;
            private String tm;
            private String drp;
            private String adcd;
            private String adnm;
            private String ttt;

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

            public String getTm() {
                return tm;
            }

            public void setTm(String tm) {
                this.tm = tm;
            }

            public String getDrp() {
                return drp;
            }

            public void setDrp(String drp) {
                this.drp = drp;
            }

            public String getAdcd() {
                return adcd;
            }

            public void setAdcd(String adcd) {
                this.adcd = adcd;
            }

            public String getAdnm() {
                return adnm;
            }

            public void setAdnm(String adnm) {
                this.adnm = adnm;
            }

            public String getTtt() {
                return ttt;
            }

            public void setTtt(String ttt) {
                this.ttt = ttt;
            }
        }

        public static class _$6hoursBean {
            /**
             * stcd : 30956807
             * stnm : 戎家庄
             * tm : null
             * drp : 1.5
             * adcd : 130522
             * adnm : 临城县
             * ttt : 15时-21时
             */

            private String stcd;
            private String stnm;
            private String tm;
            private String drp;
            private String adcd;
            private String adnm;
            private String ttt;

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

            public String getTm() {
                return tm;
            }

            public void setTm(String tm) {
                this.tm = tm;
            }

            public String getDrp() {
                return drp;
            }

            public void setDrp(String drp) {
                this.drp = drp;
            }

            public String getAdcd() {
                return adcd;
            }

            public void setAdcd(String adcd) {
                this.adcd = adcd;
            }

            public String getAdnm() {
                return adnm;
            }

            public void setAdnm(String adnm) {
                this.adnm = adnm;
            }

            public String getTtt() {
                return ttt;
            }

            public void setTtt(String ttt) {
                this.ttt = ttt;
            }
        }
    }
}
