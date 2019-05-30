package com.alibaba.excel.test.write;

import java.util.List;

/**
 * @author lixianchun
 * @Description
 * @date 2019/5/29 16:25
 */
public interface ExportListDataDao {

    /**
     * 获取数据
     * @param baseSearchParam
     * @return
     */
    List list(Object baseSearchParam);


    Long total(Object baseSearchParam);

}