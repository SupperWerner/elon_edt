package com.zsy.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Course;
import com.zsy.edu.entity.Teacher;
import com.zsy.edu.service.CourseService;
import com.zsy.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ClientController
 * @Description 客户端获取主页信息
 * @Author mybook
 * @Date 2020/5/16 9:20 PM
 * @Version 1.0
 */
@Api("客户端,请求信息")
@RestController
@RequestMapping("/edu/client")
@CrossOrigin
public class ClientController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    @Cacheable(key="#root.methodName",value="host")
    @ApiOperation("获取热门课程 8 个 与 热门讲师4 个")
    @GetMapping("courseAndTeacher")
    public ResModel courseAndTeacher(){
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("view_count");
        courseWrapper.last("limit 8");
        List<Course> courseList = courseService.list(courseWrapper);

        QueryWrapper<Teacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.eq("level",2);
        teacherWrapper.last("limit 4");
        List<Teacher> teacherList = teacherService.list(teacherWrapper);

        Map<String,Object> resMap = new HashMap<>();
        resMap.put("courses",courseList);
        resMap.put("teachers",teacherList);
        return ResModel.success().data(resMap);

    }
}
