package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckItemDao;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.daomin.Member;
import com.itheima.daomin.Order;
import com.itheima.daomin.OrderSetting;
import com.itheima.entity.Result;
import com.itheima.utils.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberDao memberDao;

    @Override
    public void saveBath(List<OrderSetting> list) {
        if (list != null && list.size() > 0) {
            List<OrderSetting> list1 = new ArrayList<OrderSetting>();
            for (OrderSetting orderSetting : list) {
                // 判断时间在数据库中是否存在 如果存在执行更新   不存在保存
                Date orderDate = orderSetting.getOrderDate();
                OrderSetting temp = orderDao.findByDate(orderDate);
                if (temp != null) {
                    // 存在  更新预约人数
                    temp.setNumber(orderSetting.getNumber());
                    orderDao.update(temp);
                } else {
//                    orderDao.add(orderSetting);
                    list1.add(orderSetting);
                }
            }
            orderDao.adds(list1);
        }
    }

    @Override
    public List<Map> appointment(String date) {
        String begin = date + "-1";
        String end = date + "-31";
        Map<String, String> m = new HashMap<>();
        m.put("begin", begin);
        m.put("end", end);
        List<OrderSetting> list = orderDao.appointment(m);
        List<Map> maps = new ArrayList<Map>();
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                // orderSetting.getOrderDate().getDate() 获取当前日期是这个月的第几天
                map.put("date", orderSetting.getOrderDate().getDate());
                map.put("number", orderSetting.getNumber());
                map.put("reservations", orderSetting.getReservations());
                maps.add(map);
            }
        }
        return maps;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        // 根据 时间判断 数据库中的预约人数
        long temp = orderDao.findCountByOrderDate(orderDate);
        if (temp > 0) {
            // 存在  更新预约人数
            orderDao.update(orderSetting);
        } else {
            orderDao.add(orderSetting);
//            list1.add(orderSetting);
        }
    }

    @Override
    public Result order(Map map) throws Exception {
        // 获取用户预约的日期
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        // 根据日期查询当前日期是否进行预约设置
        OrderSetting orderSetting = orderDao.findByDate(date);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        // 检查所预约的日期 预约人数是否已满
        // SELECT (number-reservations) FROM  t_ordersetting WHERE orderDate='2019-03-05';
        Integer number = orderDao.mayNumber(date);

        if (number <= 0) {
            // 已经约满
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        // 检查用户是否为会员
        String telephone = (String) map.get("telephone"); //用户的手机号
        Member member = memberDao.isVIP(telephone);
        // 查询用户在当前时间是否进行重复预约(每次预约如果不是会员自动保存到会员列表  所以只有是会员才可能会重复)
        if (member != null) {
            // 用户是会员
            // 获取用户的id
            Integer memberId = member.getId();
            // 获取用户所选择的套餐  (预约时间一样  可能套餐不一样)
            //String setmealId = (String) map.get("setmealId");
            int setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(memberId, date, null, null,setmealId);
            List<Order> list = orderDao.findByCondition(order);
            if (list != null && list.size() > 0) {
                // 查询到用户当前套餐已经进行预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        } else {
            // 如果不是会员
            member = new Member();  // (String) map.get("name"), (String) map.get("sex"),
                                    // (String) map.get("idCard"), telephone, new Date(),
                                    // 添加会员
            member.setName((String)map.get("name"));
            member.setSex((String)map.get("sex"));
            member.setIdCard((String)map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());

            memberDao.add(member);
        }
        // 添加预约信息
        String setmealId = (String) map.get("setmealId");
        int i = Integer.parseInt(setmealId);
        Order order = new Order(member.getId(), date, (String) map.get("orderType"), Order.ORDERSTATUS_NO, i);
        // 添加预约信息
        orderDao.addorder(order);
        //设置可预约人数减一
        orderDao.updatereservations(date);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());


    }

    @Override
    public Result findById(Integer id) {
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,orderDao.findById(id));
    }


}
