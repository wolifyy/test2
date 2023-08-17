package com.me.test.util;

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
    private static final CellStyle HEADER_STYLE;
    private static final CellStyle CELL_STYLE;
    static {
        Workbook workbook = new XSSFWorkbook();
        HEADER_STYLE = workbook.createCellStyle();
        HEADER_STYLE.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        CELL_STYLE = workbook.createCellStyle();
        CELL_STYLE.setWrapText(true);
    }

    public static void main(String[] args) {
        List<Report> reportList = new ArrayList<>();
        // 假设有一些report数据
        reportList.add(new Report("Task1", "2021-01-01 10:00:00", "192.168.0.1", "Log1"));
        reportList.add(new Report("Task1", "2021-01-01 10:00:00", "192.168.0.2", "Log2"));
        reportList.add(new Report("Task2", "2021-01-02 10:00:00", "192.168.0.3", "Log3"));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");
        writeHeader(sheet);
        writeRecords(sheet, reportList);
        adjustColumnWidth(sheet);

        try (FileOutputStream outputStream = new FileOutputStream("Report.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeHeader(Sheet sheet) {
        String[] headers = {"Task Name", "Start Time", "IP", "Log"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            //cell.setCellStyle(HEADER_STYLE);
        }
    }

    private static void writeRecords(Sheet sheet, List<Report> reportList) {
        List<Integer> startRowList = new ArrayList<>();
        Map<String, Integer> startRowMap = new HashMap<>();
        int rownum = 1;
        for (Report report : reportList) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(report.getTaskName());
            row.createCell(1).setCellValue(report.getStartTime());
            row.createCell(2).setCellValue(report.getIp());
            row.createCell(3).setCellValue(report.getLog());
            applyCellStyle(row);
            String key = report.getTaskName() + report.getStartTime();
            if (startRowMap.containsKey(key)) {
                int startRow = startRowMap.get(key);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(startRow, rownum - 1, 0, 0));
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(startRow, rownum - 1, 1, 1));
            } else {
                startRowMap.put(key, rownum - 1);
                startRowList.add(rownum - 1);
            }
        }
        for (int startRow : startRowList) {
            int endRow = startRowMap.get(reportList.get(startRow - 1).getTaskName() + reportList.get(startRow - 1).getStartTime());
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(startRow, endRow, 0, 0));
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(startRow, endRow, 1, 1));
        }
    }

    private static void applyCellStyle(Row row) {
        for (int i = 0; i < 4; i++) {
            row.getCell(i).setCellStyle(CELL_STYLE);
        }
    }

    private static void adjustColumnWidth(Sheet sheet) {
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
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