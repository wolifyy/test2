package com.me.test.controller;

import com.alibaba.fastjson.JSON;
import com.me.test.pojo.FrameworkElement;
import com.me.test.pojo.UserInfo;
import com.me.test.service.FrameworkElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/4 14:10:02
 */
@Controller
//@RequestMapping指定路径名
//@RequestMapping("/test")用这个来指定路径也是可以的
@RequestMapping(value = "/frame")
public class FrameworkElementController {

    @Autowired
    private FrameworkElementService frameworkElementService;

    //Get请求
    @GetMapping
    //@ResponseBody 注释后表示放回的是字符串
    @ResponseBody
    public String queryAll(){
        List<FrameworkElement> userInfoList = frameworkElementService.queryAll();
        return JSON.toJSONString(userInfoList);
    }
}
