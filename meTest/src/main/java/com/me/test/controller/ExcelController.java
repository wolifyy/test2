package com.me.test.controller;

import com.me.test.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/10 0:07:23
 */

@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {
    @Resource
    private ExcelService excelService;

    @GetMapping("/exportMulData")
    public void exportMulData(HttpServletResponse response) {
        excelService.exportMulData(response);
    }

    @GetMapping("/exportSingleDatas")
    public void exportSingleDatas(HttpServletResponse response) {
        excelService.exportSingleDatas(response);
    }

}