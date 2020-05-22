package com.zsy.ucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName LoginVO
 * @Description 登录信息承载对象
 * @Author mybook
 * @Date 2020/5/21 3:23 PM
 * @Version 1.0
 */


@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginVO {
    @ApiModelProperty(value = "手机号")
    private String email;
    @ApiModelProperty(value = "密码")
    private String password;
}