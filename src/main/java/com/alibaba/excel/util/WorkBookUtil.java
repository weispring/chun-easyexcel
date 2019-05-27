/*    */ package com.alibaba.excel.util;
/*    */ 
/*    */ import com.alibaba.excel.support.ExcelTypeEnum;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*    */ import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/*    */ import org.apache.poi.ss.usermodel.Cell;
/*    */ import org.apache.poi.ss.usermodel.CellStyle;
/*    */ import org.apache.poi.ss.usermodel.Row;
/*    */ import org.apache.poi.ss.usermodel.Workbook;
/*    */ import org.apache.poi.xssf.streaming.SXSSFWorkbook;
/*    */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*    */ 
/*    */ public class WorkBookUtil
/*    */ {
/*    */   public static Workbook createWorkBook(InputStream templateInputStream, ExcelTypeEnum excelType)
/*    */     throws IOException
/*    */   {
/*    */     Workbook workbook;
/* 23 */     if (ExcelTypeEnum.XLS.equals(excelType)) {
/* 24 */       workbook = templateInputStream == null ? new HSSFWorkbook() : new HSSFWorkbook(new POIFSFileSystem(templateInputStream));
/*    */     }
/*    */     else {
/* 27 */       workbook = templateInputStream == null ? new SXSSFWorkbook(500) : new SXSSFWorkbook(new XSSFWorkbook(templateInputStream));
/*    */     }
/*    */ 
/* 30 */     return workbook;
/*    */   }
/*    */ 
/*    */   public static org.apache.poi.ss.usermodel.Sheet createOrGetSheet(Workbook workbook, com.alibaba.excel.metadata.Sheet sheet) {
/* 34 */     org.apache.poi.ss.usermodel.Sheet sheet1 = null;
/*    */     try {
/*    */       try {
/* 37 */         sheet1 = workbook.getSheetAt(sheet.getSheetNo() - 1);
/*    */       } catch (Exception localException1) {
/*    */       }
/* 40 */       if (null == sheet1) {
/* 41 */         sheet1 = createSheet(workbook, sheet);
/* 42 */         StyleUtil.buildSheetStyle(sheet1, sheet.getColumnWidthMap());
/*    */       }
/*    */     } catch (Exception e) {
/* 45 */       throw new RuntimeException("constructCurrentSheet error", e);
/*    */     }
/* 47 */     return sheet1;
/*    */   }
/*    */ 
/*    */   public static org.apache.poi.ss.usermodel.Sheet createSheet(Workbook workbook, com.alibaba.excel.metadata.Sheet sheet) {
/* 51 */     return workbook.createSheet(sheet.getSheetNo() + "");
/*    */   }
/*    */ 
/*    */   public static Row createRow(org.apache.poi.ss.usermodel.Sheet sheet, int rowNum) {
/* 55 */     return sheet.createRow(rowNum);
/*    */   }
/*    */ 
/*    */   public static Cell createCell(Row row, int colNum, CellStyle cellStyle, String cellValue) {
/* 59 */     return createCell(row, colNum, cellStyle, cellValue, Boolean.valueOf(false));
/*    */   }
/*    */ 
/*    */   public static Cell createCell(Row row, int colNum, CellStyle cellStyle, Object cellValue, Boolean isNum) {
/* 63 */     Cell cell = row.createCell(colNum);
/* 64 */     cell.setCellStyle(cellStyle);
/* 65 */     if (null != cellValue) {
/* 66 */       if (isNum.booleanValue())
/* 67 */         cell.setCellValue(Double.parseDouble(cellValue.toString()));
/*    */       else {
/* 69 */         cell.setCellValue(cellValue.toString());
/*    */       }
/*    */     }
/* 72 */     return cell;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.util.WorkBookUtil
 * JD-Core Version:    0.6.0
 */