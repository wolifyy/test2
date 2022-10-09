package com.me.test.util;

import com.alibaba.excel.util.StringUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/10 0:11:04
 */

public class PoiMulExcelExporter {

    PoiMulExcelExporter() {}

    /*多个sheet页*/
    public void export(HttpServletResponse response, String fileName, List<List<String>> titleList,
                       List<List<List<String>>> contentList) {
        HSSFSheet sheet;
        try (HSSFWorkbook workbook = new HSSFWorkbook(); OutputStream output = response.getOutputStream()) {
            setResponse(response, fileName);
            int sheetNum = titleList.size();
            for (int i = 0; i < sheetNum; i++) {
                sheet = workbook.createSheet("Sheet" + (i + 1));
                int rowNum = 0;
                createHeader(workbook, sheet, titleList.get(i), rowNum);
                rowNum++;
                createContent(sheet, contentList.get(i), rowNum);
            }
            workbook.write(output);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createHeader(HSSFWorkbook workbook, HSSFSheet sheet, List<String> titles, int rowNum) {
        setHeaderStyle(workbook);
        setSheetContent(sheet, titles, rowNum);
    }


    private static void setSheetContent(HSSFSheet sheet, List<String> contentList, int rownum) {
        HSSFRow row = sheet.createRow(rownum);
        AtomicInteger i = new AtomicInteger();
        for (String title : ListUtils.emptyIfNull(contentList)) {
            if (!StringUtils.isEmpty(title)) {
                HSSFCell cell = row.createCell(i.getAndIncrement());
                cell.setCellValue(title);
            }
        }
    }

    private void createContent(HSSFSheet sheet, List<List<String>> content, int rownum) throws Exception {
        for (List<String> lineData : content) {
            setSheetContent(sheet,lineData,rownum);
            rownum++;
        }
    }

    private void setResponse(HttpServletResponse response, String fileName) {
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", getResponseHeader(fileName));
    }

    private String getResponseHeader(String fileName) {
        return "attachment; filename=" + gbToUtf8(fileName) + ".xls";
    }

    private static String gbToUtf8(String src) {
        byte[] b = src.getBytes();
        char[] c = new char[b.length];
        for (int x = 0; x < b.length; x++) {
            c[x] = (char) (b[x] & 0x00FF);
        }
        return new String(c);
    }

    private static void setHeaderStyle(HSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();//设置样式
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 13);//设置字体大小
        font.setBold(true);//字体加粗
        style.setFont(font);//设置的字体
        style.setBorderTop(BorderStyle.DASHED);//上边框
        style.setBorderBottom(BorderStyle.DASHED); //下边框
        style.setBorderBottom(BorderStyle.DASHED);//左边框
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());//右边框颜色
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());//上边框颜色
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); //下边框颜色
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); //左边框颜色
        style.setBorderBottom(BorderStyle.DASHED);//右边框
        style.setAlignment(HorizontalAlignment.LEFT);//设置水平对齐的样式为居中对齐
        style.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直对齐的样式为居中对齐
    }

}
