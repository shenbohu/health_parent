package com.itheima.service;



import com.itheima.daomin.Permission;
import com.itheima.entity.PageResult;

import java.util.List;

public interface PermissionService {
    // 分页查询所有权限
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
        // 添加权限
    void addPermission(Permission permission);
        // 删除权限
    void deleteById(Integer id);
        // 编辑回显
    Permission findById(Integer id);
        // 修改权限信息
    void update(Permission permission);
        // 批量删除
    void deleteAll(List<Integer> listID);
    // 查询所有权限
    List<Permission> findAll();

}
