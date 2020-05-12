package com.zsy.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsy.commonutils.ResModel;
import com.zsy.edu.client.VodClient;
import com.zsy.edu.entity.Video;
import com.zsy.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zsy
 * @since 2020-04-28
 */
@Api(description = "小节管理")
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private VodClient vodClient;

    @GetMapping("getVideo/{id}")
    @ApiOperation("获取指定id的小节信息")
    public ResModel getVideo(@ApiParam("小节Id")@PathVariable String id){
        return ResModel.success().data("video",videoService.getById(id));
    }

    @PostMapping("saveVideo")
    @ApiOperation("添加小节信息")
    public ResModel saveVideo(@ApiParam("小节详细信息")@RequestBody Video video){
        boolean save = videoService.save(video);
        if (save)
            return ResModel.success();
        return ResModel.error();
    }

    @PostMapping("updateVideo")
    @ApiOperation("修改小节信息")
    public ResModel updateVideo(@ApiParam("修改后的小节信息")@RequestBody Video video){
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("id",video.getId());
        boolean update = videoService.update(video, wrapper);
        if (update)
            return ResModel.success();
        return ResModel.error();
    }

    @DeleteMapping("{id}")
    @ApiOperation("根据id删除小节信息")
    public ResModel deleteVideo(@ApiParam("小节id")@PathVariable String id){
        Video vodeo = videoService.getById(id);
        String videoSourceId = vodeo.getVideoSourceId();
        boolean flag = videoService.removeById(id);
        // 删除视频
        if (StringUtils.isNotBlank(videoSourceId)){
            ResModel resModel = vodClient.removeVideo(videoSourceId);
            flag = resModel.isSuccess();
        }
        if (flag)
            return ResModel.success();

        return ResModel.error();
    }


}

