package com.me.test.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/7/6 0:09:51
 */

public class sendEmail {

    public void sendEmail(List<Student> students) throws EmailException {
        // 创建HtmlEmail实例
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.test.com"); // 设置SMTP服务器
        email.setAuthentication("username", "password"); // 设置登录信息

        // 设置邮件信息
        email.setFrom("your-email@test.com");
        email.addTo("xiaoming@test.com");
        email.addCc("zhangsan@test.com");
        email.setSubject("学生信息表");

        // 将List<Student>转换为HTML表格
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        for (Student student : students) {
            sb.append("<tr><td>" + student.getName() + "</td><td>" + student.getAge() + "</td></tr>");
        }
        sb.append("</table>");

        // 设置邮件正文
        email.setHtmlMsg(sb.toString());

        // 发送邮件
        email.send();
    }
}
