package com.bsb.bps.front.service.Impl;

import com.bsb.bps.apiresponse.ApiResponse;
import com.bsb.bps.domain.OlIdcardInfo;
import com.bsb.bps.errorcode.BizExceptionCode;
import com.bsb.bps.front.service.IdentityService;
import com.bsb.bps.front.vo.IdCardInfoVO;
import com.bsb.bps.front.vo.LivingVO;
import com.bsb.bps.service.OlIdCardInfoService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/15 10:46
 * @Description: 客户信息服务实现层
 * @JDK: 1.7
 */
@Service
public class IdentityServiceImpl implements IdentityService {
    private static Logger logger = LoggerFactory.getLogger(IdentityServiceImpl.class);

    @Resource
    private OlIdCardInfoService idCardInfoService;

    @Override
    public ApiResponse<Integer> addCustomerInfo(List<String> idCardUrlList, IdCardInfoVO idCardInfoVO) {
        OlIdcardInfo idCardInfo = new OlIdcardInfo();
        BeanUtils.copyProperties(idCardInfoVO, idCardInfo);
        for (int i = 0; i < idCardUrlList.size(); i++) {
            if (i == 0) {
                //身份证正面图片url
                idCardInfo.setPositiveUrl(idCardUrlList.get(i));
            } else {
                //身份证反面图片url
                idCardInfo.setOppositeUrl(idCardUrlList.get(i));
            }
        }
        try {
            int addNum = idCardInfoService.addIdCardInfo(idCardInfo);
            if (addNum != 1) {
                logger.info("保存身份证信息失败 userId:{}", idCardInfoVO.getUserId());
                return ApiResponse.build(BizExceptionCode.C_CUSTOM_CODE, "身份信息保存异常，请稍后再试！");
            }
        } catch (Exception e) {
            logger.error("保存客户身份证信息 调用RPC接口异常 e:{}", ExceptionUtils.getStackTrace(e));
            return ApiResponse.exceptionCode(BizExceptionCode.C_SYS_EXCPTION);
        }
        return ApiResponse.SUCCESS_RETURN;
    }

    @Override
    public ApiResponse<Integer> saveLivingInfo(LivingVO vo) {
        Integer userId = vo.getUserId();
        try {
            OlIdcardInfo olIdcardInfo = idCardInfoService.queryIdCardInfoByUserNo(userId);
            if (olIdcardInfo == null) {
                logger.info("客户经理信息不存在 userId:{}", userId);
                return ApiResponse.build(BizExceptionCode.C_CUSTOM_CODE, "信息不存在！");
            }
            olIdcardInfo.setLivingUrl1(vo.getLivingUrl1());
            olIdcardInfo.setLivingUrl2(vo.getLivingUrl2());
            olIdcardInfo.setLivingUrl3(vo.getLivingUrl3());
            olIdcardInfo.setLivingUrl4(vo.getLivingUrl4());
            olIdcardInfo.setLivingUrl5(vo.getLivingUrl5());
            int updateNum = idCardInfoService.updateIdCardInfoByCustNo(olIdcardInfo);
            if (updateNum != 1) {
                logger.info("保存活体信息失败 userId:{}", vo.getUserId());
                return ApiResponse.build(BizExceptionCode.C_CUSTOM_CODE, "激活异常，请稍后再试！");
            }
            //TODO 等待风控接口


        } catch (Exception e) {
            logger.info("活体认证激活异常 e:{}", ExceptionUtils.getStackTrace(e));
            return ApiResponse.build(BizExceptionCode.C_CUSTOM_CODE, "激活异常，请稍后再试！");
        }
        //TODO 后续完善
        return ApiResponse.SUCCESS_RETURN;
    }
}
