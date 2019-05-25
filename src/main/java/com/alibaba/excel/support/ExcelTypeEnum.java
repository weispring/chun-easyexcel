/*    */ package com.alibaba.excel.support;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.poi.poifs.filesystem.FileMagic;
/*    */ 
/*    */ public enum ExcelTypeEnum
/*    */ {
/* 13 */   XLS(".xls"), XLSX(".xlsx");
/*    */ 
/*    */   private String value;
/*    */ 
/* 18 */   private ExcelTypeEnum(String value) { setValue(value); }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 22 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value) {
/* 26 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ExcelTypeEnum valueOf(InputStream inputStream) {
/*    */     try {
/* 31 */       if (!inputStream.markSupported()) {
/* 32 */         return null;
/*    */       }
/* 34 */       FileMagic fileMagic = FileMagic.valueOf(inputStream);
/* 35 */       if (FileMagic.OLE2.equals(fileMagic)) {
/* 36 */         return XLS;
/*    */       }
/* 38 */       if (FileMagic.OOXML.equals(fileMagic)) {
/* 39 */         return XLSX;
/*    */       }
/* 41 */       return null; } catch (IOException e) {
/*    */     }
/* 43 */     throw new RuntimeException(e);
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.support.ExcelTypeEnum
 * JD-Core Version:    0.6.0
 */