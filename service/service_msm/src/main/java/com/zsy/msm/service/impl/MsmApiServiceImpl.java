package com.zsy.msm.service.impl;

import com.zsy.msm.service.MsmApiService;
import com.zsy.msm.utils.EmilUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName MsmApiServiceImpl
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/21 11:05 AM
 * @Version 1.0
 */
@Service
public class MsmApiServiceImpl implements MsmApiService{
    @Override
    public boolean sendEmailCaptcha(String email, String captcha) {
        StringBuffer strContent = new StringBuffer();
        strContent.append("<h3>验证您是该电子邮件地址的所有者</h3><br>")
                .append(email)
                .append("<br><br>最近有人在验证电子邮件地址时输入了该电子邮件地址。<br><br>")
                .append("您可以使用此验证码来验证您是该电子邮件地址的所有者。<br><br>")
                .append("<b>")
                .append(captcha)
                .append("</b><br>")
                .append("如果这不是您本人所为，则可能是有人误输了您的电子邮件地址。请勿将此验证码泄露给他人，并且您目前无需执行任何其它操作。<br><br>")
                .append("elon 团队敬上!");
        String resinfo = EmilUtils.sendMail(email, "elon注册验证", strContent.toString());
        if ("ok".equals(resinfo)) return true;
        return false;
    }
}
