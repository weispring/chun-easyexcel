/*    */ package com.alibaba.excel.analysis;
/*    */ 
/*    */ import com.alibaba.excel.analysis.v03.XlsSaxAnalyser;
/*    */ import com.alibaba.excel.analysis.v07.XlsxSaxAnalyser;
/*    */ import com.alibaba.excel.context.AnalysisContext;
/*    */ import com.alibaba.excel.context.AnalysisContextImpl;
/*    */ import com.alibaba.excel.event.AnalysisEventListener;
/*    */ import com.alibaba.excel.exception.ExcelAnalysisException;
/*    */ import com.alibaba.excel.metadata.Sheet;
/*    */ import com.alibaba.excel.modelbuild.ModelBuildEventListener;
/*    */ import com.alibaba.excel.support.ExcelTypeEnum;
/*    */ import java.io.InputStream;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ExcelAnalyserImpl
/*    */   implements ExcelAnalyser
/*    */ {
/*    */   private AnalysisContext analysisContext;
/*    */   private BaseSaxAnalyser saxAnalyser;
/*    */ 
/*    */   public ExcelAnalyserImpl(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom, AnalysisEventListener eventListener, boolean trim)
/*    */   {
/* 27 */     this.analysisContext = new AnalysisContextImpl(inputStream, excelTypeEnum, custom, eventListener, trim);
/*    */   }
/*    */ 
/*    */   private BaseSaxAnalyser getSaxAnalyser()
/*    */   {
/* 32 */     if (this.saxAnalyser != null)
/* 33 */       return this.saxAnalyser;
/*    */     try
/*    */     {
/* 36 */       if (this.analysisContext.getExcelType() != null)
/* 37 */         switch (1.$SwitchMap$com$alibaba$excel$support$ExcelTypeEnum[this.analysisContext.getExcelType().ordinal()]) {
/*    */         case 1:
/* 39 */           this.saxAnalyser = new XlsSaxAnalyser(this.analysisContext);
/* 40 */           break;
/*    */         case 2:
/* 42 */           this.saxAnalyser = new XlsxSaxAnalyser(this.analysisContext);
/*    */         }
/*    */       else
/*    */         try
/*    */         {
/* 47 */           this.saxAnalyser = new XlsxSaxAnalyser(this.analysisContext);
/*    */         } catch (Exception e) {
/* 49 */           if (!this.analysisContext.getInputStream().markSupported()) {
/* 50 */             throw new ExcelAnalysisException("Xls must be available markSupported,you can do like this <code> new BufferedInputStream(new FileInputStream(\"/xxxx\"))</code> ");
/*    */           }
/*    */ 
/* 54 */           this.saxAnalyser = new XlsSaxAnalyser(this.analysisContext);
/*    */         }
/*    */     }
/*    */     catch (Exception e) {
/* 58 */       throw new ExcelAnalysisException("File type error，io must be available markSupported,you can do like this <code> new BufferedInputStream(new FileInputStream(\\\"/xxxx\\\"))</code> \"", e);
/*    */     }
/*    */ 
/* 61 */     return this.saxAnalyser;
/*    */   }
/*    */ 
/*    */   public void analysis(Sheet sheetParam)
/*    */   {
/* 66 */     this.analysisContext.setCurrentSheet(sheetParam);
/* 67 */     analysis();
/*    */   }
/*    */ 
/*    */   public void analysis()
/*    */   {
/* 72 */     BaseSaxAnalyser saxAnalyser = getSaxAnalyser();
/* 73 */     appendListeners(saxAnalyser);
/* 74 */     saxAnalyser.execute();
/* 75 */     this.analysisContext.getEventListener().doAfterAllAnalysed(this.analysisContext);
/*    */   }
/*    */ 
/*    */   public List<Sheet> getSheets()
/*    */   {
/* 80 */     BaseSaxAnalyser saxAnalyser = getSaxAnalyser();
/* 81 */     saxAnalyser.cleanAllListeners();
/* 82 */     return saxAnalyser.getSheets();
/*    */   }
/*    */ 
/*    */   private void appendListeners(BaseSaxAnalyser saxAnalyser) {
/* 86 */     saxAnalyser.cleanAllListeners();
/* 87 */     if ((this.analysisContext.getCurrentSheet() != null) && (this.analysisContext.getCurrentSheet().getClazz() != null)) {
/* 88 */       saxAnalyser.appendLister("model_build_listener", new ModelBuildEventListener());
/*    */     }
/* 90 */     if (this.analysisContext.getEventListener() != null)
/* 91 */       saxAnalyser.appendLister("user_define_listener", this.analysisContext.getEventListener());
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.analysis.ExcelAnalyserImpl
 * JD-Core Version:    0.6.0
 */