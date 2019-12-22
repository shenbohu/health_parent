package com.itheima.service;

import com.itheima.daomin.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member isVip(String telephone);

    void addVIP(Member member);

    List<Integer> infoMenber(List<String> list, List<String> aslist);

    List<Map<String, Object>> getSetmealReport();

    Map<String, Object> getBusinessReportData();

}
