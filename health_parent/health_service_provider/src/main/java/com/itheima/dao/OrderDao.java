package com.itheima.dao;

import com.itheima.daomin.Order;
import com.itheima.daomin.OrderSetting;
import com.itheima.daomin.Succeed;
import com.itheima.entity.Result;
import org.apache.ibatis.annotations.Param;


import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    OrderSetting findByDate(Date orderDate);

    void update(OrderSetting temp);

    void add(OrderSetting orderSetting);

    void adds(List<OrderSetting> list);


    List<OrderSetting> appointment(Map m);

    long findCountByOrderDate(Date orderDate);
// 查询剩余可预约人数
    Integer mayNumber(Date date);
// 查询当前用户是否进行了预约
    List<Order> findByCondition(Order order);
// 设置可预约人数减一
    void updatereservations(Date date);
// 添加预约
    void addorder(Order order);

    Succeed findById(Integer id);
}
