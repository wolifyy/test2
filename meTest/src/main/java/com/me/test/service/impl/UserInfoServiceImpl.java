package com.me.test.service.impl;

import com.me.test.Model.Page;
import com.me.test.Model.ResponseB;
import com.me.test.Model.SearchCondition;
import com.me.test.Model.Sorter;
import com.me.test.mapper.UserInfoMapper;
import com.me.test.pojo.UserInfo;
import com.me.test.service.UserInfoService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.me.test.util.ExcelMergedRegionUtil.getMergedRegionValue;
import static com.me.test.util.ExcelMergedRegionUtil.isMergedRegion;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/4 13:16:43
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void add(UserInfo userInfo) {
        userInfoMapper.add(userInfo);
    }

    @Override
    public void delete(Integer id) {
        userInfoMapper.delete(id);
    }

    @Override
    public void update(UserInfo userInfo) {
        userInfoMapper.update(userInfo);
    }

    @Override
    public UserInfo queryById(Integer id) {
        return userInfoMapper.queryById(id);
    }

    @Override
    public List<UserInfo> queryAll() {
        return userInfoMapper.queryAll();
    }

    @Override
    public ResponseB queryByPage(SearchCondition searchCondition, Sorter sorter, Page page) {
        Map<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put("searchCondition",searchCondition);
        paraMap.put("sorter",sorter);
        page.setCurrentItem((page.getCurrentPage()-1)*page.getPageSize());
        paraMap.put("page",page);
        ResponseB responseB = new ResponseB();
        responseB.setUserInfoList(userInfoMapper.queryByPage(paraMap));
        responseB.setTotalItem(userInfoMapper.queryTotal());
        responseB.setTotalPage(userInfoMapper.queryTotal()/page.getPageSize()+1);
        return responseB;
    }

    @Override
    public Integer queryTotal() {
        return userInfoMapper.queryTotal();
    }


    @Override
    public Boolean addUserInfo(MultipartFile file) throws Exception{

        boolean result = false;
//		存放excel表中所有user细腻
        List<UserInfo> userInfoList = new ArrayList<>();
        /**
         *
         * 判断文件版本
         */
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);

        InputStream ins = file.getInputStream();

        Workbook wb = null;

        if(suffix.equals("xlsx")){

            wb = new XSSFWorkbook(ins);

        }else{
            wb = new HSSFWorkbook(ins);
        }
        /**
         * 获取excel表单
         */
        Sheet sheet = wb.getSheetAt(0);


        /**
         * line = 2 :从表的第三行开始获取记录
         *
         */
        if(null != sheet){

            for(int line = 1; line <= sheet.getLastRowNum();line++){

                UserInfo userInfo = new UserInfo();

                Row row = sheet.getRow(line);

                if(null == row){
                    continue;
                }
                /**
                 * 判断单元格类型是否为文本类型
                 */
//                if(1 != row.getCell(0).getCellType()){
////                    throw new MyException("单元格类型不是文本类型！");
//                    continue;
//                }

                /**
                 * 获取第一个单元格的内容
                 */
                String username = row.getCell(0).getStringCellValue();

//                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                /**
                 * 获取第二个单元格的内容
                 */

                String password = row.getCell(1).getStringCellValue();
                String authority = "";
                if(isMergedRegion(sheet,line,2)){
                    authority = getMergedRegionValue(sheet,line,2);
                }
                Long userId = (long)row.getCell(3).getNumericCellValue();

                userInfo.setUsername(username);
                userInfo.setPassword(password);
                userInfo.setAuthority(authority);
                userInfo.setUserId(userId);
                userInfoList.add(userInfo);

            }

            userInfoMapper.deleteAll();
            for(UserInfo userInfo:userInfoList){

                /**
                 * 判断数据库表中是否存在用户记录，若存在，则更新，不存在，则保存记录
                 */
//                String username = userInfo.getUsername();
//
//                int count = userInfoMapper.selectUser(username);
//
//                if(0 == count){
//                    userInfoMapper.add(userInfo);
//                    result = true;
//                }else{
//                    userInfoMapper.update(userInfo);
//                    result = true;
//                }
                userInfoMapper.add(userInfo);
                result = true;




            }
        }

        return result;
    }


}

