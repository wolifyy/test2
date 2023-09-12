package com.me.test.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2023/9/12 23:04:57
 */

public class FileTransfer {

    @Test
    public void json_gson(){
        String jsonStr = "{\"class\":\"xxxx\",\"students\":[{\"name\":\"zhangsan\",\"age\":12},{\"name\":\"lisi\",\"age\":12}],\"school\":\"2\"}";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // 将JSON字符串解析为JsonObject
        JsonObject jsonObj = JsonParser.parseString(jsonStr).getAsJsonObject();

        // 清空students数组
        jsonObj.add("students", gson.toJsonTree(new String[]{}));

        // 返回新的json字符串
        String newJsonStr = gson.toJson(jsonObj);
        System.out.println(newJsonStr);
    }

    @Test
    public void json_jackson(){
        String jsonStr = "{\"class\":\"xxxx\",\"students\":[{\"name\":\"zhangsan\",\"age\":12},{\"name\":\"lisi\",\"age\":12}],\"school\":\"2\"}";
        ObjectMapper mapper = new ObjectMapper();

        // 将JSON字符串解析为JsonNode
        String newJsonStr = null;
        try {
            JsonNode rootNode = mapper.readTree(jsonStr);

            // 清空students数组
            ((ArrayNode) rootNode.get("students")).removeAll();

            // 返回新的json字符串
            newJsonStr = mapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {

            //todo
        }
        System.out.println(newJsonStr);
    }

    @Test
    public void json_org() throws JSONException {
        String jsonStr = "{\"class\":\"xxxx\",\"students\":[{\"name\":\"zhangsan\",\"age\":12},{\"name\":\"lisi\",\"age\":12}]}";
        JSONObject jsonObj = new JSONObject(jsonStr);

        // 清空students数组
        jsonObj.put("students", new JSONArray());

        // 返回新的json字符串
        String newJsonStr = jsonObj.toString();
        System.out.println(newJsonStr);
    }

    @Test
    public void json_fastjson(){
        String jsonStr = "{\"class\":\"xxxx\",\"students\":[{\"name\":\"zhangsan\",\"age\":12},{\"name\":\"lisi\",\"age\":12}]}";

        // 将JSON字符串解析为JSONObject

        com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(jsonStr);

        // 清空students数组
        jsonObj.put("students", new JSONArray());

        // 返回新的json字符串
        String newJsonStr = jsonObj.toJSONString();
        System.out.println(newJsonStr);
    }

    //第一种或第二种不会该表顺序,gson最优可以beauty


    @Test
    public void yml_org(){
        Yaml yaml = new Yaml();
        InputStream inputStream = FileTransfer.class
                .getClassLoader()
                .getResourceAsStream("config.yml");
        Map<String, Object> obj = yaml.load(inputStream);
        Map<String, Object> server = (Map<String, Object>) obj.get("server");
        server.put("type", new ArrayList<>());

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        yaml = new Yaml(options);
        String output = yaml.dump(obj);
        System.out.println(output);
    }

    @Test
    public void test_org2(){
        String yamlStr = "server:\n" +
                "  #设置端口号\n" +
                "  port: 8081 #默认端口是8080\n" +
                "  type:\n" +
                "    - id: 1\n" +
                "      name: abc\n" +
                "      require: ture\n" +
                "    - id: 2\n" +
                "      name: d\n" +
                "      require: ture";

        Yaml yaml = new Yaml();
        Map<String, Object> yamlMap = yaml.load(yamlStr);

        // 修改type数组为一个空数组
        ((Map<String, Object>) yamlMap.get("server")).put("type", new ArrayList<>());

        // 设置输出选项
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        // 创建一个新的Yaml对象用于输出
        Yaml newYaml = new Yaml(options);
        String newYamlStr = newYaml.dump(yamlMap);

        System.out.println(newYamlStr);
    }

    @Test
    public void yml_jackson() throws JsonProcessingException {
        String yamlStr = "server:\n" +
                "  #设置端口号\n" +
                "  port: 8081 #默认端口是8080\n" +
                "  type:\n" +
                "    - id: 1\n" +
                "      name: abc\n" +
                "      require: ture\n" +
                "    - id: 2\n" +
                "      name: d\n" +
                "      require: ture";

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        Map<String, Object> yamlMap = mapper.readValue(yamlStr, Map.class);

        // 修改type数组为一个空数组
        ((Map<String, Object>) yamlMap.get("server")).put("type", new ArrayList<>());

        String newYamlStr = mapper.writeValueAsString(yamlMap);

        System.out.println(newYamlStr);
    }

    //综上对比，jackson更加（需要单独引用jackson库的YAML模块）
}
