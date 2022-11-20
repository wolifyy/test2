package org.ph.share._04_CompletableFuture_advance;

import java.util.concurrent.*;

public class _01_thenApply {
    public static void main(String[] args) {
        ForkJoinPool pool=new ForkJoinPool();
        ForkJoinPool pool2=new ForkJoinPool();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        SmallTool.printTimeAndThread("小白吃好了");
        SmallTool.printTimeAndThread("小白 结账、要求开发票");


        CompletableFuture<String> invoice = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("服务员收款 500元");
            SmallTool.sleepMillis(100);
            return "500";
        });

        CompletableFuture<String> invoice2 = invoice.thenApplyAsync(money -> {
            SmallTool.printTimeAndThread(String.format("服务员开发票 面额 %s元", money));
            SmallTool.sleepMillis(200);
            return String.format("%s元发票", money);},executor);
        CompletableFuture<String> invoice3 = invoice2.thenApplyAsync(money -> {
            SmallTool.printTimeAndThread(String.format("服务员开发票 面额 %s元", money));
            SmallTool.sleepMillis(200);
            return String.format("%s元发票", money);},executor);

        SmallTool.printTimeAndThread("小白 接到朋友的电话，想一起打游戏");

        SmallTool.printTimeAndThread(String.format("小白拿到%s，准备回家", invoice3.join()));
    }


    private static void one() {
        SmallTool.printTimeAndThread("小白吃好了");
        SmallTool.printTimeAndThread("小白 结账、要求开发票");

        CompletableFuture<String> invoice = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("服务员收款 500元");
            SmallTool.sleepMillis(100);
            SmallTool.printTimeAndThread("服务员开发票 面额 500元");
            SmallTool.sleepMillis(200);
            return "500元发票";
        });

        SmallTool.printTimeAndThread("小白 接到朋友的电话，想一起打游戏");

        SmallTool.printTimeAndThread(String.format("小白拿到%s，准备回家", invoice.join()));
    }


    private static void two() {
        SmallTool.printTimeAndThread("小白吃好了");
        SmallTool.printTimeAndThread("小白 结账、要求开发票");

        CompletableFuture<String> invoice = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("服务员收款 500元");
            SmallTool.sleepMillis(100);

            CompletableFuture<String> waiter2 = CompletableFuture.supplyAsync(() -> {
                SmallTool.printTimeAndThread("服务员开发票 面额 500元");
                SmallTool.sleepMillis(200);
                return "500元发票";
            });

            return waiter2.join();
        });

        SmallTool.printTimeAndThread("小白 接到朋友的电话，想一起打游戏");

        SmallTool.printTimeAndThread(String.format("小白拿到%s，准备回家", invoice.join()));
    }
}
