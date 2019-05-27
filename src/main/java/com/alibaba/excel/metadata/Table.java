/*    */ package com.alibaba.excel.metadata;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class Table
/*    */ {
/*    */   private Class<? extends BaseRowModel> clazz;
/*    */   private List<List<String>> head;
/*    */   private int tableNo;
/*    */   private TableStyle tableStyle;
/*    */ 
/*    */   public TableStyle getTableStyle()
/*    */   {
/* 26 */     return this.tableStyle;
/*    */   }
/*    */ 
/*    */   public void setTableStyle(TableStyle tableStyle) {
/* 30 */     this.tableStyle = tableStyle;
/*    */   }
/*    */ 
/*    */   public Table(Integer tableNo) {
/* 34 */     this.tableNo = tableNo.intValue();
/*    */   }
/*    */ 
/*    */   public Class<? extends BaseRowModel> getClazz() {
/* 38 */     return this.clazz;
/*    */   }
/*    */ 
/*    */   public void setClazz(Class<? extends BaseRowModel> clazz) {
/* 42 */     this.clazz = clazz;
/*    */   }
/*    */ 
/*    */   public List<List<String>> getHead() {
/* 46 */     return this.head;
/*    */   }
/*    */ 
/*    */   public void setHead(List<List<String>> head) {
/* 50 */     this.head = head;
/*    */   }
/*    */ 
/*    */   public int getTableNo() {
/* 54 */     return this.tableNo;
/*    */   }
/*    */ 
/*    */   public void setTableNo(int tableNo) {
/* 58 */     this.tableNo = tableNo;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.metadata.Table
 * JD-Core Version:    0.6.0
 */