/*    */ package com.alibaba.excel.metadata;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.poi.ss.usermodel.CellStyle;
/*    */ 
/*    */ public class BaseRowModel
/*    */ {
/* 17 */   private Map<Integer, CellStyle> cellStyleMap = new HashMap();
/*    */ 
/*    */   public void addStyle(Integer row, CellStyle cellStyle) {
/* 20 */     this.cellStyleMap.put(row, cellStyle);
/*    */   }
/*    */ 
/*    */   public CellStyle getStyle(Integer row) {
/* 24 */     return (CellStyle)this.cellStyleMap.get(row);
/*    */   }
/*    */ 
/*    */   public Map<Integer, CellStyle> getCellStyleMap() {
/* 28 */     return this.cellStyleMap;
/*    */   }
/*    */ 
/*    */   public void setCellStyleMap(Map<Integer, CellStyle> cellStyleMap) {
/* 32 */     this.cellStyleMap = cellStyleMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.metadata.BaseRowModel
 * JD-Core Version:    0.6.0
 */