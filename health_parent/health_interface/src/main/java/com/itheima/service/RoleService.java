package com.itheima.service;

import com.itheima.daomin.Request;
import com.itheima.daomin.Role;
import com.itheima.entity.PageResult;

import java.util.List;

public interface RoleService {
//    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
    List<Role> findAll();


    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void add(Request request);

    void deleteById(Integer id);

    Role findById(Integer id);

    List<Integer> findMenuIdsById(Integer id);

    List<Integer> findPermissionIdsById(Integer id);

    void edit(Request request);
}
