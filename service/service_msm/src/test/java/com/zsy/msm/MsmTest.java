package com.zsy.msm;

import com.zsy.msm.utils.EmilUtils;
import org.junit.Test;

/**
 * @ClassName MsmTest
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/20 9:53 PM
 * @Version 1.0
 */

public class MsmTest {

    @Test
    public void test(){
        StringBuffer strContent = new StringBuffer();
        strContent.append("<h3>验证您是该电子邮件地址的所有者</h3><br>")
                .append("4594143@qq.com")
                .append("<br><br>最近有人在验证电子邮件地址时输入了该电子邮件地址。<br><br>")
                .append("您可以使用此验证码来验证您是该电子邮件地址的所有者。<br><br>")
                .append("<b>6379</b><br>")
                .append("如果这不是您本人所为，则可能是有人误输了您的电子邮件地址。请勿将此验证码泄露给他人，并且您目前无需执行任何其它操作。<br><br>")
                .append("elon 团队敬上!");
        String res = EmilUtils.sendMail("4594143@qq.com", "elon注册验证", strContent.toString());
        System.out.println(res);


    }
}