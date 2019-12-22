package com.itheima.service;

import com.itheima.daomin.CheckItem;
import com.itheima.entity.PageResult;

import java.util.List;

public interface CheckItemService {
    //新增
    public void add(CheckItem checkItem);

    //分页
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    //删除
    void delete(Integer id);

    CheckItem echo(Integer id);

    void update(CheckItem checkItem);

    List<CheckItem> findAll();

}
