/*    */ package com.alibaba.excel.util;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class EasyExcelTempFile
/*    */ {
/*    */   private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
/*    */   private static final String POIFILES = "poifiles";
/*    */   private static final String EASY_EXCEL_FILES = "easyexcel";
/*    */ 
/*    */   public static void createPOIFilesDirectory()
/*    */   {
/* 11 */     String tmpDir = System.getProperty("java.io.tmpdir");
/* 12 */     if (tmpDir == null) {
/* 13 */       throw new RuntimeException("Systems temporary directory not defined - set the -Djava.io.tmpdir jvm property!");
/*    */     }
/*    */ 
/* 17 */     File directory = new File(tmpDir, "poifiles");
/* 18 */     if (!directory.exists())
/* 19 */       syncCreatePOIFilesDirectory(directory);
/*    */   }
/*    */ 
/*    */   public static String getEasyExcelTmpDir() {
/* 23 */     String tmpDir = System.getProperty("java.io.tmpdir");
/* 24 */     if (tmpDir == null) {
/* 25 */       throw new RuntimeException("Systems temporary directory not defined - set the -Djava.io.tmpdir jvm property!");
/*    */     }
/*    */ 
/* 29 */     File directory = new File(tmpDir, "easyexcel");
/* 30 */     if (!directory.exists()) {
/* 31 */       syncCreatePOIFilesDirectory(directory);
/*    */     }
/* 33 */     return tmpDir + File.separator + "easyexcel";
/*    */   }
/*    */ 
/*    */   private static synchronized void syncCreatePOIFilesDirectory(File directory) {
/* 37 */     if (!directory.exists())
/* 38 */       directory.mkdirs();
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.util.EasyExcelTempFile
 * JD-Core Version:    0.6.0
 */