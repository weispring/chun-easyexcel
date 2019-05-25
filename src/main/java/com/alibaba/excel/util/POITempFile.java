/*    */ package com.alibaba.excel.util;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class POITempFile
/*    */ {
/*    */   private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
/*    */   private static final String POIFILES = "poifiles";
/*    */ 
/*    */   public static void createPOIFilesDirectory()
/*    */   {
/* 19 */     String tmpDir = System.getProperty("java.io.tmpdir");
/* 20 */     if (tmpDir == null) {
/* 21 */       throw new RuntimeException("Systems temporary directory not defined - set the -Djava.io.tmpdir jvm property!");
/*    */     }
/*    */ 
/* 24 */     File directory = new File(tmpDir, "poifiles");
/* 25 */     if (!directory.exists())
/* 26 */       syncCreatePOIFilesDirectory(directory);
/*    */   }
/*    */ 
/*    */   private static synchronized void syncCreatePOIFilesDirectory(File directory)
/*    */   {
/* 36 */     if (!directory.exists())
/* 37 */       directory.mkdirs();
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.util.POITempFile
 * JD-Core Version:    0.6.0
 */