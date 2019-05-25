/*     */ package com.alibaba.excel.metadata;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Sheet
/*     */ {
/*     */   private int headLineMun;
/*     */   private int sheetNo;
/*     */   private String sheetName;
/*     */   private Class<? extends BaseRowModel> clazz;
/*     */   private List<List<String>> head;
/*     */   private TableStyle tableStyle;
/*  42 */   private Map<Integer, Integer> columnWidthMap = new HashMap();
/*     */ 
/*  47 */   private Boolean autoWidth = Boolean.FALSE;
/*     */ 
/*  52 */   private int startRow = 0;
/*     */ 
/*     */   public Sheet(int sheetNo)
/*     */   {
/*  56 */     this.sheetNo = sheetNo;
/*     */   }
/*     */ 
/*     */   public Sheet(int sheetNo, int headLineMun) {
/*  60 */     this.sheetNo = sheetNo;
/*  61 */     this.headLineMun = headLineMun;
/*     */   }
/*     */ 
/*     */   public Sheet(int sheetNo, int headLineMun, Class<? extends BaseRowModel> clazz) {
/*  65 */     this.sheetNo = sheetNo;
/*  66 */     this.headLineMun = headLineMun;
/*  67 */     this.clazz = clazz;
/*     */   }
/*     */ 
/*     */   public Sheet(int sheetNo, int headLineMun, Class<? extends BaseRowModel> clazz, String sheetName, List<List<String>> head)
/*     */   {
/*  72 */     this.sheetNo = sheetNo;
/*  73 */     this.clazz = clazz;
/*  74 */     this.headLineMun = headLineMun;
/*  75 */     this.sheetName = sheetName;
/*  76 */     this.head = head;
/*     */   }
/*     */ 
/*     */   public List<List<String>> getHead() {
/*  80 */     return this.head;
/*     */   }
/*     */ 
/*     */   public void setHead(List<List<String>> head) {
/*  84 */     this.head = head;
/*     */   }
/*     */ 
/*     */   public Class<? extends BaseRowModel> getClazz() {
/*  88 */     return this.clazz;
/*     */   }
/*     */ 
/*     */   public void setClazz(Class<? extends BaseRowModel> clazz) {
/*  92 */     this.clazz = clazz;
/*  93 */     if (this.headLineMun == 0)
/*  94 */       this.headLineMun = 1;
/*     */   }
/*     */ 
/*     */   public int getHeadLineMun()
/*     */   {
/*  99 */     return this.headLineMun;
/*     */   }
/*     */ 
/*     */   public void setHeadLineMun(int headLineMun) {
/* 103 */     this.headLineMun = headLineMun;
/*     */   }
/*     */ 
/*     */   public int getSheetNo() {
/* 107 */     return this.sheetNo;
/*     */   }
/*     */ 
/*     */   public void setSheetNo(int sheetNo) {
/* 111 */     this.sheetNo = sheetNo;
/*     */   }
/*     */ 
/*     */   public String getSheetName() {
/* 115 */     return this.sheetName;
/*     */   }
/*     */ 
/*     */   public void setSheetName(String sheetName) {
/* 119 */     this.sheetName = sheetName;
/*     */   }
/*     */ 
/*     */   public TableStyle getTableStyle() {
/* 123 */     return this.tableStyle;
/*     */   }
/*     */ 
/*     */   public void setTableStyle(TableStyle tableStyle) {
/* 127 */     this.tableStyle = tableStyle;
/*     */   }
/*     */ 
/*     */   public Map<Integer, Integer> getColumnWidthMap()
/*     */   {
/* 133 */     return this.columnWidthMap;
/*     */   }
/*     */ 
/*     */   public void setColumnWidthMap(Map<Integer, Integer> columnWidthMap) {
/* 137 */     this.columnWidthMap = columnWidthMap;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 142 */     return "Sheet{headLineMun=" + this.headLineMun + ", sheetNo=" + this.sheetNo + ", sheetName='" + this.sheetName + '\'' + ", clazz=" + this.clazz + ", head=" + this.head + ", tableStyle=" + this.tableStyle + ", columnWidthMap=" + this.columnWidthMap + '}';
/*     */   }
/*     */ 
/*     */   public Boolean getAutoWidth()
/*     */   {
/* 154 */     return this.autoWidth;
/*     */   }
/*     */ 
/*     */   public void setAutoWidth(Boolean autoWidth) {
/* 158 */     this.autoWidth = autoWidth;
/*     */   }
/*     */ 
/*     */   public int getStartRow() {
/* 162 */     return this.startRow;
/*     */   }
/*     */ 
/*     */   public void setStartRow(int startRow) {
/* 166 */     this.startRow = startRow;
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.metadata.Sheet
 * JD-Core Version:    0.6.0
 */