/*     */ package com.alibaba.excel.analysis.v07;
/*     */ 
/*     */ import com.alibaba.excel.annotation.FieldType;
/*     */ import com.alibaba.excel.context.AnalysisContext;
/*     */ import com.alibaba.excel.event.AnalysisEventRegisterCenter;
/*     */ import com.alibaba.excel.event.OneRowAnalysisFinishEvent;
/*     */ import com.alibaba.excel.util.PositionUtils;
/*     */ import java.util.Arrays;
/*     */ import org.apache.poi.xssf.model.SharedStringsTable;
/*     */ import org.apache.poi.xssf.usermodel.XSSFRichTextString;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ public class XlsxRowHandler extends DefaultHandler
/*     */ {
/*     */   private String currentCellIndex;
/*     */   private FieldType currentCellType;
/*     */   private int curRow;
/*     */   private int curCol;
/*  33 */   private String[] curRowContent = new String[20];
/*     */   private String currentCellValue;
/*     */   private SharedStringsTable sst;
/*     */   private AnalysisContext analysisContext;
/*     */   private AnalysisEventRegisterCenter registerCenter;
/*     */ 
/*     */   public XlsxRowHandler(AnalysisEventRegisterCenter registerCenter, SharedStringsTable sst, AnalysisContext analysisContext)
/*     */   {
/*  45 */     this.registerCenter = registerCenter;
/*  46 */     this.analysisContext = analysisContext;
/*  47 */     this.sst = sst;
/*     */   }
/*     */ 
/*     */   public void startElement(String uri, String localName, String name, Attributes attributes)
/*     */     throws SAXException
/*     */   {
/*  54 */     setTotalRowCount(name, attributes);
/*     */ 
/*  56 */     startCell(name, attributes);
/*     */ 
/*  58 */     startCellValue(name);
/*     */   }
/*     */ 
/*     */   private void startCellValue(String name)
/*     */   {
/*  63 */     if ((name.equals("v")) || (name.equals("t")))
/*     */     {
/*  65 */       this.currentCellValue = "";
/*     */     }
/*     */   }
/*     */ 
/*     */   private void startCell(String name, Attributes attributes) {
/*  70 */     if ("c".equals(name)) {
/*  71 */       this.currentCellIndex = attributes.getValue("r");
/*  72 */       int nextRow = PositionUtils.getRow(this.currentCellIndex);
/*  73 */       if (nextRow > this.curRow) {
/*  74 */         this.curRow = nextRow;
/*     */       }
/*     */ 
/*  77 */       this.analysisContext.setCurrentRowNum(Integer.valueOf(this.curRow));
/*  78 */       this.curCol = PositionUtils.getCol(this.currentCellIndex);
/*     */ 
/*  80 */       String cellType = attributes.getValue("t");
/*  81 */       this.currentCellType = FieldType.EMPTY;
/*  82 */       if ((cellType != null) && (cellType.equals("s")))
/*  83 */         this.currentCellType = FieldType.STRING;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void endCellValue(String name)
/*     */     throws SAXException
/*     */   {
/*  90 */     if (this.curCol >= this.curRowContent.length) {
/*  91 */       this.curRowContent = ((String[])Arrays.copyOf(this.curRowContent, (int)(this.curCol * 1.5D)));
/*     */     }
/*  93 */     if ("v".equals(name))
/*     */     {
/*  95 */       switch (1.$SwitchMap$com$alibaba$excel$annotation$FieldType[this.currentCellType.ordinal()]) {
/*     */       case 1:
/*  97 */         int idx = Integer.parseInt(this.currentCellValue);
/*  98 */         this.currentCellValue = new XSSFRichTextString(this.sst.getEntryAt(idx)).toString();
/*  99 */         this.currentCellType = FieldType.EMPTY;
/*     */       }
/*     */ 
/* 102 */       this.curRowContent[this.curCol] = this.currentCellValue;
/* 103 */     } else if ("t".equals(name)) {
/* 104 */       this.curRowContent[this.curCol] = this.currentCellValue;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void endElement(String uri, String localName, String name) throws SAXException
/*     */   {
/* 110 */     endRow(name);
/* 111 */     endCellValue(name);
/*     */   }
/*     */ 
/*     */   public void characters(char[] ch, int start, int length) throws SAXException
/*     */   {
/* 116 */     this.currentCellValue += new String(ch, start, length);
/*     */   }
/*     */ 
/*     */   private void setTotalRowCount(String name, Attributes attributes)
/*     */   {
/* 121 */     if ("dimension".equals(name)) {
/* 122 */       String d = attributes.getValue("ref");
/* 123 */       String totalStr = d.substring(d.indexOf(":") + 1, d.length());
/* 124 */       String c = totalStr.toUpperCase().replaceAll("[A-Z]", "");
/* 125 */       this.analysisContext.setTotalCount(Integer.valueOf(Integer.parseInt(c)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void endRow(String name)
/*     */   {
/* 131 */     if (name.equals("row")) {
/* 132 */       this.registerCenter.notifyListeners(new OneRowAnalysisFinishEvent(this.curRowContent, this.curCol));
/* 133 */       this.curRowContent = new String[20];
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.analysis.v07.XlsxRowHandler
 * JD-Core Version:    0.6.0
 */