package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.daomin.CheckGroup;
import com.itheima.daomin.CheckItem;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {
    @Reference
    private CheckGroupService service;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

        // 获取检查组的id  添加主键
        Integer checkGroupId = 0;
        try {
            service.add(checkGroup);
            checkGroupId = service.maxid() + 0;
            service.addPRIMARY_KEY(checkGroupId, checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
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

    // 回显
    @RequestMapping("/findById")
    public Result findById(Integer id) {

        //        System.out.println(id);
        try {
            CheckGroup data = service.findById(id);
//            System.out.println(data.getSex());
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, data);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //   根据检查组合id查询对应的所有检查项id  (打钩)
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {
        try {
            List<Integer> list = service.findCheckItemIdsByCheckGroupId(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        //update_t_checkgroup 修改检查组的表
        //update_t_checkgroup_checkitem 修改关联表

        Integer checkGroupId = checkGroup.getId();
        try {
            // 更新检查组的信息
            service.update_t_checkgroup(checkGroup);
            // 删除中间表的信息
            service.delete_t_checkgroup_checkitem(checkGroupId);
            // 重新添加中间表的信息
            service.addPRIMARY_KEY(checkGroupId, checkitemIds);

            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
// 删除检查组
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            service.delete_t_checkgroup_checkitem(id);
            service.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant. DELETE_CHECKGROUP_SUCCESS);
        }
    }

    // 查询所有的检查组 回显到套餐
    @RequestMapping("/findAll")
    public Result findAll( ) {
        List<CheckGroup> list = null;
        try {
            list = service.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

}
