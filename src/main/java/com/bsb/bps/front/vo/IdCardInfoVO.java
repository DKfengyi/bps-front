package com.bsb.bps.front.vo;

import java.util.Date;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/15 11:12
 * @Description: 身份证信息VO
 * @JDK: 1.7
 */
public class IdCardInfoVO {
    //用户编号
    private Integer userId;

    //身份证正面照
    private String positiveUrl;

    //身份证反面照片
    private String oppositeUrl;

    //身份证号
    private String idcard;

    //身份证姓名
    private String custName;

    //发证机关
    private String idcardOrg;

    //开始时间
    private Date beginDate;

    //结束日期
    private Date endDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPositiveUrl() {
        return positiveUrl;
    }

    public void setPositiveUrl(String positiveUrl) {
        this.positiveUrl = positiveUrl;
    }

    public String getOppositeUrl() {
        return oppositeUrl;
    }

    public void setOppositeUrl(String oppositeUrl) {
        this.oppositeUrl = oppositeUrl;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIdcardOrg() {
        return idcardOrg;
    }

    public void setIdcardOrg(String idcardOrg) {
        this.idcardOrg = idcardOrg;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
