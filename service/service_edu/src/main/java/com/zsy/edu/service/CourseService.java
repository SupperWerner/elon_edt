package com.zsy.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.edu.entity.query.CourseQuery;
import com.zsy.edu.entity.vo.CourseVO;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zsy
 * @since 2020-04-28
 */
public interface CourseService extends IService<Course> {

    ResModel mySave(CourseVO courseVO);

    ResModel myCourseAndCourseDescriptionById(String id);

    ResModel myUpload(CourseVO courseVO);

    ResModel myGetCoursePublishInfoById(String courseId);

    boolean publishCourseById(String id);

    void myFindCoursePage(Page<Course> page, CourseQuery courseQuery);
}
