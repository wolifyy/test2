package com.me.test.service.impl;

import com.me.test.mapper.FrameworkElementMapper;
import com.me.test.pojo.FrameworkElement;
import com.me.test.service.FrameworkElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/4 14:04:34
 */
@Service
public class FrameworkElementServiceImpl implements FrameworkElementService {

    @Autowired
    private FrameworkElementMapper frameworkElementMapper;

    @Override
    public List<FrameworkElement> queryAll() {
        return frameworkElementMapper.queryAll();
    }
}
