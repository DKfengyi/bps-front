package com.bsb.bps.front.controller;

import com.alibaba.fastjson.JSON;
import com.bsb.bps.apiresponse.ApiResponse;
import com.bsb.bps.front.service.SmsService;
import com.bsb.bps.front.vo.SmsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/14 15:37
 * @Description: 短信相关控制层
 * @JDK: 1.7
 */
@Controller
@RequestMapping("/sms")
public class SmsController {
    private static Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Resource
    private SmsService smsService;

    /**
     * 获取短信验证码
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/sendMsgCode", method = RequestMethod.POST)
    @ResponseBody
    public Object sendMsgCode(@RequestBody SmsVo vo) {
        logger.info("开始获取短信验证码 vo:{}", JSON.toJSONString(vo));
        ApiResponse apiResponse = smsService.getDynamicCode(vo.getMobileNo(), vo.getDynamicCodeType());
        logger.info("获取短信验证码结束 vo:{}", JSON.toJSONString(vo));
        return apiResponse;
    }
}
