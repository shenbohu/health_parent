package com.itheima.dao;

import com.itheima.daomin.Menu;

import java.util.List;

public interface MenuDao {

    List<Menu> getListMenu(Integer id);

    List<Menu> findAll();

}
