package com.zz.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.zz.Dao.UserExcelModel;
import com.zz.bs.ExcelOptionService;
import com.zz.config.ExcelListener;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.apache.logging.log4j.core.util.Assert.*;

/**
 * 统计每个月加班次数
 * @author zhangzhen
 * @date 2021/12/29 11:18
 **/

@RestController
public class CountWorkDays {

    @Resource
    UserExcelModel userExcelModel=new UserExcelModel();
    @Autowired
      ExcelOptionService excelOptionsService=new ExcelOptionService();


    @PostMapping("readExcel")
    public List<List<String>> readExcel(InputStream inputStream) {
        ExcelListener listener = new ExcelListener();
        ExcelReader excelReader = EasyExcelFactory.read(inputStream, null, listener).headRowNumber(0).build();
        excelReader.read();
        List<List<String>> datas = listener.getDatas();
        excelReader.finish();
        return datas;

    }
    /**
     * 测试读取excel
     **/
    @Test
    public void testReadExcel() {
        // 这里的excel文件可以 为xls或xlsx结尾
        File file = new File("C:\\Users\\Administrator\\Desktop\\test.xls");
        List<List<String>> result = new ArrayList<>();
        try {
            result =excelOptionsService.writeWithoutHead(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出为文本文档
     * @author zhangzhen
     * @date 2021/12/29 17:13
     * @param result
     * @return void
     */
    public void outputText( List<List<String>> result ){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("D:/count.txt");//创建文本文件
            int i=0;
            for (List<String> strings : result) {
                if(strings.get(17).equals("0")){
                    i++;
                    fileWriter.write(i+"."+strings.get(3)+" "+strings.get(5)+"-"+strings.get(7)+" "+"\r\n");//写入 \r\n换行
                }
            }
            fileWriter.write("节假日加班"+"共"+i+"天"+"\r\n");
            fileWriter.write("---------------------------------------------------------"+"\r\n");//写入 \r\n换行
            i=0;
            for (List<String> strings : result) {
                if(strings.get(17).equals("1")){
                    i++;
                    fileWriter.write(i+"."+strings.get(3)+" "+strings.get(5)+"-"+strings.get(7)+" "+"\r\n");//写入 \r\n换行
                }
            }
            fileWriter.write("工作日加班"+"共"+i+"天"+"\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
