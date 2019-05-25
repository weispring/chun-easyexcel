package com.alibaba.excel.test.read;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class MarketImportService {

    @Setter
    @Getter
    public static class BusinessImport extends BaseRowModel {
        @ExcelProperty(index = 0)
        private String cityName;
        @ExcelProperty(index = 1)
        private String cityCode;
        @ExcelProperty(index = 2)
        private String areaName;
        @ExcelProperty(index = 3)
        private String areaCode;
        @ExcelProperty(index = 4)
        private String userMobile;
        @ExcelProperty(index = 5)
        private String businessType;
        @ExcelProperty(index = 6)
        private String businessCode;
        @ExcelProperty(index = 7)
        private String businessName;

    }


    public void importBusiness(File file) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            AnalysisEventListener<BusinessImport> excelListener = new ImportAddListener();
            EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, BusinessImport.class),
                    excelListener);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public class ImportAddListener extends AnalysisEventListener<BusinessImport> {

        public ImportAddListener() {
            super();
        }

        @Override
        public void invoke(BusinessImport businessImport, AnalysisContext context) {
            log.info("max:{},total:{},free:{}",1,1,1);
          /*  Runtime rt = Runtime.getRuntime();
            long total = rt.totalMemory();
            long free = rt.freeMemory();
            long max = rt.maxMemory();
            log.info("max:{},total:{},free:{}",max/1024/1024,total/1024/1024,free/1024/1024);*/
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {

        }

        public void doSomething() {

        }
    }

    public static void main(String[] args) {
        Long begin = System.currentTimeMillis();
        String file = "./"+"扫码验证108档3.xlsx";
        new MarketImportService().importBusiness(new File(file));
        log.info("耗时:{}",System.currentTimeMillis() - begin);
    }

}
