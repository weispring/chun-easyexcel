/*     */ package com.alibaba.excel.analysis.v07;
/*     */ 
/*     */ import com.alibaba.excel.analysis.BaseSaxAnalyser;
/*     */ import com.alibaba.excel.context.AnalysisContext;
/*     */ import com.alibaba.excel.exception.ExcelAnalysisException;
/*     */ import com.alibaba.excel.metadata.Sheet;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
/*     */ import org.apache.poi.openxml4j.opc.OPCPackage;
/*     */ import org.apache.poi.xssf.eventusermodel.XSSFReader;
/*     */ import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
/*     */ import org.apache.poi.xssf.model.SharedStringsTable;
/*     */ import org.apache.xmlbeans.XmlException;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbook;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbookPr;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorkbookDocument;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorkbookDocument.Factory;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ public class XlsxSaxAnalyser extends BaseSaxAnalyser
/*     */ {
/*     */   private XSSFReader xssfReader;
/*     */   private SharedStringsTable sharedStringsTable;
/*  36 */   private List<SheetSource> sheetSourceList = new ArrayList();
/*     */ 
/*  38 */   private boolean use1904WindowDate = false;
/*     */ 
/*     */   public XlsxSaxAnalyser(AnalysisContext analysisContext) throws IOException, OpenXML4JException, XmlException {
/*  41 */     this.analysisContext = analysisContext;
/*     */ 
/*  43 */     analysisContext.setCurrentRowNum(Integer.valueOf(0));
/*  44 */     this.xssfReader = new XSSFReader(OPCPackage.open(analysisContext.getInputStream()));
/*  45 */     this.sharedStringsTable = this.xssfReader.getSharedStringsTable();
/*     */ 
/*  47 */     InputStream workbookXml = this.xssfReader.getWorkbookData();
/*  48 */     WorkbookDocument ctWorkbook = WorkbookDocument.Factory.parse(workbookXml);
/*  49 */     CTWorkbook wb = ctWorkbook.getWorkbook();
/*  50 */     CTWorkbookPr prefix = wb.getWorkbookPr();
/*  51 */     if (prefix != null) {
/*  52 */       this.use1904WindowDate = prefix.getDate1904();
/*     */     }
/*  54 */     this.analysisContext.setUse1904WindowDate(this.use1904WindowDate);
/*     */ 
/*  58 */     this.sheetSourceList = new ArrayList();
/*  59 */     XSSFReader.SheetIterator ite = (XSSFReader.SheetIterator)this.xssfReader.getSheetsData();
/*  60 */     while (ite.hasNext()) {
/*  61 */       InputStream inputStream = ite.next();
/*  62 */       String sheetName = ite.getSheetName();
/*  63 */       SheetSource sheetSource = new SheetSource(sheetName, inputStream);
/*  64 */       this.sheetSourceList.add(sheetSource);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void execute()
/*     */   {
/*  71 */     Sheet sheetParam = this.analysisContext.getCurrentSheet();
/*     */     int i;
/*  72 */     if ((sheetParam != null) && (sheetParam.getSheetNo() > 0) && (this.sheetSourceList.size() >= sheetParam.getSheetNo())) {
/*  73 */       InputStream sheetInputStream = ((SheetSource)this.sheetSourceList.get(sheetParam.getSheetNo() - 1)).getInputStream();
/*  74 */       parseXmlSource(sheetInputStream);
/*     */     }
/*     */     else {
/*  77 */       i = 0;
/*  78 */       for (SheetSource sheetSource : this.sheetSourceList) {
/*  79 */         i++;
/*  80 */         this.analysisContext.setCurrentSheet(new Sheet(i));
/*  81 */         parseXmlSource(sheetSource.getInputStream());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseXmlSource(InputStream inputStream) {
/*  87 */     InputSource sheetSource = new InputSource(inputStream);
/*     */     try {
/*  89 */       SAXParserFactory saxFactory = SAXParserFactory.newInstance();
/*  90 */       saxFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
/*  91 */       saxFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
/*  92 */       saxFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
/*  93 */       SAXParser saxParser = saxFactory.newSAXParser();
/*  94 */       XMLReader xmlReader = saxParser.getXMLReader();
/*  95 */       ContentHandler handler = new XlsxRowHandler(this, this.sharedStringsTable, this.analysisContext);
/*  96 */       xmlReader.setContentHandler(handler);
/*  97 */       xmlReader.parse(sheetSource);
/*  98 */       inputStream.close();
/*     */     } catch (Exception e) {
/* 100 */       e.printStackTrace();
/* 101 */       throw new ExcelAnalysisException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<Sheet> getSheets()
/*     */   {
/* 107 */     List sheets = new ArrayList();
/* 108 */     int i = 1;
/* 109 */     for (SheetSource sheetSource : this.sheetSourceList) {
/* 110 */       Sheet sheet = new Sheet(i, 0);
/* 111 */       sheet.setSheetName(sheetSource.getSheetName());
/* 112 */       i++;
/* 113 */       sheets.add(sheet);
/*     */     }
/*     */ 
/* 116 */     return sheets;
/*     */   }
/*     */ 
/*     */   class SheetSource {
/*     */     private String sheetName;
/*     */     private InputStream inputStream;
/*     */ 
/*     */     public SheetSource(String sheetName, InputStream inputStream) {
/* 126 */       this.sheetName = sheetName;
/* 127 */       this.inputStream = inputStream;
/*     */     }
/*     */ 
/*     */     public String getSheetName() {
/* 131 */       return this.sheetName;
/*     */     }
/*     */ 
/*     */     public void setSheetName(String sheetName) {
/* 135 */       this.sheetName = sheetName;
/*     */     }
/*     */ 
/*     */     public InputStream getInputStream() {
/* 139 */       return this.inputStream;
/*     */     }
/*     */ 
/*     */     public void setInputStream(InputStream inputStream) {
/* 143 */       this.inputStream = inputStream;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.analysis.v07.XlsxSaxAnalyser
 * JD-Core Version:    0.6.0
 */