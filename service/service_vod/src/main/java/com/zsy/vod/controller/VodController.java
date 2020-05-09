package com.zsy.vod.controller;

import com.zsy.commonutils.ResModel;
import com.zsy.vod.service.VodService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName VodController
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/9 12:22 PM
 * @Version 1.0
 */
@Api(description = "aliyun视频点播")
@RestController
@RequestMapping("vod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    public ResModel uploadVideo(MultipartFile file){
        String vodId = vodService.uploadVideoAly(file);
        return ResModel.success().data("vodId",vodId);
    }

}
