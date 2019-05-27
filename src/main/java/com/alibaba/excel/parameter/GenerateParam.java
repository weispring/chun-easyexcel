/*    */ package com.alibaba.excel.parameter;
/*    */ 
/*    */ import com.alibaba.excel.support.ExcelTypeEnum;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class GenerateParam
/*    */ {
/*    */   private OutputStream outputStream;
/*    */   private String sheetName;
/*    */   private Class clazz;
/*    */   private ExcelTypeEnum type;
/*    */ 
/*    */   public GenerateParam(String sheetName, Class clazz, OutputStream outputStream)
/*    */   {
/* 21 */     this.outputStream = outputStream;
/* 22 */     this.sheetName = sheetName;
/* 23 */     this.clazz = clazz;
/*    */   }
/*    */ 
/*    */   public OutputStream getOutputStream() {
/* 27 */     return this.outputStream;
/*    */   }
/*    */ 
/*    */   public void setOutputStream(OutputStream outputStream) {
/* 31 */     this.outputStream = outputStream;
/*    */   }
/*    */ 
/*    */   public String getSheetName()
/*    */   {
/* 36 */     return this.sheetName;
/*    */   }
/*    */ 
/*    */   public void setSheetName(String sheetName) {
/* 40 */     this.sheetName = sheetName;
/*    */   }
/*    */ 
/*    */   public Class getClazz() {
/* 44 */     return this.clazz;
/*    */   }
/*    */ 
/*    */   public void setClazz(Class clazz) {
/* 48 */     this.clazz = clazz;
/*    */   }
/*    */ 
/*    */   public ExcelTypeEnum getType() {
/* 52 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setType(ExcelTypeEnum type) {
/* 56 */     this.type = type;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.parameter.GenerateParam
 * JD-Core Version:    0.6.0
 */