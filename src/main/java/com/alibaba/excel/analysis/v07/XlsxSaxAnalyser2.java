/*     */ package com.alibaba.excel.analysis.v07;
/*     */ 
/*     */ import com.alibaba.excel.analysis.BaseSaxAnalyser;
/*     */ import com.alibaba.excel.context.AnalysisContext;
/*     */ import com.alibaba.excel.exception.ExcelAnalysisException;
/*     */ import com.alibaba.excel.metadata.Sheet;
/*     */ import com.alibaba.excel.util.FileUtil;
/*     */ import com.alibaba.excel.util.StringUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
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
/*     */ public class XlsxSaxAnalyser2 extends BaseSaxAnalyser
/*     */ {
/*     */   private XSSFReader xssfReader;
/*     */   private SharedStringsTable sharedStringsTable;
/*  42 */   private List<SheetSource> sheetSourceList = new ArrayList();
/*     */ 
/*  44 */   private boolean use1904WindowDate = false;
/*     */   private String path;
/*     */   private File tmpFile;
/*     */ 
/*     */   public XlsxSaxAnalyser2(AnalysisContext analysisContext, String tmpPath)
/*     */     throws IOException, OpenXML4JException, XmlException
/*     */   {
/*  50 */     this.analysisContext = analysisContext;
/*  51 */     this.path = tmpPath;
/*  52 */     if (StringUtils.isEmpty(this.path)) {
/*  53 */       this.path = XMLTempFile.createPath();
/*     */     }
/*  55 */     this.tmpFile = new File(XMLTempFile.getTmpFilePath(this.path));
/*  56 */     createTmpFile();
/*     */ 
/*  58 */     analysisContext.setCurrentRowNum(Integer.valueOf(0));
/*  59 */     this.xssfReader = new XSSFReader(OPCPackage.open(this.tmpFile));
/*  60 */     this.sharedStringsTable = this.xssfReader.getSharedStringsTable();
/*     */ 
/*  62 */     InputStream workbookXml = this.xssfReader.getWorkbookData();
/*  63 */     WorkbookDocument ctWorkbook = WorkbookDocument.Factory.parse(workbookXml);
/*  64 */     CTWorkbook wb = ctWorkbook.getWorkbook();
/*  65 */     CTWorkbookPr prefix = wb.getWorkbookPr();
/*  66 */     if (prefix != null) {
/*  67 */       this.use1904WindowDate = prefix.getDate1904();
/*     */     }
/*  69 */     this.analysisContext.setUse1904WindowDate(this.use1904WindowDate);
/*     */ 
/*  72 */     this.sheetSourceList = new ArrayList();
/*  73 */     XSSFReader.SheetIterator ite = (XSSFReader.SheetIterator)this.xssfReader.getSheetsData();
/*  74 */     while (ite.hasNext()) {
/*  75 */       InputStream inputStream = ite.next();
/*  76 */       String sheetName = ite.getSheetName();
/*  77 */       SheetSource sheetSource = new SheetSource(sheetName, inputStream);
/*  78 */       this.sheetSourceList.add(sheetSource);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createTmpFile() throws FileNotFoundException {
/*  83 */     FileUtil.writeFile(this.tmpFile, this.analysisContext.getInputStream());
/*     */   }
/*     */ 
/*     */   protected void execute()
/*     */   {
/*  88 */     Sheet sheetParam = this.analysisContext.getCurrentSheet();
/*     */     int i;
/*  89 */     if ((sheetParam != null) && (sheetParam.getSheetNo() > 0) && 
/*  90 */       (this.sheetSourceList
/*  90 */       .size() >= sheetParam.getSheetNo()))
/*     */     {
/*  92 */       InputStream sheetInputStream = ((SheetSource)this.sheetSourceList.get(sheetParam.getSheetNo() - 1))
/*  92 */         .getInputStream();
/*  93 */       parseXmlSource(sheetInputStream);
/*     */     }
/*     */     else {
/*  96 */       i = 0;
/*  97 */       for (SheetSource sheetSource : this.sheetSourceList) {
/*  98 */         i++;
/*  99 */         this.analysisContext.setCurrentSheet(new Sheet(i));
/* 100 */         parseXmlSource(sheetSource.getInputStream());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseXmlSource(InputStream inputStream) {
/* 106 */     InputSource sheetSource = new InputSource(inputStream);
/*     */     try {
/* 108 */       SAXParserFactory saxFactory = SAXParserFactory.newInstance();
/* 109 */       saxFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
/* 110 */       saxFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
/* 111 */       saxFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
/* 112 */       SAXParser saxParser = saxFactory.newSAXParser();
/* 113 */       XMLReader xmlReader = saxParser.getXMLReader();
/* 114 */       ContentHandler handler = new XlsxRowHandler(this, this.sharedStringsTable, this.analysisContext);
/* 115 */       xmlReader.setContentHandler(handler);
/* 116 */       xmlReader.parse(sheetSource);
/* 117 */       inputStream.close();
/*     */     } catch (Exception e) {
/* 119 */       e.printStackTrace();
/* 120 */       throw new ExcelAnalysisException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<Sheet> getSheets()
/*     */   {
/* 126 */     List sheets = new ArrayList();
/* 127 */     int i = 1;
/* 128 */     for (SheetSource sheetSource : this.sheetSourceList) {
/* 129 */       Sheet sheet = new Sheet(i, 0);
/* 130 */       sheet.setSheetName(sheetSource.getSheetName());
/* 131 */       i++;
/* 132 */       sheets.add(sheet);
/*     */     }
/*     */ 
/* 135 */     return sheets;
/*     */   }
/*     */ 
/*     */   class SheetSource {
/*     */     private String sheetName;
/*     */     private InputStream inputStream;
/*     */ 
/*     */     public SheetSource(String sheetName, InputStream inputStream) {
/* 145 */       this.sheetName = sheetName;
/* 146 */       this.inputStream = inputStream;
/*     */     }
/*     */ 
/*     */     public String getSheetName() {
/* 150 */       return this.sheetName;
/*     */     }
/*     */ 
/*     */     public void setSheetName(String sheetName) {
/* 154 */       this.sheetName = sheetName;
/*     */     }
/*     */ 
/*     */     public InputStream getInputStream() {
/* 158 */       return this.inputStream;
/*     */     }
/*     */ 
/*     */     public void setInputStream(InputStream inputStream) {
/* 162 */       this.inputStream = inputStream;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.analysis.v07.XlsxSaxAnalyser2
 * JD-Core Version:    0.6.0
 */