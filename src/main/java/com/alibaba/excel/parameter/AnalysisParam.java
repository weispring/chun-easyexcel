/*    */ package com.alibaba.excel.parameter;
/*    */ 
/*    */ import com.alibaba.excel.support.ExcelTypeEnum;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ @Deprecated
/*    */ public class AnalysisParam
/*    */ {
/*    */   private ExcelTypeEnum excelTypeEnum;
/*    */   private InputStream in;
/*    */   private Object customContent;
/*    */ 
/*    */   public AnalysisParam(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent)
/*    */   {
/* 30 */     this.in = in;
/* 31 */     this.excelTypeEnum = excelTypeEnum;
/* 32 */     this.customContent = customContent;
/*    */   }
/*    */ 
/*    */   public ExcelTypeEnum getExcelTypeEnum() {
/* 36 */     return this.excelTypeEnum;
/*    */   }
/*    */ 
/*    */   public void setExcelTypeEnum(ExcelTypeEnum excelTypeEnum) {
/* 40 */     this.excelTypeEnum = excelTypeEnum;
/*    */   }
/*    */ 
/*    */   public Object getCustomContent() {
/* 44 */     return this.customContent;
/*    */   }
/*    */ 
/*    */   public void setCustomContent(Object customContent) {
/* 48 */     this.customContent = customContent;
/*    */   }
/*    */ 
/*    */   public InputStream getIn() {
/* 52 */     return this.in;
/*    */   }
/*    */ 
/*    */   public void setIn(InputStream in) {
/* 56 */     this.in = in;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.parameter.AnalysisParam
 * JD-Core Version:    0.6.0
 */