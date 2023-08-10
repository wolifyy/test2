package com.me.designpattern.handler;

import com.me.designpattern.factory.AbstarctFactory;
import com.me.designpattern.factory.Factory;
import org.springframework.stereotype.Component;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/8/10 21:10:25
 */
@Component
public class LisiHandler implements Handler {
    @Override
    public void AAA(String name) {
        //业务逻辑A
        System.out.println("李四执行任务");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Factory.register("李四", this);
    }
}
