package com.zz.Dao;/**
 * @Description:
 * @Author: Zuck
 * @Date: $date$ $time$
 **/

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 存储加班字段
 * @author xxx
 * @date 2021/12/29 11:46
 **/
@Data
@Repository
public class UserExcelModel extends BaseRowModel implements Serializable {
    @ExcelProperty(value = "工作日加班明细",index = 0)
    private String workDaysDetail;
    @ExcelProperty(value = "工作日加班天数",index = 0)

    private  Integer workDaysCount;
    @ExcelProperty(value = "节假日加班明细",index = 0)

    private String holidaysDays;
    @ExcelProperty(value = "节假日加班天数",index = 0)

    private  Integer holidaysDaysCount;


}
