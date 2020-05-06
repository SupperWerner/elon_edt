package com.zsy.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Chapter;
import com.zsy.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zsy
 * @since 2020-04-28
 */
@Api(description = "课程章节管理")
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {

    @Autowired
    private ChapterService chapterService;


    @GetMapping("getChapterAndVideo/{id}")
    @ApiOperation("获取课程章节与小节信息")
    public ResModel getChapterAndVido(@ApiParam("课程id")@PathVariable String id){
        return chapterService.myGetChapterAndVideo(id);
    }

    @GetMapping("getChapter/{id}")
    @ApiOperation("获取指定id课程章节信息")
    public ResModel getChapter(@ApiParam("课程章节id")@PathVariable String id){
        Chapter chapter = chapterService.getById(id);
        return ResModel.success().data("chapter",chapter);
    }

    @PostMapping("saveChapter")
    @ApiOperation("添加课程章节")
    public ResModel saveChapter(@ApiParam("保存的章节信息")@RequestBody Chapter chapter){
        boolean save = chapterService.save(chapter);
        if(save)
            return ResModel.success();

        return ResModel.error();
    }

    @PostMapping("updateChapter")
    @ApiOperation("修改课程章节")
    public ResModel updateChapter(@ApiParam("修改的章节信息")@RequestBody Chapter chapter){
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("id",chapter.getId());
        boolean update = chapterService.update(chapter,wrapper);
        if(update)
            return ResModel.success();

        return ResModel.error();
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除指定id课程信息")
    public ResModel deleteChapter(@ApiParam("课程章节id")@PathVariable String id){
        boolean flag = chapterService.myRemoveById(id);
        if (flag)
            return ResModel.success();
        return ResModel.error();
    }



}

