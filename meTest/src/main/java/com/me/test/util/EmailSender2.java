package com.me.test.util;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/7/5 23:51:36
 */

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EmailSender2 {
    public void sendEmail(List<Student> students) throws Exception {
        // 创建一个工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建一个工作表
        Sheet sheet = workbook.createSheet("Students");

        // 在工作表中添加学生数据
        int rowNum = 0;
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getName());
            row.createCell(1).setCellValue(student.getAge());
            // 添加更多的学生信息...
        }

        // 将工作簿转换为字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } finally {
            bos.close();
        }
        byte[] bytes = bos.toByteArray();

        // 创建一个邮件会话
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.test.com");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("username", "password");
            }
        });

        // 创建一个邮件消息
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@test.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("xiaoming@test.com"));
        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("zhangsan@test.com"));
        message.setSubject("学生信息");

        // 创建一个多部分消息
        Multipart multipart = new MimeMultipart();

        // 添加文本部分
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("这是学生信息表格");
        multipart.addBodyPart(messageBodyPart);

        // 添加附件部分
        messageBodyPart = new MimeBodyPart();
        DataSource source = new ByteArrayDataSource(bytes, "application/vnd.ms-excel");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("students.xlsx");
        multipart.addBodyPart(messageBodyPart);

        // 设置邮件消息的内容
        message.setContent(multipart);

        // 发送邮件
        Transport.send(message);
    }
}