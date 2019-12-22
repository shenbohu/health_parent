package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.daomin.Menu;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.UserService;
import com.itheima.utils.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import sun.applet.Main;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
        Calendar calendar = Calendar.getInstance();
        List<String> list = new ArrayList<>();
        List<String> Aslist = new ArrayList<>();
        calendar.add(Calendar.MONTH, -12);
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, +1);
            String format = simpleDateFormat.format(calendar.getTime()) + ".31";
            String ASformat = simpleDateFormat.format(calendar.getTime());
            list.add(format);
            Aslist.add(ASformat);
        }
        Map<String, Object> map = new HashMap<>();

        try {
            List<Integer> listdata = memberService.infoMenber(list, Aslist);
            map.put("months", Aslist);
            map.put("memberCount", listdata);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }

    }


    //    public static void main(String[] args) throws Exception {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
//        Calendar calendar = Calendar.getInstance();
//        List<String> list = new ArrayList<>();
//        calendar.add(Calendar.MONTH, -12);
//        for (int i = 0; i < 12; i++) {
//            calendar.add(Calendar.MONTH, +1);
//            String format = simpleDateFormat.format(calendar.getTime()) + "-31";
//            list.add(format);
//        }
//        System.out.println(list.toString());
//
//    }
    // 统计套餐getSetmealReport 统计每个套餐你出现的次数
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        List<Map<String, Object>> mapList = memberService.getSetmealReport();
        List<String> listname = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("setmealCount", mapList);
        for (Map<String, Object> ma : mapList) {
            String name = (String) ma.get("name");
            listname.add(name);
        }
        map.put("setmealNames", listname);
        return new Result(true,
                MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
    } // 统计套餐getSetmealReport

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> mapList = memberService.getBusinessReportData();
        return new Result(true,
                MessageConstant.GET_BUSINESS_REPORT_SUCCESS, mapList);
    }

    // 导出数据 从redis 获取数据 若果没有 调取数据库
    //          String reportData1 = jedisPool.getResource().get("reportData");
    //        Map map = JSON.parseObject(reportData1, Map.class);

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpSession session, HttpServletResponse response) throws Exception{
        // 从 redis 获取数据
        String reportData1 = jedisPool.getResource().get("reportData");
        Map map = JSON.parseObject(reportData1, Map.class);
        if(map==null) {
            this.getBusinessReportData();
        }
        //   getServletContext   上下文对象 getRealPath 返回文件的绝对路径
        String filePath =  session.getServletContext().getRealPath("/")+"template/report_template.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook(new File(filePath));
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = sheet.getRow(2);
        XSSFCell cell = row.getCell(5);

        cell.setCellValue((Date) map.get("reportDate"));
        return null;
    }
}
