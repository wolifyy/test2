package com.me.test.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/7/7 22:38:02
 */

public class StudentListGenerator {
    public static void main(String[] args) throws Exception {
        // 创建一个配置实例
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDirectoryForTemplateLoading(new File("E:\\java\\wolify\\meTest\\src\\main\\resources\\templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        // 创建数据模型
        Map<String, Object> root = new HashMap<>();
        root.put("classTitle", "一年级一班");
        root.put("teacherName", "张老师");

        List<Map<String, String>> students = new ArrayList<>();
        Map<String, String> student1 = new HashMap<>();
        student1.put("name", "小明");
        student1.put("gender", "男");
        student1.put("age", "7");
        students.add(student1);

        // 添加更多学生...

        root.put("students", students);

        // 获取模板并处理
        Template temp = cfg.getTemplate("student_list.ftl");
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
    }
}