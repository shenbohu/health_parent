package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.itheima.dao.SystemDao;

import com.itheima.daomin.Role;
import com.itheima.daomin.User;
import com.itheima.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SystemService.class)
@Transactional
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SystemDao systemDao;

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<User> page = systemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<User> result = page.getResult();
        return new PageResult(total,result);
    }

    @Override
    public List<Role> findAllRoles() {
        return systemDao.findAllRoles();
    }

    @Override
    public void addUserAndRole(User user, Integer[] roleIds) {
        systemDao.addUser(user);
        Integer id = user.getId();
        List<Map<String,Object>> list = new ArrayList<>();
        if(roleIds!=null) {
            for (Integer roleId : roleIds) {
                Map<String,Object> map = new HashMap<>();
                map.put("userid",id);
                map.put("roleid",roleId);
                list.add(map);
            }

            systemDao.addPRIMARY_KEY(list);
        }
    }

    @Override
    public List<User> isrepetition(String username) {
        return systemDao.isrepetition(username);
    }

    @Override
    public void deleteById(Integer id) {
        // 删除中间表
        systemDao.deleteByIdMiddle(id);
        //删除用户
        systemDao.deleteByIdUser(id);

    }

    @Override
    public void deleteAllList(List<Integer> list) {
        systemDao.deleteAllListMiddle(list);
        systemDao.deleteAllListUser(list);
    }

}
