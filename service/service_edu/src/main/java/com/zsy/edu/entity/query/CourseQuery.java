package com.zsy.edu.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CourseQuery
 * @Description 也成查询条件
 * @Author mybook
 * @Date 2020/5/6 10:42 PM
 * @Version 1.0
 */

@Data
@ApiModel("课程查询对象")
public class CourseQuery implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

}
