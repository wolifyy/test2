package com.me.test.util;

import com.google.gson.Gson;
import com.me.test.service.XftComparator;
import org.junit.Test;

import java.util.*;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/8/17 21:44:03
 */

public class SortTest {

    @Test
    public void test(){
        List<Map<String, Object>> dataList = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name","张三");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name","汇总");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("name","刘八");
        Map<String, Object> map4 = new HashMap<>();
        map4.put("name","汇总");
        Map<String, Object> map5 = new HashMap<>();
        map5.put("name","李四");

        dataList.add(map1);
        dataList.add(map2);
        dataList.add(map3);
        dataList.add(map4);
        dataList.add(map5);
        // 假设你的dataList已经被填充了数据

        // 指定排序顺序
        List<String> order = Arrays.asList("张三", "李四", "王五");

        Collections.sort(dataList, new XftComparator());

        System.out.println(new Gson().toJson(dataList));
    }
}
