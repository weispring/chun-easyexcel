/*     */ package com.alibaba.excel.context;
/*     */ 
/*     */ import com.alibaba.excel.event.AnalysisEventListener;
/*     */ import com.alibaba.excel.exception.ExcelAnalysisException;
/*     */ import com.alibaba.excel.metadata.BaseRowModel;
/*     */ import com.alibaba.excel.metadata.ExcelHeadProperty;
/*     */ import com.alibaba.excel.metadata.Sheet;
/*     */ import com.alibaba.excel.support.ExcelTypeEnum;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AnalysisContextImpl
/*     */   implements AnalysisContext
/*     */ {
/*     */   private Object custom;
/*     */   private Sheet currentSheet;
/*     */   private ExcelTypeEnum excelType;
/*     */   private InputStream inputStream;
/*     */   private AnalysisEventListener eventListener;
/*     */   private Integer currentRowNum;
/*     */   private Integer totalCount;
/*     */   private ExcelHeadProperty excelHeadProperty;
/*     */   private boolean trim;
/*  38 */   private boolean use1904WindowDate = false;
/*     */   private Object currentRowAnalysisResult;
/*     */ 
/*     */   public void setUse1904WindowDate(boolean use1904WindowDate)
/*     */   {
/*  42 */     this.use1904WindowDate = use1904WindowDate;
/*     */   }
/*     */ 
/*     */   public Object getCurrentRowAnalysisResult()
/*     */   {
/*  47 */     return this.currentRowAnalysisResult;
/*     */   }
/*     */ 
/*     */   public void interrupt()
/*     */   {
/*  52 */     throw new ExcelAnalysisException("interrupt error");
/*     */   }
/*     */ 
/*     */   public boolean use1904WindowDate()
/*     */   {
/*  57 */     return this.use1904WindowDate;
/*     */   }
/*     */ 
/*     */   public void setCurrentRowAnalysisResult(Object currentRowAnalysisResult)
/*     */   {
/*  62 */     this.currentRowAnalysisResult = currentRowAnalysisResult;
/*     */   }
/*     */ 
/*     */   public AnalysisContextImpl(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom, AnalysisEventListener listener, boolean trim)
/*     */   {
/*  69 */     this.custom = custom;
/*  70 */     this.eventListener = listener;
/*  71 */     this.inputStream = inputStream;
/*  72 */     this.excelType = excelTypeEnum;
/*  73 */     this.trim = trim;
/*     */   }
/*     */ 
/*     */   public void setCurrentSheet(Sheet currentSheet)
/*     */   {
/*  78 */     cleanCurrentSheet();
/*  79 */     this.currentSheet = currentSheet;
/*  80 */     if (currentSheet.getClazz() != null)
/*  81 */       buildExcelHeadProperty(currentSheet.getClazz(), null);
/*     */   }
/*     */ 
/*     */   private void cleanCurrentSheet()
/*     */   {
/*  86 */     this.currentSheet = null;
/*  87 */     this.excelHeadProperty = null;
/*  88 */     this.totalCount = Integer.valueOf(0);
/*  89 */     this.currentRowAnalysisResult = null;
/*  90 */     this.currentRowNum = Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */   public ExcelTypeEnum getExcelType()
/*     */   {
/*  95 */     return this.excelType;
/*     */   }
/*     */ 
/*     */   public void setExcelType(ExcelTypeEnum excelType) {
/*  99 */     this.excelType = excelType;
/*     */   }
/*     */ 
/*     */   public Object getCustom() {
/* 103 */     return this.custom;
/*     */   }
/*     */ 
/*     */   public void setCustom(Object custom) {
/* 107 */     this.custom = custom;
/*     */   }
/*     */ 
/*     */   public Sheet getCurrentSheet()
/*     */   {
/* 112 */     return this.currentSheet;
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream()
/*     */   {
/* 117 */     return this.inputStream;
/*     */   }
/*     */ 
/*     */   public void setInputStream(InputStream inputStream) {
/* 121 */     this.inputStream = inputStream;
/*     */   }
/*     */ 
/*     */   public AnalysisEventListener getEventListener()
/*     */   {
/* 126 */     return this.eventListener;
/*     */   }
/*     */ 
/*     */   public void setEventListener(AnalysisEventListener eventListener) {
/* 130 */     this.eventListener = eventListener;
/*     */   }
/*     */ 
/*     */   public Integer getCurrentRowNum()
/*     */   {
/* 135 */     return this.currentRowNum;
/*     */   }
/*     */ 
/*     */   public void setCurrentRowNum(Integer row)
/*     */   {
/* 140 */     this.currentRowNum = row;
/*     */   }
/*     */ 
/*     */   public Integer getTotalCount()
/*     */   {
/* 145 */     return this.totalCount;
/*     */   }
/*     */ 
/*     */   public void setTotalCount(Integer totalCount)
/*     */   {
/* 150 */     this.totalCount = totalCount;
/*     */   }
/*     */ 
/*     */   public ExcelHeadProperty getExcelHeadProperty()
/*     */   {
/* 155 */     return this.excelHeadProperty;
/*     */   }
/*     */ 
/*     */   public void buildExcelHeadProperty(Class<? extends BaseRowModel> clazz, List<String> headOneRow)
/*     */   {
/* 160 */     if ((this.excelHeadProperty == null) && ((clazz != null) || (headOneRow != null))) {
/* 161 */       this.excelHeadProperty = new ExcelHeadProperty(clazz, new ArrayList());
/*     */     }
/* 163 */     if ((this.excelHeadProperty.getHead() == null) && (headOneRow != null))
/* 164 */       this.excelHeadProperty.appendOneRow(headOneRow);
/*     */   }
/*     */ 
/*     */   public boolean trim()
/*     */   {
/* 170 */     return this.trim;
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.context.AnalysisContextImpl
 * JD-Core Version:    0.6.0
 */