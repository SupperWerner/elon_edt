package com.zsy.vod.controller;

import com.zsy.commonutils.ResModel;
import com.zsy.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName VodController
 * @Description 阿里云视频点播
 * @Author mybook
 * @Date 2020/5/9 12:22 PM
 * @Version 1.0
 */
@Api(description = "aliyun视频点播")
@RestController
@RequestMapping("vod/video")
@CrossOrigin
@Slf4j
public class VodController {
    @Autowired
    private VodService vodService;

    @PostMapping("uploadVideo")
    @ApiOperation("上传视频并返回保存后的视频id")
    public ResModel uploadVideo(@ApiParam("上传的视频文件")@RequestParam("file") MultipartFile file){
        return vodService.uploadVideoAly(file);
    }

    @DeleteMapping("{videoId}")
    @ApiOperation("删除云视频")
    public ResModel removeVideo(@ApiParam("云视频id")@PathVariable String videoId){
        log.info("执行删除方法");
        return vodService.myRemoveVideo(videoId);
    }

    @DeleteMapping("/deleteByVodIdList")
    @ApiOperation("批量删除视频 根据VodId集合")
    public ResModel deleteByVodIdList(@RequestParam List<String> vodIds){
        log.info("执行批量删除方法");
        return vodService.myDeleteByVodIdList(vodIds);
    }

}
