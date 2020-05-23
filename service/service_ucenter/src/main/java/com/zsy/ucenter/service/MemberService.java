package com.zsy.ucenter.service;

import com.zsy.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.ucenter.entity.vo.LoginVO;
import com.zsy.ucenter.entity.vo.RegisterVO;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zsy
 * @since 2020-05-21
 */
public interface MemberService extends IService<Member> {

    String login(LoginVO loginVo);

    boolean register(RegisterVO registerVo);

    Member getByOpenid(String openid);
}
