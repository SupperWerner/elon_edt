package com.zsy.edu.entity.vo;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("课程发布信息")
@Data
public class CoursePublishVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("课程封面")
    private String cover;

    @ApiModelProperty("课时数")
    private Integer lessonNum;

    @ApiModelProperty("课程一级分类")
    private String subjectLevelOne;

    @ApiModelProperty("课程二级分类")
    private String subjectLevelTwo;

    @ApiModelProperty("讲师名称")
    private String teacherName;

    @ApiModelProperty("课程价格")
    private String price;//只用于显示
}
