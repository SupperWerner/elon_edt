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
        EmilUtils.sendMail("4594143@qq.com","用户注册","你好,您注册本站账号的验证码是:8899");
    }
}
