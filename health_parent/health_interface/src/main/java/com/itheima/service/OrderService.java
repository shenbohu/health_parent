package com.itheima.service;

import com.itheima.daomin.OrderSetting;
import com.itheima.entity.Result;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void saveBath(List<OrderSetting> list);


    List<Map> appointment(String date);

    void editNumberByDate(OrderSetting orderSetting);

    Result order(Map map) throws Exception;

    Result findById(Integer id);
}
