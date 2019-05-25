/*    */ package com.alibaba.excel.parameter;
/*    */ 
/*    */ import com.alibaba.excel.support.ExcelTypeEnum;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ @Deprecated
/*    */ public class ExcelWriteParam
/*    */ {
/*    */   private OutputStream outputStream;
/*    */   private ExcelTypeEnum type;
/*    */ 
/*    */   public ExcelWriteParam(OutputStream outputStream, ExcelTypeEnum type)
/*    */   {
/* 24 */     this.outputStream = outputStream;
/* 25 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public OutputStream getOutputStream()
/*    */   {
/* 30 */     return this.outputStream;
/*    */   }
/*    */ 
/*    */   public void setOutputStream(OutputStream outputStream) {
/* 34 */     this.outputStream = outputStream;
/*    */   }
/*    */ 
/*    */   public ExcelTypeEnum getType() {
/* 38 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setType(ExcelTypeEnum type) {
/* 42 */     this.type = type;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.parameter.ExcelWriteParam
 * JD-Core Version:    0.6.0
 */