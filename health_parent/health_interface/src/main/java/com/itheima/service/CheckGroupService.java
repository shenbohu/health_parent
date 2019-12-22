package com.itheima.service;

import com.itheima.daomin.CheckGroup;
import com.itheima.daomin.CheckItem;
import com.itheima.entity.PageResult;

import java.util.List;

public interface CheckGroupService {
    void addPRIMARY_KEY(Integer checkGroupId, Integer[] checkitemIds);

    void add(CheckGroup checkGroup);

    Integer maxid();

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);


    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void update_t_checkgroup(CheckGroup checkGroup);



    void delete_t_checkgroup_checkitem(Integer checkGroupId);

    void delete(Integer id);

    List<CheckGroup> findAll();

}
