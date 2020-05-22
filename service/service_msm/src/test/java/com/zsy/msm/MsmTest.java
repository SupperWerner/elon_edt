package com.zsy.msm;

import com.zsy.msm.utils.EmilUtils;
import org.junit.Test;

/**
 * @ClassName MsmTest
 * @Description 测试类
 * @Author mybook
 * @Date 2020/5/20 9:53 PM
 * @Version 1.0
 */

public class MsmTest {

    @Test
    public void test(){
        StringBuffer strContent = new StringBuffer();
        strContent.append("<table width=\"100%\" height=\"100%\" style=\"min-width:348px;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr height=\"32px\"></tr><tr align=\"center\"><td><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"padding-bottom: 20px; max-width: 600px; min-width: 220px;\"><tbody><tr><td><table cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td></td><td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"direction: ltr; padding-bottom: 7px;\"><tbody><tr><td align=\"left\"><img width=\"92\" height=\"32\" src=\"https://elon-edu.oss-cn-beijing.aliyuncs.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202020-05-22%20%E4%B8%8B%E5%8D%8812.53.17.png\" style=\"width:92px;height:30px;\"></td><td align=\"right\" style=\"font-family: Roboto-Light,Helvetica,Arial,sans-serif;\">")
                .append("werner yang")
                .append("</td><td align=\"right\" width=\"35\"><img width=\"92\" height=\"32\" src=\"http://ssl.gstatic.com/images/branding/product/2x/avatar_circle_blue_32dp.png\" style=\"width:32px;height:32px;\"></td></tr></tbody></table></td><td></td></tr><tr><td style=\"background:url('https://www.gstatic.com/accountalerts/email/hodor/4-corner-nw.png') top left no-repeat;\" width=\"6\" height=\"5\"><div></div></td><td style=\"background:url('https://www.gstatic.com/accountalerts/email/hodor/4-pixel-n.png') top center repeat-x;\" height=\"5\"><div></div></td><td style=\"background:url('https://www.gstatic.com/accountalerts/email/hodor/4-corner-ne.png') top right no-repeat;\" width=\"6\" height=\"5\"><div></div></td></tr><tr> <td style=\"background:url('https://www.gstatic.com/accountalerts/email/hodor/4-pixel-w.png') center left repeat-y;\" width=\"6\"><div></div></td><td><div style=\"font-family: Roboto-Regular,Helvetica,Arial,sans-serif;padding-left: 20px; padding-right: 20px;border-bottom: thin solid #f0f0f0; color: rgba(0,0,0,0.87); font-size: 24px; padding-top: 40px; text-align: center; word-break: break-word;\"><div class=\"v2sp\"><h3>验证您是该电子邮件地址的所有者</h3><p style=\"font-family: Roboto-Regular,Helvetica,Arial,sans-serif;color: rgba(0,0,0,0.87); font-size: 16px; line-height: 1.8;\"><a href=\"mailto:4594143@qq.com\" rel=\"noopener\" target=\"_blank\">")
                .append("4594143@qq.com")
                .append("</a></p></div></div><div style=\"font-family: Roboto-Regular,Helvetica,Arial,sans-serif; font-size: 13px; color: rgba(0,0,0,0.87); line-height: 1.6;padding-left: 20px; padding-right: 20px;padding-bottom: 32px; padding-top: 24px;\"><p class=\"v2sp\">最近有人在验证电子邮件地址时输入了该电子邮件地址。</p><p>您可以使用此验证码来验证您是该电子邮件地址的所有者。</p><div style=\"font-size:24px;padding-top:20px;padding-bottom:20px;font-weight:bold;text-align:center;\">")
                .append("6379")
                .append("</div><p class=\"v2sp\">如果这不是您本人所为，则可能是有人误输了您的电子邮件地址。请勿将此验证码泄露给他人，并且您目前无需执行任何其它操作。</p><p class=\"v2sp\">elon 帐号团队敬上</p></div></td><td style=\"background:url('https://www.gstatic.com/accountalerts/email/hodor/4-pixel-e.png') center left repeat-y;\" width=\"6\"><div></div></td></tr><tr><td style=\"background:url('https://www.gstatic.com/accountalerts/email/hodor/4-corner-sw.png') top left no-repeat;\" width=\"6\" height=\"5\"><div></div></td><td style=\"background:url('https://www.gstatic.com/accountalerts/email/hodor/4-pixel-s.png') top center repeat-x\" height=\"5\"><div></div></td><td style=\"background:url('https://www.gstatic.com/accountalerts/email/hodor/4-corner-se.png') top left no-repeat;\" width=\"6\" height=\"5\"><div></div></td></tr></tbody></table></td></tr></tbody></table></td></tr><tr height=\"32px\"></tr></tbody></table>");
        String res = EmilUtils.sendMail("4594143@qq.com", "<b>ELON注册验证</b>", strContent.toString());
        System.out.println(res);


    }
}
