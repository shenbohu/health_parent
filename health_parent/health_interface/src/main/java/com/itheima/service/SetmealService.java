package com.itheima.service;

import com.itheima.daomin.Setmeal;
import com.itheima.entity.PageResult;

import java.util.List;

public interface SetmealService {
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
