package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SystemDao;
import com.itheima.daomin.Request;
import com.itheima.daomin.Role;
import com.itheima.daomin.User;
import com.itheima.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SystemDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Role> page = roleDao.findAllRole(queryString);
        long total = page.getTotal();
        List<Role> roles = page.getResult();
        return new PageResult(total, roles);
    }

    @Override
    public void add(Request request) {
        Role role = request.getRole();
        Integer[] menuIds = request.getMenuIds();
        Integer[] permissionIds = request.getPermissionIds();
        roleDao.addRole(role);
        Integer roleId = role.getId();
        List<Map<String,Object>> menu = new ArrayList<>();
        for (Integer menuId : menuIds) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("role_id",roleId);
            map.put("menu_id",menuId);
            menu.add(map);
        }
        List<Map<String,Object>> permission = new ArrayList<>();

        for (Integer permissionId : permissionIds) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("role_id",roleId);
            map.put("permission_id",permissionId);
            permission.add(map);

        }


        roleDao.addMenuIds(menu);
        roleDao.addPermissionIds(permission);
    }

    @Override
    public void deleteById(Integer id) {
        roleDao.deleteByIdMenuIds(id);
        roleDao.deleteByIdPermissionIds(id);
        roleDao.deleteByIdRole(id);
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Integer> findMenuIdsById(Integer id) {
        return roleDao.findMenuIdsById(id);
    }

    @Override
    public List<Integer> findPermissionIdsById(Integer id) {
        return roleDao.findPermissionIdsById(id);
    }

    @Override
    public void edit(Request request) {
        Role role = request.getRole();
        Integer roleId = role.getId();
        Integer[] menuIds = request.getMenuIds();
        Integer[] permissionIds = request.getPermissionIds();
        roleDao.deleteByIdMenuIds(roleId);
        roleDao.deleteByIdPermissionIds(roleId);
        List<Map<String,Object>> menu = new ArrayList<>();
        for (Integer menuId : menuIds) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("role_id",roleId);
            map.put("menu_id",menuId);
            menu.add(map);
        }
        List<Map<String,Object>> permission = new ArrayList<>();

        for (Integer permissionId : permissionIds) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("role_id",roleId);
            map.put("permission_id",permissionId);
            permission.add(map);

        }

        roleDao.edit(role);
        roleDao.addMenuIds(menu);
        roleDao.addPermissionIds(permission);

    }

}
