package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.daomin.CheckGroup;
import com.itheima.daomin.CheckItem;
import com.itheima.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao groupDao;


    @Override
    public void addPRIMARY_KEY(Integer checkGroupId, Integer[] checkitemIds) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if (checkitemIds!=null) {
            for (Integer checkitemId : checkitemIds) {
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                list.add(map);
            }
            groupDao.addPRIMARY_KEY(list);
        }
    }

    @Override
    public void add(CheckGroup checkGroup) {
            groupDao.add(checkGroup);
    }

    @Override
    public Integer maxid() {
        return groupDao.maxid();
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        // 分页 插件
        PageHelper.startPage(currentPage, pageSize);
        // 吧分页查询后的信息封装到  page中
        Page<CheckGroup> page =  groupDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> result = page.getResult();
        return new PageResult(total,result);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return groupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return  groupDao.findCheckItemIdsByCheckGroupId(id);

    }

    @Override
    public void update_t_checkgroup(CheckGroup checkGroup) {
        groupDao.update_t_checkgroup(checkGroup);
    }

//    @Override
//    public void update_t_checkgroup_checkitem(Integer checkGroupId, Integer[] checkitemIds) {
//        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        for (Integer checkitemId : checkitemIds) {
//            Map<String,Object> map = new HashMap<String,Object>();
//            map.put("checkGroupId",checkGroupId);
//            map.put("checkitemId",checkitemId);
//            list.add(map);
//        }
//        groupDao.addPRIMARY_KEY(list);
//    }

    @Override
    public void delete_t_checkgroup_checkitem(Integer checkGroupId) {
        groupDao.delete_t_checkgroup_checkitem(checkGroupId);
    }

    @Override
    public void delete(Integer id) {
        groupDao.delete(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return groupDao.findAll();
    }


}
