package com.zsy.ucenter.controller;

import com.google.gson.Gson;
import com.zsy.commonutils.JwtUtils;
import com.zsy.servicebase.exceptionHandler.ElonException;
import com.zsy.ucenter.entity.Member;
import com.zsy.ucenter.service.MemberService;
import com.zsy.ucenter.utils.ConstantPropertiesUtil;
import com.zsy.ucenter.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @ClassName WechatController
 * @Description 微信扫码登录
 * @Author mybook
 * @Date 2020/5/22 11:02 PM
 * @Version 1.0
 */
@CrossOrigin
@Controller
@Api(description = "微信登录")
@RequestMapping("/api/ucenter/wx")
@Slf4j
public class WechatController {
    @Autowired
    private MemberService memberService;
    /**
     * @Author zsy
     * @Description 获取二维码登录
     * @Date 12:11 AM 2020/5/23
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("/QRCode")
    public String QRCode(){
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String appId = ConstantPropertiesUtil.WX_OPEN_APP_ID;


        String url = String.format(baseUrl, appId, redirectUrl, "atguigu");
        log.info(url);
        return "redirect:" + url;
    }

    @GetMapping("callback")
    public String callback(String code,String state){

        // 拼接请求
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        baseAccessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        String result = null;

        // 发送请求
        try {
            result = HttpClientUtils.get(baseAccessTokenUrl);
        } catch (Exception e) {
            throw new ElonException(20001,"获取微信请求令牌失败!");
        }

        // 解析响应
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        Member member = memberService.getByOpenid(openid);
        // 判断是否从来没有登录过
        if (member == null) {
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new ElonException(20001,"获取微信用户信息失败!");
            }

            HashMap userMap = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = userMap.get("nickname").toString();
            String headimgurl = userMap.get("headimgurl").toString();

            //向数据库中插入一条记录
            member = new Member();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            member.setEmail("");
            memberService.save(member);

        }
        // TODO 登录

        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
        return "redirect:http://localhost:3000?token="+token;
    }

}
