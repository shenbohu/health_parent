package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmeaDao;
import com.itheima.daomin.CheckGroup;
import com.itheima.daomin.Setmeal;
import com.itheima.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private SetmeaDao setmeaDao;

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        // 分页 插件
        PageHelper.startPage(currentPage, pageSize);
        // 吧分页查询后的信息封装到  page中
        Page<Setmeal> page = setmeaDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<Setmeal> result = page.getResult();
        return new PageResult(total, result);
    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmeaDao.add(setmeal);
        Integer setmealid = setmeaDao.maxid();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (Integer checkgroupId : checkgroupIds) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("setmealid",setmealid);
            map.put("checkgroupId",checkgroupId);
            list.add(map);
        }
        setmeaDao.addPRIMARY_KEY(list);
    }

    @Override
    public List<Setmeal> findAll() {
        return setmeaDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmeaDao.findById(id);
    }
}
