package com.zz.bs;/**
 * @Description:
 * @Author: Zuck
 * @Date: $date$ $time$
 **/

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.zz.config.ExcelListener;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 读取类
 * @author xxx
 * @date 2021/12/29 13:58
 **/
@Service
public class ExcelOptionService {


        /**
         * 根据excel输入流，读取excel文件
         *
         * @param inputStream exece表格的输入流
         * @return 返回双重list的集合
         **/
        public List<List<String>> writeWithoutHead(InputStream inputStream) {
            StringExcelListener listener = new StringExcelListener();
            ExcelReader excelReader = EasyExcelFactory.read(inputStream, null, listener).headRowNumber(0).build();
            excelReader.read();
            List<List<String>> datas = listener.getDatas();
            excelReader.finish();
            return datas;
        }

        /**
         * StringList 解析监听器
         *
         * @author zhangcanlong
         * @since 2019-10-21
         */
        private  static class StringExcelListener extends ExcelListener {

            /**
             * 自定义用于暂时存储data
             * 可以通过实例获取该值
             */
            private List<List<String>> datas = new ArrayList<>();

            /**
             * 每解析一行都会回调invoke()方法
             *
             * @param object  读取后的数据对象
             * @param context 内容
             */
            int i=0;
            @Override
            public void invoke(Object object, AnalysisContext context) {
                @SuppressWarnings("unchecked") Map<String, String> stringMap = (HashMap<String, String>) object;
                // 这里可以获取excel的基本信息，包含excel的总行数
                //记录次数  或者从第5行开始统计
                i+=1;
                //数据存储到list，供批量处理，或后续自己业务逻辑处理。
                if(i>=5){
                    // 第17列为1说明是应出勤日，过滤下班时间大于8点的（第7列）
                    // 过滤最后一天未打卡的
                        if(stringMap.get(17)!=null&&stringMap.get(7)!=null){
                            //当天统计状态没有更新
                            if (stringMap.get(7).equals("未打卡")) {
                                datas.add(new ArrayList<>(stringMap.values()));
                            }else{
                                DateFormat df=new SimpleDateFormat("HH:mm:ss");
                                try {
                                    Date date=df.parse(stringMap.get(7));
                                    Date lastDate=df.parse("20:00:00");
                                    //工作日筛选
                                    if(stringMap.get(17).equals("1")&&date.after(lastDate)){
                                        datas.add(new ArrayList<>(stringMap.values()));
                                    }
                                    //节假日筛选
                                    if(stringMap.get(17).equals("0")&&date.toString()!=null){
                                        datas.add(new ArrayList<>(stringMap.values()));
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }


                //
                // 18列为0为休息日

            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                //解析结束销毁不用的资源
                //注意不要调用datas.clear(),否则getDatas为null
            }

            /**
             * 返回数据
             *
             * @return 返回读取的数据集合
             **/
            public List<List<String>> getDatas() {
                return datas;
            }

            /**
             * 设置读取的数据集合
             *
             * @param datas 设置读取的数据集合
             **/
            public void setDatas(List<List<String>> datas) {
                this.datas = datas;
            }
        }

}
