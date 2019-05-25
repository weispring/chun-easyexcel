/*    */ package com.alibaba.excel.event;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class OneRowAnalysisFinishEvent
/*    */ {
/*    */   private Object data;
/*    */ 
/*    */   public OneRowAnalysisFinishEvent(Object content)
/*    */   {
/* 12 */     this.data = content;
/*    */   }
/*    */ 
/*    */   public OneRowAnalysisFinishEvent(String[] content, int length) {
/* 16 */     if (content != null) {
/* 17 */       List ls = new ArrayList(length);
/* 18 */       for (int i = 0; i <= length; i++) {
/* 19 */         ls.add(content[i]);
/*    */       }
/* 21 */       this.data = ls;
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object getData()
/*    */   {
/* 28 */     return this.data;
/*    */   }
/*    */ 
/*    */   public void setData(Object data) {
/* 32 */     this.data = data;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.event.OneRowAnalysisFinishEvent
 * JD-Core Version:    0.6.0
 */