package com.itheima.service;

import com.itheima.daomin.Role;
import com.itheima.daomin.User;
import com.itheima.entity.PageResult;

import java.util.List;

// 用户
public interface SystemService {
    // 分页条件查询所有的用户
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    // 查询所有角色
    List<Role> findAllRoles();

    // 添加用户  和  选择用户的角色
    void addUserAndRole(User user, Integer[] roleIds);

    // 查询用户名是否重复
    List<User> isrepetition(String username);

    // 删除
    void deleteById(Integer id);

    // 删除选中的
    void deleteAllList(List<Integer> listid);
}
