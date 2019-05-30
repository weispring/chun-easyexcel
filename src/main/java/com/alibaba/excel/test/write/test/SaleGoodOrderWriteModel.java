package com.alibaba.excel.test.write.test;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 导出销售单
 * Author: lixianchun
 * Date: 2019/3/25
 * Description:
 */
@Setter
@Getter
@NoArgsConstructor
public class SaleGoodOrderWriteModel extends BaseRowModel {

    /**
     * index 可以不赋值,如何保证顺序
     * 可用get+Field 覆盖原来的值，并且类型可以不同，得到值后是直接调用toString ?
     */
    @ExcelProperty(value = "订单编号")
    protected Long id;

    public String getId(){
        return null;
    }
    /**
     * 创建时间
     */
    @ExcelProperty(value = "下单时间")
    protected String createdTime;

    @ExcelProperty(value = "支付流水号")
    private String paySerialNumber;


    @ExcelProperty(value = "下单时间")
    protected String createdTime1;

    @ExcelProperty(value = "支付流水号")
    private String paySerialNumber2;


    public Long getPaySerialNumber(){
        return 123789L;
    }

}
