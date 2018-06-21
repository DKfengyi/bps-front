package com.bsb.bps.front.service.Impl;

import com.bsb.bps.apiresponse.ApiResponse;
import com.bsb.bps.errorcode.BizExceptionCode;
import com.bsb.bps.front.service.SmsService;
import com.bsb.bps.service.OlSmsLogService;
import com.smy.cif.dto.DynamicCodeCheckRequest;
import com.smy.cif.dto.DynamicCodeGetRequest;
import com.smy.cif.service.DynamicSmsService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/14 14:33
 * @Description: 短信服务
 * @JDK: 1.7
 */
@Service
public class SmsServiceImpl implements SmsService {
    private static Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Resource
    private DynamicSmsService dynamicSmsService;

    @Resource
    private OlSmsLogService olSmsLogService;

    @Override
    public ApiResponse getDynamicCode(String mobileNo, String dynamicCodeType) {
        logger.info("请求获取短信验证码 mobileNo:{},dynamicCodeType:{}", mobileNo, dynamicCodeType);
        DynamicCodeGetRequest request = new DynamicCodeGetRequest();
        request.setMobileNo(mobileNo);
        request.setDynamicCodeType(dynamicCodeType);
        try {
            ApiResponse apiResponse = olSmsLogService.addOrUpdateSmsResidueCount(mobileNo, Integer.valueOf(dynamicCodeType));
            if (!apiResponse.check()) {
                logger.info("短信验证码获取前添加记录异常 mobileNo:{},code:{},msg:{}", mobileNo, apiResponse.getCode(), apiResponse.getMsg());
                return apiResponse;
            }
            dynamicSmsService.getDynamicCode(request);
        } catch (Exception e) {
            logger.error("获取短信验证码异常 mobileNo:{},codeType:{},e:{}", mobileNo, dynamicCodeType, ExceptionUtils.getStackTrace(e));
            return ApiResponse.build(BizExceptionCode.C_CUSTOM_CODE, "获取短信验证码异常，请稍后再试！");
        }
        logger.info("成功发送短信验证码");
        return ApiResponse.SUCCESS_RETURN;
    }

    @Override
    public ApiResponse<Boolean> checkDynamicCode(String mobileNo, String dynamicCodeType, String dynamicCodeValue) {
        DynamicCodeCheckRequest request = new DynamicCodeCheckRequest();
        request.setMobileNo(mobileNo);
        request.setDynamicCodeType(dynamicCodeType);
        request.setDynamicCodeValue(dynamicCodeValue);
        boolean result = false;
        try {
            result = dynamicSmsService.checkDynamicCode(request);
            if (!result) {
                logger.info("短信验证失败 mobileNo:{},codeType:{}", mobileNo, dynamicCodeType);
                ApiResponse<Integer> apiResponse = olSmsLogService.getSmsResidueCount(mobileNo, Integer.valueOf(dynamicCodeType));
                Integer residueCount = apiResponse.getReturnData();
                return ApiResponse.build(BizExceptionCode.C_CUSTOM_CODE, "验证码错误，还有" + residueCount + "次获取机会！", result);
            }
        } catch (Exception e) {
            logger.error("校验短信验证码异常 mobileNo:{},codeType:{},codeValue:{},e:{}", mobileNo, dynamicCodeType, dynamicCodeValue, ExceptionUtils.getStackTrace(e));
            return ApiResponse.build(result);
        }
        return ApiResponse.build(result);
    }
}
