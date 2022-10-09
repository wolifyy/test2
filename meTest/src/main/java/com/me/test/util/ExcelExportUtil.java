package com.me.test.util;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/10 0:09:16
 */

@Component
public class ExcelExportUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(ExcelExportUtil.class);


    public static void exportExcel(String fileName, String[] titles, List<List<String>> result,
                                   HttpServletResponse response) {
        List<String[]> titleList = Lists.newArrayList();
        List<List<List<String>>> contentList = Lists.newArrayList();
        titleList.add(titles);
        contentList.add(result);
        exportExcel(fileName, titleList, contentList, response);
    }

    public static void exportExcel(String fileName, List<String[]> titleList, List<List<List<String>>> result,
                                   HttpServletResponse response) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            new PoiMulExcelExporter().export(response, fileName, changeStrArrToList(titleList), result);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static List<List<String>> changeStrArrToList(List<String[]> titleList){

        List<List<String>> listList = new ArrayList();
        for (int i = 0; i < titleList.size(); i++) {
            String[] str = titleList.get(i);
            List<String> list2 = new ArrayList();
            Collections.addAll(list2, str);
            listList.add(list2);
            }
        return listList;
        }


    }
