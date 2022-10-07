package com.me.test.controller;

import com.google.gson.Gson;
import com.me.test.Model.Page;
import com.me.test.Model.RequestBodyB;
import com.me.test.Model.SearchCondition;
import com.me.test.Model.Sorter;
import org.junit.Test;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/5 21:40:35
 */
public class UserInfoControllerTest {

    @Test
    public void queryByPage() {
        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setUsername("root");
        Page page = new Page();
        page.setCurrentPage(1);
        page.setPageSize(10);
        Sorter sorter = new Sorter();
        sorter.setColumn("id");
        sorter.setOrder("ASC");
        RequestBodyB requestBodyB = new RequestBodyB();
        requestBodyB.setPage(page);
        requestBodyB.setSearchCondition(searchCondition);
        requestBodyB.setSorter(sorter);
        Gson gson = new Gson();
        String json = gson.toJson(requestBodyB);
        System.out.println(json);
    }
}