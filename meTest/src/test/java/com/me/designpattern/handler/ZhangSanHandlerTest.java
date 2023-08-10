package com.me.designpattern.handler;

import com.me.designpattern.factory.AbstarctFactory;
import com.me.designpattern.factory.Factory;
import org.junit.Test;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/8/10 21:45:21
 */
public class ZhangSanHandlerTest {

    @Test
    public void AAA() {
        String name = "张三";
        AbstractHandler invokeStrategy = AbstarctFactory.getInvokeStrategy(name);
        invokeStrategy.AAA(name);
        String bbb = invokeStrategy.BBB(name);
        System.out.println(bbb);
    }


    @Test
    public void lisi() {
        String name = "李四";
        Handler invokeStrategy = Factory.getInvokeStrategy(name);
        invokeStrategy.AAA(name);
    }
}