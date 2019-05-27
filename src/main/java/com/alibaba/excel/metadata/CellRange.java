/*    */ package com.alibaba.excel.metadata;
/*    */ 
/*    */ public class CellRange
/*    */ {
/*    */   private int firstRow;
/*    */   private int lastRow;
/*    */   private int firstCol;
/*    */   private int lastCol;
/*    */ 
/*    */   public CellRange(int firstRow, int lastRow, int firstCol, int lastCol)
/*    */   {
/* 14 */     this.firstRow = firstRow;
/* 15 */     this.lastRow = lastRow;
/* 16 */     this.firstCol = firstCol;
/* 17 */     this.lastCol = lastCol;
/*    */   }
/*    */ 
/*    */   public int getFirstRow() {
/* 21 */     return this.firstRow;
/*    */   }
/*    */ 
/*    */   public void setFirstRow(int firstRow) {
/* 25 */     this.firstRow = firstRow;
/*    */   }
/*    */ 
/*    */   public int getLastRow() {
/* 29 */     return this.lastRow;
/*    */   }
/*    */ 
/*    */   public void setLastRow(int lastRow) {
/* 33 */     this.lastRow = lastRow;
/*    */   }
/*    */ 
/*    */   public int getFirstCol() {
/* 37 */     return this.firstCol;
/*    */   }
/*    */ 
/*    */   public void setFirstCol(int firstCol) {
/* 41 */     this.firstCol = firstCol;
/*    */   }
/*    */ 
/*    */   public int getLastCol() {
/* 45 */     return this.lastCol;
/*    */   }
/*    */ 
/*    */   public void setLastCol(int lastCol) {
/* 49 */     this.lastCol = lastCol;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.metadata.CellRange
 * JD-Core Version:    0.6.0
 */