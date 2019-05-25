/*     */ package com.alibaba.excel;
/*     */ 
/*     */ import com.alibaba.excel.context.AnalysisContext;
/*     */ import com.alibaba.excel.event.AnalysisEventListener;
/*     */ import com.alibaba.excel.event.WriteHandler;
/*     */ import com.alibaba.excel.metadata.Sheet;
/*     */ import com.alibaba.excel.support.ExcelTypeEnum;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class EasyExcelFactory
/*     */ {
/*     */   public static List<Object> read(InputStream in, Sheet sheet)
/*     */   {
/*  29 */     List rows = new ArrayList();
/*  30 */     new ExcelReader(in, null, new AnalysisEventListener(rows)
/*     */     {
/*     */       public void invoke(Object object, AnalysisContext context) {
/*  33 */         this.val$rows.add(object);
/*     */       }
/*     */ 
/*     */       public void doAfterAllAnalysed(AnalysisContext context)
/*     */       {
/*     */       }
/*     */     }
/*     */     , false)
/*  39 */       .read(sheet);
/*     */ 
/*  40 */     return rows;
/*     */   }
/*     */ 
/*     */   public static void readBySax(InputStream in, Sheet sheet, AnalysisEventListener listener)
/*     */   {
/*  51 */     new ExcelReader(in, null, listener).read(sheet);
/*     */   }
/*     */ 
/*     */   public static ExcelReader getReader(InputStream in, AnalysisEventListener listener)
/*     */   {
/*  62 */     return new ExcelReader(in, null, listener);
/*     */   }
/*     */ 
/*     */   public static ExcelWriter getWriter(OutputStream outputStream)
/*     */   {
/*  72 */     return new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
/*     */   }
/*     */ 
/*     */   public static ExcelWriter getWriter(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead)
/*     */   {
/*  84 */     return new ExcelWriter(outputStream, typeEnum, needHead);
/*     */   }
/*     */ 
/*     */   public static ExcelWriter getWriterWithTemp(InputStream temp, OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead)
/*     */   {
/*  98 */     return new ExcelWriter(temp, outputStream, typeEnum, Boolean.valueOf(needHead));
/*     */   }
/*     */ 
/*     */   public static ExcelWriter getWriterWithTempAndHandler(InputStream temp, OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead, WriteHandler handler)
/*     */   {
/* 117 */     return new ExcelWriter(temp, outputStream, typeEnum, Boolean.valueOf(needHead), handler);
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.EasyExcelFactory
 * JD-Core Version:    0.6.0
 */