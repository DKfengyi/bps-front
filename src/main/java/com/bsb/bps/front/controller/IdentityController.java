package com.bsb.bps.front.controller;

import com.bsb.bps.apiresponse.ApiResponse;
import com.bsb.bps.front.service.FileService;
import com.bsb.bps.front.service.IdentityService;
import com.bsb.bps.front.vo.IdCardInfoVO;
import com.bsb.bps.front.vo.LivingVO;
import com.bsb.bps.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/14 16:48
 * @Description: 认证相关功能接口
 * @JDK: 1.7
 */
@Controller
@RequestMapping("/identity")
public class IdentityController {
    private static Logger logger = LoggerFactory.getLogger(IdentityController.class);

    @Resource
    private FileService fileService;

    @Resource
    private IdentityService identityService;

    /**
     * 保存客户身份证信息
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveIdCardInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object saveIdCardInfo(@RequestParam MultipartFile[] file, HttpServletRequest request) {
        logger.info("开始身份证信息录入");
        ApiResponse<List<String>> apiResponse = fileService.upLoadImage(file);
        if (!apiResponse.check()) {
            logger.info("身份证照片上传失败 code:{},msg:{}", apiResponse.getCode(), apiResponse.getMsg());
            return apiResponse;
        }
        List<String> returnData = apiResponse.getReturnData();
        logger.info("身份证信息录入结束");
        //组装身份证信息
        IdCardInfoVO vo = this.bulidIdCardInfoVO(request);
        ApiResponse<Integer> addInfoApi = identityService.addCustomerInfo(returnData, vo);
        if (!addInfoApi.check()) {
            logger.info("保存客户身份证信息失败");
            return apiResponse;
        }
        logger.info("身份证信息录入结束");
        return addInfoApi;
    }

    /**
     * 组装身份证信息vo
     *
     * @param request
     * @return
     */
    private IdCardInfoVO bulidIdCardInfoVO(HttpServletRequest request) {
        String idcard = request.getParameter("idcard");
        String custName = request.getParameter("custName");
        String idcardOrg = request.getParameter("idcardOrg");
        Date beginDate = DateUtils.formatDateString(request.getParameter("beginDate"));
        Date endDate = DateUtils.formatDateString(request.getParameter("endDate"));
        IdCardInfoVO vo = new IdCardInfoVO();
        vo.setIdcard(idcard);
        vo.setCustName(custName);
        vo.setIdcardOrg(idcardOrg);
        vo.setBeginDate(beginDate);
        vo.setEndDate(endDate);
        return vo;
    }

    /**
     * 保存活体信息并激活
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveLivingAndAct", method = RequestMethod.POST)
    @ResponseBody
    public Object saveLiving(@RequestParam MultipartFile[] file, HttpServletRequest request) {
        logger.info("开始活体信息录入");
        ApiResponse<List<String>> fileApiResponse = fileService.upLoadImage(file);
        if (!fileApiResponse.check()) {
            logger.info("活体照片上传失败 code:{},msg:{}", fileApiResponse.getCode(), fileApiResponse.getMsg());
            return fileApiResponse;
        }
        List<String> returnUrlData = fileApiResponse.getReturnData();
        logger.info("活体信息录入结束");
        logger.info("开始激活客户");
        LivingVO vo = this.buildLivingVO(request, returnUrlData);
        ApiResponse<Integer> apiResponse = identityService.saveLivingInfo(vo);
        if (!apiResponse.check()) {
            logger.info("激活用户失败 userId:{},code:{},msg:{}", vo.getUserId(), apiResponse.getCode(), apiResponse.getMsg());
            return apiResponse;
        }
        logger.info("激活客户结束");
        //TODO 待完善
        return apiResponse;
    }

    /**
     * 组装LivingVO
     *
     * @param request
     * @param urlList
     * @return
     */
    private LivingVO buildLivingVO(HttpServletRequest request, List<String> urlList) {
        LivingVO vo = new LivingVO();
        vo.setUserId(Integer.valueOf(request.getParameter("userId")));
        for (int i = 0; i < urlList.size(); i++) {
            if (i == 0) {
                vo.setLivingUrl1(urlList.get(i));
            } else if (i == 1) {
                vo.setLivingUrl2(urlList.get(i));
            } else if (i == 2) {
                vo.setLivingUrl3(urlList.get(i));
            } else if (i == 3) {
                vo.setLivingUrl4(urlList.get(i));
            } else {
                vo.setLivingUrl5(urlList.get(i));
            }
        }
        return vo;
    }
}
