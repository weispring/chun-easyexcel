/*    */ package com.alibaba.excel.metadata;
/*    */ 
/*    */ public class Font
/*    */ {
/*    */   private String fontName;
/*    */   private short fontHeightInPoints;
/*    */   private boolean bold;
/*    */ 
/*    */   public String getFontName()
/*    */   {
/* 22 */     return this.fontName;
/*    */   }
/*    */ 
/*    */   public void setFontName(String fontName) {
/* 26 */     this.fontName = fontName;
/*    */   }
/*    */ 
/*    */   public short getFontHeightInPoints() {
/* 30 */     return this.fontHeightInPoints;
/*    */   }
/*    */ 
/*    */   public void setFontHeightInPoints(short fontHeightInPoints) {
/* 34 */     this.fontHeightInPoints = fontHeightInPoints;
/*    */   }
/*    */ 
/*    */   public boolean isBold() {
/* 38 */     return this.bold;
/*    */   }
/*    */ 
/*    */   public void setBold(boolean bold) {
/* 42 */     this.bold = bold;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.metadata.Font
 * JD-Core Version:    0.6.0
 */