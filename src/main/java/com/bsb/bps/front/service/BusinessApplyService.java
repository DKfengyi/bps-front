package com.bsb.bps.front.service;

import com.bsb.bps.apiresponse.ApiResponse;
import com.bsb.bps.front.vo.BusinessApplyVO;

public interface BusinessApplyService {
    /**
     * 发起营销操作
     *
     * @param vo
     * @return
     */
    ApiResponse businessApply(BusinessApplyVO vo);
}
