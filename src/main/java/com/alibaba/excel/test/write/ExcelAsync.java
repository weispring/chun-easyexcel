package com.alibaba.excel.test.write;

import com.alibaba.excel.ExcelWriter;
import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.sl.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: lixianchun
 * Date: 2019/3/25
 * Description:
 */
@Slf4j
public class ExcelAsync {

    private ReentrantLock lock = new ReentrantLock();

    private int size = 100000;

    private List<SaleGoodOrderWriteModel> list = new Vector<>(size);

    {
        for (int i=0;i<size;i++){
            SaleGoodOrderWriteModel model = new SaleGoodOrderWriteModel();
            model.setId(Long.valueOf(i));
            model.setCreatedTime("t"+String.valueOf(i));
            model.setPaySerialNumber("p"+String.valueOf(i));
            list.add(model);
        }
    }

    /**
     * writer.write(list, sheet); 不支持多线程
     * @param executor
     * @param writer
     * @param countDownLatch
     * @param i
     */
    public void AsyncExport(ThreadPoolExecutor executor,ExcelWriter writer, CountDownLatch countDownLatch,int i){
        executor.submit(()->{
            try{
                //2*i+1 解决多个sheet会自动合并到一个sheet里面
                com.alibaba.excel.metadata.Sheet  sheet = new com.alibaba.excel.metadata.Sheet(2*i+1, 1, SaleGoodOrderWriteModel.class);
                sheet.setSheetName("测试"+"_"+i);
                synchronized (Integer.class){
                    writer.write(list, sheet);
                }
                //writer.write(list, sheet);
            }catch (Exception e){
                e.printStackTrace();
            }

            countDownLatch.countDown();
            log.info("写完：{}",i);
        });
     /*   com.alibaba.excel.metadata.Sheet  sheet = new com.alibaba.excel.metadata.Sheet(2*i+1, 1, SaleGoodOrderWriteModel.class);
        sheet.setSheetName("测试"+"_"+(2*i+1));
        writer.write(list, sheet);
        countDownLatch.countDown();
        log.info("写完：{}",i);*/


    }
}
