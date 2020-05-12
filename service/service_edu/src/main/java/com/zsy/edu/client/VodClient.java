package com.zsy.edu.client;

import com.zsy.commonutils.ResModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName VodClient
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/11 5:59 PM
 * @Version 1.0
 */
@FeignClient("service-vod")
@Component
public interface VodClient {
    @DeleteMapping("/vod/video/{videoId}")
    ResModel removeVideo(@ApiParam("云视频id")@PathVariable("videoId") String videoId);

    @DeleteMapping("/vod/video/deleteByVodIdList")
    ResModel deleteByVodIdList(@RequestParam("vodIds") List<String> vodIds);
}
