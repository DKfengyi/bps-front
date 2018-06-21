package com.bsb.bps.front.vo;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/19 14:28
 * @Description: 短信VO
 * @JDK: 1.7
 */
public class SmsVo {
    //手机号
    String mobileNo;

    //动态码类型
    String dynamicCodeType;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDynamicCodeType() {
        return dynamicCodeType;
    }

    public void setDynamicCodeType(String dynamicCodeType) {
        this.dynamicCodeType = dynamicCodeType;
    }
}
