package com.bsb.bps.front.service;

import com.bsb.bps.apiresponse.ApiResponse;

public interface SmsService {
    /**
     * 获取短信验证码
     *
     * @param mobileNo        手机号
     * @param dynamicCodeType 验证码类型
     * @return
     */
    ApiResponse getDynamicCode(String mobileNo, String dynamicCodeType);

    /**
     * 验证短信验证码
     *
     * @param mobileNo         手机号
     * @param dynamicCodeType  验证码类型
     * @param dynamicCodeValue 收到的短信验证码
     * @return
     */
    ApiResponse<Boolean> checkDynamicCode(String mobileNo, String dynamicCodeType, String dynamicCodeValue);
}
