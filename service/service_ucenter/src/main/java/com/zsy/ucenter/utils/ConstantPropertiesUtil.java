package com.zsy.ucenter.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName ConstantPropertiesUtil
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/22 11:21 PM
 * @Version 1.0
 */

@Slf4j
@Component
public class ConstantPropertiesUtil implements InitializingBean{
    @Value("${wx.open.app_id}")
    private String appId;
    @Value("${wx.open.app_secret}")
    private String appSecret;
    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    public static  String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("属性赋值操作:{} === {} === {}",this.appId , this.appSecret , this.redirectUrl);
        this.WX_OPEN_APP_ID = this.appId;
        this.WX_OPEN_APP_SECRET = this.appSecret;
        this.WX_OPEN_REDIRECT_URL = this.redirectUrl;
    }
}
