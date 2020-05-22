package com.zsy.edu.client;

import com.zsy.commonutils.ResModel;
import com.zsy.edu.client.impl.VodClientImpl;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName VodClient
 * @Description Feign调用Vod方法的接口
 * @Author mybook
 * @Date 2020/5/11 5:59 PM
 * @Version 1.0
 */
@FeignClient(name = "service-vod" ,fallback =  VodClientImpl.class)
@Component
public interface VodClient {
    @DeleteMapping("/vod/video/{videoId}")
    ResModel removeVideo(@ApiParam("云视频id")@PathVariable("videoId") String videoId);

    @DeleteMapping("/vod/video/deleteByVodIdList")
    ResModel deleteByVodIdList(@RequestParam("vodIds") List<String> vodIds);
}
