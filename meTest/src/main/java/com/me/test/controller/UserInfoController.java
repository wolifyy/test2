package com.me.test.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.me.test.Model.*;
import com.me.test.pojo.UserInfo;
import com.me.test.service.UserInfoService;
import com.me.test.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpContext;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/4 13:17:34
 */

//@Controller 控制层需要的注解
//@RestController 使用这个也是可以的，但是使用后他里面所有请求返回的都是字符串！
//一般只需要作为接口放回JSON格式数据的话推荐使用@RestController
//@Controller这个是可以与Thymeleaf模板引擎使用时可以返回一个页面的
@Controller
//@RequestMapping指定路径名
//@RequestMapping("/test")用这个来指定路径也是可以的
@RequestMapping(value = "/test")
public class UserInfoController {
    //获取到UserInfoService
    @Autowired
    private UserInfoService userInfoService;

    //Get请求
    @GetMapping
    //@ResponseBody 注释后表示放回的是字符串
    @ResponseBody
    public String queryAll() {
        List<UserInfo> userInfoList = userInfoService.queryAll();
        return JSON.toJSONString(userInfoList);
    }

    //使用了RestFull风格
    @GetMapping("/{id}")
    @ResponseBody
    public String query(@PathVariable(value = "id") Integer id) {
        UserInfo userInfo = userInfoService.queryById(id);
        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoList.add(userInfo);
        return JSON.toJSONString(userInfoList);
    }

    //post请求
    //@RequestBodyB 表示接收请求是JSON格式的数据
    @PostMapping
    @ResponseBody
    public String add(@RequestBody UserInfo userInfo) {
        userInfoService.add(userInfo);
        return "添加OK";
    }

    //Delete请求
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) {
        userInfoService.delete(id);
        return "删除成功";
    }

    //Put请求
    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable("id") Integer id,
                         @RequestBody UserInfo userInfo) {
        userInfo.setId(id);
        userInfoService.update(userInfo);
        return "修改成功";
    }

    //post请求
    //@RequestBodyB 表示接收请求是JSON格式的数据
    @PostMapping("/queryByPage")
    @ResponseBody
    public String queryByPage(@RequestBody RequestBodyB rb){
        Gson gson = new Gson();
        ResponseB responseB = userInfoService.queryByPage(rb.getSearchCondition(), rb.getSorter(), rb.getPage());
        return gson.toJson(responseB);

    }

    @PostMapping("/importExcel")
    @ResponseBody
    public Boolean addUserInfo(@RequestBody MultipartFile filee) throws Exception {
        return userInfoService.addUserInfo(filee);
    }

    /**
     * 导出报表，这里get和post请求复用了该方法，仅仅是为了测试
     *
     * @return
     */
    @GetMapping(value = "/export")
    @ResponseBody
    public void export( HttpServletResponse response) throws Exception {
//        if (user == null && !StringUtils.isEmpty(username)) {
//            //GET 请求的参数
//            user = new UserInfo();
//            user.setUsername(username);
//        }
        //获取数据
        List<UserInfo> list = userInfoService.queryAll();

        //excel标题
        String[] title = {"序号", "用户名", "密码", "权限"};

        //excel文件名
        String fileName = System.currentTimeMillis() + ".xls";

        //sheet名
        String sheetName = "用户信息";

        //没有数据就传入null吧，Excel工具类有对null判断
        String[][] content = null;

        if (list != null && list.size() > 0) {
            content = new String[list.size()][title.length];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (int i = 0; i < list.size(); i++) {
                content[i] = new String[title.length];
                UserInfo obj = list.get(i);
                content[i][0] = String.valueOf(obj.getId());
                content[i][1] = obj.getUsername();
                content[i][2] = obj.getPassword();
                content[i][3] = obj.getAuthority();
            }
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content);

        //响应到客户端
        try {
            fileName = new String(fileName.getBytes(), "UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

