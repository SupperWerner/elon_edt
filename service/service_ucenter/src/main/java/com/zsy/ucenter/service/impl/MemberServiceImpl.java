package com.zsy.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsy.commonutils.JwtUtils;
import com.zsy.commonutils.MD5;
import com.zsy.servicebase.exceptionHandler.ElonException;
import com.zsy.ucenter.entity.Member;
import com.zsy.ucenter.entity.vo.LoginVO;
import com.zsy.ucenter.entity.vo.RegisterVO;
import com.zsy.ucenter.mapper.MemberMapper;
import com.zsy.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2020-05-21
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * @Author zsy
     * @Description 邮箱登录
     * @Date 4:12 PM 2020/5/21
     * @Param [loginVo]
     * @return java.lang.String
     **/
    @Override
    public String login(LoginVO loginVo) {
        String email = loginVo.getEmail();
        String pass = loginVo.getPassword();
        // 验证是否为空
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(pass))
            throw new ElonException(20001,"邮箱/密码不许为空");
        // 验证是否有该邮箱与密码以及没有禁用的
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        wrapper.eq("password", MD5.encrypt(pass));

        Member member = baseMapper.selectOne(wrapper);
        if (member==null)
            throw new ElonException(20001,"用户名/密码错误");
        if (member.getIsDisabled())
            throw new ElonException(20001,"用户已被禁用");
        return JwtUtils.getJwtToken(member.getId(),member.getEmail());
    }

    @Override
    public boolean register(RegisterVO registerVo) {
        // 获取注册信息
        String code = registerVo.getCode();
        String email = registerVo.getEmail();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        // 校验参数
        if (StringUtils.isEmpty(code)||
                StringUtils.isEmpty(email)||
                StringUtils.isEmpty(mobile)||
                StringUtils.isEmpty(nickname)||
                StringUtils.isEmpty(password))
            throw new ElonException(20001,"参数不可为空");

        // 校验验证码
        // 从redis获取验证码
        String captcha = redisTemplate.opsForValue().get(email);
        if (ObjectUtils.notEqual(captcha,code))
            throw new ElonException(20001,"验证码不正确");

        // 验证数据库中是否存在该手机号与邮箱
        Integer mobileNum = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", mobile));
        if (mobileNum > 0)
            throw new ElonException(20001,"该手机已注册");
        Integer emailNum = baseMapper.selectCount(new QueryWrapper<Member>().eq("email", email));
        if (emailNum > 0)
            throw new ElonException(20001,"该邮箱已注册");

        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setEmail(email);
        member.setAvatar("http://ssl.gstatic.com/images/branding/product/2x/avatar_circle_blue_32dp.png");
        int flag = baseMapper.insert(member);

        return flag > 0;

    }
}
