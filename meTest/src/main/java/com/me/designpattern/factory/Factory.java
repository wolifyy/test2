package com.me.designpattern.factory;

import com.me.designpattern.handler.AbstractHandler;
import com.me.designpattern.handler.Handler;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 * <p>
 * 工厂设计模式
 *
 * @author wolify
 * @version 1.0
 * @date 2023/8/10 21:16:35
 */

public class Factory {
    private static Map<String, Handler> strategyMap = new HashMap<>();

    public static Handler getInvokeStrategy(String str) {
        return strategyMap.get(str);
    }

    public static void register(String str, Handler handler) {
        if (Strings.isEmpty(str) || null == handler) {
            return;
        }
        strategyMap.put(str, handler);
    }
}
