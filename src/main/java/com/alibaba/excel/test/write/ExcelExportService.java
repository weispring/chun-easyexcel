package com.alibaba.excel.test.write;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.net.URLEncoder;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 导出exel
 * Author: lixianchun
 * Date: 2019/3/25
 * Description:
 */
@Slf4j
@Service
public class ExcelExportService {

    int pageSize = 500;

    public  <baseRowModel extends BaseRowModel> void export(Object baseSearchParam, ExportListDataDao exportListData, Class<baseRowModel>  baseRowModelClass,
                                                            String fileName, HttpServletResponse response) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        BufferedOutputStream bos = null;
        try {
            fileName = fileName + ".xlsx";
            String userAgent = request.getHeader("USER-AGENT");
            String finalFileName;
            if (userAgent.contains("Mozilla")) {
                finalFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileName, "UTF-8");
            }

            Long total = exportListData.total(baseSearchParam);
            if (total <= 0){
                String fail = "fail";
                response.getOutputStream().write(fail.getBytes());
                response.getOutputStream().flush();
            }
            response.setContentType("application/vnd.ms-ExcelAsync;charset=utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + finalFileName);
            bos = new BufferedOutputStream(response.getOutputStream());
            ExcelWriter writer = new ExcelWriter(bos, ExcelTypeEnum.XLSX);
            long sheets = total / pageSize + ((total % pageSize) > 0 ? 1 : 0);
            CountDownLatch countDownLatch = new CountDownLatch(new Long(sheets).intValue());
            int cores = Runtime.getRuntime().availableProcessors();
            ThreadPoolExecutor executor = new ThreadPoolExecutor(2,cores,3, TimeUnit.SECONDS,new LinkedBlockingQueue<>(20));
            for (int i=0;i<sheets;i++){
                ExcelAsync.asyncExport(baseSearchParam,exportListData,baseRowModelClass,
                        writer,countDownLatch,executor,i);
            }
            countDownLatch.await();
            writer.finish();
        } catch (Exception e) {
            log.error("导出错误", e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e1) {
                }
            }
        }
    }

}
