package com.zsy.edu.entity.vo;

import com.zsy.edu.entity.Course;
import com.zsy.edu.entity.CourseDescription;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Course course;
    private CourseDescription courseDescription;
}
