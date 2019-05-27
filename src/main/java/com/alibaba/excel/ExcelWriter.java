/*     */ package com.alibaba.excel;
/*     */ 
/*     */ import com.alibaba.excel.event.WriteHandler;
/*     */ import com.alibaba.excel.metadata.BaseRowModel;
/*     */ import com.alibaba.excel.metadata.Sheet;
/*     */ import com.alibaba.excel.metadata.Table;
/*     */ import com.alibaba.excel.parameter.GenerateParam;
/*     */ import com.alibaba.excel.support.ExcelTypeEnum;
/*     */ import com.alibaba.excel.write.ExcelBuilder;
/*     */ import com.alibaba.excel.write.ExcelBuilderImpl;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ExcelWriter
/*     */ {
/*     */   private ExcelBuilder excelBuilder;
/*     */ 
/*     */   @Deprecated
/*     */   private Class<? extends BaseRowModel> objectClass;
/*     */ 
/*     */   public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum)
/*     */   {
/*  35 */     this(outputStream, typeEnum, true);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public ExcelWriter(GenerateParam generateParam)
/*     */   {
/*  46 */     this(generateParam.getOutputStream(), generateParam.getType(), true);
/*  47 */     this.objectClass = generateParam.getClazz();
/*     */   }
/*     */ 
/*     */   public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead)
/*     */   {
/*  58 */     this.excelBuilder = new ExcelBuilderImpl(null, outputStream, typeEnum, needHead, null);
/*     */   }
/*     */ 
/*     */   public ExcelWriter(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum typeEnum, Boolean needHead)
/*     */   {
/*  68 */     this.excelBuilder = new ExcelBuilderImpl(templateInputStream, outputStream, typeEnum, needHead.booleanValue(), null);
/*     */   }
/*     */ 
/*     */   public ExcelWriter(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum typeEnum, Boolean needHead, WriteHandler writeHandler)
/*     */   {
/*  81 */     this.excelBuilder = new ExcelBuilderImpl(templateInputStream, outputStream, typeEnum, needHead.booleanValue(), writeHandler);
/*     */   }
/*     */ 
/*     */   public ExcelWriter write(List<? extends BaseRowModel> data, Sheet sheet)
/*     */   {
/*  91 */     this.excelBuilder.addContent(data, sheet);
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public ExcelWriter write(List data)
/*     */   {
/* 103 */     if (this.objectClass != null) {
/* 104 */       return write(data, new Sheet(1, 0, this.objectClass));
/*     */     }
/* 106 */     return write0(data, new Sheet(1, 0, this.objectClass));
/*     */   }
/*     */ 
/*     */   public ExcelWriter write1(List<List<Object>> data, Sheet sheet)
/*     */   {
/* 119 */     this.excelBuilder.addContent(data, sheet);
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   public ExcelWriter write0(List<List<String>> data, Sheet sheet)
/*     */   {
/* 130 */     this.excelBuilder.addContent(data, sheet);
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   public ExcelWriter write(List<? extends BaseRowModel> data, Sheet sheet, Table table)
/*     */   {
/* 142 */     this.excelBuilder.addContent(data, sheet, table);
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */   public ExcelWriter write0(List<List<String>> data, Sheet sheet, Table table)
/*     */   {
/* 154 */     this.excelBuilder.addContent(data, sheet, table);
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */   public ExcelWriter merge(int firstRow, int lastRow, int firstCol, int lastCol)
/*     */   {
/* 167 */     this.excelBuilder.merge(firstRow, lastRow, firstCol, lastCol);
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */   public ExcelWriter write1(List<List<Object>> data, Sheet sheet, Table table)
/*     */   {
/* 179 */     this.excelBuilder.addContent(data, sheet, table);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public void finish()
/*     */   {
/* 187 */     this.excelBuilder.finish();
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.ExcelWriter
 * JD-Core Version:    0.6.0
 */