package com.zsy.msm.controller;

import com.zsy.commonutils.ResModel;
import com.zsy.msm.service.MsmApiService;
import com.zsy.msm.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName MsmApiController
 * @Description 发送信息服务
 * @Author mybook
 * @Date 2020/5/21 11:04 AM
 * @Version 1.0
 */

@RestController
@CrossOrigin
@Api(description="发送邮件信息模块")
@RequestMapping("/msm")
public class MsmApiController {
    @Autowired
    private MsmApiService msmApiService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @ApiOperation("发送邮件验证码")
    @GetMapping("/sendEmailCaptcha/{email}/{nickname}")
    public ResModel sendEmailCaptcha(@PathVariable String email,@PathVariable String nickname){
        // 随机生成一段验证码
        if (StringUtils.isEmpty(email)){return ResModel.error().message("邮箱不可为空");}
        String captcha = redisTemplate.opsForValue().get(email);
        if (StringUtils.isEmpty(captcha)) captcha= RandomUtil.getFourBitRandom(); else return ResModel.error().message("验证码已发送,请注意查收");

        boolean flag = msmApiService.sendEmailCaptcha(email,captcha,nickname);
        if (flag) {
            redisTemplate.opsForValue().set(email,captcha,5, TimeUnit.MINUTES);
            return ResModel.success().message("验证码已发送,请注意查收");
        }
        return ResModel.error().message("验证码发送失败,请检查邮箱是否正确");
    }

}
