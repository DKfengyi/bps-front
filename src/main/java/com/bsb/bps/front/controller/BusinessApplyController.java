package com.bsb.bps.front.controller;

import com.alibaba.fastjson.JSON;
import com.bsb.bps.apiresponse.ApiResponse;
import com.bsb.bps.front.service.BusinessApplyService;
import com.bsb.bps.front.vo.BusinessApplyVO;
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
 * @Date: 2018/6/14 14:08
 * @Description: 营销相关控制层
 * @JDK: 1.7
 */
@Controller
@RequestMapping("/busApply")
public class BusinessApplyController {
    private static Logger logger = LoggerFactory.getLogger(BusinessApplyController.class);

    @Resource
    private BusinessApplyService businessApplyService;

    /**
     * 发起营销
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/startMarketing", method = RequestMethod.POST)
    @ResponseBody
    public Object startMarketing(@RequestBody BusinessApplyVO vo) {
        logger.info("开始发起营销 入参 businessApply:{}", JSON.toJSONString(vo));
        ApiResponse apiResponse = businessApplyService.businessApply(vo);
        logger.info("发起营销结束 返回 apiResponse:{}", JSON.toJSONString(apiResponse));
        return apiResponse;
    }
}
