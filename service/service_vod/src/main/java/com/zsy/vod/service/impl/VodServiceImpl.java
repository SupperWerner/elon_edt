package com.zsy.vod.service.impl;

import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.zsy.servicebase.exceptionHandler.ElonException;
import com.zsy.vod.service.VodService;
import com.zsy.vod.util.AliyunVodUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName VodServiceImpl
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/9 12:23 PM
 * @Version 1.0
 */
@Service
public class VodServiceImpl implements VodService{
    @Override
    public String uploadVideoAly(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new ElonException(20001,"上传文件获取失败");
        }

        UploadStreamResponse response = AliyunVodUtils.uploadVideoByStream(title, fileName, inputStream);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }

        return null;
    }
}
