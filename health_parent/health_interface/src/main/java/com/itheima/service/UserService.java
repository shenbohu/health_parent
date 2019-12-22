package com.itheima.service;

import com.itheima.daomin.Menu;
import com.itheima.daomin.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
// 查询用户的菜单
   List<Menu>  getMenu(String username);
}
