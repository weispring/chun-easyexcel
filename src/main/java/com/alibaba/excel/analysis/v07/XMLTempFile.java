/*    */ package com.alibaba.excel.analysis.v07;
/*    */ 
/*    */ import com.alibaba.excel.util.EasyExcelTempFile;
/*    */ import java.io.File;
/*    */ import java.security.SecureRandom;
/*    */ 
/*    */ public class XMLTempFile
/*    */ {
/*    */   private static final String TMP_FILE_NAME = "tmp.xlsx";
/*    */   private static final String XL = "xl";
/*    */   private static final String XML_WORKBOOK = "workbook.xml";
/*    */   private static final String XML_SHARED_STRING = "sharedStrings.xml";
/*    */   private static final String SHEET = "sheet";
/*    */   private static final String WORK_SHEETS = "worksheets";
/* 15 */   private static final SecureRandom random = new SecureRandom();
/*    */ 
/*    */   public static String getTmpFilePath(String path) {
/* 18 */     return path + File.separator + "tmp.xlsx";
/*    */   }
/*    */ 
/*    */   public static String createPath() {
/* 22 */     return EasyExcelTempFile.getEasyExcelTmpDir() + File.separator + random.nextLong();
/*    */   }
/*    */ 
/*    */   public static String getWorkBookFilePath(String path) {
/* 26 */     return path + File.separator + "xl" + File.separator + "workbook.xml";
/*    */   }
/*    */ 
/*    */   public static String getSharedStringFilePath(String path) {
/* 30 */     return path + File.separator + "xl" + File.separator + "sharedStrings.xml";
/*    */   }
/*    */ 
/*    */   public static String getSheetFilePath(String path, int id) {
/* 34 */     return path + File.separator + "xl" + File.separator + "worksheets" + File.separator + "sheet" + id + ".xml";
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.analysis.v07.XMLTempFile
 * JD-Core Version:    0.6.0
 */