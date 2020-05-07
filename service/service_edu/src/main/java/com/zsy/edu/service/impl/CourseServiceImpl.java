package com.zsy.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Course;
import com.zsy.edu.entity.CourseDescription;
import com.zsy.edu.entity.query.CourseQuery;
import com.zsy.edu.entity.vo.CoursePublishVO;
import com.zsy.edu.entity.vo.CourseVO;
import com.zsy.edu.mapper.CourseMapper;
import com.zsy.edu.service.ChapterService;
import com.zsy.edu.service.CourseDescriptionService;
import com.zsy.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.servicebase.exceptionHandler.ElonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2020-04-28
 */
@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Autowired
    private ChapterService chapterService;


    /**
     * @Author zsy
     * @Description 分页查询用户信息
     * @Date 10:57 PM 2020/5/6
     * @Param [page, courseQuery]
     * @return void
     **/
    @Override
    public void myFindCoursePage(Page<Course> page, CourseQuery courseQuery) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        /* 组装查询信息*/
        if (StringUtils.isNotBlank(courseQuery.getSubjectId())){
            wrapper.eq("subject_id",courseQuery.getSubjectId());
        }else if (StringUtils.isNotBlank(courseQuery.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseQuery.getSubjectParentId());
        }else if (StringUtils.isNotBlank(courseQuery.getTeacherId())){
            wrapper.eq("teacher_id",courseQuery.getTeacherId());
        }else if (StringUtils.isNotBlank(courseQuery.getTitle())){
            wrapper.like("title",courseQuery.getTitle());
        }
        this.baseMapper.selectPage(page, wrapper);
    }

    /**
     * 保存课程基本信息
     * @param courseVO
     * @return
     */
    @Override
    public ResModel mySave(CourseVO courseVO) {
        Course course = courseVO.getCourse();

        log.debug("获取到课程信息为:{}",courseVO);
        course.setStatus(Course.COURSE_DRAFT);

        int insertCourse = this.baseMapper.insert(course);  // 添加课程信息

        String id = course.getId(); // 获取自动生成的id值

        log.debug("是否添加返回值为:{}",insertCourse);
        log.debug("获取的id为:{}",id);
        if(insertCourse==0||StringUtils.isEmpty(id)){   // 如果添加返回值为零 或者 获取的id为null时 则报错
            throw new ElonException(20001,"课程信息添加失败!");
        }


        courseVO.getCourseDescription().setId(id);  // 设置关联id
        // 反之则添加课程简介
        boolean flag = courseDescriptionService.save(courseVO.getCourseDescription());
        if (!flag)
            throw new ElonException(20001,"课程简介添加失败");

        return ResModel.success().data("id",course.getId());
    }

    @Override
    public ResModel myUpload(CourseVO courseVO) {
        // 更新课程基本信息
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        Course course = courseVO.getCourse();
        courseWrapper.eq("id",course.getId());
        int flag = this.baseMapper.update(course, courseWrapper);

        // 更新课程简介
        CourseDescription courseDescription = courseVO.getCourseDescription();
        QueryWrapper<CourseDescription> descriptionWrapper = new QueryWrapper<>();
        descriptionWrapper.eq("id",courseDescription.getId());
        boolean flagDesc = courseDescriptionService.update(courseDescription, descriptionWrapper);

        return ResModel.success().data("id",course.getId());
    }

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    @Override
    public ResModel myCourseAndCourseDescriptionById(String id) {

        // 获取基本信息对象
        Course course = this.baseMapper.selectById(id);

        // 获取课程简介对象
        QueryWrapper<CourseDescription> wrapper = new QueryWrapper<>();
        wrapper.select("id","description");
        wrapper.eq("id",id);
        CourseDescription courseDescription = courseDescriptionService.getOne(wrapper);
        CourseVO courseVO = new CourseVO();
        courseVO.setCourse(course);
        courseVO.setCourseDescription(courseDescription);
        return ResModel.success().data("courseVO",courseVO);
    }

    /**
     * @Author zsy
     * @Description 获取课程提交信息
     * @Date 5:12 PM 2020/5/2
     * @Param [courseId]
     * @return com.zsy.commonutils.ResModel
     **/
    @Override
    public ResModel myGetCoursePublishInfoById(String courseId) {
        CoursePublishVO coursePublish = this.baseMapper.myGetCoursePublishInfoById(courseId);
        return ResModel.success().data("coursePublish",coursePublish);
    }

    /**
     * @Author zsy
     * @Description 发布课程
     * @Date 6:10 PM 2020/5/2
     * @Param [id]
     * @return boolean
     **/
    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        Integer count = baseMapper.updateById(course);
        return null != count && count > 0;
    }

    /**
     * @Author zsy
     * @Description 删除课程信息相关的所有信息
     * @Date 3:03 PM 2020/5/7
     * @Param [id]
     * @return boolean
     **/
    @Override
    public boolean myDeleteCourse(String id) {
        boolean flagChapter = chapterService.myRemoveByCourseId(id); // 删除课程章节与课时视频信息
        boolean flagDescription = courseDescriptionService.removeById(id); // 删除课程描述信息
        int i = baseMapper.deleteById(id); // 删除基本信息
        return flagChapter&&flagDescription&&i>0;
    }
}
