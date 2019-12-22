package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.daomin.CheckGroup;
import com.itheima.daomin.Role;
import com.itheima.daomin.User;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.CheckGroupService;
import com.itheima.service.SystemService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemController {
    @Reference
    private SystemService service;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageResult pageResult = service.pageQuery(currentPage, pageSize, queryString);
        return pageResult;

    }


    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<Role> roles = service.findAllRoles();
            return new Result(true, MessageConstant.cuzscg, roles);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.cuzs);
        }
    }


    @RequestMapping("/add")
    public Result add(@RequestBody User user, Integer[] roleIds) {

        try {
            // 查询用户名是否重复
            List<User> userList = service.isrepetition(user.getUsername());
            int size = userList.size();
            if (size != 0) {
                return new Result(false, MessageConstant.yhmcf);
            }

            String password = user.getPassword();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encode = encoder.encode(password);
            user.setPassword(encode);
            service.addUserAndRole(user, roleIds);
            return new Result(true, MessageConstant.xzyhcg);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.xzyhsb);
        }

    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id) {

        try {
            service.deleteById(id);
            return new Result(true, MessageConstant.scyh);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.scyhsb);
        }
    }

    @RequestMapping("/deleteAll")
    public Result deleteAll(@RequestBody List<User> multipleSelection) {
        List<Integer> listid = new ArrayList<>();
        for (User user : multipleSelection) {
            Integer id = user.getId();
            listid.add(id);
        }
        try {
            service.deleteAllList(listid);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"删除失败");
        }


    }


}
