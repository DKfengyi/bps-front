package com.bsb.bps.front.service.Impl;

import com.bsb.bps.apiresponse.ApiResponse;
import com.bsb.bps.errorcode.BizExceptionCode;
import com.bsb.bps.front.service.FileService;
import com.smy.framework.core.config.Property;
import com.smy.framework.core.util.DateUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Li Jiulong
 * @Date: 2018/6/14 19:39
 * @Description: 文件操作相关服务
 * @JDK: 1.7
 */
@Service
public class FileServiceImpl implements FileService {
    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private static String UPLOAD_IMAGE_URL = Property.getProperty("uploadImageUrl");

    @Override
    public ApiResponse<List<String>> upLoadImage(MultipartFile[] file) {
        logger.info("上传图片到本地 start");
        if (file == null || file.length == 0) {
            logger.info("上传的文件为空");
            return ApiResponse.exceptionCode(BizExceptionCode.C_FILE_NULL);
        }
        String date = DateUtil.getCurIntDate() + "";
        String dir = UPLOAD_IMAGE_URL + date;
//        String dir = "F:\\img\\";//本地测试地址
        File locFile = new File(dir);
        if (!locFile.exists() && !locFile.isDirectory()) {
            locFile.mkdir();
        }
        List<String> idCardUrlList = new ArrayList<>();
        try {
            for (MultipartFile mf : file) {
                File newFile;
                if (!mf.isEmpty()) {
                    //生成uuid作为文件名称
                    UUID name = UUID.randomUUID();
                    //获得文件类型（可以判断如果不是图片，禁止上传）
                    //String contentType = mf.getContentType();
                    String saveDir = dir + "/" + name + ".png";
                    String returnDir = date + "/" + name + ".png";
                    idCardUrlList.add(returnDir);
                    InputStream ins = mf.getInputStream();
                    newFile = new File(saveDir);
                    inputStreamToFile(ins, newFile);
                }
            }
            logger.info("上传图片到本地 end");
            return ApiResponse.build(idCardUrlList);
        } catch (IOException e) {
            logger.error("上传图片到本地时，获取InputStream异常 e:{}", ExceptionUtils.getStackTrace(e));
            return ApiResponse.exceptionCode(BizExceptionCode.C_SYS_EXCPTION);
        }
    }

    /**
     * 写文件
     *
     * @param ins  输入流
     * @param file 文件
     */
    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            FileOutputStream os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            logger.error("文件转换异常 e:{}", ExceptionUtils.getStackTrace(e));
        }
    }
}
