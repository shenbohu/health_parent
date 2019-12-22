package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.daomin.Menu;

import com.itheima.entity.Result;
import com.itheima.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户操作
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;


    //添加操作
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<Menu> list = menuService.findAll();
            return new Result(true, "",list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "");
        }
    }


}
