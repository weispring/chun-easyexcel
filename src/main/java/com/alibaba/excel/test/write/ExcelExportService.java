package com.alibaba.excel.test.write;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Author: lixianchun
 * Date: 2019/3/25
 * Description:
 */
@Slf4j
public class ExcelExportService {

    private ExcelAsync excelAsync = new ExcelAsync();



    public void exportDetail(){
        try {
            File file = new File("./"+ new Random().nextInt(100)+"ceshi.xlsx");
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            ExcelWriter writer = new ExcelWriter(bos, ExcelTypeEnum.XLSX);
            int sheets = 1;
            CountDownLatch countDownLatch = new CountDownLatch(sheets);
            Long start = System.currentTimeMillis();
            ThreadPoolExecutor executor = new ThreadPoolExecutor(2,2,3, TimeUnit.SECONDS,new LinkedBlockingDeque<>(10));
            for (int i=0;i<sheets;i++){
                excelAsync.AsyncExport(executor,writer,countDownLatch,i);
            }
            try {
                countDownLatch.await();
                writer.finish();
                executor.shutdown();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            log.info("总共耗时：{}",System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.error("导出错误", e);
        } finally {
        }
        log.info("完成");
    }

    public static void main(String[] args) {
        new ExcelExportService().exportDetail();;
    }



}
