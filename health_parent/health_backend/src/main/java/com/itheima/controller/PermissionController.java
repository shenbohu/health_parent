package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.daomin.Permission;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService service;

    @RequestMapping("/findPage")
    public PageResult findAll(@RequestBody QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageResult pageResult = service.pageQuery(currentPage, pageSize, queryString);
        return pageResult;

    }


    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission) {
        if (permission == null) {
            return new Result(false, "添加权限失败");
        }
        try {
            service.addPermission(permission);
            return new Result(true, "添加权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加权限失败");
        }
    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id) {
        if (id == null) {
            return new Result(false, "删除失败");
        }
        try {
            service.deleteById(id);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除权限失败");
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {

        try {
            Permission permission = service.findById(id);
            return new Result(true, "", permission);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Permission permission) {
        if (permission == null) {
            return new Result(false, "");
        }
        try {
            service.update(permission);
            return new Result(true, "修改权限信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改权限信息失败");
        }

    }

    @RequestMapping("/deleteAll")
    public Result deleteAll(@RequestBody List<Permission> permissionList) {
        if (permissionList == null) {
            return new Result(false, "");
        }
        List<Integer> listID = new ArrayList<>();
        for (Permission permission : permissionList) {
            Integer id = permission.getId();
            listID.add(id);
        }
        try {
            service.deleteAll(listID);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        List<Permission> list = service.findAll();
        return new Result(true, "",list);

    }


}
