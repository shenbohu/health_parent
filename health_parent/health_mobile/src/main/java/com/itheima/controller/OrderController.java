package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.daomin.Order;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/success")
    public Result send4Order(@RequestBody Map map) {
        // 获取用户的手机号
        String telephone = (String) map.get("telephone");
        // 从redis中获取验证码
        String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        // 获取用户输入的验证码
        String validateCode = (String) map.get("validateCode");
        // 校验手机验证码
//        if(code == null || !code.equals(validateCode)) {
//            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
//        }
        Result result =null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result =   orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }

        if(result.getFlag()) {
            try {
                String orderDate = (String) map.get("orderDate");
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
// 预约成功
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        return  orderService.findById(id);

    }



}
