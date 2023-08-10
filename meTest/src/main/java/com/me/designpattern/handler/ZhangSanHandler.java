package com.me.designpattern.handler;

import com.me.designpattern.factory.AbstarctFactory;
import org.springframework.stereotype.Component;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/8/10 21:10:25
 */
@Component
public class ZhangSanHandler extends AbstractHandler {
    @Override
    public void AAA(String name) {
        //业务逻辑A
        System.out.println("张三执行任务");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstarctFactory.register("张三",this);
    }
}
