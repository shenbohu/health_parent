package com.itheima.dao;

import com.itheima.daomin.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {
    Set<Permission> lisePermission(Integer id);

    List<Permission> findAllPermission(String queryString);

    void addPermission(Permission permission);

    void deleteByIdMiddle(Integer id);

    void deleteById(Integer id);

    Permission findById(Integer id);

    void update(Permission permission);

    void deleteAllMiddle(List<Integer> listID);

    void deleteAll(List<Integer> listID);

    List<Permission> findAll();
}
