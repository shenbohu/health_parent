package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.MemberDao;
import com.itheima.daomin.Member;
import com.itheima.utils.DateUtils;
import com.itheima.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private JedisPool jedisPool;


    @Override
    public Member isVip(String telephone) {
        return memberDao.isVIP(telephone);
    }

    @Override
    public void addVIP(Member member) {
        member.setPassword(MD5Utils.md5(member.getPassword()));
        memberDao.add(member);
    }

    @Override
    public List<Integer> infoMenber(List<String> list, List<String> aslist) {
        List<Map> list1 = memberDao.infoMenber(list);

        System.out.println(list.toString());
        return null;
    }

    @Override
    public List<Map<String, Object>> getSetmealReport() {
        return memberDao.getSetmealReport();
    }

    @Override
    public Map<String, Object> getBusinessReportData() {
        // 新增会员数
        Integer todayNewMember = memberDao.dateVIP();
        Date reportDate = DateUtils.getToday();
        // 总会员数
        Integer totalMember = memberDao.countVIP();
        //-- 本周新增会员数
        Integer thisWeekNewMember = memberDao.thisWeekNewMember();
        // 本月新增会员数
        Integer thisMonthNewMember = memberDao.thisMonthNewMember();


        //本日预定的套餐数
        Integer todayOrderNumber = memberDao.todayOrderNumber();
        // 本日到诊数
        Integer todayVisitsNumber = memberDao.todayVisitsNumber();

        // 本周预定的套餐数
        Integer thisWeekOrderNumber = memberDao.thisWeekOrderNumber();
        //  本周到诊数
        Integer thisWeekVisitsNumber = memberDao.thisWeekVisitsNumber();

        // 本月预定的套餐数
        Integer thisMonthOrderNumber = memberDao.thisMonthOrderNumber();
        //本月到诊数
        Integer thisMonthVisitsNumber = memberDao.thisMonthVisitsNumber();
//        热门套餐  前3个
//        套餐名称、当前套餐预定的数量、当前套餐预定的数量占比
        List<Map<String, Object>> hotSetmeal = memberDao.hotSetmeal();
        Map<String, Object> reportData = new HashMap<>();
        reportData.put("reportDate", reportDate);
        reportData.put("todayNewMember", todayNewMember);
        reportData.put("totalMember", totalMember);
        reportData.put("thisWeekNewMember", thisWeekNewMember);
        reportData.put("thisMonthNewMember", thisMonthNewMember);
        reportData.put("todayOrderNumber", todayOrderNumber);
        reportData.put("todayVisitsNumber", todayVisitsNumber);
        reportData.put("thisWeekOrderNumber", thisWeekOrderNumber);
        reportData.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        reportData.put("thisMonthOrderNumber", thisMonthOrderNumber);
        reportData.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        reportData.put("hotSetmeal", hotSetmeal);

        String s = JSON.toJSONString(reportData);
        jedisPool.getResource().setex("reportData",300,  s);

        return reportData;


    }
}
