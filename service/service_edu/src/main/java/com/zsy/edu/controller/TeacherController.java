package com.zsy.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Teacher;
import com.zsy.edu.entity.query.TeacherQuery;
import com.zsy.edu.service.TeacherService;
import io.swagger.annotations.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zsy
 * @since 2020-04-01
 */
@Api(description = "讲师管理")
@CrossOrigin
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value="所有讲师列表")
    @GetMapping("findTeacher")
    public ResModel findTeacher(){
        return ResModel.success().data("data",teacherService.list(null));
    }

    @ApiOperation("分页查询讲师信息")
    @GetMapping("findTeacherPage/{curNum}/{size}")
    public ResModel findTeacherPage(@ApiParam("当前页码")@PathVariable Integer curNum
                                    ,@ApiParam("每页显示数量")@PathVariable Integer size){
        Page<Teacher> page = new Page<>(curNum,size);
        teacherService.page(page, null);
        return ResModel.success().data("page",page);
    }

    @ApiOperation("多条件组合分页查询讲师信息")
    @PostMapping("findTeacherPageCondition/{curNum}/{size}")
    public ResModel findTeacherPageCondition(@ApiParam("当前页码")@PathVariable Integer curNum
                                            ,@ApiParam("每页显示数量")@PathVariable Integer size
                                            ,@ApiParam("查询条件")@RequestBody(required = false) TeacherQuery teacherQuery){
        Page<Teacher> teacherPage = new Page<>(curNum,size);

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        String begin = teacherQuery.getBegin();// 开始时间
        String end = teacherQuery.getEnd();// 结束时间
        Integer level = teacherQuery.getLevel();// 级别
        String name = teacherQuery.getName();// 讲师名称
        if (StringUtils.isNotBlank(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (StringUtils.isNotBlank(end)){
            wrapper.le("gmt_create",end);
        }
        if (StringUtils.isNotBlank(name)){
            wrapper.like("name",name);
        }
        if (level!=null){
            wrapper.eq("level",level);
        }
        wrapper.orderByDesc("gmt_create");
        teacherService.page(teacherPage,wrapper);
        return ResModel.success().data("page",teacherPage);
    }


    @ApiOperation(value = "根据讲师id删除")
    @DeleteMapping("{id}")
    public ResModel deleteTeacher(@ApiParam(value = "讲师id")@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        ResModel res = null ;
        if (flag)
            res = ResModel.success();
        else
            res = ResModel.error();

        return res;
    }
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public ResModel addTeacher(@ApiParam("接收讲师信息")@RequestBody() Teacher teacher){
        boolean flag = teacherService.save(teacher);
        if (flag)
            return ResModel.success();

        return ResModel.error();
    }

    @ApiOperation("根据id查询一个讲师")
    @GetMapping("getTeacher/{id}")
    public ResModel getTeacher(@ApiParam("讲师Id")@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        if (teacher!=null)
            return ResModel.success().data("teacher",teacher);
        return ResModel.error();
    }

    @ApiOperation("修改讲师信息")
    @PostMapping("updateTeacher")
    public ResModel updateTeacher(@RequestBody@ApiParam("接收修改的讲师信息") Teacher teacher){
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("id",teacher.getId());
        boolean flag = teacherService.update(teacher, wrapper);
        if (flag)
            return ResModel.success();
        return ResModel.error();
    }

}

