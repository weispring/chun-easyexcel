package com.alibaba.excel.test.write;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: lixianchun
 * Date: 2019/3/25
 * Description:
 */
@Slf4j
public class ExcelAsync {

    private static ReentrantLock lock = new ReentrantLock();


    /**
     *
     * @param baseSearchParam
     * @param exportListData
     * @param baseRowModelClass
     * @param writer
     * @param countDownLatch
     * @param executor
     * @param i
     * @param <baseRowModel>
     */
    public static  <baseRowModel extends BaseRowModel> void asyncExport(Object baseSearchParam, ExportListDataDao exportListData, Class<baseRowModel>  baseRowModelClass,
                                                                        ExcelWriter writer, CountDownLatch countDownLatch, ThreadPoolExecutor executor, int i){
        executor.submit(()->{
            List list = exportListData.list(baseSearchParam);
            List<baseRowModel> rowModels = new ArrayList<>(list.size());
            list.forEach(e->{
                try {
                    baseRowModel model = baseRowModelClass.newInstance();
                    BeanUtils.copyProperties(e, model);
                    rowModels.add(model);
                }catch (Exception ex){
                    log.error("bean 转换:{}",ex);
                }

            });
            Sheet sheet = new Sheet(2*i+1, 1, baseRowModelClass);
            lock.lock();
            writer.write(rowModels, sheet);
            lock.unlock();
        });
        countDownLatch.countDown();
    }

}
