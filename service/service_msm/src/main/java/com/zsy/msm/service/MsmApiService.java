package com.zsy.msm.service;

/**
 * @ClassName MsmApiService
 * @Description
 * @Author mybook
 * @Date 2020/5/21 11:05 AM
 * @Version 1.0
 */
public interface MsmApiService {

    boolean sendEmailCaptcha(String email, String captcha, String nickname);
}
