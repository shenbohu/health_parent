package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.daomin.Permission;
import com.itheima.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;


    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Permission> page = (Page<Permission>) permissionDao.findAllPermission(queryString);
        long total = page.getTotal();
        List<Permission> result = page.getResult();
        return new PageResult(total, result);
    }

    @Override
    public void addPermission(Permission permission) {
        permissionDao.addPermission(permission);
    }

    @Override
    public void deleteById(Integer id) {
        // 删除权限和角色的中间表
        permissionDao.deleteByIdMiddle(id);
        permissionDao.deleteById(id);
    }

    @Override
    public Permission findById(Integer id) {
        return permissionDao.findById(id);
    }

    @Override
    public void update(Permission permission) {
        permissionDao.update(permission);
    }

    @Override
    public void deleteAll(List<Integer> listID) {
        permissionDao.deleteAllMiddle(listID);
        permissionDao.deleteAll(listID);
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
