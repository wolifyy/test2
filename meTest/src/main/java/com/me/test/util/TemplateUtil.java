package com.me.test.util;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/7/7 0:33:15
 */

import com.me.test.pojo.Student;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.List;

public class TemplateUtil {
    private static final TemplateEngine templateEngine;

    static {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("E:\\java\\wolify\\meTest\\src\\main\\resources\\");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    public static String processTemplate(String templateName, String className, List<Student> students) {
        Context context = new Context();
        context.setVariable("className", className);
        context.setVariable("students", students);

        return templateEngine.process(templateName, context);
    }
}