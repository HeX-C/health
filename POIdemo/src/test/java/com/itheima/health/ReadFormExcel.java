package com.itheima.health;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author : qiangshengchen
 * @date : 上午 11:59 21/9/2020
 */
public class ReadFormExcel {

    @Test
    public void readFormExcel() throws IOException {

//        创建工作簿
        Workbook workbook = new XSSFWorkbook("E:\\read.xlsx");

//        获取工作表
        Sheet sheet = workbook.getSheetAt(0);

//        遍历工作表获取行对象
        for (Row row : sheet) {

//            遍历行对象获取单元格
            for (Cell cell : row) {
//                单元格的类型
                int cellType = cell.getCellType();

                if (cell.CELL_TYPE_NUMERIC == cellType) {
//                    数值类型的单元格（STRING 字符串类型的单元格）
//                    取值
                    System.out.print(cell.getNumericCellValue() + ",");
                }else {
                    System.out.print(cell.getStringCellValue() + ":");
                }
            }
            System.out.println();
        }
//        关闭工作簿
        workbook.close();
    }

    @Test
    public void create() throws IOException {

//        创建工作簿
       Workbook workbook = new XSSFWorkbook();

//        创建工作表
        Sheet sheet = workbook.createSheet("这是我的工作表");

//        在工作表中创建行
        Row row = sheet.createRow(0);

//        在行中创建单元格
        Cell cell = row.createCell(0);

//        给单元格赋值
//          表头
        cell.setCellValue("姓名");
        row.createCell(1).setCellValue("年龄");
        row.createCell(2).setCellValue("所在地");

        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("陈强盛");
        row1.createCell(1).setCellValue("99");
        row1.createCell(2).setCellValue("深圳");

        Row row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("哈哈");
        row2.createCell(1).setCellValue("88");
        row2.createCell(2).setCellValue("北京");

//        保存工作簿，持久化本地硬盘
        workbook.write(new FileOutputStream(new File("e:\\create.xlsx")));

//        关闭工作簿
        workbook.close();

    }
}
