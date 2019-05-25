/*     */ package com.alibaba.excel.write;
/*     */ 
/*     */ import com.alibaba.excel.context.WriteContext;
/*     */ import com.alibaba.excel.event.WriteHandler;
/*     */ import com.alibaba.excel.exception.ExcelGenerateException;
/*     */ import com.alibaba.excel.metadata.BaseRowModel;
/*     */ import com.alibaba.excel.metadata.ExcelColumnProperty;
/*     */ import com.alibaba.excel.metadata.ExcelHeadProperty;
/*     */ import com.alibaba.excel.metadata.Table;
/*     */ import com.alibaba.excel.support.ExcelTypeEnum;
/*     */ import com.alibaba.excel.util.CollectionUtils;
/*     */ import com.alibaba.excel.util.POITempFile;
/*     */ import com.alibaba.excel.util.TypeUtil;
/*     */ import com.alibaba.excel.util.WorkBookUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.List;
/*     */ import net.sf.cglib.beans.BeanMap;
/*     */ import org.apache.poi.ss.usermodel.Cell;
/*     */ import org.apache.poi.ss.usermodel.CellStyle;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Workbook;
/*     */ import org.apache.poi.ss.util.CellRangeAddress;
/*     */ 
/*     */ public class ExcelBuilderImpl
/*     */   implements ExcelBuilder
/*     */ {
/*     */   private WriteContext context;
/*     */ 
/*     */   public ExcelBuilderImpl(InputStream templateInputStream, OutputStream out, ExcelTypeEnum excelType, boolean needHead, WriteHandler writeHandler)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       POITempFile.createPOIFilesDirectory();
/*  40 */       this.context = new WriteContext(templateInputStream, out, excelType, needHead, writeHandler);
/*     */     } catch (Exception e) {
/*  42 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addContent(List data, int startRow)
/*     */   {
/*  48 */     if (CollectionUtils.isEmpty(data)) {
/*  49 */       return;
/*     */     }
/*  51 */     int rowNum = this.context.getCurrentSheet().getLastRowNum();
/*  52 */     if (rowNum == 0) {
/*  53 */       Row row = this.context.getCurrentSheet().getRow(0);
/*  54 */       if ((row == null) && (
/*  55 */         (this.context.getExcelHeadProperty() == null) || (!this.context.needHead()))) {
/*  56 */         rowNum = -1;
/*     */       }
/*     */     }
/*     */ 
/*  60 */     if (rowNum < startRow) {
/*  61 */       rowNum = startRow;
/*     */     }
/*  63 */     for (int i = 0; i < data.size(); i++) {
/*  64 */       int n = i + rowNum + 1;
/*  65 */       addOneRowOfDataToExcel(data.get(i), n);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addContent(List data, com.alibaba.excel.metadata.Sheet sheetParam)
/*     */   {
/*  71 */     this.context.currentSheet(sheetParam);
/*  72 */     addContent(data, sheetParam.getStartRow());
/*     */   }
/*     */ 
/*     */   public void addContent(List data, com.alibaba.excel.metadata.Sheet sheetParam, Table table)
/*     */   {
/*  77 */     this.context.currentSheet(sheetParam);
/*  78 */     this.context.currentTable(table);
/*  79 */     addContent(data, sheetParam.getStartRow());
/*     */   }
/*     */ 
/*     */   public void merge(int firstRow, int lastRow, int firstCol, int lastCol)
/*     */   {
/*  84 */     CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
/*  85 */     this.context.getCurrentSheet().addMergedRegion(cra);
/*     */   }
/*     */ 
/*     */   public void finish()
/*     */   {
/*     */     try {
/*  91 */       this.context.getWorkbook().write(this.context.getOutputStream());
/*  92 */       this.context.getWorkbook().close();
/*     */     } catch (IOException e) {
/*  94 */       throw new ExcelGenerateException("IO error", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addBasicTypeToExcel(List<Object> oneRowData, Row row) {
/*  99 */     if (CollectionUtils.isEmpty(oneRowData)) {
/* 100 */       return;
/*     */     }
/* 102 */     for (int i = 0; i < oneRowData.size(); i++) {
/* 103 */       Object cellValue = oneRowData.get(i);
/* 104 */       Cell cell = WorkBookUtil.createCell(row, i, this.context.getCurrentContentStyle(), cellValue, 
/* 105 */         TypeUtil.isNum(cellValue));
/*     */ 
/* 106 */       if (null != this.context.getAfterWriteHandler())
/* 107 */         this.context.getAfterWriteHandler().cell(i, cell);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addJavaObjectToExcel(Object oneRowData, Row row)
/*     */   {
/* 113 */     int i = 0;
/* 114 */     BeanMap beanMap = BeanMap.create(oneRowData);
/* 115 */     for (ExcelColumnProperty excelHeadProperty : this.context.getExcelHeadProperty().getColumnPropertyList()) {
/* 116 */       BaseRowModel baseRowModel = (BaseRowModel)oneRowData;
/* 117 */       String cellValue = TypeUtil.getFieldStringValue(beanMap, excelHeadProperty.getField().getName(), excelHeadProperty
/* 118 */         .getFormat());
/*     */ 
/* 120 */       CellStyle cellStyle = baseRowModel.getStyle(Integer.valueOf(i)) != null ? baseRowModel.getStyle(Integer.valueOf(i)) : this.context
/* 120 */         .getCurrentContentStyle();
/* 121 */       Cell cell = WorkBookUtil.createCell(row, i, cellStyle, cellValue, 
/* 122 */         TypeUtil.isNum(excelHeadProperty
/* 122 */         .getField()));
/* 123 */       if (null != this.context.getAfterWriteHandler()) {
/* 124 */         this.context.getAfterWriteHandler().cell(i, cell);
/*     */       }
/* 126 */       i++;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addOneRowOfDataToExcel(Object oneRowData, int n)
/*     */   {
/* 132 */     Row row = WorkBookUtil.createRow(this.context.getCurrentSheet(), n);
/* 133 */     if (null != this.context.getAfterWriteHandler()) {
/* 134 */       this.context.getAfterWriteHandler().row(n, row);
/*     */     }
/* 136 */     if ((oneRowData instanceof List))
/* 137 */       addBasicTypeToExcel((List)oneRowData, row);
/*     */     else
/* 139 */       addJavaObjectToExcel(oneRowData, row);
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.write.ExcelBuilderImpl
 * JD-Core Version:    0.6.0
 */