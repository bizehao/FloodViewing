package com.bzh.floodview.model.floodsituation;

import java.io.Serializable;

/**
 * 水库水情基本信息表
 */
public class RsvrTwoLevelBase implements Serializable {
    private String stcd; //测站编号
    private String stnm; //测站名称
    private String adnm; //政区
    private String rvnm; //河流
    private String hnnm; //水系
    private String bsnm; //流域
    private String scale; //规模==========
    private String stlc; //站址
    private String atcunit; //隶属行业单位
    private String admauth; //信息管理单位
    private String tm; //时间
    private String rz; //水位(米)
    private String w; //蓄水量
    private String inq; //入库流量
    private String otq; //出库流量
    private String apdinq; //日均入库流量============
    private String apdotq; //日均出库流量==============
    private String fsltdz; //汛限水位(米)
    private String efsltdz; //超汛限水位
    private String fsltdw; //汛限库容
    private String efsltdw; //超汛限库容
    private String enormz; //超正常高水位
    private String damel; //坝顶高程
    private String ckflz; //校核洪水位
    private String dsflz; //设计洪水位
    private String normz; //正常高水位
    private String ddz; //死水位
    private String actz; //兴利水位
    private String ttcp; //总库容
    private String fldcp; //防洪库容
    private String actcp; //兴利库容
    private String ddcp; //死库容
    private String hhrz; //历史最高水位
    private String hhrztm; //历史最高水位时间
    private String hmxinq; //历史最大入流
    private String rstdr; //历史最大入流时段
    private String hmxinqtm; //历史最大入流出现时间
    private String hmxw; //历史最大蓄水量
    private String hmxotq; //历史最大出流
    private String hmxotqtm; //历史最大出流出现时间
    private String hlrz; //历史最低库水位
    private String hlrztm; //历史最低库水位出现时间
    private String hmninq; //历史最小日均入流
    private String hmninqtm; //历史最小日均入流出现时间
    private String lwa; //低水位告警值=====

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getStnm() {
        return stnm == null ? "" : stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getAdnm() {
        return adnm == null ? "" : adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public String getRvnm() {
        return rvnm == null ? "" : rvnm;
    }

    public void setRvnm(String rvnm) {
        this.rvnm = rvnm;
    }

    public String getHnnm() {
        return hnnm == null ? "" : hnnm;
    }

    public void setHnnm(String hnnm) {
        this.hnnm = hnnm;
    }

    public String getBsnm() {
        return bsnm == null ? "" : bsnm;
    }

    public void setBsnm(String bsnm) {
        this.bsnm = bsnm;
    }

    public String getScale() {
        return scale == null ? "" : scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getStlc() {
        return stlc == null ? "" : stlc;
    }

    public void setStlc(String stlc) {
        this.stlc = stlc;
    }

    public String getAtcunit() {
        return atcunit == null ? "" : atcunit;
    }

    public void setAtcunit(String atcunit) {
        this.atcunit = atcunit;
    }

    public String getAdmauth() {
        return admauth == null ? "" : admauth;
    }

    public void setAdmauth(String admauth) {
        this.admauth = admauth;
    }

    public String getTm() {
        return tm == null ? "" : tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getRz() {
        return rz == null ? "" : rz;
    }

    public void setRz(String rz) {
        this.rz = rz;
    }

    public String getW() {
        return w == null ? "" : w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getInq() {
        return inq == null ? "" : inq;
    }

    public void setInq(String inq) {
        this.inq = inq;
    }

    public String getOtq() {
        return otq == null ? "" : otq;
    }

    public void setOtq(String otq) {
        this.otq = otq;
    }

    public String getApdinq() {
        return apdinq == null ? "" : apdinq;
    }

    public void setApdinq(String apdinq) {
        this.apdinq = apdinq;
    }

    public String getApdotq() {
        return apdotq == null ? "" : apdotq;
    }

    public void setApdotq(String apdotq) {
        this.apdotq = apdotq;
    }

    public String getFsltdz() {
        return fsltdz == null ? "" : fsltdz;
    }

    public void setFsltdz(String fsltdz) {
        this.fsltdz = fsltdz;
    }

    public String getEfsltdz() {
        return efsltdz == null ? "" : efsltdz;
    }

    public void setEfsltdz(String efsltdz) {
        this.efsltdz = efsltdz;
    }

    public String getFsltdw() {
        return fsltdw == null ? "" : fsltdw;
    }

    public void setFsltdw(String fsltdw) {
        this.fsltdw = fsltdw;
    }

    public String getEfsltdw() {
        return efsltdw == null ? "" : efsltdw;
    }

    public void setEfsltdw(String efsltdw) {
        this.efsltdw = efsltdw;
    }

    public String getEnormz() {
        return enormz == null ? "" : enormz;
    }

    public void setEnormz(String enormz) {
        this.enormz = enormz;
    }

    public String getDamel() {
        return damel == null ? "" : damel;
    }

    public void setDamel(String damel) {
        this.damel = damel;
    }

    public String getCkflz() {
        return ckflz == null ? "" : ckflz;
    }

    public void setCkflz(String ckflz) {
        this.ckflz = ckflz;
    }

    public String getDsflz() {
        return dsflz == null ? "" : dsflz;
    }

    public void setDsflz(String dsflz) {
        this.dsflz = dsflz;
    }

    public String getNormz() {
        return normz == null ? "" : normz;
    }

    public void setNormz(String normz) {
        this.normz = normz;
    }

    public String getDdz() {
        return ddz == null ? "" : ddz;
    }

    public void setDdz(String ddz) {
        this.ddz = ddz;
    }

    public String getActz() {
        return actz == null ? "" : actz;
    }

    public void setActz(String actz) {
        this.actz = actz;
    }

    public String getTtcp() {
        return ttcp == null ? "" : ttcp;
    }

    public void setTtcp(String ttcp) {
        this.ttcp = ttcp;
    }

    public String getFldcp() {
        return fldcp == null ? "" : fldcp;
    }

    public void setFldcp(String fldcp) {
        this.fldcp = fldcp;
    }

    public String getActcp() {
        return actcp == null ? "" : actcp;
    }

    public void setActcp(String actcp) {
        this.actcp = actcp;
    }

    public String getDdcp() {
        return ddcp == null ? "" : ddcp;
    }

    public void setDdcp(String ddcp) {
        this.ddcp = ddcp;
    }

    public String getHhrz() {
        return hhrz == null ? "" : hhrz;
    }

    public void setHhrz(String hhrz) {
        this.hhrz = hhrz;
    }

    public String getHhrztm() {

        if (hhrztm!=null){
            return hhrztm.substring(0, hhrztm.length() - 2);
        }
        return null;
    }

    public void setHhrztm(String hhrztm) {
        this.hhrztm = hhrztm;
    }

    public String getHmxinq() {
        return hmxinq == null ? "" : hmxinq;
    }

    public void setHmxinq(String hmxinq) {
        this.hmxinq = hmxinq;
    }

    public String getRstdr() {
        return rstdr == null ? "" : rstdr;
    }

    public void setRstdr(String rstdr) {
        this.rstdr = rstdr;
    }

    public String getHmxinqtm() {
        if (hmxinqtm != null) {
            return hmxinqtm.substring(0, hmninqtm.length() - 2);
        }
        return null;
    }

    public void setHmxinqtm(String hmxinqtm) {
        this.hmxinqtm = hmxinqtm;
    }

    public String getHmxw() {
        return hmxw == null ? "" : hmxw;
    }

    public void setHmxw(String hmxw) {
        this.hmxw = hmxw;
    }

    public String getHmxotq() {
        return hmxotq == null ? "" : hmxotq;
    }

    public void setHmxotq(String hmxotq) {
        this.hmxotq = hmxotq;
    }

    public String getHmxotqtm() {
        if (hmxotqtm!=null){
            return hmxotqtm.substring(0, hmxotqtm.length() - 2);
        }
        return null;
    }

    public void setHmxotqtm(String hmxotqtm) {
        this.hmxotqtm = hmxotqtm;
    }

    public String getHlrz() {
        return hlrz == null ? "" : hlrz;
    }

    public void setHlrz(String hlrz) {
        this.hlrz = hlrz;
    }

    public String getHlrztm() {
        if (hlrztm != null) {
            return hlrztm.substring(0, hlrztm.length() - 2);
        }
        return null;
    }

    public void setHlrztm(String hlrztm) {
        this.hlrztm = hlrztm;
    }

    public String getHmninq() {
        return hmninq == null ? "" : hmninq;
    }

    public void setHmninq(String hmninq) {
        this.hmninq = hmninq;
    }

    public String getHmninqtm() {
        if (hmninqtm != null) {
            return hmninqtm.substring(0, hmninqtm.length() - 2);
        }
        return null;

    }

    public void setHmninqtm(String hmninqtm) {
        this.hmninqtm = hmninqtm;
    }

    public String getLwa() {
        return lwa == null ? "" : lwa;
    }

    public void setLwa(String lwa) {
        this.lwa = lwa;
    }

    @Override
    public String toString() {
        return "RsvrTwoLevelBase{" +
                "stcd='" + stcd + '\'' +
                ", stnm='" + stnm + '\'' +
                ", adnm='" + adnm + '\'' +
                ", rvnm='" + rvnm + '\'' +
                ", hnnm='" + hnnm + '\'' +
                ", bsnm='" + bsnm + '\'' +
                ", scale='" + scale + '\'' +
                ", stlc='" + stlc + '\'' +
                ", atcunit='" + atcunit + '\'' +
                ", admauth='" + admauth + '\'' +
                ", tm='" + tm + '\'' +
                ", rz='" + rz + '\'' +
                ", w='" + w + '\'' +
                ", inq='" + inq + '\'' +
                ", otq='" + otq + '\'' +
                ", apdinq='" + apdinq + '\'' +
                ", apdotq='" + apdotq + '\'' +
                ", fsltdz='" + fsltdz + '\'' +
                ", efsltdz='" + efsltdz + '\'' +
                ", fsltdw='" + fsltdw + '\'' +
                ", efsltdw='" + efsltdw + '\'' +
                ", enormz='" + enormz + '\'' +
                ", damel='" + damel + '\'' +
                ", ckflz='" + ckflz + '\'' +
                ", dsflz='" + dsflz + '\'' +
                ", normz='" + normz + '\'' +
                ", ddz='" + ddz + '\'' +
                ", actz='" + actz + '\'' +
                ", ttcp='" + ttcp + '\'' +
                ", fldcp='" + fldcp + '\'' +
                ", actcp='" + actcp + '\'' +
                ", ddcp='" + ddcp + '\'' +
                ", hhrz='" + hhrz + '\'' +
                ", hhrztm='" + hhrztm + '\'' +
                ", hmxinq='" + hmxinq + '\'' +
                ", rstdr='" + rstdr + '\'' +
                ", hmxinqtm='" + hmxinqtm + '\'' +
                ", hmxw='" + hmxw + '\'' +
                ", hmxotq='" + hmxotq + '\'' +
                ", hmxotqtm='" + hmxotqtm + '\'' +
                ", hlrz='" + hlrz + '\'' +
                ", hlrztm='" + hlrztm + '\'' +
                ", hmninq='" + hmninq + '\'' +
                ", hmninqtm='" + hmninqtm + '\'' +
                ", lwa='" + lwa + '\'' +
                '}';
    }
}
