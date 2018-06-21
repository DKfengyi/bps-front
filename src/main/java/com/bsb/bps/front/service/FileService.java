package com.bsb.bps.front.service;

import com.bsb.bps.apiresponse.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/14 17:09
 * @Description: 图片
 * @JDK: 1.7
 */
public interface FileService {

    /**
     * 保存身份证图片
     *
     * @param file
     * @return
     */
    ApiResponse<List<String>> upLoadImage(MultipartFile[] file);
}
