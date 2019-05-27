package com.alibaba.excel.test.write;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
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
     *
     */
    @ExcelProperty(value = "订单编号",index = 0)
    protected Long id;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "下单时间",index = 1)
    protected String createdTime;

    @ExcelProperty(value = "支付流水号",index = 2)
    private String paySerialNumber;



}
