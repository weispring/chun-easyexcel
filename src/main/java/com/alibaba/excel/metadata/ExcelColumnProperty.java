/*    */ package com.alibaba.excel.metadata;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ExcelColumnProperty
/*    */   implements Comparable<ExcelColumnProperty>
/*    */ {
/*    */   private Field field;
/* 18 */   private int index = 99999;
/*    */ 
/* 22 */   private List<String> head = new ArrayList();
/*    */   private String format;
/*    */ 
/*    */   public String getFormat()
/*    */   {
/* 29 */     return this.format;
/*    */   }
/*    */ 
/*    */   public void setFormat(String format) {
/* 33 */     this.format = format;
/*    */   }
/*    */ 
/*    */   public Field getField() {
/* 37 */     return this.field;
/*    */   }
/*    */ 
/*    */   public void setField(Field field) {
/* 41 */     this.field = field;
/*    */   }
/*    */ 
/*    */   public int getIndex() {
/* 45 */     return this.index;
/*    */   }
/*    */ 
/*    */   public void setIndex(int index) {
/* 49 */     this.index = index;
/*    */   }
/*    */ 
/*    */   public List<String> getHead() {
/* 53 */     return this.head;
/*    */   }
/*    */ 
/*    */   public void setHead(List<String> head) {
/* 57 */     this.head = head;
/*    */   }
/*    */ 
/*    */   public int compareTo(ExcelColumnProperty o) {
/* 61 */     int x = this.index;
/* 62 */     int y = o.getIndex();
/* 63 */     return x == y ? 0 : x < y ? -1 : 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.metadata.ExcelColumnProperty
 * JD-Core Version:    0.6.0
 */