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
/*     */   public ExcelReader(InputStream in, Object customContent, AnalysisEventListener eventListener, boolean trim, Boolean isLowerVison, String tmpPath)
/*     */   {
/*  43 */     ExcelTypeEnum excelTypeEnum = ExcelTypeEnum.valueOf(in);
/*  44 */     validateParam(in, eventListener);
/*  45 */     this.analyser = new ExcelAnalyserImpl(in, excelTypeEnum, customContent, eventListener, trim, isLowerVison, tmpPath);
/*     */   }
/*     */ 
/*     */   public ExcelReader(InputStream in, Object customContent, AnalysisEventListener eventListener)
/*     */   {
/*  57 */     this(in, customContent, eventListener, true);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public ExcelReader(AnalysisParam param, AnalysisEventListener eventListener)
/*     */   {
/*  68 */     this(param.getIn(), param.getExcelTypeEnum(), param.getCustomContent(), eventListener, true);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent, AnalysisEventListener eventListener, boolean trim)
/*     */   {
/*  86 */     validateParam(in, eventListener);
/*  87 */     this.analyser = new ExcelAnalyserImpl(in, excelTypeEnum, customContent, eventListener, trim);
/*     */   }
/*     */ 
/*     */   public ExcelReader(InputStream in, Object customContent, AnalysisEventListener eventListener, boolean trim)
/*     */   {
/* 102 */     ExcelTypeEnum excelTypeEnum = ExcelTypeEnum.valueOf(in);
/* 103 */     validateParam(in, eventListener);
/* 104 */     this.analyser = new ExcelAnalyserImpl(in, excelTypeEnum, customContent, eventListener, trim);
/*     */   }
/*     */ 
/*     */   public void read()
/*     */   {
/* 111 */     this.analyser.analysis();
/*     */   }
/*     */ 
/*     */   public void read(Sheet sheet)
/*     */   {
/* 120 */     this.analyser.analysis(sheet);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void read(Sheet sheet, Class<? extends BaseRowModel> clazz)
/*     */   {
/* 131 */     sheet.setClazz(clazz);
/* 132 */     this.analyser.analysis(sheet);
/*     */   }
/*     */ 
/*     */   public List<Sheet> getSheets()
/*     */   {
/* 141 */     return this.analyser.getSheets();
/*     */   }
/*     */ 
/*     */   private void validateParam(InputStream in, AnalysisEventListener eventListener)
/*     */   {
/* 151 */     if (eventListener == null)
/* 152 */       throw new IllegalArgumentException("AnalysisEventListener can not null");
/* 153 */     if (in == null)
/* 154 */       throw new IllegalArgumentException("InputStream can not null");
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.ExcelReader
 * JD-Core Version:    0.6.0
 */