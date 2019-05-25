/*     */ package com.alibaba.excel;
/*     */ 
/*     */ import com.alibaba.excel.analysis.ExcelAnalyser;
/*     */ import com.alibaba.excel.analysis.ExcelAnalyserImpl;
/*     */ import com.alibaba.excel.event.AnalysisEventListener;
/*     */ import com.alibaba.excel.metadata.BaseRowModel;
/*     */ import com.alibaba.excel.metadata.Sheet;
/*     */ import com.alibaba.excel.parameter.AnalysisParam;
/*     */ import com.alibaba.excel.support.ExcelTypeEnum;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ExcelReader
/*     */ {
/*     */   private ExcelAnalyser analyser;
/*     */ 
/*     */   @Deprecated
/*     */   public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent, AnalysisEventListener eventListener)
/*     */   {
/*  38 */     this(in, excelTypeEnum, customContent, eventListener, true);
/*     */   }
/*     */ 
/*     */   public ExcelReader(InputStream in, Object customContent, AnalysisEventListener eventListener)
/*     */   {
/*  50 */     this(in, customContent, eventListener, true);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public ExcelReader(AnalysisParam param, AnalysisEventListener eventListener)
/*     */   {
/*  61 */     this(param.getIn(), param.getExcelTypeEnum(), param.getCustomContent(), eventListener, true);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent, AnalysisEventListener eventListener, boolean trim)
/*     */   {
/*  78 */     validateParam(in, eventListener);
/*  79 */     this.analyser = new ExcelAnalyserImpl(in, excelTypeEnum, customContent, eventListener, trim);
/*     */   }
/*     */ 
/*     */   public ExcelReader(InputStream in, Object customContent, AnalysisEventListener eventListener, boolean trim)
/*     */   {
/*  94 */     ExcelTypeEnum excelTypeEnum = ExcelTypeEnum.valueOf(in);
/*  95 */     validateParam(in, eventListener);
/*  96 */     this.analyser = new ExcelAnalyserImpl(in, excelTypeEnum, customContent, eventListener, trim);
/*     */   }
/*     */ 
/*     */   public void read()
/*     */   {
/* 103 */     this.analyser.analysis();
/*     */   }
/*     */ 
/*     */   public void read(Sheet sheet)
/*     */   {
/* 112 */     this.analyser.analysis(sheet);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void read(Sheet sheet, Class<? extends BaseRowModel> clazz)
/*     */   {
/* 123 */     sheet.setClazz(clazz);
/* 124 */     this.analyser.analysis(sheet);
/*     */   }
/*     */ 
/*     */   public List<Sheet> getSheets()
/*     */   {
/* 133 */     return this.analyser.getSheets();
/*     */   }
/*     */ 
/*     */   private void validateParam(InputStream in, AnalysisEventListener eventListener)
/*     */   {
/* 143 */     if (eventListener == null)
/* 144 */       throw new IllegalArgumentException("AnalysisEventListener can not null");
/* 145 */     if (in == null)
/* 146 */       throw new IllegalArgumentException("InputStream can not null");
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.ExcelReader
 * JD-Core Version:    0.6.0
 */