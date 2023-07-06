package com.me.test.util;

import com.me.test.pojo.Student;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/7/7 0:39:28
 */
public class TemplateUtilTest {

    @Test
    public void processTemplate() {
        Student student = Student.builder().name("张三").gender("男").age(10).build();
        Student student2 = Student.builder().name("张三1").gender("男").age(11).build();
        Student student3 = Student.builder().name("张三2").gender("男").age(12).build();
        List<Student> students = Arrays.asList(student, student2, student3);
        String result = TemplateUtil.processTemplate("temp", "实际班级名称", students);
        System.out.println(result);
    }
}