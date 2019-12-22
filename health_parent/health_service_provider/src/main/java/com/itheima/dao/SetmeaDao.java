package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.daomin.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmeaDao {
    Page<Setmeal> selectByCondition(String queryString);

    void add(Setmeal setmeal);

    Integer maxid();

    void addPRIMARY_KEY(List<Map<String, Object>> list);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
