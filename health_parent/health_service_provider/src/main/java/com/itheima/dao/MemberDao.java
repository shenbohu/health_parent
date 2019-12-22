package com.itheima.dao;

import com.itheima.daomin.Member;

import java.util.List;
import java.util.Map;

public interface MemberDao {
    // 查询用户是否是会员
    Member isVIP(String telephone);
    // 添加会员
    void add(Member member);

    List<Map> infoMenber(List<String> list);

    List<Map<String, Object>> getSetmealReport();

    Integer dateVIP();

    Integer countVIP();

    Integer thisWeekNewMember();

    Integer thisMonthNewMember();

    Integer todayOrderNumber();

    Integer todayVisitsNumber();

    Integer thisWeekOrderNumber();

    Integer thisWeekVisitsNumber();

    Integer thisMonthOrderNumber();

    Integer thisMonthVisitsNumber();

    List<Map<String, Object>> hotSetmeal();
}
