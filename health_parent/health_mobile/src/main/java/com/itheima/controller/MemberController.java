package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.daomin.Member;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class MemberController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService service;

    @RequestMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map) {
        // 获取用户的手机号
        String telephone = (String) map.get("telephone");
        // 获取用户登录的验证码
        String validateCode = (String) map.get("validateCode");
        // 获取生成的验证码
        String code = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_LOGIN);
        // 校验手机验证码
//        if(code == null || !code.equals(validateCode)) {
//            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
//        }
        // 判断 当前用户是否为会员
        Member member = service.isVip(telephone);
        if (member == null) {
            // 把用户信息存入数据库
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            service.addVIP(member);
        }

        // 登录成功 写入
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setPath("/"); // 路径
        cookie.setMaxAge(60 * 60 * 24 * 30); //有效期
        response.addCookie(cookie);
        String json = JSON.toJSON(member).toString();
        jedisPool.getResource().setex(telephone,60*30,json);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }


}
