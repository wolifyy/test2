package com.me.designpattern.handler;

import org.springframework.beans.factory.InitializingBean;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 * <p>
 * 策略设计模式
 *
 * @author wolify
 * @version 1.0
 * @date 2023/8/10 21:06:23
 */
public interface Handler extends InitializingBean {

    public void AAA(String name);
}
