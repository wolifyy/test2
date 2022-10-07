package com.me.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/4 1:33:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrameworkElement {
    private String productDomain;
    private String component;
    private String service;
    private String microService;
    private String serviceGroup;
    private String createUser;
    private String dataSource;
}
