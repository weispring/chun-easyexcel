/*    */ package com.alibaba.excel.util;
/*    */ 
/*    */ public class PositionUtils
/*    */ {
/*    */   public static int getRow(String currentCellIndex)
/*    */   {
/*  9 */     int row = 0;
/* 10 */     if (currentCellIndex != null) {
/* 11 */       String rowStr = currentCellIndex.replaceAll("[A-Z]", "").replaceAll("[a-z]", "");
/* 12 */       row = Integer.parseInt(rowStr) - 1;
/*    */     }
/* 14 */     return row;
/*    */   }
/*    */ 
/*    */   public static int getCol(String currentCellIndex) {
/* 18 */     int col = 0;
/* 19 */     if (currentCellIndex != null)
/*    */     {
/* 21 */       char[] currentIndex = currentCellIndex.replaceAll("[0-9]", "").toCharArray();
/* 22 */       for (int i = 0; i < currentIndex.length; i++) {
/* 23 */         col = (int)(col + (currentIndex[i] - '@') * Math.pow(26.0D, currentIndex.length - i - 1));
/*    */       }
/*    */     }
/* 26 */     return col - 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.util.PositionUtils
 * JD-Core Version:    0.6.0
 */