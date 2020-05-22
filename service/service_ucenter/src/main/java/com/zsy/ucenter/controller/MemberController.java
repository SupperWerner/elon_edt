package com.zsy.ucenter.controller;


import com.zsy.commonutils.JwtUtils;
import com.zsy.commonutils.ResModel;
import com.zsy.ucenter.entity.vo.LoginVO;
import com.zsy.ucenter.entity.vo.RegisterVO;
import com.zsy.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zsy
 * @since 2020-05-21
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
@Api(description = "登录注册方法")
public class MemberController {
    @Autowired
    private MemberService memberService;


    // TODO 手机号登录方式

    @ApiOperation("邮件登录")
    @PostMapping("/login")
    public ResModel login(@ApiParam("登录信息")@RequestBody LoginVO loginVo){
        String token = memberService.login(loginVo);
        return ResModel.success().data("token",token);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ResModel register(@ApiParam("注册信息")@RequestBody(required = true) RegisterVO registerVo){
        boolean flag = memberService.register(registerVo);
        if (flag) return ResModel.success();
        return ResModel.error();
    }

    @ApiOperation("根据Token获取会员信息")
    @GetMapping("/getMemberInfo")
    public ResModel getMemnberInfo(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        return ResModel.success().data("data",memberService.getById(id));
    }
}

