package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.daomin.Role;
import com.itheima.daomin.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SystemDao {
    // 系统设置分页模糊查询
    Page<User> selectByCondition(String queryString);

    // 查询所有的角色
    List<Role> findAllRoles();

    // 添加用户
    void addUser(User user);

    // 添加用户与角色的主外键
    void addPRIMARY_KEY(List<Map<String, Object>> list);

    // 查询用户是否重复
    List<User> isrepetition(String username);

    // 删除中间表
    void deleteByIdMiddle(Integer id);

    //删除用户
    void deleteByIdUser(Integer id);

    // 删除选中的中间表
    void deleteAllListMiddle(List<Integer> list);

    //删除选中的用户
    void deleteAllListUser(List<Integer> list);

    public Set<Role> findByUserId(Integer userId);

    List<Role> findAll();


    Page<Role> findAllRole(String queryString);

    void addRole(Role role);


    void addMenuIds(List<Map<String, Object>> list);

    void addPermissionIds(List<Map<String, Object>> list);

    void deleteByIdMenuIds(Integer id);

    void deleteByIdPermissionIds(Integer id);

    void deleteByIdRole(Integer id);

    Role findById(Integer id);

    List<Integer> findMenuIdsById(Integer id);

    List<Integer> findPermissionIdsById(Integer id);

    void edit(Role role);
}
