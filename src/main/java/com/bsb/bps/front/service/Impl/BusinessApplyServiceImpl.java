package com.bsb.bps.front.service.Impl;

import com.alibaba.fastjson.JSON;
import com.bsb.bps.apiresponse.ApiResponse;
import com.bsb.bps.domain.InitBusinessApply;
import com.bsb.bps.errorcode.BizExceptionCode;
import com.bsb.bps.front.controller.BusinessApplyController;
import com.bsb.bps.front.service.BusinessApplyService;
import com.bsb.bps.front.service.SmsService;
import com.bsb.bps.front.util.ValidateUtil;
import com.bsb.bps.front.vo.BusinessApplyVO;
import com.bsb.bps.service.InitBusinessApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/14 14:23
 * @Description: 营销相关逻辑层
 * @JDK: 1.7
 */
@Service
public class BusinessApplyServiceImpl implements BusinessApplyService {
    private static Logger logger = LoggerFactory.getLogger(BusinessApplyController.class);

    @Resource
    private SmsService smsService;

    @Resource
    private InitBusinessApplyService initBusinessApplyService;

    @Override
    public ApiResponse businessApply(BusinessApplyVO vo) {
        if (vo.getInitUserNo() == null || vo.getProductNo() == null || vo.getMobileNo() == null) {
            logger.info("发起营销的必要参数缺失");
            return ApiResponse.exceptionCode(BizExceptionCode.C_MUST_PARAM_LOST);
        }
        if (!ValidateUtil.isDynamicCode(vo.getDynamicCodeValue())) {
            logger.info("短信验证码格式错误");
            return ApiResponse.exceptionCode(BizExceptionCode.C_DYNAMIC_CODE_FORMAT);
        }
        //验证短信验证码
        ApiResponse<Boolean> booleanApiResponse = smsService.checkDynamicCode(vo.getMobileNo(), vo.getDynamicCodeType(), vo.getDynamicCodeValue());
        if (!booleanApiResponse.check()) {
            logger.info("短信验证码错误 mobileNo:{},code:{},msg:{}", vo.getMobileNo(), booleanApiResponse.getCode(), booleanApiResponse.getMsg());
            return booleanApiResponse;
        }
        InitBusinessApply businessApply = new InitBusinessApply();
        BeanUtils.copyProperties(vo, businessApply);
        logger.info("调用发起营销接口 start businessApply:{}", JSON.toJSONString(businessApply));
        ApiResponse apiResponse = initBusinessApplyService.startMarketing(businessApply);
        logger.info("调用发起营销接口 end");
        return apiResponse;
    }
}
