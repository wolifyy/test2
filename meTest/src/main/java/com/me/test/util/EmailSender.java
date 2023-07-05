package com.me.test.util;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/7/5 23:30:26
 */

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.util.*;

public class EmailSender {
    public static void sendEmail(List<Student> students) throws MessagingException {
        String to = "xiaoming@test.com";
        String from = "your-email@example.com";
        String host = "your-smtp-server.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Convert List<Student> to HTML table
        StringBuilder sb = new StringBuilder();
        sb.append("<table><tr><th>Name</th><th>Age</th></tr>");
        for (Student student : students) {
            sb.append("<tr><td>").append(student.getName()).append("</td><td>").append(student.getAge()).append("</td></tr>");
        }
        sb.append("</table>");

        message.setContent(sb.toString(), "text/html");

        Transport.send(message);
    }


    /*
    ==============================================================================================
    按照表格转换方式
     */


    public String createTable(List<Student> students) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Age");

        // 填充数据
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(student.getName());
            row.createCell(1).setCellValue(student.getAge());
        }

        // 将workbook转换为字符串
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        return Base64.getEncoder().encodeToString(bos.toByteArray());
    }

    public void sendEmail(String to, String subject, String content) {
        String from = "your-email@example.com";
        String host = "smtp.example.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public void sendEmailWithAttachment(String to, String subject, String content, String attachment) {
        String from = "your-email@example.com";
        String host = "smtp.example.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);

            // 添加附件
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(Base64.getDecoder().decode(attachment), "application/vnd.ms-excel");
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName("students.xlsx");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Student> students = ... // 你的学生列表
        String table = createTable(students);
        sendEmail("xiaoming@test.com", "Student Table", table);


        //附件方式
        List<Student> students = ... // 你的学生列表
        String table = createTable(students);
        sendEmailWithAttachment("xiaoming@test.com", "Student Table", "See attached file.", table);
    }





}
