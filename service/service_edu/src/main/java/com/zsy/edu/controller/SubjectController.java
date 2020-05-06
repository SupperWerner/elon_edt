package com.zsy.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Subject;
import com.zsy.edu.entity.vo.SubjectVO;
import com.zsy.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zsy
 * @since 2020-04-16
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 添加课程
     *
     * @param subject
     * @return
     */
    @ApiOperation("添加课程")
    @PostMapping("save")
    public ResModel saveSubject(@ApiParam("接收课程分类信息") @RequestBody() Subject subject) {
        Subject isExist = existSubjectName(subject.getTitle(), subject.getParentId());
        if (isExist == null) {
            boolean flag = subjectService.save(subject);
            if (flag)
                return ResModel.success().data("msg", "课程分类添加成功");
            return ResModel.error().data("msg", "添加失败");
        }
        return ResModel.success().data("msg", "以存在数据库中");


    }

    /**
     * 获取课程分类所有根节点信息
     *
     * @return
     */
    @ApiOperation("获取课程分类所有根节点信息")
    @GetMapping("getRootSubject")
    public ResModel getRootSubject() {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title");
        wrapper.eq("parent_id", "0");
        List<Subject> rootSubjectList = subjectService.list(wrapper);
        return ResModel.success().data("data", rootSubjectList);
    }

    @ApiOperation("获取指定节点下的所有课程分类")
    @GetMapping("getTwoSubject/{id}")
    public ResModel getTwoSubject(@PathVariable String id){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.select("id","title");
        wrapper.eq("parent_id",id);
        List<Subject> twoSubject = subjectService.list(wrapper);
        return ResModel.success().data("data",twoSubject);

    }
    /**
     * 通过Excel文件批量导入课程分类数据
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "通过Excel文件批量导入课程分类数据")
    @PostMapping("importExcel")
    public ResModel importExcel(@ApiParam(value = "上传ecxel文件", required = true) @RequestParam("file") MultipartFile file) {
        return subjectService.exportExcel(file, subjectService);
    }

    /**
     * 获取tree格式的课程分类信息
     *
     * @return
     */
    @GetMapping("getSubjectTree")
    @ApiOperation("获取Tree结构的课程分类信息")
    public ResModel getSubjectTree() {
        List<Subject> subjects = subjectService.list(null);  // 获取所有数据
        List<SubjectVO> treeModelSubject = getTreeModelSubject2("0", subjects);
        return ResModel.success().data("data", treeModelSubject);
    }


    /**
     * 获取指定id的课程分类信息
     *
     * @param id
     * @return
     */
    @GetMapping("getSubjectById/{id}")
    @ApiOperation("获取指定id的课程分类信息")
    public ResModel getSubjectById(@ApiParam("课程分类id") @PathVariable String id) {
        Subject subject = subjectService.getById(id);
        return ResModel.success().data("data", subject);
    }

    /**
     * 修改课程分类
     *
     * @param subject
     * @return
     */
    @PostMapping("editSubject")
    @ApiOperation("修改课程分类")
    public ResModel editSubect(@ApiParam("修改后的课程分类信息")@RequestBody Subject subject) {
        Subject isExist = existSubjectName(subject.getTitle(), subject.getParentId());
        if (isExist == null) {
            QueryWrapper<Subject> wrapper = new QueryWrapper<>();
            wrapper.eq("id", subject.getId());
            boolean update = subjectService.update(subject, wrapper);
            if (update)
                return ResModel.success().data("msg", "修改课程分类成功");
            return ResModel.error().data("msg", "修改课程分类失败");
        }
        return ResModel.success().data("msg", "以存在数据库中");


    }

    /**
     * 删除指定Id的课程分类信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @ApiOperation("删除指定Id的课程分类信息")
    public ResModel deleteSubjectById(@ApiParam("被删除的课程分类id值") @PathVariable String id) {
        boolean flag = subjectService.removeById(id);
        if (flag)
            return ResModel.success().data("msg", "删除课程分类成功");
        return ResModel.success().data("msg", "删除课程分类失败");
    }

    // ======================================定义内部方法=====================================

    /**
     * 遍历所有数据 并改变数据对象格式  List<Subject>  ==> List<SubjectVO>
     *
     * @return
     */
    private List<SubjectVO> getTreeModelSubject2(String parentId, List<Subject> subjects) {
        List<SubjectVO> subjectVOList = new ArrayList<SubjectVO>();
        List<Subject> collect = subjects.stream()
                .filter(subject -> Objects.equals(subject.getParentId(), parentId)).collect(Collectors.toList());
        if (collect != null && collect.size() != 0) {
            collect.stream()
                    .forEach(subject -> {
                        SubjectVO subjectVO = new SubjectVO();
                        subjectVO.setId(subject.getId());
                        subjectVO.setLabel(subject.getTitle());
                        subjectVO.setChildren(getTreeModelSubject2(subject.getId(), subjects));
                        subjectVOList.add(subjectVO);
                    });
            return subjectVOList;
        } else {
            return null;
        }
    }


    /**
     * 递归的方式获取Tree结构的课程分类信息
     *
     * @param parentId
     * @return
     */
    @Deprecated
    private List<SubjectVO> getTreeModelSubject(String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<Subject> list = subjectService.list(wrapper);
        if (list == null)
            return null;
        List<SubjectVO> subjectList = new ArrayList<>();
        list.forEach(x -> {
            SubjectVO vo = new SubjectVO();
            vo.setId(x.getId());
            vo.setLabel(x.getTitle());
            vo.setChildren(getTreeModelSubject(x.getId()));
            subjectList.add(vo);
        });
        return subjectList;
    }

    /**
     * 判断是否已经存在于数据库中
     *
     * @param subjectName
     * @return
     */
    private Subject existSubjectName(String subjectName, String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", subjectName);
        wrapper.eq("parent_id", parentId);
        return subjectService.getOne(wrapper);
    }
}

