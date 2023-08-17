package com.me.test.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/8/17 22:59:27
 */

public class XftComparator implements Comparator<Map<String, Object>> {

    private static final List<String> order = Arrays.asList("张三", "李四", "王五");


    @Override
    public int compare(Map<String, Object> m1, Map<String, Object> m2) {
        String name1 = (String) m1.get("name");
        String name2 = (String) m2.get("name");

        // 如果"name"的值是"汇总"，让它的索引比任何其他元素都大
        int idx1 = "汇总".equals(name1) ? Integer.MAX_VALUE : order.indexOf(name1);
        int idx2 = "汇总".equals(name2) ? Integer.MAX_VALUE : order.indexOf(name2);

        // 如果name1或name2不在指定的排序顺序中，将其索引设置为最后
        if (idx1 == -1) idx1 = order.size();
        if (idx2 == -1) idx2 = order.size();

        return Integer.compare(idx1, idx2);
    }
}
