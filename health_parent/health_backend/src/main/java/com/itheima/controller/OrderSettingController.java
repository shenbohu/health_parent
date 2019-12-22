package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.daomin.OrderSetting;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderService service;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            // 获取表格的文件名(包括后缀)
            String originalFilename = excelFile.getOriginalFilename();
            // 获取文件的后缀名
            String extension = FilenameUtils.getExtension(originalFilename);
            // 根据文件名创建workbook
            Workbook workbook = null;
            if ("xls".equals(extension)) {
                workbook = new HSSFWorkbook(excelFile.getInputStream());
            } else if ("xlsx".equals(extension)) {
                workbook = new XSSFWorkbook(excelFile.getInputStream());
            } else {
                return new Result(false, MessageConstant.UPLOAD_FAIL);
            }
            // 获sheet
            Sheet sheetAt = workbook.getSheetAt(0);
            // 获取一共有多少行
            int lastRowNum = sheetAt.getLastRowNum();
            List<OrderSetting> list = new ArrayList<OrderSetting>();
            for (int i = 0; i <= lastRowNum; i++) {
                OrderSetting orderSetting = new OrderSetting();
                if (i == 0) {
                    continue;
                }
                // 获取单元格
                Row row = sheetAt.getRow(i);
                //            short lastCellNum = row.getLastCellNum();
                //            for (int j = 0; j < lastCellNum; j++) {
                //
                //            }
                Date dateCellValue = row.getCell(0).getDateCellValue();
                Double numericCellValue = row.getCell(1).getNumericCellValue();
                if (dateCellValue != null && numericCellValue != null) {
                    orderSetting.setOrderDate(dateCellValue);
                    orderSetting.setNumber(numericCellValue.intValue());
                    list.add(orderSetting);
                }
            }
            service.saveBath(list);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {
//        System.out.println(date);
        try {
            List<Map> list = service.appointment(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }

    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
             service.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }


}
