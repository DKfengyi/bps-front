package com.bsb.bps.front.service;

import com.bsb.bps.apiresponse.ApiResponse;
import com.bsb.bps.front.vo.IdCardInfoVO;
import com.bsb.bps.front.vo.LivingVO;

import java.util.List;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/15 10:45
 * @Description: 客户信息服务
 * @JDK: 1.7
 */
public interface IdentityService {

    /**
     * 新增客户信息（身份证信息，不包含活体信息）
     *
     * @param idCardUrlList
     * @param idCardInfoVO
     * @return
     */
    ApiResponse<Integer> addCustomerInfo(List<String> idCardUrlList, IdCardInfoVO idCardInfoVO);

    /**
     * 根据客户ID保存活体信息
     *
     * @param vo
     * @return
     */
    ApiResponse<Integer> saveLivingInfo(LivingVO vo);
}
