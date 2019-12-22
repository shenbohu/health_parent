package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.daomin.CheckItem;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.CheckItemService;
import org.aspectj.bridge.MessageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//
@RestController
@RequestMapping("/checkitem")

public class CheckItemController {
    @Reference
    private CheckItemService service;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        // @RequestBody  注意  不写这个是傻逼   2小时了

        try {
            service.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageResult pageResult = service.pageQuery(currentPage, pageSize, queryString);
        return pageResult;
    }
    @PreAuthorize("hasAnyAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        // 判断当前删除项目是否关联到检查组
        System.out.println(id);
        try {
            service.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    // 修改回显
    @RequestMapping("/echo")
    public Result echo(Integer id) {
//        System.out.println(id);
        try {
            CheckItem data = service.echo(id);
//            System.out.println(data.getSex());
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS, data);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }
//update 修改
    @RequestMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {
        try {
            service.update(checkItem);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

  //新增检查项 查询所有的检查项信息
     @RequestMapping("/findAll")
     public Result findAll(){
        List<CheckItem> list = null;
         try {
             list = service.findAll();
             return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
         } catch (Exception e) {
             e.printStackTrace();
             return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
         }

     }
}
