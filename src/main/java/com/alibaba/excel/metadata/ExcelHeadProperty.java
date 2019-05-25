/*     */ package com.alibaba.excel.metadata;
/*     */ 
/*     */ import com.alibaba.excel.annotation.ExcelColumnNum;
/*     */ import com.alibaba.excel.annotation.ExcelProperty;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ExcelHeadProperty
/*     */ {
/*     */   private Class<? extends BaseRowModel> headClazz;
/*  25 */   private List<List<String>> head = new ArrayList();
/*     */ 
/*  30 */   private List<ExcelColumnProperty> columnPropertyList = new ArrayList();
/*     */ 
/*  35 */   private Map<Integer, ExcelColumnProperty> excelColumnPropertyMap1 = new HashMap();
/*     */ 
/*     */   public ExcelHeadProperty(Class<? extends BaseRowModel> headClazz, List<List<String>> head) {
/*  38 */     this.headClazz = headClazz;
/*  39 */     this.head = head;
/*  40 */     initColumnProperties();
/*     */   }
/*     */ 
/*     */   private void initColumnProperties()
/*     */   {
/*  46 */     if (this.headClazz != null) {
/*  47 */       List fieldList = new ArrayList();
/*  48 */       Class tempClass = this.headClazz;
/*     */ 
/*  51 */       while (tempClass != null) {
/*  52 */         fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
/*     */ 
/*  54 */         tempClass = tempClass.getSuperclass();
/*     */       }
/*  56 */       List headList = new ArrayList();
/*  57 */       for (Field f : fieldList) {
/*  58 */         initOneColumnProperty(f);
/*     */       }
/*     */ 
/*  61 */       Collections.sort(this.columnPropertyList);
/*  62 */       if ((this.head == null) || (this.head.size() == 0)) {
/*  63 */         for (ExcelColumnProperty excelColumnProperty : this.columnPropertyList) {
/*  64 */           headList.add(excelColumnProperty.getHead());
/*     */         }
/*  66 */         this.head = headList;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initOneColumnProperty(Field f)
/*     */   {
/*  75 */     ExcelProperty p = (ExcelProperty)f.getAnnotation(ExcelProperty.class);
/*  76 */     ExcelColumnProperty excelHeadProperty = null;
/*  77 */     if (p != null) {
/*  78 */       excelHeadProperty = new ExcelColumnProperty();
/*  79 */       excelHeadProperty.setField(f);
/*  80 */       excelHeadProperty.setHead(Arrays.asList(p.value()));
/*  81 */       excelHeadProperty.setIndex(p.index());
/*  82 */       excelHeadProperty.setFormat(p.format());
/*  83 */       this.excelColumnPropertyMap1.put(Integer.valueOf(p.index()), excelHeadProperty);
/*     */     } else {
/*  85 */       ExcelColumnNum columnNum = (ExcelColumnNum)f.getAnnotation(ExcelColumnNum.class);
/*  86 */       if (columnNum != null) {
/*  87 */         excelHeadProperty = new ExcelColumnProperty();
/*  88 */         excelHeadProperty.setField(f);
/*  89 */         excelHeadProperty.setIndex(columnNum.value());
/*  90 */         excelHeadProperty.setFormat(columnNum.format());
/*  91 */         this.excelColumnPropertyMap1.put(Integer.valueOf(columnNum.value()), excelHeadProperty);
/*     */       }
/*     */     }
/*  94 */     if (excelHeadProperty != null)
/*  95 */       this.columnPropertyList.add(excelHeadProperty);
/*     */   }
/*     */ 
/*     */   public void appendOneRow(List<String> row)
/*     */   {
/* 105 */     for (int i = 0; i < row.size(); i++)
/*     */     {
/*     */       List list;
/* 107 */       if (this.head.size() <= i) {
/* 108 */         List list = new ArrayList();
/* 109 */         this.head.add(list);
/*     */       } else {
/* 111 */         list = (List)this.head.get(0);
/*     */       }
/* 113 */       list.add(row.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   public ExcelColumnProperty getExcelColumnProperty(int columnNum)
/*     */   {
/* 123 */     return (ExcelColumnProperty)this.excelColumnPropertyMap1.get(Integer.valueOf(columnNum));
/*     */   }
/*     */ 
/*     */   public Class getHeadClazz() {
/* 127 */     return this.headClazz;
/*     */   }
/*     */ 
/*     */   public void setHeadClazz(Class headClazz) {
/* 131 */     this.headClazz = headClazz;
/*     */   }
/*     */ 
/*     */   public List<List<String>> getHead() {
/* 135 */     return this.head;
/*     */   }
/*     */ 
/*     */   public void setHead(List<List<String>> head) {
/* 139 */     this.head = head;
/*     */   }
/*     */ 
/*     */   public List<ExcelColumnProperty> getColumnPropertyList() {
/* 143 */     return this.columnPropertyList;
/*     */   }
/*     */ 
/*     */   public void setColumnPropertyList(List<ExcelColumnProperty> columnPropertyList) {
/* 147 */     this.columnPropertyList = columnPropertyList;
/*     */   }
/*     */ 
/*     */   public List<CellRange> getCellRangeModels()
/*     */   {
/* 156 */     List cellRanges = new ArrayList();
/* 157 */     for (int i = 0; i < this.head.size(); i++) {
/* 158 */       List columnValues = (List)this.head.get(i);
/* 159 */       for (int j = 0; j < columnValues.size(); j++) {
/* 160 */         int lastRow = getLastRangNum(j, (String)columnValues.get(j), columnValues);
/* 161 */         int lastColumn = getLastRangNum(i, (String)columnValues.get(j), getHeadByRowNum(j));
/* 162 */         if (((lastRow > j) || (lastColumn > i)) && (lastRow >= 0) && (lastColumn >= 0)) {
/* 163 */           cellRanges.add(new CellRange(j, lastRow, i, lastColumn));
/*     */         }
/*     */       }
/*     */     }
/* 167 */     return cellRanges;
/*     */   }
/*     */ 
/*     */   public List<String> getHeadByRowNum(int rowNum) {
/* 171 */     List l = new ArrayList(this.head.size());
/* 172 */     for (List list : this.head) {
/* 173 */       if (list.size() > rowNum)
/* 174 */         l.add(list.get(rowNum));
/*     */       else {
/* 176 */         l.add(list.get(list.size() - 1));
/*     */       }
/*     */     }
/* 179 */     return l;
/*     */   }
/*     */ 
/*     */   private int getLastRangNum(int j, String value, List<String> values)
/*     */   {
/* 191 */     if (value == null) {
/* 192 */       return -1;
/*     */     }
/* 194 */     if (j > 0) {
/* 195 */       String preValue = (String)values.get(j - 1);
/* 196 */       if (value.equals(preValue)) {
/* 197 */         return -1;
/*     */       }
/*     */     }
/* 200 */     int last = j;
/* 201 */     for (int i = last + 1; i < values.size(); i++) {
/* 202 */       String current = (String)values.get(i);
/* 203 */       if (value.equals(current)) {
/* 204 */         last = i;
/*     */       }
/*     */       else {
/* 207 */         if (i > j) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 212 */     return last;
/*     */   }
/*     */ 
/*     */   public int getRowNum()
/*     */   {
/* 217 */     int headRowNum = 0;
/* 218 */     for (List list : this.head) {
/* 219 */       if ((list != null) && (list.size() > 0) && 
/* 220 */         (list.size() > headRowNum)) {
/* 221 */         headRowNum = list.size();
/*     */       }
/*     */     }
/*     */ 
/* 225 */     return headRowNum;
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.metadata.ExcelHeadProperty
 * JD-Core Version:    0.6.0
 */