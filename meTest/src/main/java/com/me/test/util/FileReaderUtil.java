package com.me.test.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/7/20 22:01:33
 */

public class FileReaderUtil {
        public static void main(String[] args) {
            File dir = new File("E:\\java\\wolify\\meTest\\src\\main\\resources\\");
            File[] files = dir.listFiles((d, name) -> name.startsWith("t"));
            StringBuilder result = new StringBuilder();

            for (File file : files) {
                File target = new File(file.getAbsolutePath() + "/student_list.ftl");
                if (target.exists()) {
                    try (BufferedReader br = new BufferedReader(new FileReader(target))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            result.append(line).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println(result.toString());
        }
}
