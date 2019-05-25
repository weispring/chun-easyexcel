/*    */ package com.alibaba.excel.metadata;
/*    */ 
/*    */ import org.apache.poi.ss.usermodel.IndexedColors;
/*    */ 
/*    */ public class TableStyle
/*    */ {
/*    */   private IndexedColors tableHeadBackGroundColor;
/*    */   private Font tableHeadFont;
/*    */   private Font tableContentFont;
/*    */   private IndexedColors tableContentBackGroundColor;
/*    */ 
/*    */   public IndexedColors getTableHeadBackGroundColor()
/*    */   {
/* 31 */     return this.tableHeadBackGroundColor;
/*    */   }
/*    */ 
/*    */   public void setTableHeadBackGroundColor(IndexedColors tableHeadBackGroundColor) {
/* 35 */     this.tableHeadBackGroundColor = tableHeadBackGroundColor;
/*    */   }
/*    */ 
/*    */   public Font getTableHeadFont() {
/* 39 */     return this.tableHeadFont;
/*    */   }
/*    */ 
/*    */   public void setTableHeadFont(Font tableHeadFont) {
/* 43 */     this.tableHeadFont = tableHeadFont;
/*    */   }
/*    */ 
/*    */   public Font getTableContentFont() {
/* 47 */     return this.tableContentFont;
/*    */   }
/*    */ 
/*    */   public void setTableContentFont(Font tableContentFont) {
/* 51 */     this.tableContentFont = tableContentFont;
/*    */   }
/*    */ 
/*    */   public IndexedColors getTableContentBackGroundColor() {
/* 55 */     return this.tableContentBackGroundColor;
/*    */   }
/*    */ 
/*    */   public void setTableContentBackGroundColor(IndexedColors tableContentBackGroundColor) {
/* 59 */     this.tableContentBackGroundColor = tableContentBackGroundColor;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.metadata.TableStyle
 * JD-Core Version:    0.6.0
 */