/*    */ package com.alibaba.excel.modelbuild;
/*    */ 
/*    */ import com.alibaba.excel.context.AnalysisContext;
/*    */ import com.alibaba.excel.event.AnalysisEventListener;
/*    */ import com.alibaba.excel.exception.ExcelGenerateException;
/*    */ import com.alibaba.excel.metadata.ExcelHeadProperty;
/*    */ import com.alibaba.excel.util.TypeUtil;
/*    */ import java.util.List;
/*    */ import net.sf.cglib.beans.BeanMap;
/*    */ 
/*    */ public class ModelBuildEventListener extends AnalysisEventListener
/*    */ {
/*    */   public void invoke(Object object, AnalysisContext context)
/*    */   {
/* 19 */     if ((context.getExcelHeadProperty() != null) && (context.getExcelHeadProperty().getHeadClazz() != null))
/*    */       try {
/* 21 */         Object resultModel = buildUserModel(context, (List)object);
/* 22 */         context.setCurrentRowAnalysisResult(resultModel);
/*    */       } catch (Exception e) {
/* 24 */         throw new ExcelGenerateException(e);
/*    */       }
/*    */   }
/*    */ 
/*    */   private Object buildUserModel(AnalysisContext context, List<String> stringList) throws Exception
/*    */   {
/* 30 */     ExcelHeadProperty excelHeadProperty = context.getExcelHeadProperty();
/* 31 */     Object resultModel = excelHeadProperty.getHeadClazz().newInstance();
/* 32 */     if (excelHeadProperty == null) {
/* 33 */       return resultModel;
/*    */     }
/* 35 */     BeanMap.create(resultModel).putAll(
/* 36 */       TypeUtil.getFieldValues(stringList, excelHeadProperty, 
/* 36 */       Boolean.valueOf(context
/* 36 */       .use1904WindowDate())));
/* 37 */     return resultModel;
/*    */   }
/*    */ 
/*    */   public void doAfterAllAnalysed(AnalysisContext context)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.modelbuild.ModelBuildEventListener
 * JD-Core Version:    0.6.0
 */