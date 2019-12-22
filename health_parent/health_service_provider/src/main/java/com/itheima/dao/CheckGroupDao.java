package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.daomin.CheckGroup;

import java.util.List;

import java.util.Map;

public interface CheckGroupDao {
    void addPRIMARY_KEY(List<Map<String, Object>> list);

    void add(CheckGroup checkGroup);

    Integer maxid();

    Page<CheckGroup> selectByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void update_t_checkgroup(CheckGroup checkGroup);



    void delete_t_checkgroup_checkitem(Integer checkGroupId);

    void delete(Integer id);

    List<CheckGroup> findAll();
}
