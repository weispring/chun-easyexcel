/*    */ package com.alibaba.excel.metadata;
/*    */ 
/*    */ public class IndexValue
/*    */ {
/*    */   private String v_index;
/*    */   private String v_value;
/*    */ 
/*    */   public IndexValue(String v_index, String v_value)
/*    */   {
/* 13 */     this.v_index = v_index;
/* 14 */     this.v_value = v_value;
/*    */   }
/*    */ 
/*    */   public String getV_index() {
/* 18 */     return this.v_index;
/*    */   }
/*    */ 
/*    */   public void setV_index(String v_index) {
/* 22 */     this.v_index = v_index;
/*    */   }
/*    */ 
/*    */   public String getV_value() {
/* 26 */     return this.v_value;
/*    */   }
/*    */ 
/*    */   public void setV_value(String v_value) {
/* 30 */     this.v_value = v_value;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 35 */     return "IndexValue [v_index=" + this.v_index + ", v_value=" + this.v_value + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.metadata.IndexValue
 * JD-Core Version:    0.6.0
 */