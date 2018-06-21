package com.bsb.bps.front.vo;

import com.bsb.bps.domain.InitBusinessApply;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/14 15:50
 * @Description: 营销VO
 * @JDK: 1.7
 */
public class BusinessApplyVO extends InitBusinessApply {
    //短信验证码类型
    private String dynamicCodeType;

    //获取到的短信验证码
    private String dynamicCodeValue;

    public String getDynamicCodeType() {
        return dynamicCodeType;
    }

    public void setDynamicCodeType(String dynamicCodeType) {
        this.dynamicCodeType = dynamicCodeType;
    }

    public String getDynamicCodeValue() {
        return dynamicCodeValue;
    }

    public void setDynamicCodeValue(String dynamicCodeValue) {
        this.dynamicCodeValue = dynamicCodeValue;
    }
}
