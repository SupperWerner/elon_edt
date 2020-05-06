package com.zsy.edu.mapper;

import com.zsy.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.edu.entity.vo.CoursePublishVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zsy
 * @since 2020-04-28
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * @Author zsy
     * @Description 查询课程提交信息
     * @Date 5:11 PM 2020/5/2
     * @Param [courseId]
     * @return com.zsy.edu.entity.vo.CoursePublishVO
     **/
    CoursePublishVO myGetCoursePublishInfoById(@Param("id") String id);
}
