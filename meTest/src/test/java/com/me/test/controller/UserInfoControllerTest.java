package com.me.test.controller;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.me.test.Model.*;
import com.me.test.util.FileUtil;
import org.bouncycastle.util.Strings;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.apache.commons.net.util.Base64;
import org.python.antlr.ast.Str;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ExecutorService executorService2 = Executors.newFixedThreadPool(2);
        List<CompletableFuture<Double>> futureList = new ArrayList<>();
        List<Double> doubles = Arrays.asList(1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9);

        // 创建异步执行任务:
        doubles.forEach(d->{
            CompletableFuture<Double> cf = CompletableFuture.supplyAsync(()->{
                System.out.println(Thread.currentThread()+" start job1,time->"+System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                System.out.println(Thread.currentThread()+" exit job1,time->"+System.currentTimeMillis());
                return 1.2;
            },pool);
            futureList.add(cf);
        });

        //cf关联的异步任务的返回值作为方法入参，传入到thenApply的方法中
        //thenApply这里实际创建了一个新的CompletableFuture实例
        futureList.forEach(cf ->{
            CompletableFuture<String> cf2=cf.thenApplyAsync((result)->{
                System.out.println(Thread.currentThread()+" start job2,time->"+System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                System.out.println(Thread.currentThread()+" exit job2,time->"+System.currentTimeMillis());
                return "test:"+result;
            });
        });

        System.out.println("main thread start cf.get(),time->"+System.currentTimeMillis());
        //等待子任务执行完成
//        System.out.println("run result->"+cf.get());
        System.out.println("main thread start cf2.get(),time->"+System.currentTimeMillis());
//        System.out.println("run result->"+cf2.get());
        Thread.sleep(20000);
        System.out.println("main thread exit,time->"+System.currentTimeMillis());
    }

    @Test
    public void test6(){
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("E:\\python\\test.py");
        PyFunction function = (PyFunction)interpreter.get("test",PyFunction.class);
        PyObject o = function.__call__(new PyInteger(8),new PyInteger(23));
    }

    @Test
    public void test7(){
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("import sys");
        interpreter.set("a", new PyString("hello"));
        interpreter.exec("print a");

        interpreter.cleanup();
        PyObject x = interpreter.get("x");
        System.out.println("x: " + x);

        interpreter.exec("x = 2+2");
        x = interpreter.get("x");
        System.out.println("x: " + x);

        System.out.println(interpreter.getLocals());
        System.out.println(interpreter.getSystemState());
    }

    @Test
    public void test8(){
        PythonInterpreter interpreter = new PythonInterpreter();

        interpreter.execfile("E:\\python\\add.py");

// 第一个参数为期望获得的函数(变量)的名字，第二个参数为期望返回的对象类型

        PyFunction pyFunction = interpreter.get("add", PyFunction.class);

        int a = 5, b = 10;

//调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”

        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));

        System.out.println("the anwser is: " + pyobj);

    }

    @Test
    public void test9(){
        System.out.println(Runtime.getRuntime().availableProcessors());
    }


    @Test
    public void test10() throws IOException {
        Map<String,Date> map = new HashMap<>();
        map.put("startTime",new Date());
        String json = new Gson().toJson(map);
        System.out.println(json);
        Map map1 = new Gson().fromJson(json, Map.class);
        Sorter sorter = new Sorter();
        sorter.setColumn("");
        sorter.setOrder("");
        sorter.setStartTime(new Date());
        String json1 = new Gson().toJson(sorter);
        System.out.println(json1);
        Sorter sorter1 = new Gson().fromJson(json1, Sorter.class);

        System.out.println("==============fastjson======");
        String f = JSON.toJSONString(map);
        String f2 = JSON.toJSONString(sorter);
        Map map2 = JSON.parseObject(f, Map.class);
        Sorter sorter2 = JSON.parseObject(f2, Sorter.class);

        ObjectMapper mapper = new ObjectMapper();
        String string = mapper.writeValueAsString(map);
        System.out.println(string);
        Map map3 = mapper.readValue(string, Map.class);


    }

    @Test
    public void test11(){
        String str = "ASD";
        System.out.println("2424".toLowerCase());
        System.out.println(Strings.toLowerCase(null));
    }


    @Test
    public void test12(){
        List<String> strings = Arrays.asList("1,2,,s".split(","));
        System.out.println(strings.stream().filter(s->StringUtils.isNotBlank(s)).collect(Collectors.toList() ));
    }

    @Test
    public void test13(){
        File file = new File("C:\\Users\\11864\\Desktop\\项目构建步骤.txt");
        Long lastModified = file.lastModified();
        Date date = new Date(lastModified);
        System.out.println(date);
        System.out.println("====================");
        long start = System.currentTimeMillis();
        byte[] bytes = FileUtil.getBytes("C:\\Users\\11864\\Desktop\\项目构建步骤.txt");
        String str = Base64.encodeBase64String(bytes);
        byte[] bytes2 = FileUtil.getBytes("C:\\Users\\11864\\Desktop\\study\\机考注意事项.doc");
        String str2 = Base64.encodeBase64String(bytes2);
        Map<String,String> map = new HashMap<>();
        map.put("test.doc",str2);
        map.put("test2.doc",str2);
        map.put("test3.doc",str2);
        map.put("test4.doc",str2);
        map.put("test5.doc",str2);
        String s = new Gson().toJson(map);
        Map<String, String> map1 = new Gson().fromJson(s, Map.class);

        map1.forEach((k,v)->{
            byte[] byteres = Base64.decodeBase64(v);
            FileUtil.getFile(byteres,"C:\\Users\\11864\\Desktop",k);
        });
        long end = System.currentTimeMillis();
        System.out.println(getDatePoor(new Date(end),new Date(start)));
        System.out.println(end-start);
    }

    private static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
// long ns = 1000;
// 获得两个时间的毫秒时间差异
        long time = endDate.getTime() - nowDate.getTime();
// 计算差多少天
        long day = time / nd;
// 计算差多少小时
        long hour = time % nd / nh;
// 计算差多少分钟
        long min = time % nd % nh / nm;
// 计算差多少秒//输出结果
// long sec = time % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }
}