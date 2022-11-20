package com.me.test.controller;

import com.google.gson.Gson;
import com.me.test.Model.*;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Test
    public void test(){
        Map<String,String> map = new HashMap<>();
        map.put("a","b");
        map.put("a","c");
        long count = map.entrySet().stream().distinct().count();
        System.out.println(count);
        System.out.println(map.size());
        System.out.println(count< map.size());
    }

    @Test
    public void test2(){
        List<String> strings = Arrays.asList("a", "bb", "cc", "aa", "bb", "dd", "cc");
        List<String> result = new ArrayList<>();
        Map<String, Long> collect = strings.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        collect.forEach((k,v)->{
            if(v>1)
                result.add(k);
        });
        System.out.println(result.toString());

    }

    @Test
    public void test3(){
        List<String> strings = Arrays.asList("a", "bb", "cc", "aa", "bb", "dd", "cc");
        Map<String, Long> collect = strings.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<String> result = collect.entrySet().stream()
                .filter(e -> e.getValue() > 1).map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println(result.toString());

    }

    @Test
    public void test4(){
        List<User> users = new ArrayList<>();
        users.add(new User("xpf1","1238",18));
        users.add(new User("xpf2","1234",18));
        users.add(new User("xpf3","1234",18));
        users.add(new User("xpf","1236",18));
        users.add(new User("xpf","1237",18));
        Map<String, Long> collect = users
                .stream()
                .map(User::getUserName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<String> result = new ArrayList<>();
        collect.forEach((k,v)->{
            if(v>1)
                result.add(k);
        });
        System.out.println(result.toString());

    }

    @Test
    public void test5() throws Exception {
        ForkJoinPool pool=new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread()+" start job1,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread()+" exit job1,time->"+System.currentTimeMillis());
            return 1.2;
        },pool);
        //cf关联的异步任务的返回值作为方法入参，传入到thenApply的方法中
        //thenApply这里实际创建了一个新的CompletableFuture实例
        CompletableFuture<String> cf2=cf.thenApplyAsync((result)->{
            System.out.println(Thread.currentThread()+" start job2,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread()+" exit job2,time->"+System.currentTimeMillis());
            return "test:"+result;
        });
        System.out.println("main thread start cf.get(),time->"+System.currentTimeMillis());
        //等待子任务执行完成
        System.out.println("run result->"+cf.get());
        System.out.println("main thread start cf2.get(),time->"+System.currentTimeMillis());
        System.out.println("run result->"+cf2.get());
        System.out.println("main thread exit,time->"+System.currentTimeMillis());
    }
}