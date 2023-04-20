package com.me.test.controller;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/4/21 1:11:45
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportExport {
    public static void main(String[] args) {
        List<Report> reportList = new ArrayList<Report>();
        // 假设有一些report数据
        reportList.add(new Report("Task1", "2021-01-01 10:00:00", "192.168.0.1", "Log1"));
        reportList.add(new Report("Task1", "2021-01-01 10:00:00", "192.168.0.2", "Log2"));
        reportList.add(new Report("Task2", "2021-01-02 10:00:00", "192.168.0.3", "Log3"));

        // 创建Excel工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建Excel表格
        Sheet sheet = workbook.createSheet("Report");

        // 定义表头
        String[] headers = {"Task Name", "Start Time", "IP", "Log"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            // 设置表头样式
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//            headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(headerStyle);
        }

        // 定义单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);

        // 定义记录行数和相同记录的开始行数
        int rownum = 1;
        Map<String, Integer> startRowMap = new HashMap<String, Integer>();

        // 遍历report数据，写入Excel表格
        for (Report report : reportList) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(report.getTaskName());
            row.createCell(1).setCellValue(report.getStartTime());
            row.createCell(2).setCellValue(report.getIp());
            row.createCell(3).setCellValue(report.getLog());
            row.getCell(0).setCellStyle(cellStyle);
            row.getCell(1).setCellStyle(cellStyle);
            row.getCell(2).setCellStyle(cellStyle);
            row.getCell(3).setCellStyle(cellStyle);

            // 判断是否需要合并单元格
            String key = report.getTaskName() + report.getStartTime();
            if (startRowMap.containsKey(key)) {
                int startRow = startRowMap.get(key);
                /**
                 * sheet 表示当前操作的工作表。
                 * addMergedRegion 是工作表的一个方法，用来将单元格进行合并。
                 * new org.apache.poi.ss.util.CellRangeAddress(startRow, rownum - 1, 0, 0)
                 * 表示要合并的单元格的范围，其中startRow表示起始行，rownum-1表示结束行，0表示起始列，0表示结束列。
                 */
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(startRow, rownum - 1, 0, 0));
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(startRow, rownum - 1, 1, 1));
            } else {
                startRowMap.put(key, rownum - 1);
            }
        }

        // 调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 保存Excel文件
        try (FileOutputStream outputStream = new FileOutputStream("Report.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Report {
    private String taskName;
    private String startTime;
    private String ip;
    private String log;

    public Report(String taskName, String startTime, String ip, String log) {
        this.taskName = taskName;
        this.startTime = startTime;
        this.ip = ip;
        this.log = log;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
