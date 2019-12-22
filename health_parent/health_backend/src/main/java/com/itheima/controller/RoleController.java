package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.daomin.Menu;
import com.itheima.daomin.Request;
import com.itheima.daomin.Role;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.RoleService;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;


    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageResult pageResult = roleService.pageQuery(currentPage, pageSize, queryString);
        return pageResult;

    }

    @RequestMapping("/findAll")
    public PageResult findAll(@RequestBody QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageResult pageResult = roleService.pageQuery(currentPage, pageSize, queryString);
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Request request) {
        try {
            roleService.add(request);
            return new Result(true, "添加角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加角色失败");
        }

    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id) {
        try {
            roleService.deleteById(id);
            return new Result(true, "删除角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除角色失败");
        }

    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Role role = roleService.findById(id);
            return new Result(true, "", role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "");
        }

    }

    @RequestMapping("/findMenuIdsById")
    public Result findMenuIdsById(Integer id) {
        try {
            List<Integer> ids = roleService.findMenuIdsById(id);
            return new Result(true, "", ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "");
        }

    }

    @RequestMapping("/findPermissionIdsById")
    public Result findPermissionIdsById(Integer id) {
        try {
            List<Integer> ids = roleService.findPermissionIdsById(id);
            return new Result(true, "", ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "");
        }

    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Request request) {
        try {
            roleService.edit(request);
            return new Result(true, "修改角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改角色失败");

        }


    }


}
