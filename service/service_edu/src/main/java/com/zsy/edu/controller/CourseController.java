package com.zsy.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Course;
import com.zsy.edu.entity.query.CourseQuery;
import com.zsy.edu.entity.vo.CourseVO;
import com.zsy.edu.service.CourseDescriptionService;
import com.zsy.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zsy
 * @since 2020-04-28
 */
@Api(description = "课程维护模块")
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;


    @ApiOperation("分页条件查询课程信息")
    @PostMapping("findCoursePage/{curPage}/{size}")
    public ResModel findCoursePage(
            @ApiParam("当前页码")@PathVariable(required = true) Integer curPage,
            @ApiParam("显示条数")@PathVariable(required = true) Integer size,
            @ApiParam("查询条件")@RequestBody CourseQuery courseQuery){
        Page<Course> page = new Page<>(curPage,size);
        courseService.myFindCoursePage(page,courseQuery);
        return ResModel.success().data("page",page);
    }

    @ApiOperation("添加课程详细信息")
    @PostMapping("saveCourse")
    public ResModel saveCourse(@ApiParam("保存的信息对象")@RequestBody CourseVO courseVO){
        return courseService.mySave(courseVO);
    }

    @ApiOperation("更新课程信息")
    @PostMapping("uploadCourse")
    public ResModel uploadCourse(@ApiParam("变更的信息对象")@RequestBody CourseVO courseVO){
        return courseService.myUpload(courseVO);
    }

    @ApiOperation("查询ResModel信息")
    @GetMapping("getCourseAndCourseDescription/{id}")
    public ResModel getCourseAndCourseDescriptionById(@ApiParam("查询id值")@PathVariable String id){
        return courseService.myCourseAndCourseDescriptionById(id);
    }

    @GetMapping("getCoursePublishInfo/{courseId}")
    @ApiOperation("获取课程提交确认信息")
    public ResModel getCoursePublishInfo(@ApiParam(value = "课程id")@PathVariable String courseId){
        return courseService.myGetCoursePublishInfoById(courseId);
    }

    @PutMapping("publish/{id}")
    @ApiOperation(value = "根据id发布课程")
    public ResModel publishCourseById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String id){
        boolean flag = courseService.publishCourseById(id);
        if (flag)
            return ResModel.success();
        return ResModel.error();
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除课程信息")
    public ResModel deleteCourseById(@ApiParam(value = "课程id")@PathVariable String id){
        /*
            删除与该课程相关的所有的数据
         */
        boolean b = courseService.myDeleteCourse(id);
        if (b)
            return ResModel.success();
        return ResModel.error();
    }

}

