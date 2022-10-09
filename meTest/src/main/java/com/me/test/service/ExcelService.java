package com.me.test.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.me.test.util.ExcelExportUtil;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/10 0:08:24
 */

@Service
public class ExcelService {

    public void exportSingleDatas(HttpServletResponse response) {
        String tableName = "order single " + LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-"
                + LocalDate.now().getDayOfMonth();
        // 获取需要导出的数据
        List<Map<String, Object>> dataList = getResultData();
        // 需要展示的列
        List<String> fieldList = Lists.newArrayList("orderTime","total","except","overTime","successRate");
        // 匹配数据
        List<List<String>> results = matchFieldData(dataList, fieldList);
        // 设置表题
        String[] titleList = {"日期","订单总量","异常量","超时量","成功率"};
        try {
            ExcelExportUtil.exportExcel(tableName, titleList, results,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportMulData(HttpServletResponse response) {
        String tableName = "order mul sheet " + LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-"
                + LocalDate.now().getDayOfMonth();
        // 获取需要导出的数据
        List<Map<String, Object>> dataList = getResultData();
        List<String[]> titleList = new ArrayList<>();
        List<List<List<String>>> contentList = new ArrayList<>();
        // 需要展示的列
        List<String> fieldList1 = Lists.newArrayList("orderTime","total","except","overTime","successRate");
        List<String> fieldList2 = Lists.newArrayList("orderTime","total","except","timelyRate","successRate");
        // 匹配数据
        List<List<String>> results1 = matchFieldData(dataList, fieldList1);
        List<List<String>> results2 = matchFieldData(dataList, fieldList2);
        contentList.add(results1);
        contentList.add(results2);
        // 设置表题
        String[] title1 = {"日期","订单总量","异常量","超时量","成功率"};
        String[] title2 = {"日期","订单总量","异常量","及时率","成功率"};
        titleList.add(title1);
        titleList.add(title2);
        try {
            ExcelExportUtil.exportExcel(tableName, titleList, contentList,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<Map<String, Object>> getResultData() {

        String data = "[{\"orderTime\":\"2021-03-03 10:10:10\",\"total\":55,\"except\":12,\"overTime\":8,\"timelyRate\":\"88%\",\"successRate\":\"77%\"},{\"orderTime\":\"2021-03-03 10:15:10\",\"total\":155,\"except\":44,\"overTime\":20,\"timelyRate\":\"78%\",\"successRate\":\"65%\"},{\"orderTime\":\"2021-03-03 10:20:10\",\"total\":85,\"except\":6,\"overTime\":5,\"timelyRate\":\"98%\",\"successRate\":\"97%\"}]";

        return new Gson().fromJson(data, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
    }

    private List<List<String>> matchFieldData(List<Map<String, Object>> dataList, List<String> fieldList) {
        return ListUtils
                .emptyIfNull(dataList).stream().filter(Objects::nonNull).map(e -> ListUtils.emptyIfNull(fieldList)
                        .stream().map(f -> MapUtils.getString(e, f)).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

}