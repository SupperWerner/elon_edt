package com.zsy.ucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName RegisterVO
 * @Description 注册信息接收类
 * @Author mybook
 * @Date 2020/5/21 4:07 PM
 * @Version 1.0
 */
@Data
@ApiModel(value="注册对象", description="注册对象")
public class RegisterVO {
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}

