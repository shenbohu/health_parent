package com;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class APP {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-jobs.xml");
    }

    @Test
    public void readExcel() throws IOException, InvalidFormatException {

        String filePath = "C:\\Users\\申博虎\\Desktop\\222.xlsx";
        XSSFWorkbook sheets = new XSSFWorkbook(new File(filePath));
        XSSFSheet sheet = sheets.getSheetAt(0);
        for (Row row : sheet) {
//            for (Cell cell : row) {
//                System.out.println(cell);
//            }
            String stringCellValue = row.getCell(0).getStringCellValue();
            double numericCellValue = row.getCell(1).getNumericCellValue();
            System.out.println(stringCellValue);
//            System.out.println(numericCellValue);

        }
        sheets.close();

    }

    @Test
    public void readExcel2() throws IOException, InvalidFormatException {

        String name = "";
        double age = 0;
        String filePath = "C:\\Users\\申博虎\\Desktop\\222.xlsx";
        XSSFWorkbook sheets = new XSSFWorkbook(new File(filePath));
        XSSFSheet sheet = sheets.getSheetAt(0);
        // 获取最后的行号
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i < lastRowNum; i++) {
            // 获取每一行
            XSSFRow row = sheet.getRow(i);
            // 获取每一行有多少列
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                // 获取每一行的信息
                XSSFCell cell = row.getCell(j);
                //获取每一个单元格的类型
                int cellType = cell.getCellType();
                if (cellType == cell.CELL_TYPE_STRING) {
                    name = cell.getStringCellValue();

                }
                if (cellType == Cell.CELL_TYPE_NUMERIC) {
                    age = cell.getNumericCellValue();
                }

            }
            System.out.println("姓名为:" + name + ";年龄为:" + age);
        }
    }

    @Test
    public void testWriteExcel() throws Exception {
        // creat 创造
        XSSFWorkbook sheets = new XSSFWorkbook();
        XSSFSheet sheet = sheets.createSheet("传智播客");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("地址");
        row.createCell(2).setCellValue("年龄");

        // 查询数据库获得数据
        List<User> users = new ArrayList<User>();
        for (int i = 0; i <5; i++) {
            XSSFRow content = sheet.createRow(i+1);
          //  User user = users.get(i);
            content.createCell(0).setCellValue("shenbohu");
            content.createCell(1).setCellValue("陕西省西安市");
            content.createCell(2).setCellValue(18);
        }
        FileOutputStream out = new FileOutputStream("C:\\Users\\申博虎\\Desktop\\222.xlsx");
        sheets.write(out);
        out.close();
        sheets.close();
    }



}
