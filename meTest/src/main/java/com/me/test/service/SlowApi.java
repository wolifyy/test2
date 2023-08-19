package com.me.test.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/8/20 1:24:30
 */

public class SlowApi {


    private static Cache<String, String> cache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build();


    public void tes1() {
        List<String> teams = new ArrayList<>();
        List<CompletableFuture<Boolean>> futures = teams.stream()
                .map(team -> CompletableFuture.supplyAsync(() -> calXft(team))
                        .exceptionally(ex -> {
                            // 异常处理逻辑
                            return false; // 返回默认值或其他处理
                        }))
                .collect(Collectors.toList());

        List<Boolean> resultList = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        boolean calResult = resultList.stream().allMatch(Boolean::booleanValue);
        //todo 结果判断
    }


    public boolean calXft(String team) {
        String result = cache.get(team, (String mon) -> calMon(mon));
        return true;
    }

    public String calMon(String mon) {
        //调用dao
        String res = "调用dao后结果";
        if (mon != "当前月") {
            cache.put(mon, res);
        }

        return res;
    }
}
