package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.daomin.Menu;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.apache.zookeeper.server.auth.SaslServerCallbackHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userServce;
    String username = "";

    @RequestMapping("/getUsername")
    public Result getUsername() {
        // 用户名在上下文对象中 底层在session
        // 上下文对象                    认证信息对象                    用户提供的user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            username = user.getUsername();
            // 获取用户的菜单
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
        }
        return new Result(false, MessageConstant.GET_USERNAME_FAIL);
    }

    @RequestMapping("/getMenu")
    public Result getMenu(String username) {
        List<Menu>  menuList = userServce.getMenu(username);
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, menuList);

    }
    @RequestMapping("/getError")
    public Map getError(HttpSession session){
        Map map = new HashMap();
        //1.当SpringSecurity出现错误后，将异常信息存入session，
        // 存入key为 SPRING_SECURITY_LAST_EXCEPTION
        // 存入的value为异常对象
        Exception exception = (Exception)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if(exception!=null){
            //2.取出异常信息，将信息返回到页面
            map.put("errorMsg",exception.getMessage());
        }
        return map;
    }
}
