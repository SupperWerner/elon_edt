package com.zsy.vod.service.impl;

import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.zsy.commonutils.ResModel;
import com.zsy.servicebase.exceptionHandler.ElonException;
import com.zsy.vod.service.VodService;
import com.zsy.vod.util.AliyunVodUtils;
import org.apache.commons.lang3.StringUtils;
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
    /**
     * @Author zsy
     * @Description 上传视频
     * @Date 10:14 PM 2020/5/10
     * @Param [file]
     * @return com.zsy.commonutils.ResModel
     **/
    @Override
    public ResModel uploadVideoAly(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new ElonException(20001,"上传文件获取失败");
        }

        UploadStreamResponse response = AliyunVodUtils.uploadVideoByStream(title, fileName, inputStream); // 上传视频并返回响应数据
        if (response.isSuccess()) {
            return ResModel.success().data("vodId",response.getVideoId());
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            if(StringUtils.isNotBlank(response.getVideoId())){
                return ResModel.success().data("vodId",response.getVideoId()).message(response.getMessage());
            }else{
                return ResModel.error().message(response.getMessage());
            }
            /*System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");*/
        }
    }

    /**
     * @Author zsy
     * @Description 删除视频
     * @Date 10:21 PM 2020/5/10
     * @Param [videoId]
     * @return com.zsy.commonutils.ResModel
     **/
    @Override
    public ResModel myRemoveVideo(String videoId) {
        try {
            AliyunVodUtils.deleteVideo(null,videoId);
        } catch (Exception e) {
            throw new ElonException(20001,"删除失败!");
        }
        return ResModel.success();
    }
}
