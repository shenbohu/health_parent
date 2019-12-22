package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.daomin.CheckItem;

import java.util.List;

public interface CheckItemDao {
    public void insert(CheckItem checkItem);


    Page<CheckItem> selectByCondition(String queryString);

    void delete(Integer id);

    CheckItem echo(Integer id);

    void update(CheckItem checkItem);

    List<CheckItem> findAll();

}
