package com.itheima.dao;

import com.itheima.daomin.User;

public interface UserDao {
    User findByUsername(String username);
}
