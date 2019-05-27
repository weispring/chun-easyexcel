/*     */ package com.alibaba.excel.context;
/*     */ 
/*     */ import com.alibaba.excel.event.WriteHandler;
/*     */ import com.alibaba.excel.metadata.BaseRowModel;
/*     */ import com.alibaba.excel.metadata.CellRange;
/*     */ import com.alibaba.excel.metadata.ExcelHeadProperty;
/*     */ import com.alibaba.excel.metadata.Table;
/*     */ import com.alibaba.excel.metadata.TableStyle;
/*     */ import com.alibaba.excel.support.ExcelTypeEnum;
/*     */ import com.alibaba.excel.util.CollectionUtils;
/*     */ import com.alibaba.excel.util.StyleUtil;
/*     */ import com.alibaba.excel.util.WorkBookUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.poi.ss.usermodel.Cell;
/*     */ import org.apache.poi.ss.usermodel.CellStyle;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Workbook;
/*     */ import org.apache.poi.ss.util.CellRangeAddress;
/*     */ 
/*     */ public class WriteContext
/*     */ {
/*     */   private org.apache.poi.ss.usermodel.Sheet currentSheet;
/*     */   private com.alibaba.excel.metadata.Sheet currentSheetParam;
/*     */   private String currentSheetName;
/*     */   private Table currentTable;
/*     */   private ExcelTypeEnum excelType;
/*     */   private Workbook workbook;
/*     */   private OutputStream outputStream;
/*  68 */   private Map<Integer, Table> tableMap = new ConcurrentHashMap();
/*     */   private CellStyle defaultCellStyle;
/*     */   private CellStyle currentHeadCellStyle;
/*     */   private CellStyle currentContentCellStyle;
/*     */   private ExcelHeadProperty excelHeadProperty;
/*  90 */   private boolean needHead = Boolean.TRUE.booleanValue();
/*     */   private WriteHandler afterWriteHandler;
/*     */ 
/*     */   public WriteHandler getAfterWriteHandler()
/*     */   {
/*  95 */     return this.afterWriteHandler;
/*     */   }
/*     */ 
/*     */   public WriteContext(InputStream templateInputStream, OutputStream out, ExcelTypeEnum excelType, boolean needHead, WriteHandler afterWriteHandler) throws IOException
/*     */   {
/* 100 */     this.needHead = needHead;
/* 101 */     this.outputStream = out;
/* 102 */     this.afterWriteHandler = afterWriteHandler;
/* 103 */     this.workbook = WorkBookUtil.createWorkBook(templateInputStream, excelType);
/* 104 */     this.defaultCellStyle = StyleUtil.buildDefaultCellStyle(this.workbook);
/*     */   }
/*     */ 
/*     */   public void currentSheet(com.alibaba.excel.metadata.Sheet sheet)
/*     */   {
/* 112 */     if ((null == this.currentSheetParam) || (this.currentSheetParam.getSheetNo() != sheet.getSheetNo())) {
/* 113 */       cleanCurrentSheet();
/* 114 */       this.currentSheetParam = sheet;
/*     */

        try {
        this.currentSheet = this.workbook.getSheetAt(sheet.getSheetNo() - 1);
        } catch (Exception e) {
            /* 118 */         this.currentSheet = WorkBookUtil.createSheet(this.workbook, sheet);
            /* 119 */         if (null != this.afterWriteHandler) {
                /* 120 */           this.afterWriteHandler.sheet(sheet.getSheetNo(), this.currentSheet);
                /*     */         }
            /*     */       }

/* 116 */
/*     */
/* 123 */       StyleUtil.buildSheetStyle(this.currentSheet, sheet.getColumnWidthMap());
/*     */ 
/* 125 */       initCurrentSheet(sheet);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initCurrentSheet(com.alibaba.excel.metadata.Sheet sheet)
/*     */   {
/* 133 */     initExcelHeadProperty(sheet.getHead(), sheet.getClazz());
/*     */ 
/* 135 */     initTableStyle(sheet.getTableStyle());
/*     */ 
/* 137 */     initTableHead();
/*     */   }
/*     */ 
/*     */   private void cleanCurrentSheet()
/*     */   {
/* 142 */     this.currentSheet = null;
/* 143 */     this.currentSheetParam = null;
/* 144 */     this.excelHeadProperty = null;
/* 145 */     this.currentHeadCellStyle = null;
/* 146 */     this.currentContentCellStyle = null;
/* 147 */     this.currentTable = null;
/*     */   }
/*     */ 
/*     */   private void initExcelHeadProperty(List<List<String>> head, Class<? extends BaseRowModel> clazz)
/*     */   {
/* 158 */     if ((head != null) || (clazz != null)) this.excelHeadProperty = new ExcelHeadProperty(clazz, head); 
/*     */   }
/*     */ 
/*     */   public void initTableHead()
/*     */   {
/* 162 */     if ((this.needHead) && (null != this.excelHeadProperty) && (!CollectionUtils.isEmpty(this.excelHeadProperty.getHead()))) {
/* 163 */       int startRow = this.currentSheet.getLastRowNum();
/* 164 */       if (startRow > 0)
/* 165 */         startRow += 4;
/*     */       else {
/* 167 */         startRow = this.currentSheetParam.getStartRow();
/*     */       }
/* 169 */       addMergedRegionToCurrentSheet(startRow);
/* 170 */       int i = startRow;
/* 171 */       for (; i < this.excelHeadProperty.getRowNum() + startRow; i++) {
/* 172 */         Row row = WorkBookUtil.createRow(this.currentSheet, i);
/* 173 */         if (null != this.afterWriteHandler) {
/* 174 */           this.afterWriteHandler.row(i, row);
/*     */         }
/* 176 */         addOneRowOfHeadDataToExcel(row, this.excelHeadProperty.getHeadByRowNum(i - startRow));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addMergedRegionToCurrentSheet(int startRow) {
/* 182 */     for (CellRange cellRangeModel : this.excelHeadProperty.getCellRangeModels())
/* 183 */       this.currentSheet.addMergedRegion(
/* 185 */         new CellRangeAddress(cellRangeModel.getFirstRow() + startRow, cellRangeModel
/* 184 */         .getLastRow() + startRow, cellRangeModel
/* 185 */         .getFirstCol(), cellRangeModel.getLastCol()));
/*     */   }
/*     */ 
/*     */   private void addOneRowOfHeadDataToExcel(Row row, List<String> headByRowNum)
/*     */   {
/* 190 */     if ((headByRowNum != null) && (headByRowNum.size() > 0))
/* 191 */       for (int i = 0; i < headByRowNum.size(); i++) {
/* 192 */         Cell cell = WorkBookUtil.createCell(row, i, getCurrentHeadCellStyle(), (String)headByRowNum.get(i));
/* 193 */         if (null != this.afterWriteHandler)
/* 194 */           this.afterWriteHandler.cell(i, cell);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void initTableStyle(TableStyle tableStyle)
/*     */   {
/* 201 */     if (tableStyle != null) {
/* 202 */       this.currentHeadCellStyle = StyleUtil.buildCellStyle(this.workbook, tableStyle.getTableHeadFont(), tableStyle
/* 203 */         .getTableHeadBackGroundColor());
/* 204 */       this.currentContentCellStyle = StyleUtil.buildCellStyle(this.workbook, tableStyle.getTableContentFont(), tableStyle
/* 205 */         .getTableContentBackGroundColor());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void cleanCurrentTable() {
/* 210 */     this.excelHeadProperty = null;
/* 211 */     this.currentHeadCellStyle = null;
/* 212 */     this.currentContentCellStyle = null;
/* 213 */     this.currentTable = null;
/*     */   }
/*     */ 
/*     */   public void currentTable(Table table)
/*     */   {
/* 218 */     if ((null == this.currentTable) || (this.currentTable.getTableNo() != table.getTableNo())) {
/* 219 */       cleanCurrentTable();
/* 220 */       this.currentTable = table;
/* 221 */       initExcelHeadProperty(table.getHead(), table.getClazz());
/* 222 */       initTableStyle(table.getTableStyle());
/* 223 */       initTableHead();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ExcelHeadProperty getExcelHeadProperty()
/*     */   {
/* 229 */     return this.excelHeadProperty;
/*     */   }
/*     */ 
/*     */   public boolean needHead() {
/* 233 */     return this.needHead;
/*     */   }
/*     */ 
/*     */   public org.apache.poi.ss.usermodel.Sheet getCurrentSheet() {
/* 237 */     return this.currentSheet;
/*     */   }
/*     */ 
/*     */   public void setCurrentSheet(org.apache.poi.ss.usermodel.Sheet currentSheet) {
/* 241 */     this.currentSheet = currentSheet;
/*     */   }
/*     */ 
/*     */   public String getCurrentSheetName() {
/* 245 */     return this.currentSheetName;
/*     */   }
/*     */ 
/*     */   public void setCurrentSheetName(String currentSheetName) {
/* 249 */     this.currentSheetName = currentSheetName;
/*     */   }
/*     */ 
/*     */   public ExcelTypeEnum getExcelType() {
/* 253 */     return this.excelType;
/*     */   }
/*     */ 
/*     */   public void setExcelType(ExcelTypeEnum excelType) {
/* 257 */     this.excelType = excelType;
/*     */   }
/*     */ 
/*     */   public OutputStream getOutputStream() {
/* 261 */     return this.outputStream;
/*     */   }
/*     */ 
/*     */   public CellStyle getCurrentHeadCellStyle() {
/* 265 */     return this.currentHeadCellStyle == null ? this.defaultCellStyle : this.currentHeadCellStyle;
/*     */   }
/*     */ 
/*     */   public CellStyle getCurrentContentStyle() {
/* 269 */     return this.currentContentCellStyle;
/*     */   }
/*     */ 
/*     */   public Workbook getWorkbook() {
/* 273 */     return this.workbook;
/*     */   }
/*     */ 
/*     */   public com.alibaba.excel.metadata.Sheet getCurrentSheetParam() {
/* 277 */     return this.currentSheetParam;
/*     */   }
/*     */ 
/*     */   public void setCurrentSheetParam(com.alibaba.excel.metadata.Sheet currentSheetParam) {
/* 281 */     this.currentSheetParam = currentSheetParam;
/*     */   }
/*     */ 
/*     */   public Table getCurrentTable() {
/* 285 */     return this.currentTable;
/*     */   }
/*     */ 
/*     */   public void setCurrentTable(Table currentTable) {
/* 289 */     this.currentTable = currentTable;
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.context.WriteContext
 * JD-Core Version:    0.6.0
 */