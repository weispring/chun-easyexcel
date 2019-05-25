/*    */ package com.alibaba.excel.analysis;
/*    */ 
/*    */ import com.alibaba.excel.context.AnalysisContext;
/*    */ import com.alibaba.excel.event.AnalysisEventListener;
/*    */ import com.alibaba.excel.event.AnalysisEventRegisterCenter;
/*    */ import com.alibaba.excel.event.OneRowAnalysisFinishEvent;
/*    */ import com.alibaba.excel.metadata.Sheet;
/*    */ import com.alibaba.excel.util.TypeUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public abstract class BaseSaxAnalyser
/*    */   implements AnalysisEventRegisterCenter, ExcelAnalyser
/*    */ {
/*    */   protected AnalysisContext analysisContext;
/* 22 */   private LinkedHashMap<String, AnalysisEventListener> listeners = new LinkedHashMap();
/*    */ 
/*    */   protected abstract void execute();
/*    */ 
/*    */   public void appendLister(String name, AnalysisEventListener listener)
/*    */   {
/* 32 */     if (!this.listeners.containsKey(name))
/* 33 */       this.listeners.put(name, listener);
/*    */   }
/*    */ 
/*    */   public void analysis(Sheet sheetParam)
/*    */   {
/* 39 */     execute();
/*    */   }
/*    */ 
/*    */   public void analysis()
/*    */   {
/* 44 */     execute();
/*    */   }
/*    */ 
/*    */   public void cleanAllListeners()
/*    */   {
/* 51 */     this.listeners = new LinkedHashMap();
/*    */   }
/*    */ 
/*    */   public void notifyListeners(OneRowAnalysisFinishEvent event)
/*    */   {
/* 56 */     this.analysisContext.setCurrentRowAnalysisResult(event.getData());
/*    */ 
/* 58 */     if (this.analysisContext.getCurrentRowNum().intValue() < this.analysisContext.getCurrentSheet().getHeadLineMun()) {
/* 59 */       if (this.analysisContext.getCurrentRowNum().intValue() <= this.analysisContext.getCurrentSheet().getHeadLineMun() - 1)
/* 60 */         this.analysisContext.buildExcelHeadProperty(null, 
/* 61 */           (List)this.analysisContext
/* 61 */           .getCurrentRowAnalysisResult());
/*    */     }
/*    */     else {
/* 64 */       List content = converter((List)event.getData());
/*    */ 
/* 66 */       this.analysisContext.setCurrentRowAnalysisResult(content);
/* 67 */       if (this.listeners.size() == 1) {
/* 68 */         this.analysisContext.setCurrentRowAnalysisResult(content);
/*    */       }
/*    */ 
/* 71 */       for (Map.Entry entry : this.listeners.entrySet())
/* 72 */         ((AnalysisEventListener)entry.getValue()).invoke(this.analysisContext.getCurrentRowAnalysisResult(), this.analysisContext);
/*    */     }
/*    */   }
/*    */ 
/*    */   private List<String> converter(List<String> data)
/*    */   {
/* 78 */     List list = new ArrayList();
/* 79 */     if (data != null) {
/* 80 */       for (String str : data) {
/* 81 */         list.add(TypeUtil.formatFloat(str));
/*    */       }
/*    */     }
/* 84 */     return list;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.analysis.BaseSaxAnalyser
 * JD-Core Version:    0.6.0
 */