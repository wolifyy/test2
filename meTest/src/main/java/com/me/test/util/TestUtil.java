package com.me.test.util;


import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.python.antlr.ast.Str;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author Song
 * @Date 2021/2/26 16:55
 * @Version 1.0
 * @Description
 */
public class TestUtil {
    /**
     * 利用好其预编译功能，可以有效加快正则匹配速度
     */
    public static Pattern dynamic = Pattern.compile(".*\\$\\{([x00-xF]+)\\}.*");
    public static Pattern dynamicLimitCount = Pattern.compile("\\$\\{([x00-xF]+)\\}");

    /**
     * 判断内容中是否包含动态参数(${key}形式的)
     *
     * @param content 要判断的内容
     * @return
     */
    public static boolean isContainsDynamicParameter(String content) {
        return dynamic.matcher(content).matches();
    }

    /**
     * 按照动态内容的参数出现顺序,将参数放到List中
     *
     * @param content
     * @return
     */
    public static List<String> getKeyListByContent(String content) {
        List<String> paramSet = new ArrayList<>();
        Matcher m = dynamicLimitCount.matcher(content);
        while (m.find()) {
            paramSet.add(m.group(0));
        }
        return new ArrayList<>(paramSet);
    }


    public static void main(String[] args) {
        //测试代码
        String content = "尊敬的${name_json}客户您好，请于${time}前到达";
        System.out.println(isContainsDynamicParameter(content));
        List<String> keyListByContent = getKeyListByContent(content);
        System.out.println("内容中的动态参数为:");
        keyListByContent.forEach(System.out::println);


        System.out.println("==================");
        Map<String, String> map = new HashMap();
        for (String s : keyListByContent) {
            map.put(s,"xxxx");
        }

        for (String s : map.keySet()) {
            content = content.replace(s,map.get(s));
        }

        System.out.println(content);
    }
}

