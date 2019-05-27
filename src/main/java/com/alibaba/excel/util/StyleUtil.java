/*    */ package com.alibaba.excel.util;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import org.apache.poi.ss.usermodel.BorderStyle;
/*    */ import org.apache.poi.ss.usermodel.CellStyle;
/*    */ import org.apache.poi.ss.usermodel.FillPatternType;
/*    */ import org.apache.poi.ss.usermodel.HorizontalAlignment;
/*    */ import org.apache.poi.ss.usermodel.IndexedColors;
/*    */ import org.apache.poi.ss.usermodel.Sheet;
/*    */ import org.apache.poi.ss.usermodel.VerticalAlignment;
/*    */ import org.apache.poi.ss.usermodel.Workbook;
/*    */ 
/*    */ public class StyleUtil
/*    */ {
/*    */   public static CellStyle buildDefaultCellStyle(Workbook workbook)
/*    */   {
/* 18 */     CellStyle newCellStyle = workbook.createCellStyle();
/* 19 */     org.apache.poi.ss.usermodel.Font font = workbook.createFont();
/* 20 */     font.setFontName("宋体");
/* 21 */     font.setFontHeightInPoints((short) 14);
/* 22 */     font.setBold(true);
/* 23 */     newCellStyle.setFont(font);
/* 24 */     newCellStyle.setWrapText(true);
/* 25 */     newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
/* 26 */     newCellStyle.setAlignment(HorizontalAlignment.CENTER);
/* 27 */     newCellStyle.setLocked(true);
/* 28 */     newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
/* 29 */     newCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
/* 30 */     newCellStyle.setBorderBottom(BorderStyle.THIN);
/* 31 */     newCellStyle.setBorderLeft(BorderStyle.THIN);
/* 32 */     return newCellStyle;
/*    */   }
/*    */ 
/*    */   public static CellStyle buildCellStyle(Workbook workbook, com.alibaba.excel.metadata.Font f, IndexedColors indexedColors)
/*    */   {
/* 44 */     CellStyle cellStyle = buildDefaultCellStyle(workbook);
/* 45 */     if (f != null) {
/* 46 */       org.apache.poi.ss.usermodel.Font font = workbook.createFont();
/* 47 */       font.setFontName(f.getFontName());
/* 48 */       font.setFontHeightInPoints(f.getFontHeightInPoints());
/* 49 */       font.setBold(f.isBold());
/* 50 */       cellStyle.setFont(font);
/*    */     }
/* 52 */     if (indexedColors != null) {
/* 53 */       cellStyle.setFillForegroundColor(indexedColors.getIndex());
/*    */     }
/* 55 */     return cellStyle;
/*    */   }
/*    */ 
/*    */   public static Sheet buildSheetStyle(Sheet currentSheet, Map<Integer, Integer> sheetWidthMap) {
/* 59 */     currentSheet.setDefaultColumnWidth(20);
/* 60 */     for (Map.Entry entry : sheetWidthMap.entrySet()) {
/* 61 */       currentSheet.setColumnWidth(((Integer)entry.getKey()).intValue(), ((Integer)entry.getValue()).intValue());
/*    */     }
/* 63 */     return currentSheet;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.util.StyleUtil
 * JD-Core Version:    0.6.0
 */